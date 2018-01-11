package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Resource;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 11:26
 */
public interface ResourceService {

    /**
     * @apiNote 保存审核通过的资源
     * @param resource 要保存的系统资源
     * @return Map
     */
    Map save(Resource resource);
}
