package com.xiniunet.serviceImpl;

import com.xiniunet.service.ProdecerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@Service
public class ProviderServiceImpl implements ProdecerService {
   /* @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;
    @Override
    public void sendMessage(Destination destination,final String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    @Override
    public void sendMessage(final String message) {
        jmsMessagingTemplate.convertAndSend(this.queue, message);
    }

    @Autowired
    private Topic topic;
    @Override
    public void publish(String message) {
        jmsMessagingTemplate.convertAndSend(this.topic,message);
    }

    @Override
    public void publish(Destination destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination,message);
    }*/
}
