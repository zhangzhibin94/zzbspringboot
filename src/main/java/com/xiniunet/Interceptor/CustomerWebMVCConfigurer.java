package com.xiniunet.Interceptor;

import com.xiniunet.domain.ServerSettings;
import com.xiniunet.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截规则配置
 */
@Configuration
public class CustomerWebMVCConfigurer implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor interceptor(){
        return new LoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(interceptor()).addPathPatterns("/*").excludePathPatterns("/login","/","/register",
                    "/send_check_code");//拦截除登录页面和首页外的所有页面
            /*WebMvcConfigurer.super.addInterceptors(registry);*/
    }
}
