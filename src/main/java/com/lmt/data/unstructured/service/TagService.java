package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Tag;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 16:09
 */
public interface TagService {

    /**
     * @apiNote 保存系统资源标签
     * @param tag 要保存的标签
     * @return Map
     */
    Map save(Tag tag);
}
