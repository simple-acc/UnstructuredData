package com.lmt.data.unstructured.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.ResourceUpload;
import org.springframework.stereotype.Repository;

/**
 * @author MT-Lin
 * @date 2018/2/1 13:57
 */
@Repository
public interface ResourceUploadRepository extends JpaRepository<ResourceUpload, String> {
}
