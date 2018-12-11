package com.xiniunet.Interceptor;

import com.xiniunet.domain.User;
import com.xiniunet.utils.CookieUtils;
import com.xiniunet.utils.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisClient jedisClient;
    /*
     * 进入controller层之前拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle init");
        //从拦截器中获取cookie的信息
        String token = CookieUtils.getCookieValue("token");
        if(StringUtils.isNotEmpty(token)){//如果存在token，则取redis中查看是否存在该记录
            jedisClient.get("");
        }
        request.setAttribute("user",new User());
        return HandlerInterceptor.super.preHandle(request, response,handler);
    }
    /*
     * 处理请求完成后视图渲染之前的处理操作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        HandlerInterceptor.super.postHandle(request, response,handler,modelAndView);
    }
    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion()");
        HandlerInterceptor.super.afterCompletion(request, response,handler,ex);
    }
}
