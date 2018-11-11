package com.xiniunet.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class IndexController {
   @RequestMapping(value = "/api/v1/gopage")
   public Object index(){
       return "index";
   }
}
