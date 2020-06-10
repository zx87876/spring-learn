package com.damon.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/phishing")
public class Controller {

    @Autowired
    private PhishingService phishingService;

    @Value("${regular.info}")
    private String regularInfo;

    @Value("${regular.source}")
    private String source;

    @GetMapping("/validateDomain")
    public ViewData validateDomain(String domain) {
        return phishingService.validateDomain(domain);
    }


    @GetMapping("/pushDomain")
    public ViewData pushDomain(String domain) {
        return phishingService.pushDomain(domain);
    }


    @GetMapping("/test")
    public String test() {
        return "The connection is successful,source is:" + source + " and regularInfo is :" + regularInfo;
    }
}
