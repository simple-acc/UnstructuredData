package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/11 22:49
 */
public interface LoginLogRepository extends JpaRepository<LoginLog, String> {
}
