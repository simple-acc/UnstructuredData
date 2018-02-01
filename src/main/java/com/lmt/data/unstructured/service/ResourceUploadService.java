package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.search.ResourceUploadSearch;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/2/1 13:58
 */
public interface ResourceUploadService {

    /**
     * @apiNote 查询用户上传记录
     * @param resourceUploadSearch 查询条件
     * @return Map
     */
    Map search(ResourceUploadSearch resourceUploadSearch);
}
