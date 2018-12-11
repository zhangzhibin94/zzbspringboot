package com.xiniunet.Interceptor;


import com.xiniunet.domain.ServerSettings;
import com.xiniunet.domain.User;
import com.xiniunet.utils.CookieUtils;
import com.xiniunet.utils.JedisClient;
import com.xiniunet.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private ServerSettings serverSettings;
    /**
     * 进入controller层之前拦截请求
     * sso拦截说明：redis中key为uuid生成的32为token，格式为user:sso:token,
     * value为user对象的json字符串
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("sso init");
        //从拦截器中获取cookie的信息
        String token = CookieUtils.getCookieValue(request,"token");
        //如果cookie存在token，则取redis中查看是否存在与之匹配的记录
        if(StringUtils.isNotEmpty(token)){
            String userStr = jedisClient.get("user:sso:" + token);
            //若redis同样存在该记录，说明此用户已经登录
            if(StringUtils.isNotEmpty(userStr)){
                //将此json串转为user对象
                User user = JsonUtils.string2Obj(userStr, User.class);
                //将user信息存放在request作用域中
                request.setAttribute("user",user);
                //刷新redis中的key的过期时间（每次用户操作都会走此拦截器，所以此过程可以模拟session的过期时间）
                jedisClient.expire("user:sso:" + token,30, TimeUnit.MINUTES);
                return true;
            }
        }
        //重定向到登录页面
        response.sendRedirect("http://"+serverSettings.getDomainName()+"/login?redirectUrl=" + request.getRequestURL());
        return false;
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
