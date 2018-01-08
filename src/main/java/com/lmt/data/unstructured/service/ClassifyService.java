package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Classify;
import com.lmt.data.unstructured.entity.search.ClassifySearch;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/7 7:20
 */
public interface ClassifyService {

    /**
     * @apiNote 获取父类树的选项
     * @return Map
     */
    Map getParentTree();

    /**
     * @apiNote 保存分类
     * @param classify 要保存的分类
     * @return Map
     */
    Map save(Classify classify);

    /**
     * @apiNote 搜索分类
     * @param classifySearch 搜索条件
     * @return Map
     */
    Map search(ClassifySearch classifySearch);
}
