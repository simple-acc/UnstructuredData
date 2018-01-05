package com.lmt.data.unstructured.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author MT-Lin
 * @date 2018/1/5 20:02
 */
@Component
public class GetRedisTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void setNeedRedisTemplate(){
        RedisCache.setRedisTemplate(redisTemplate);
    }
}
