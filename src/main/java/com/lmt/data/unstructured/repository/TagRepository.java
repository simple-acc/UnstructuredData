package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:15
 */
public interface TagRepository extends PagingAndSortingRepository<Tag, String> {
}
