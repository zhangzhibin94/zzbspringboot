package com.xiniunet.Interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截规则配置
 */
@Configuration
public class CustomerWebMVCConfigurer implements WebMvcConfigurer {
    /**
     * 如果不在这里先实例化拦截器的话，拦截器中的@Autowired都不可用，因为springBoot先加载interceptor再加载spring容器
     * @return
     */
    @Bean
    public LoginInterceptor interceptor(){
        return new LoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(interceptor()).addPathPatterns("/*").excludePathPatterns("/login","/","/register", "/send_check_code","/login_in");//拦截除登录页面和首页外的所有页面请求
            /*WebMvcConfigurer.super.addInterceptors(registry);*/
    }
}
