package com.xiniunet.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 启动参数设置domain类
 */
@Component
@PropertySource({"classpath:application.properties"})
@ConfigurationProperties
public class ServerSettings {
    /**
     * 应用名称
     */
    @Value("${web.apps.name}")
    private String webName;
    /**
     * 端口号
     */
    @Value("${server.port}")
    private String port;

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
