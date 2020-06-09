package com.damon.service;


import com.trustasia.mpki.common.model.ViewData;
import com.trustasia.mpki.log.CatalogEnum;
import com.trustasia.mpki.log.LogData;
import com.trustasia.mpki.log.LogUtil;
import com.trustasia.phishingservice.redis.RedisClient;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("phishingService")
@RefreshScope
public class PhishingService {

    private static final String PHISHING_DOMAIN_KEY = "PHISHING_DOMAIN_SET";

    @Autowired
    private RedisClient redisClient;

    @Value("${regular.info}")
    private String regularInfo;

    public ViewData validateDomain(String domains) {
        ViewData viewData = new ViewData();
        LogData logData = new LogData();
        List<String> errors = new ArrayList<>();
        long beginAt = System.currentTimeMillis();
        Map<String, Object> logMap = new HashMap<>();
        try {
            logData.setCatalog(CatalogEnum.COMMON_LOGGER);
            logData.setContent("Phishing-service 域名验证");
            logMap.put("request_domain", domains);
            if (StringUtils.isNotBlank(domains)) {
                String[] domainAttr = domains.toLowerCase().split(",");
                if (StringUtils.isNotBlank(regularInfo)) {
                    String[] regularArr = regularInfo.split(",");
                    Map<String, Double> mapSet = new HashMap<>();
                    for (String domain : domainAttr) {
                        if (StringUtils.isNotBlank(domain)) {
                            for (String regular : regularArr) {
                                Pattern p = Pattern.compile(regular);
                                Matcher m = p.matcher(domain);
                                if (m.matches()) {
                                    errors.add(domain + "域名包含 " + regular.replace(".*", "") + " 不支持申请此类型证书!");
                                    mapSet.put(domain, Double.valueOf(System.currentTimeMillis()));
                                    break;
                                }

                            }
                        }
                    }
                    if (errors.size() > 0) {
                        //将违规域名记录进redis中(可以考虑不做此操作)
//                    redisClient.zaddMax5000(PHISHING_DOMAIN_KEY, mapSet);
                        viewData.addErrors(errors);
                        viewData.setCode(ViewData.CODE_VALIDATE_ERROR);
                    }
                }


                //查询redis中 存在的违规域名 进行匹配
                if (viewData.getCode() == 0) {
                    List<String> list = redisClient.haveValue(PHISHING_DOMAIN_KEY, domainAttr);
                    if (list.size() > 0) {
                        for (String str : list) {
                            errors.add(str + "域名包含违规词汇");
                        }
                        viewData.addErrors(errors);
                        viewData.setCode(ViewData.CODE_VALIDATE_ERROR);
                    } else {
                        viewData.success();
                    }
                }
            } else {
                viewData.addError("域名无效");
            }

        } catch (Exception e) {
            viewData.addError("错误信息" + e.toString());
            logMap.put("response_exception_info", e.toString());
        } finally {
            logInfo(beginAt, logMap, logData, viewData);
            return viewData;
        }

    }

    public ViewData pushDomain(String domains) {
        ViewData viewData = new ViewData();
        LogData logData = new LogData();
        long beginAt = System.currentTimeMillis();
        Map<String, Object> logMap = new HashMap<>();
        try {
            logData.setCatalog(CatalogEnum.COMMON_LOGGER);
            logData.setContent("Phishing-service 添加违规域名");
            logMap.put("request_domain", domains);
            if (StringUtils.isNotBlank(domains)) {
                String[] domainAttr = domains.split(",");
                for (String domain : domainAttr) {
                    if (StringUtils.isNotBlank(domain)) {
                        redisClient.zaddMax5000(PHISHING_DOMAIN_KEY, domain, Double.valueOf(System.currentTimeMillis()));
                    }
                }
            } else {
                viewData.addError("域名无效");
            }
        } catch (Exception e) {
            viewData.addError("错误信息" + e.toString());
            logMap.put("response_exception_info", e.toString());
        } finally {
            logInfo(beginAt, logMap, logData, viewData);
            return viewData;
        }
    }

    private void logInfo(long beginAt, Map<String, Object> logMap, LogData logData, ViewData viewData) {
        long endAt = System.currentTimeMillis();
        logMap.put("time", LogUtil.simpleDateFormat.format(new Date()));
        logMap.put("use_time", (endAt - beginAt) / 1000.00);
        logMap.put("response_info", JSONObject.fromObject(viewData));
        logData.setParams(logMap);
        LogUtil.COMMON_LOGGER.info(logData);
    }

}
