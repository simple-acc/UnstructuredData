package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.UserInfo;
import com.lmt.data.unstructured.entity.search.UserInfoSearch;
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.UdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/3 8:55
 */
@RestController
@RequestMapping("/UserInfoApi")
public class UserInfoApi {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getUserInfo")
    public Map getUserInfo(@RequestBody UserInfoSearch userInfoSearch){
        return this.userInfoService.getUserInfo(userInfoSearch.getTokenId());
    }

    @RequestMapping("/register")
    public Map register(@RequestBody UserInfo userInfo){
        // 用户注册
        userInfo.setUserType(UdConstant.USER_TYPE_USER_CODE);
        return this.userInfoService.save(userInfo);
    }

    @RequestMapping("/login")
    public Map login(@RequestBody UserInfo userInfo, HttpSession session){
        return this.userInfoService.login(userInfo, session);
    }

    @RequestMapping("/search")
    public Map search(@RequestBody UserInfoSearch userInfoSearch) {
        return this.userInfoService.search(userInfoSearch);
    }

    @RequestMapping("/addAdmin")
    public Map addAdmin(@RequestBody UserInfo userInfo){
        // 管理员添加管理员
        userInfo.setUserType(UdConstant.USER_TYPE_ADMIN_CODE);
        return this.userInfoService.save(userInfo);
    }

    @RequestMapping("/delete")
    public Map delete(@RequestBody List<UserInfo> userInfoList){
        return this.userInfoService.delete(userInfoList);
    }

    @RequestMapping("/freeze")
    public Map freeze(@RequestBody List<UserInfo> userInfoList){
        return this.userInfoService.freeze(userInfoList);
    }

    @RequestMapping("/unfreeze")
    public Map unfreeze(@RequestBody List<UserInfo> userInfoList){
        return this.userInfoService.unfreeze(userInfoList);
    }

    @RequestMapping("/resetPassword")
    public Map resetPassword(@RequestBody List<UserInfo> userInfoList){
        return this.userInfoService.resetPassword(userInfoList);
    }
}
