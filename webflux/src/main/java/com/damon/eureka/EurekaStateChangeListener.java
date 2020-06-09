package com.damon.eureka;

import com.netflix.appinfo.InstanceInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateChangeListener {


    private static final Log log = LogFactory.getLog(com.trustasia.EurekaStateChangeListener.class);

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        log.warn(event.getServerId() + "\t" + event.getAppName() + " 服务下线");
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        log.info(instanceInfo.getAppName() + "--进行注册");
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        log.info(event.getServerId() + "\t" + event.getAppName() + " 服务进行续约");
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        log.info("注册中心 启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        log.info("Eureka Server 启动");
    }

}
