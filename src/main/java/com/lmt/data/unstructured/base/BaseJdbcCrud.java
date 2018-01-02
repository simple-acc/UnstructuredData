package com.lmt.data.unstructured.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author MT-Lin
 * @date 2018/1/2 12:12
 */
public class BaseJdbcCrud {

    @Autowired
    private JdbcTemplate jdbcTemplate;


}
