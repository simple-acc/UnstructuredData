package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.LoginLog;
import com.lmt.data.unstructured.entity.UserInfo;
import com.lmt.data.unstructured.entity.search.UserInfoSearch;
import com.lmt.data.unstructured.repository.UserInfoRepository;
import com.lmt.data.unstructured.service.CollectionService;
import com.lmt.data.unstructured.service.LoginLogService;
import com.lmt.data.unstructured.service.ResourceDownloadService;
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author MT-Lin
 * @date 2018/1/5 14:03
 */
@Service("UserInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService{

    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ResourceDownloadService resourceDownloadService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private EntityManagerQuery entityManagerQuery;
    
    @Autowired
    private RedisCache redisCache;

    @Override
    public Map save(UserInfo userInfo) {
        String newPassword = EncryptUtil.encrypt(userInfo.getUserPassword());
        String userType = UdConstant.USER_TYPE_ADMIN_CODE.equals(userInfo.getUserType()) ? "管理员" : "用户";
        userInfo.setUserPassword(newPassword);
        userInfo.setPasswordErrorTime(UdConstant.DEFAULT_PASSWORD_ERROR_TIME);
        userInfo.setStatus(UdConstant.COUNT_UNFREEZE_CODE);
        UserInfo exitUser = this.userInfoRepository.findByUserName(userInfo.getUserName());
        if (null != exitUser){
            return ResultData.newError("该用户名已存在");
        }
        this.userInfoRepository.save(userInfo);
        if (null == userInfo.getId()){
            return ResultData.newError(userType + "注册失败");
        }
        return ResultData.newOK(userType + "注册成功");
    }

    @Override
    public Map login(UserInfo userInfo, HttpSession session) {
        UserInfo loginUser = this.userInfoRepository.findByUserName(userInfo.getUserName());
        if (null != loginUser){
            LoginLog loginLog = new LoginLog();
            loginLog.setUserId(loginUser.getId());
            loginLog.setStatus(loginUser.getStatus());
            loginLog.setPasswordErrorTime(loginUser.getPasswordErrorTime());
            // TODO 在判断密码前应该先判断该用户的密码错误次数是否可以冻结用户，密码错误出错5次或者被管理员冻结
            if (loginUser.getPasswordErrorTime() >= UdConstant.PASSWORD_ERROR_TIME
                    || UdConstant.COUNT_FREEZE_CODE.equals(loginUser.getStatus())){
                loginLog.setResult(UdConstant.LOGIN_FAIL);
                this.loginLogService.save(loginLog);
                return ResultData.newError("该帐号已被冻结");
            }
            String newPassword = EncryptUtil.encrypt(userInfo.getUserPassword());
            if (newPassword.equals(loginUser.getUserPassword())){
                // TODO 登陆成功，将用户信息包括tokenId和sessionId缓存到Redis
                UUID uuid = UUID.randomUUID();
                String tokenId = uuid.toString();
                session.setAttribute(UdConstant.USER_LOGIN_EVIDENCE, tokenId);
                loginUser.setTokenId(tokenId);
                loginUser.setSessionId(session.getId());
                redisCache.cacheUserInfo(loginUser);
                // 将密码错误次数改成默认值
                if (loginUser.getPasswordErrorTime() != UdConstant.DEFAULT_PASSWORD_ERROR_TIME){
                    loginUser.setPasswordErrorTime(UdConstant.DEFAULT_PASSWORD_ERROR_TIME);
                    this.userInfoRepository.save(loginUser);
                }
                loginLog.setResult(UdConstant.LOGIN_SUCCESS);
                this.loginLogService.save(loginLog);
                logger.info("用户名：{} ID：{} 登录成功", loginUser.getUserName(), loginUser.getId());
                return ResultData.newOK("登录成功", loginUser.getUserType());
            }
            // TODO 密码错误，更新该帐号的密码错误次数
            loginUser.setPasswordErrorTime(loginUser.getPasswordErrorTime() + 1);
            loginLog.setPasswordErrorTime(loginUser.getPasswordErrorTime());
            loginLog.setResult(UdConstant.LOGIN_FAIL);
            this.userInfoRepository.save(loginUser);
            if (loginUser.getPasswordErrorTime() >= UdConstant.PASSWORD_ERROR_TIME){
                loginUser.setStatus(UdConstant.COUNT_FREEZE_CODE);
                loginLog.setStatus(loginUser.getStatus());
                this.loginLogService.save(loginLog);
                this.userInfoRepository.save(loginUser);
                return ResultData.newError("密码错误次数达到 5 次，该帐号已被冻结，请联系管理员解冻！");
            }
            this.loginLogService.save(loginLog);
        }
        return ResultData.newError("密码或用户名错误");
    }

    @Override
    public Map search(UserInfoSearch userInfoSearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ui.id, ui.birthday, ui.description, ui.email, ");
        sql.append("ui.address_code AS addressCode, ");
        sql.append("ui.user_name AS userName, ");
        sql.append("ui.register_time AS registerTime, ");
        sql.append("ui.phone_number AS phoneNumber, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = ui.profession) ");
        sql.append("AS profession, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = ui.sex) ");
        sql.append("AS sex, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = ui.user_type) ");
        sql.append("AS userType, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = ui.status) ");
        sql.append("AS status ");
        sql.append("FROM user_info AS ui WHERE 1=1 ");
        if (!StringUtils.isEmpty(userInfoSearch.getKeyword())){
            sql.append("AND (ui.user_name LIKE ? OR ui.description LIKE ?) ");
            userInfoSearch.setParamsCount(2);
        }
        Map searchResult = entityManagerQuery.paginationSearch(sql, userInfoSearch);
        return ResultData.newOK("查询成功", searchResult);
    }

    @Override
    public Map delete(List<UserInfo> userInfoList) {
        for (UserInfo userInfo : userInfoList) {
            this.userInfoRepository.delete(userInfo.getId());
        }
        return ResultData.newOK("删除成功");
    }

    @Override
    public Map freeze(List<UserInfo> userInfoList) {
        UserInfo userInfoFreeze;
        for (UserInfo userInfo : userInfoList) {
            userInfoFreeze = this.userInfoRepository.findOne(userInfo.getId());
            userInfoFreeze.setStatus(UdConstant.COUNT_FREEZE_CODE);
            this.userInfoRepository.save(userInfoFreeze);
        }
        return ResultData.newOK("冻结成功");
    }

    @Override
    public Map unfreeze(List<UserInfo> userInfoList) {
        UserInfo userInfoUnfreeze;
        for (UserInfo userInfo : userInfoList) {
            userInfoUnfreeze = this.userInfoRepository.findOne(userInfo.getId());
            userInfoUnfreeze.setStatus(UdConstant.COUNT_UNFREEZE_CODE);
            userInfoUnfreeze.setPasswordErrorTime(UdConstant.DEFAULT_PASSWORD_ERROR_TIME);
            this.userInfoRepository.save(userInfoUnfreeze);
        }
        return ResultData.newOK("解冻成功");
    }

    @Override
    public Map resetPassword(List<UserInfo> userInfoList) {
        UserInfo userInfoResetPassword;
        String newPassword = EncryptUtil.encrypt(UdConstant.DEFAULT_PASSWORD);
        for (UserInfo userInfo : userInfoList) {
            userInfoResetPassword = this.userInfoRepository.findOne(userInfo.getId());
            userInfoResetPassword.setUserPassword(newPassword);
            userInfoResetPassword.setPasswordErrorTime(UdConstant.DEFAULT_PASSWORD_ERROR_TIME);
            userInfoResetPassword.setStatus(UdConstant.COUNT_UNFREEZE_CODE);
            this.userInfoRepository.save(userInfoResetPassword);
        }
        return ResultData.newOK("密码重置成功");
    }

    @Override
    public String getUserNameById(String id) {
        return this.userInfoRepository.findOne(id).getUserName();
    }

    @Override
    public Map getUserInfo(String tokenId) {
        UserInfo userInfo = this.redisCache.getUserInfoFromCache(tokenId);
        Map<String, Object> result = new HashMap<>(3);
        result.put("userName", userInfo.getUserName());
        result.put("downloadNum", this.resourceDownloadService.getDownloadNum(userInfo.getId()));
        result.put("collectNum", this.collectionService.getCollectNum(userInfo.getId()));
        return ResultData.newOK("用户信息获取成功", result);
    }
}
