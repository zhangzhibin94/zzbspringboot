package com.xiniunet.service;

import com.xiniunet.domain.User;

import java.util.List;

public interface UserService {
    long insertUser(User user);
    List<User> findUser(User user);
}
