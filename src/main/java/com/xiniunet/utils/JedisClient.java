package com.xiniunet.utils;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class JedisClient {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Boolean set(String key,String value){
        if(!StringUtils.isEmpty(key)){
            try {
                redisTemplate.opsForValue().set(key,value);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }
    public String get(String key){
        String value="";
        if(!StringUtils.isEmpty(key)){
            try{
                 value = redisTemplate.opsForValue().get(key);
                return value;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            value = "key cannot be null";
        }
        return value;
    }

    /**
     * 设置过期时间
     * @param key
     * @param mm，时间
     * @param timeUnit，时间单位秒，毫秒，天，时等
     * @return
     */
    public  Boolean expire(String key, long mm, TimeUnit timeUnit){
        Boolean expire = redisTemplate.expire(key, mm, timeUnit);
        return expire;
    }

    /**
     * 获得过期时间
     * @param key
     * @return
     */
    public Long getExpire(String key){
        Long expire = redisTemplate.getExpire(key);
        return expire;
    }
    /**
     * 获得过期时间,带单位
     * @param key
     * @return
     */
    public Long getExpire(String key,TimeUnit timeUnit){
        Long expire = redisTemplate.getExpire(key, timeUnit);
        return expire;
    }
}
