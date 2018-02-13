package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.ResourceComment;
import com.lmt.data.unstructured.entity.search.ResourceCommentSearch;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/02/12 21:51:52
 */
public interface ResourceCommentService {

    /**
     * @apiNote 保存评论内容
     * @param resourceComment 评论内容
     * @return Map
     */
    Map save(ResourceComment resourceComment);

    /**
     * @apiNote 根据资源ID获取资源评论内容
     * @param resourceCommentSearch 查询条件
     * @return Map
     */
    Map getCommentByResourceId(ResourceCommentSearch resourceCommentSearch);
}
