package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.DigitalDictionary;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:11
 */
public interface DigitalDictionaryRepository extends PagingAndSortingRepository<DigitalDictionary, String> {
}
