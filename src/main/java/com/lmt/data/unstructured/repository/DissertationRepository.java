package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Dissertation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:12
 */
public interface DissertationRepository extends JpaRepository<Dissertation, String> {
}
