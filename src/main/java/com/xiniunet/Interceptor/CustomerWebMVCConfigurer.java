package com.xiniunet.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截规则配置
 */
@Configuration
public class CustomerWebMVCConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/*").excludePathPatterns("/login","/");//拦截除登录页面和首页外的所有页面
            WebMvcConfigurer.super.addInterceptors(registry);
    }
}
