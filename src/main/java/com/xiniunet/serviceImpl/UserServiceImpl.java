package com.xiniunet.serviceImpl;

import com.xiniunet.domain.ErrorType;
import com.xiniunet.domain.User;
import com.xiniunet.mapper.UserMapper;
import com.xiniunet.response.LoginResponse;
import com.xiniunet.response.RegisterCreateResponse;
import com.xiniunet.service.UserService;
import com.xiniunet.utils.CoreUtil;
import com.xiniunet.utils.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

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
        String msg = jedisClient.get("register:" + user.getTelephone());
        //匹配用户输入的验证码与redis中的验证码是否相同
        if(StringUtils.isNotEmpty(msg)){
            if(StringUtils.isNotEmpty(user.getCode())){
                if(msg.equalsIgnoreCase(user.getCode())){//匹配成功，则在数据库中插入用户信息
                    //密码进行md5加密
                    if(StringUtils.isNotEmpty(user.getPassword())){
                        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                    }else {
                        response.addError(ErrorType.BUSINESS_ERROR,"密码不能为空，请检查");
                        return response;
                    }
                    user.setId(CoreUtil.getId());
                    user.setUserName("zzb");
                    user.setPhone(user.getTelephone());
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
            response.addError(ErrorType.BUSINESS_ERROR,"验证码不正确，请重新注册");
            return response;
        }
        return response;
    }
    /**
     * 通过用户名密码登录
     * @param user
     * @return
     */
    @Override
    public LoginResponse loginByUserName(User user) {
        LoginResponse response = new LoginResponse();
        if(StringUtils.isEmpty(user.getUserName())||StringUtils.isEmpty(user.getPassword())){//用户名或密码为空
            response.addError(ErrorType.EXPECTATION_NULL,"用户名或密码不能为空，请检查");
            return response;
        }else{
            //密码需经过md5加密
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user= userMapper.loginByUserName(user);
            if(user==null){
                response.addError(ErrorType.BUSINESS_ERROR,"用户名或密码错误！");
                return response;
            }else {
                response.setUser(user);
            }
        }
        return response;
    }

    @Override
    public Long isExistUser(User user) {
        if(StringUtils.isNotEmpty(user.getUserName())||user.getPhone()!=null){
            Long result = userMapper.isExistUser(user);
            return result;
        }else {
            return 0l;
        }
    }
}
