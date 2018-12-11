package com.xiniunet.serviceImpl;

import com.xiniunet.domain.ErrorType;
import com.xiniunet.domain.User;
import com.xiniunet.mapper.UserMapper;
import com.xiniunet.response.RegisterCreateResponse;
import com.xiniunet.service.UserService;
import com.xiniunet.utils.JedisClient;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private JedisClient jedisClient;
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
    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public RegisterCreateResponse register(User user) {
        RegisterCreateResponse response = new RegisterCreateResponse();
        //获取redis中的验证码
        String msg = jedisClient.get("register:" + user.getPhone());
        //匹配用户输入的验证码与redis中的验证码是否相同
        if(StringUtils.isNotEmpty(msg)){
            if(StringUtils.isNotEmpty(user.getCode())){
                if(msg.equalsIgnoreCase(user.getCode())){//匹配成功，则在数据库中插入用户信息
                    long insert = userMapper.insert(user);
                    if(insert==1){
                        response.setUser(user);
                    }else {
                        response.addError(ErrorType.BUSINESS_ERROR,"插入失败，请刷新页面后重试");
                        return response;
                    }

                }else {//匹配失败
                    response.addError(ErrorType.BUSINESS_ERROR,"您输入的验证码错误，请检查");
                    return response;
                }
            }else {
                response.addError(ErrorType.BUSINESS_ERROR,"请输入验证码");
                return response;
            }
        }else {
            response.addError(ErrorType.BUSINESS_ERROR,"验证码已过期，请重新注册");
            return response;
        }
        return response;
    }
}
