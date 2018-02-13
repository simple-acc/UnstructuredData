package com.lmt.data.unstructured.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * @author MT-Lin
 * @date 2018/1/11 22:49
 */
@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, String> {
}
