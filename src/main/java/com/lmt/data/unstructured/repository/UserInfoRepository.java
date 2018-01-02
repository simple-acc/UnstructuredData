package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:16
 */
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, String> {
}
