package com.xiniunet.mapper;

import com.xiniunet.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserMapper {
    int insert(@Param("request") User user);

    List<User> find(@Param("request")User user);

    User loginByUserName(@Param("request")User user);

    /**
     * 用户是否存在
     * @param user
     * @return
     */
    Long isExistUser(@Param("request")User user);
}
