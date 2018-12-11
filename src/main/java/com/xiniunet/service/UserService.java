package com.xiniunet.service;

import com.xiniunet.domain.User;
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
}
