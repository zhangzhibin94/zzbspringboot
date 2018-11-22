package com.xiniunet;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截器
 */
//@WebFilter(urlPatterns = "/api/*",filterName = "loginFilter")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init SSO-filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if("Jack".equals(request.getParameter("username"))){
            filterChain.doFilter(servletRequest,servletResponse);//放行
        }else {
            response.sendRedirect("/index.html");
            return;
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy SSO-filter");
    }
}
