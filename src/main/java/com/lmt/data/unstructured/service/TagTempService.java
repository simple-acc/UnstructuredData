package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.TagTemp;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/10 12:23
 */
public interface TagTempService {

    /**
     * @apiNote 保存待审核资源标签
     * @param tagTemp 待审核标签
     * @return Map
     */
    Map save(TagTemp tagTemp);
}
