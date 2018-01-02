package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.ResourceDownload;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:14
 */
public interface ResourceDownloadRepository extends PagingAndSortingRepository<ResourceDownload, String> {
}
