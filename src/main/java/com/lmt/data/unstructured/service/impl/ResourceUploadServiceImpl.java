package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.search.ResourceUploadSearch;
import com.lmt.data.unstructured.service.ResourceUploadService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/2/1 13:58
 */
@Service("ResourceUploadServiceImpl")
public class ResourceUploadServiceImpl implements ResourceUploadService {

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public Map search(ResourceUploadSearch resourceUploadSearch) {
        StringBuffer sql = new StringBuffer();
        List<Object> parameters = new ArrayList<>();
        sql.append("SELECT ");
        sql.append("rt.designation AS designation, ");
        sql.append("c.designation AS classify, ");
        sql.append("rt.upload_time AS uploadTime, ");
        sql.append("dd.designation AS auditStatus, ");
        sql.append("a.create_time AS auditCreateTime, ");
        sql.append("a.audit_time AS auditTime ");
        sql.append("FROM resource_temp AS rt, digital_dictionary AS dd, ");
        sql.append("audit AS a, resource_upload AS ru, classify AS c ");
        sql.append("WHERE ru.user_id = ? ");
        parameters.add(resourceUploadSearch.getUserId());
        sql.append("AND ru.resource_temp_id = rt.id AND a.obj_id = rt.id ");
        sql.append("AND a.status = dd.code AND rt.classify_id = c.id ");
        if (!StringUtils.isEmpty(resourceUploadSearch.getDesignation())){
            sql.append("AND rt.designation LIKE ? ");
            parameters.add("%" + resourceUploadSearch.getDesignation() + "%");
        }
        Map<String, Object> result = this.entityManagerQuery.paginationSearch(sql, parameters, resourceUploadSearch);
        return ResultData.newOK("上传记录查询成功", result);
    }
}
