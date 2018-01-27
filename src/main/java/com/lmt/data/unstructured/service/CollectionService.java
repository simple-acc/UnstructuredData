package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Collection;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/25 9:43
 */
public interface CollectionService {

    /**
     * @apiNote 收藏资源
     * @param collection 收藏数据
     * @return Map
     */
    Map save(Collection collection);

    /**
     * @apiNote 判断用户是否收藏过该资源
     * @param userId 用户ID
     * @param resourceId 资源ID
     * @return boolean
     */
    boolean isCollected(String userId, String resourceId);

    /**
     * @apiNote 获取用收藏的资源数量
     * @param userId 用户ID
     * @return int
     */
    int getCollectNum(String userId);

    /**
     * @apiNote 取消收藏
     * @param collection 收藏信息
     * @return Map
     */
    Map delete(Collection collection);
}
