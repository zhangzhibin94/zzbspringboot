package com.xiniunet.controller;

import com.xiniunet.domain.ServerSettings;
import com.xiniunet.domain.User;
import com.xiniunet.service.UserService;
import com.xiniunet.utils.JedisClient;
import com.xiniunet.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController = @Controller + @ResponseBody
@Controller
@EnableAutoConfiguration
public class ReidsController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JedisClient jedisClient;
    @RequestMapping("/test_add_redis")
    public Object addKey(){
        User user = new User();
        user.setId(9993l);
        user.setPassword("zzb123456");
        user.setName("lisi");
        user.setPhone(1323412142l);
        user.setUserName("zhangsan");
        String userJson = JsonUtils.obj2String(user);
        Boolean set = jedisClient.set("springboot:user:"+user.getId(), userJson);
        return true;
    }
    @RequestMapping("/get_redis")
    @ResponseBody
    public Object getKey(){
        String value = jedisClient.get("springboot:user:"+9993l);
        return value;
    }
}
