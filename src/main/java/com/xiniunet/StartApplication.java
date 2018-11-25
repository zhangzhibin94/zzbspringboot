package com.xiniunet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

//一个注解等于以下三个注解
/*@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan*/
@SpringBootApplication
@MapperScan("com.xiniunet.mapper")
public class StartApplication {
    public static void main(String[] args) {

        /*SpringApplication.run(DemoController.class,args);*/
        SpringApplication.run(StartApplication.class,args);
    }
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
