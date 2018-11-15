package com.xiniunet.controller;

import com.xiniunet.domain.MyException;
import com.xiniunet.domain.ServerSettings;
import com.xiniunet.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@PropertySource({"classpath:application.properties"})
public class IndexController {
    @Value("${server.port}")
    private String path;
   @RequestMapping(value = "/api/v1/gopage")
   public Object index(){
       int i = 1/0;
       return "index";
   }
    @RequestMapping(value = "/api/throw_exception")
   public Object tesetMyException(){
       throw new MyException("500","test throw exception");
   }
}
