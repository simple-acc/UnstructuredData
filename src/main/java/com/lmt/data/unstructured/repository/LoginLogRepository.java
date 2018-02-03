package com.lmt.data.unstructured.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.LoginLog;

/**
 * @author MT-Lin
 * @date 2018/1/11 22:49
 */
public interface LoginLogRepository extends JpaRepository<LoginLog, String> {
}
