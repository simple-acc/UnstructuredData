package com.lmt.data.unstructured.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/2 12:12
 */
public class BaseJdbcFind {

    @Autowired
    private EntityManager entityManager;

    public List<Map<String, Object>> find(){
        return null;
    }
}
