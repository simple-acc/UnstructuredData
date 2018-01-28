package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Collection;
import com.lmt.data.unstructured.entity.search.CollectionSearch;

import java.util.List;
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

    /**
     * @apiNote 搜索个人收藏
     * @param collectionSearch 搜索条件
     * @return Map
     */
    Map search(CollectionSearch collectionSearch);

    /**
     * @apiNote 更新个人收藏
     * @param collection 更新的收藏信息
     * @return Map
     */
    Map update(Collection collection);

    /**
     * @apiNote 批量取消收藏资源
     * @param collections 取消收藏的数据
     * @return Map
     */
    Map delete(List<Collection> collections);
}
