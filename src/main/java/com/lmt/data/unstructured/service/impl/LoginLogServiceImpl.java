package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.LoginLog;
import com.lmt.data.unstructured.entity.search.LoginLogSearch;
import com.lmt.data.unstructured.repository.LoginLogRepository;
import com.lmt.data.unstructured.service.LoginLogService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 22:50
 */
@Service("LoginLogServiceImpl")
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public void save(LoginLog loginLog) {
        this.loginLogRepository.save(loginLog);
    }

    @Override
    public Map search(LoginLogSearch loginLogSearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ll.password_error_time AS passwordErrorTime, ");
        sql.append("ll.login_time AS loginTime, ");
        sql.append("ll.exit_time AS exitTime, ");
        sql.append("ui.user_name AS userName, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = ll.result) ");
        sql.append("AS result, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = ll.status) ");
        sql.append("AS status, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = ui.user_type) ");
        sql.append("AS userType ");
        sql.append("FROM login_log AS ll, user_info AS ui WHERE ui.id = ll.user_id ");
        if (!StringUtils.isEmpty(loginLogSearch.getKeyword())){
            sql.append("AND ui.user_name LIKE ? ");
            loginLogSearch.setParamsCount(1);
        }
        sql.append("ORDER BY ll.login_time DESC ");
        Map result = entityManagerQuery.paginationSearch(sql, loginLogSearch);
        return ResultData.newOK("日志查询成功", result);
    }
}
