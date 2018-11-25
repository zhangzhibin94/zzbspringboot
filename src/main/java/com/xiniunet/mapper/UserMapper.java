package com.xiniunet.mapper;

import com.xiniunet.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserMapper {
    long insert(@Param("request") User user);

    List<User> find(@Param("request")User user);
}
