package com.xiniunet.controller;

import com.xiniunet.domain.ServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@RequestMapping("/freemarker")
public class FreemarkerController {
    @Autowired
    ServerSettings serverSettings;
    @GetMapping("/test_freemarker")
    public String testFreemarker(ModelMap modelMap){
        modelMap.addAttribute("setting",serverSettings);
        return "freemarker/test";
    }

}
