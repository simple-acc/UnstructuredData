package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.UserInfo;
import com.lmt.data.unstructured.entity.search.UserInfoSearch;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/5 14:03
 */
public interface UserInfoService {

    /**
     * @apiNote 添加用户
     * @param userInfo 添加的用户
     * @return Map
     */
    Map save(UserInfo userInfo);

    /**
     * @apiNote 用户登录
     * @param userInfo 登陆的用户
     * @return Map
     */
    Map login(UserInfo userInfo, HttpSession session);

    /**
     * @apiNote 搜索用户
     * @param userInfoSearch 搜索条件
     * @return Map
     */
    Map search(UserInfoSearch userInfoSearch);

    /**
     * @apiNote 删除用户
     * @param userInfoList 要删除的用户集合
     * @return Map
     */
    Map delete(List<UserInfo> userInfoList);

    /**
     * @apiNote 冻结账户
     * @param userInfoList 要冻结的账户
     * @return Map
     */
    Map freeze(List<UserInfo> userInfoList);

    /**
     * @apiNote 解冻用户
     * @param userInfoList 要解冻的账户
     * @return Map
     */
    Map unfreeze(List<UserInfo> userInfoList);

    /**
     * @apiNote 重置用户密码
     * @param userInfoList 要重置密码的用户
     * @return Map
     */
    Map resetPassword(List<UserInfo> userInfoList);

    /**
     * @apiNote 根据ID获取用户名
     * @param id 用户ID
     * @return 用户名
     */
    String getUserNameById(String id);

    /**
     * @apiNote 获取用户信息
     * @param tokenId tokenId
     * @return Map
     */
    Map getUserInfo(String tokenId);
}
