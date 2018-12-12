package com.xiniunet.mapper;

import com.xiniunet.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * 添加用户
     * @param user
     * @return
     */
    long insert(@Param("request") User user);

    /**
     * 查询用户
     * @param user
     * @return
     */
    List<User> find(@Param("request")User user);

    /**
     * 通过用户名密码登录
     * @param user
     * @return
     */
    User loginByUserName(@Param("request")User user);

    /**
     * 用户是否存在
     * @param user
     * @return
     */
    Long isExistUser(@Param("request")User user);

    /**
     * 删除用户
     * @param user
     * @return
     */
    long delete(@Param("request")User user);

    /**
     * 通过用户名密码登录
     * @param user
     * @return
     */
    User loginByTelephone(@Param("request")User user);
}
