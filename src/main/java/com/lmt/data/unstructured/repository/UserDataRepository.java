package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:16
 */
public interface UserDataRepository extends JpaRepository<UserData, String> {
}
