package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.ResourceUpload;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/2/1 13:57
 */
public interface ResourceUploadRepository extends JpaRepository<ResourceUpload, String> {
}
