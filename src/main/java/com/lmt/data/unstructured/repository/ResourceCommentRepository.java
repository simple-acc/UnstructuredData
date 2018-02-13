package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.ResourceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/02/12 21:42:05
 */
@Repository
public interface ResourceCommentRepository extends JpaRepository<ResourceComment, String> {

    /**
     * @apiNote 根据资源ID获取评论列表
     * @param resourceId 资源ID
     * @return List
     */
    List<ResourceComment> findByResourceId(String resourceId);
}
