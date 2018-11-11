package com.xiniunet;

import com.xiniunet.controller.DemoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//一个注解等于以下三个注解
/*@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan*/
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoController.class,args);
    }
}
