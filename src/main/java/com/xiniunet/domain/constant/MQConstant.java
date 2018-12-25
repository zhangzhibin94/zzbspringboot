package com.xiniunet.domain.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration

public class   MQConstant {
    //ons配置文件
    @Value("${zzb.ons.topic}")
    private  String TOPIC;
    @Value("${zzb.ons.topic}")
    private   String TAG = "*";
    @Value("${zzb.ons.producerId}")
    private   String PRODUCER_ID;
    @Value("${zzb.ons.consumerId}")
    private   String CONSUMER_ID ;
    @Value("${oss.keyid}")
    private   String ACCESS_KEY;
    @Value("${oss.keysecret}")
    private   String SECRET_KEY;
    @Value("${zzb.ons.ons.address}")
    private   String ONS_ADDRESS;

    public String getTOPIC() {
        return TOPIC;
    }

    public void setTOPIC(String TOPIC) {
        this.TOPIC = TOPIC;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public String getPRODUCER_ID() {
        return PRODUCER_ID;
    }

    public void setPRODUCER_ID(String PRODUCER_ID) {
        this.PRODUCER_ID = PRODUCER_ID;
    }

    public String getCONSUMER_ID() {
        return CONSUMER_ID;
    }

    public void setCONSUMER_ID(String CONSUMER_ID) {
        this.CONSUMER_ID = CONSUMER_ID;
    }

    public String getACCESS_KEY() {
        return ACCESS_KEY;
    }

    public void setACCESS_KEY(String ACCESS_KEY) {
        this.ACCESS_KEY = ACCESS_KEY;
    }

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public String getONS_ADDRESS() {
        return ONS_ADDRESS;
    }

    public void setONS_ADDRESS(String ONS_ADDRESS) {
        this.ONS_ADDRESS = ONS_ADDRESS;
    }
}
