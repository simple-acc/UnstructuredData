package com.lmt.data.unstructured.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.lmt.data.unstructured.base.BaseEntity;
import com.lmt.data.unstructured.base.BaseSearch;
import com.lmt.data.unstructured.entity.UserInfo;

/**
 * @author MT-Lin
 * @date 2018/1/5 18:09
 */
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RedisCache {

	private RedisTemplate redisTemplate;

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private Logger logger = LoggerFactory.getLogger(RedisCache.class);

	/**
	 * @apiNote 将用户信息缓存到Redis
	 * @param userInfo
	 *            要缓存的用户信息
	 */
	public void cacheUserInfo(UserInfo userInfo) {
		ValueOperations<String, UserInfo> userInfoValueOperations = redisTemplate.opsForValue();
		userInfoValueOperations.set(userInfo.getTokenId(), userInfo);
	}

	/**
	 * @apiNote 从Redis缓存中获取用户信息
	 * @param tokenId
	 *            用户凭证
	 * @return 通过tokenId获取的用户信息
	 */
	public UserInfo getUserInfoFromCache(String tokenId) {
		ValueOperations<String, UserInfo> userInfoValueOperations = redisTemplate.opsForValue();
		if (null == userInfoValueOperations) {
			logger.error("userInfoValueOperations is NULL");
			return null;
		}
		return userInfoValueOperations.get(tokenId);
	}

	/**
	 * @apiNote 从Redis缓存中获取用户名
	 * @param baseEntity
	 *            BaseEntity
	 * @return 用户名
	 */
	public String getUserName(BaseEntity baseEntity) {
		UserInfo userInfo = this.getUserInfoFromCache(baseEntity.getTokenId());
		if (null == userInfo) {
			return null;
		}
		return userInfo.getUserName();
	}

	/**
	 * @apiNote 从Redis缓存中获取用户名
	 * @param tokenId
	 *            tokenId
	 * @return 用户名
	 */
	public String getUserName(String tokenId) {
		UserInfo userInfo = this.getUserInfoFromCache(tokenId);
		if (null == userInfo) {
			return null;
		}
		return userInfo.getUserName();
	}

	/**
	 * @apiNote 获取用户ID
	 * @param object
	 *            tokenId或者BaseEntity
	 * @return 用户ID或null
	 */
	public String getUserId(Object object) {
		UserInfo userInfo = null;
		if (object instanceof String) {
			userInfo = this.getUserInfoFromCache((String) object);
		}
		if (object instanceof BaseEntity) {
			userInfo = this.getUserInfoFromCache(((BaseEntity) object).getTokenId());
		}
		if (object instanceof BaseSearch) {
			userInfo = this.getUserInfoFromCache(((BaseSearch) object).getTokenId());
		}
		if (null == userInfo) {
			return null;
		}
		return userInfo.getId();
	}
}
