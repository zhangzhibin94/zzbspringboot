package com.xiniunet.domain;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
//全局异常捕获并返回页面
@ControllerAdvice
public class CustomerExtHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map handler (Exception e, HttpServletRequest request){
        Map<String, Object> testHandler = new HashMap<>();
        testHandler.put("status",100);
        testHandler.put("msg",e.getMessage());
        testHandler.put("url",request.getRequestURI());
        return testHandler;
    }
    //自定义异常捕获
    @ExceptionHandler(value = MyException.class)
    public ModelAndView handlerMyException (Exception e, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        modelAndView.addObject("",e.getMessage());
        return modelAndView;
    }
}
