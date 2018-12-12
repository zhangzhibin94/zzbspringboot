package com.xiniunet.service;

import com.xiniunet.domain.User;
import com.xiniunet.request.LoginByTelephoneRequest;
import com.xiniunet.response.LoginResponse;
import com.xiniunet.response.RegisterCreateResponse;

import java.util.List;

public interface UserService {
    long insertUser(User user);
    List<User> findUser(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    RegisterCreateResponse register(User user);

    /**
     * 通过用户名密码登录
     * @param user
     * @return
     */
    LoginResponse loginByUserName(User user);

    /**
     * 通过手机号验证码登录
     * @param request
     * @return
     */
    LoginResponse loginByTelephone(LoginByTelephoneRequest request);

    /**
     * 用户是否存在（判断用户名或手机号是否已经存在）
     * @param user
     * @return
     */
    Long isExistUser(User user);
}
