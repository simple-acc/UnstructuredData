package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.ResourceTemp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:13
 */
public interface ResourceTempRepository extends JpaRepository<ResourceTemp, String> {

    /**
     * @apiNote 根据文件MD5值查找
     * @param md5 MD5
     * @return ResourceTemp
     */
    ResourceTemp findByMd5(String md5);
}
