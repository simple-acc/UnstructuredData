package com.lmt.data.unstructured.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.ResourceUpload;

/**
 * @author MT-Lin
 * @date 2018/2/1 13:57
 */
public interface ResourceUploadRepository extends JpaRepository<ResourceUpload, String> {
}
