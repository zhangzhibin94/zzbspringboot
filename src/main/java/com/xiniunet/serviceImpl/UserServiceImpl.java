package com.xiniunet.serviceImpl;

import com.xiniunet.domain.User;
import com.xiniunet.mapper.UserMapper;
import com.xiniunet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)//事务隔离级别
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public long insertUser(User user) {
        long insert = userMapper.insert(user);
        return insert;
    }

    @Override
    public List<User> findUser(User user) {
        List<User> users = userMapper.find(user);
        return users;
    }
}
