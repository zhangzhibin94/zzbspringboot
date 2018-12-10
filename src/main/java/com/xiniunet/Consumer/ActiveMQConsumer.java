package com.xiniunet.Consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConsumer {
   /* @JmsListener(destination = "zzb.queue")
    public void consumerQueue(String message){
        System.out.println("zzb.queue"+message);
    }

    @JmsListener(destination = "zzb.topic",containerFactory = "jmsListenerContainerTopic")
    public void consumerTopic(String message){
        System.out.println("zzb.topic消费"+message);
    }*/
}
