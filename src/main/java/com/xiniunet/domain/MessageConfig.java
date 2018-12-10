package com.xiniunet.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class MessageConfig {
    @Value("${aliyun.message.product}")
    private  String product  ;//你的accessKeyId,参考本文档步骤2
    @Value("${aliyun.message.domian}")
    private  String domain  ;
    //替换成你的AK
    @Value("${oss.keyid}")
    private  String accessKeyId  ;//你的accessKeyId,参考本文档步骤2
    @Value("${oss.keysecret}")
    private  String accessKeySecret ;
    @Value("${aliyun.message.signName}")
    private String signName;
    @Value("${aliyun.message.templateName}")
    private String templateCode;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
