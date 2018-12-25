package com.xiniunet.utils;/**
 * Created by zzb on 2018/12/24.
 */

import com.aliyun.openservices.ons.api.*;
import com.xiniunet.domain.constant.MQConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author zzb
 * @create 2018-12-24 15:29
 * @desc ONS工具类
 **/
@Component
public class ONSUtil {
    @Autowired
    private MQConstant mqConstant;
    public  Producer getProducer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, mqConstant.getPRODUCER_ID());
        properties.put(PropertyKeyConst.AccessKey, mqConstant.getACCESS_KEY());
        properties.put(PropertyKeyConst.SecretKey, mqConstant.getSECRET_KEY());
        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();
        return producer;
    }
    private static Message getInstance(String topic, String tag, String body) {
        if (body.equals("")||body == null)
            body = "";
        Message msg = new Message(topic, tag, body.getBytes());
        return msg;
    }

    /**
     * 发送ONS消息
     * @param topic
     * @param tag
     * @param msgStr
     */
    public void sendMessage(String topic, String tag, String msgStr) {
        StringBuilder logsb = new StringBuilder("send OnsMQ Msg:");
        Message msg = getInstance(topic, tag, msgStr);
        String messageId = "";
        Producer producer = this.getProducer();
        SendResult sendResult = producer.send(msg);
        messageId = sendResult.getMessageId();
        if (StringUtils.isNotEmpty(messageId)) {
            logsb.append("[OnsProducer] : " + "{ messageId : " + messageId + " , msgStr :" + msgStr + "}");
        } else {
            logsb.append("[OnsProducer] fail: " + "{ " + " msgStr :" + msgStr + "}");
        }
    }


}
