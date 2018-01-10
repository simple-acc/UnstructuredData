package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.TagTemp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:15
 */
public interface TagTempRepository extends JpaRepository<TagTemp, String> {

    /**
     * @apiNote 根据待审核资源ID查找标签
     * @param resourceTempId 待审核资源ID
     * @return TagTemp
     */
    TagTemp findByResourceTempId(String resourceTempId);
}
