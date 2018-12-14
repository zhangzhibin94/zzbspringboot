package com.xiniunet;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;


import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.MultipartConfigElement;

//一个注解等于以下三个注解
/*@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan*/
@SpringBootApplication
@MapperScan("com.xiniunet.mapper")
@EnableScheduling//开启定时任务
@EnableAsync//开启异步
@EnableJms//开启消息队列
public class StartApplication {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        /*SpringApplication.run(DemoController.class,args);*/
        SpringApplication.run(StartApplication.class,args);
    }
    //注入新的topic
    /*@Bean
    public Topic topic(){
        return new ActiveMQTopic("zzb.topic");
    }

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("zzb.queue");
    }*/

    /**
     * 同时支持topic和queue
     * @param
     * @return
     */
   /* @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }*/

    //设置文件上传的最大占用空间
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大值
        factory.setMaxFileSize(DataSize.ofKilobytes(10240l));
        //总上传数据大小
        factory.setMaxRequestSize(DataSize.ofKilobytes(102400l));
        return factory.createMultipartConfig();
    }

}
