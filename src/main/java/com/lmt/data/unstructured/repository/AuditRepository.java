package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:06
 */
public interface AuditRepository extends JpaRepository<Audit, String> {
}
