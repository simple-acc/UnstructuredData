package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:13
 */
public interface ResourceRepository extends JpaRepository<Resource, String> {

    /**
     * @apiNote 根据资源文件名查找
     * @param resourceFileName 资源文件名
     * @return Resource
     */
    Resource findByResourceFileName(String resourceFileName);
}
