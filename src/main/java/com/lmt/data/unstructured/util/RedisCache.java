package com.lmt.data.unstructured.util;

import com.lmt.data.unstructured.base.BaseEntity;
import com.lmt.data.unstructured.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author MT-Lin
 * @date 2018/1/5 18:09
 */
@SuppressWarnings("unchecked")
@Component
public class RedisCache {

    private static RedisTemplate redisTemplate;

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
    }

    /**
     * @apiNote 将用户信息缓存到Redis
     * @param userInfo 要缓存的用户信息
     */
    public static void cacheUserInfo(UserInfo userInfo){
        ValueOperations<String, UserInfo> userInfoValueOperations = redisTemplate.opsForValue();
        userInfoValueOperations.set(userInfo.getTokenId(), userInfo);
    }

    /**
     * @apiNote 从Redis缓存中获取用户信息
     * @param tokenId 用户凭证
     * @return 通过tokenId获取的用户信息
     */
    public static UserInfo getUserInfoFromCache(String tokenId){
        ValueOperations<String, UserInfo> userInfoValueOperations = redisTemplate.opsForValue();
        return userInfoValueOperations.get(tokenId);
    }

    /**
     * @apiNote 从Redis缓存中获取用户名
     * @param baseEntity BaseEntity
     * @return 用户名
     */
    public static String getUserName(BaseEntity baseEntity){
        return RedisCache.getUserInfoFromCache(baseEntity.getTokenId()).getUserName();
    }
}
