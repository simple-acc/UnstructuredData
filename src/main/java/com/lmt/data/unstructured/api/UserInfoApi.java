package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.UserInfo;
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

/**
 * @author MT-Lin
 * @date 2018/1/3 8:55
 */
@RestController
@RequestMapping("/UserInfoApi")
public class UserInfoApi {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/register")
    public Map register(@RequestBody UserInfo userInfo){
        userInfo.setStatus("007001");
        userInfo.setUserType("008002");
        return this.userInfoService.register(userInfo);
    }

    @RequestMapping("/login")
    public Map login(@RequestBody UserInfo userInfo, HttpSession session){
        return this.userInfoService.login(userInfo, session);
    }

    @RequestMapping("/test")
    public Map test(@RequestBody UserInfo userInfo, HttpSession session) {
        System.err.println(userInfo);
        UUID uuid = (UUID) session.getAttribute("uuid");
        if (uuid == null){
            uuid = UUID.randomUUID();
        }
        System.err.println("uuid:" + uuid);
        System.err.println("sessionId: " + session.getId());

        return null;
    }
}
