package com.lmt.data.unstructured.util;

import com.lmt.data.unstructured.base.BaseEntity;
import com.lmt.data.unstructured.base.BaseSearch;
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
@Component
@SuppressWarnings("unchecked")
public class RedisCache {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @apiNote 将用户信息缓存到Redis
     * @param userInfo 要缓存的用户信息
     */
    public void cacheUserInfo(UserInfo userInfo){
        ValueOperations<String, UserInfo> userInfoValueOperations = redisTemplate.opsForValue();
        userInfoValueOperations.set(userInfo.getTokenId(), userInfo);
    }

    /**
     * @apiNote 从Redis缓存中获取用户信息
     * @param tokenId 用户凭证
     * @return 通过tokenId获取的用户信息
     */
    public UserInfo getUserInfoFromCache(String tokenId){
        ValueOperations<String, UserInfo> userInfoValueOperations = redisTemplate.opsForValue();
        return userInfoValueOperations.get(tokenId);
    }

    /**
     * @apiNote 从Redis缓存中获取用户名
     * @param baseEntity BaseEntity
     * @return 用户名
     */
    public String getUserName(BaseEntity baseEntity){
        return this.getUserInfoFromCache(baseEntity.getTokenId()).getUserName();
    }

    /**
     * @apiNote 从Redis缓存中获取用户名
     * @param tokenId tokenId
     * @return 用户名
     */
    public String getUserName(String tokenId){
        return this.getUserInfoFromCache(tokenId).getUserName();
    }

    /**
     * @apiNote 获取用户ID
     * @param object tokenId或者BaseEntity
     * @return 用户ID或null
     */
    public String getUserId(Object object){
        if (object instanceof String){
            return this.getUserInfoFromCache((String) object).getId();
        }
        if (object instanceof BaseEntity){
            return this.getUserInfoFromCache(((BaseEntity)object).getTokenId()).getId();
        }
        return null;
    }
}
