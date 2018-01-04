package com.lmt.data.unstructured.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/2 12:12
 */
public class BaseJdbcFind {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> find(){
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT designation FROM digital_dictionary");
        return result;
    }
}
