package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.entity.search.ResourceSearch;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 11:26
 */
public interface ResourceService {

    /**
     * @apiNote 保存审核通过的资源
     * @param resource 要保存的资源
     * @return Map
     */
    Map save(Resource resource);

    /**
     * @apiNote 搜索资源
     * @param resourceSearch 搜索条件
     * @return Map
     */
    Map search(ResourceSearch resourceSearch);

    /**
     * @apiNote 修改资源所属专题
     * @param resourceSearch 修改的数据
     * @return Map
     */
    Map modifyDissertation(ResourceSearch resourceSearch);
}
