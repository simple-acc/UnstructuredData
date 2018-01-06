package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.UserInfo;
import com.lmt.data.unstructured.entity.search.UserInfoSearch;
import com.lmt.data.unstructured.repository.UserInfoRepository;
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.EncryptUtil;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Map save(UserInfo userInfo) {
        String newPassword = EncryptUtil.encrypt(userInfo.getUserPassword());
        String userType = "008001".equals(userInfo.getUserType()) ? "管理员" : "用户";
        userInfo.setUserPassword(newPassword);
        userInfo.setPasswordErrorTime(1);
        userInfo.setStatus("007001");
        UserInfo exitUser = this.userInfoRepository.findByUserName(userInfo.getUserName());
        if (null != exitUser){
            return ResultData.newError("该用户名已存在").toMap();
        }
        this.userInfoRepository.save(userInfo);
        if (null == userInfo.getId()){
            return ResultData.newError(userType + "注册失败").toMap();
        }

        return ResultData.newOK(userType + "注册成功").toMap();
    }

    @Override
    public Map login(UserInfo userInfo, HttpSession session) {
        UserInfo loginUser = this.userInfoRepository.findByUserName(userInfo.getUserName());
        if (null != loginUser){
            String newPassword = EncryptUtil.encrypt(userInfo.getUserPassword());
            // TODO 在判断密码前应该先判断该用户的密码错误次数是否可以冻结用户，密码错误次数大于6
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
            // TODO 密码错误，更新该帐号的密码错误次数，未完成
        }
        return ResultData.newError("密码或用户名错误").toMap();
    }

    @Override
    public Map search(UserInfoSearch userInfoSearch) {
        String keyword = userInfoSearch.getKeyword();
        int currentPage = userInfoSearch.getCurrentPage() - 1;
        int pageSize = userInfoSearch.getPageSize();
        // 获取表中数据的数量
        String countSql = "select count(id) as totalElements from user_info";
        Query countQuery = entityManager.createNativeQuery(countSql);
        Object totalElements = countQuery.getResultList().get(0);
        // 开始查询，拼接SQL查询语句
        Query nativeQuery;
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
        if (!StringUtils.isEmpty(keyword)){
            sql.append("AND ui.user_name LIKE ? AND ui.description LIKE ? ");
            nativeQuery = entityManager.createNativeQuery(sql.toString());
            nativeQuery.setParameter(1, keyword);
            nativeQuery.setParameter(2, keyword);
        } else {
            nativeQuery = entityManager.createNativeQuery(sql.toString());
        }
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        nativeQuery.setFirstResult(currentPage * pageSize);
        nativeQuery.setMaxResults(pageSize);
        List resultList = nativeQuery.getResultList();
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("totalElements", totalElements);
        resultMap.put("content", resultList);
        return ResultData.newOk("查询成功", resultMap).toMap();
    }

    @Override
    public Map delete(List<UserInfo> userInfoList) {
        for (UserInfo userInfo : userInfoList) {
            this.userInfoRepository.delete(userInfo.getId());
        }
        return ResultData.newOK("删除成功").toMap();
    }

    @Override
    public Map freeze(List<UserInfo> userInfoList) {
        UserInfo userInfoFreeze;
        for (UserInfo userInfo : userInfoList) {
            userInfoFreeze = this.userInfoRepository.findOne(userInfo.getId());
            userInfoFreeze.setStatus("007002");
            this.userInfoRepository.save(userInfoFreeze);
        }
        return ResultData.newOK("冻结成功").toMap();
    }

    @Override
    public Map unfreeze(List<UserInfo> userInfoList) {
        UserInfo userInfoUnfreeze;
        for (UserInfo userInfo : userInfoList) {
            userInfoUnfreeze = this.userInfoRepository.findOne(userInfo.getId());
            userInfoUnfreeze.setStatus("007001");
            this.userInfoRepository.save(userInfoUnfreeze);
        }
        return ResultData.newOK("解冻成功").toMap();
    }

    @Override
    public Map resetPassword(List<UserInfo> userInfoList) {
        UserInfo userInfoResetPassword;
        String newPassword = EncryptUtil.encrypt("aaaaaa");
        for (UserInfo userInfo : userInfoList) {
            userInfoResetPassword = this.userInfoRepository.findOne(userInfo.getId());
            userInfoResetPassword.setUserPassword(newPassword);
            this.userInfoRepository.save(userInfoResetPassword);
        }
        return ResultData.newOK("密码重置成功").toMap();
    }
}
