package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:13
 */
public interface ResourceRepository extends PagingAndSortingRepository<Resource, String>{
}
