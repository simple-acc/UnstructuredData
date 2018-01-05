package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.UserInfo;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/5 14:03
 */
public interface UserInfoService {

    /**
     * @apiNote 用户注册
     * @param userInfo 注册的用户
     * @return Map
     */
    Map register(UserInfo userInfo);

    /**
     * @apiNote 用户登录
     * @param userInfo 登陆的用户
     * @return Map
     */
    Map login(UserInfo userInfo, HttpSession session);
}
