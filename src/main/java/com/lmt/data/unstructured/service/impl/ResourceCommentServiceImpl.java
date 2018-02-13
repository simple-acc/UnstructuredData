package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.ResourceComment;
import com.lmt.data.unstructured.entity.search.ResourceCommentSearch;
import com.lmt.data.unstructured.repository.ResourceCommentRepository;
import com.lmt.data.unstructured.service.ResourceCommentService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("ResourceCommentServiceImpl")
public class ResourceCommentServiceImpl implements ResourceCommentService {

    @Autowired
    private ResourceCommentRepository resourceCommentRepository;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public Map save(ResourceComment resourceComment) {
        this.resourceCommentRepository.save(resourceComment);
        if (null == resourceComment.getId()){
            return ResultData.newError("评论失败");
        }
        return ResultData.newOK("评论成功");
    }

    @Override
    public Map getCommentByResourceId(ResourceCommentSearch resourceCommentSearch) {
        List<Object> parameters = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT rc.content, ");
        sql.append("rc.comment_time AS commentTime, ");
        sql.append("(SELECT ui.user_name FROM user_info AS ui WHERE ui.id = rc.observer_id) AS observer ");
        sql.append("FROM resource_comment AS rc ");
        sql.append("WHERE rc.resource_id = ? ");
        parameters.add(resourceCommentSearch.getResourceId());
        sql.append("GROUP BY rc.comment_time DESC ");
        Map<String, Object> result = this.entityManagerQuery.paginationSearch(sql, parameters, resourceCommentSearch);
        return ResultData.newOK("", result);
    }
}
