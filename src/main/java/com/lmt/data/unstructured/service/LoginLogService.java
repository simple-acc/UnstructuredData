package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.LoginLog;
import com.lmt.data.unstructured.entity.search.LoginLogSearch;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 22:49
 */
public interface LoginLogService {

    /**
     * @apiNote 保存登录日志信息
     * @param loginLog 登陆日志
     */
    void save(LoginLog loginLog);

    /**
     * @apiNote 查询登录日志
     * @param loginLogSearch 查询条件
     * @return Map
     */
    Map search(LoginLogSearch loginLogSearch);
}
