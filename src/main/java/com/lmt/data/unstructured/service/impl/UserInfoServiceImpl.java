package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.UserInfo;
import com.lmt.data.unstructured.repository.UserInfoRepository;
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.EncryptUtil;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

/**
 * @author MT-Lin
 * @date 2018/1/5 14:03
 */
@Service("UserInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Map register(UserInfo userInfo) {
        String newPassword = EncryptUtil.encrypt(userInfo.getUserPassword());
        userInfo.setUserPassword(newPassword);
        UserInfo exitUser = this.userInfoRepository.findByUserName(userInfo.getUserName());
        if (null != exitUser){
            return ResultData.newError("该用户名已存在").toMap();
        }
        this.userInfoRepository.save(userInfo);
        if (null == userInfo.getId()){
            return ResultData.newError("注册失败").toMap();
        }

        return ResultData.newOK("注册成功").toMap();
    }

    @Override
    public Map login(UserInfo userInfo, HttpSession session) {
        UserInfo loginUser = this.userInfoRepository.findByUserName(userInfo.getUserName());
        if (null != loginUser){
            String newPassword = EncryptUtil.encrypt(userInfo.getUserPassword());
            if (newPassword.equals(loginUser.getUserPassword())){
                // TODO 登陆成功，将用户信息包括tokenId和sessionId缓存到Redis
                UUID uuid = UUID.randomUUID();
                String tokenId = uuid.toString();
                session.setAttribute("tokenId", tokenId);
                loginUser.setTokenId(tokenId);
                loginUser.setSessionId(session.getId());
                RedisCache.cacheUserInfo(loginUser);
                return ResultData.newOK("成功登录").toMap();
            }
        }
        return ResultData.newError("密码或用户名错误").toMap();
    }
}
