package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.ResourceTemp;
import com.lmt.data.unstructured.entity.search.ResourceTempSearch;
import com.lmt.data.unstructured.repository.ResourceTempRepository;
import com.lmt.data.unstructured.service.ResourceTempService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/9 21:09
 */
@Service("ResourceTempServiceImpl")
public class ResourceTempServiceImpl implements ResourceTempService {

    @Autowired
    private ResourceTempRepository resourceTempRepository;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public Map save(ResourceTemp resourceTemp) {
        ResourceTemp exist = this.resourceTempRepository.findByMd5(resourceTemp.getMd5());
        if (null != exist){
            return ResultData.newError("该文件已存在");
        }
        this.resourceTempRepository.save(resourceTemp);
        if (null == resourceTemp.getId()){
            return ResultData.newError("资源信息保存失败");
        }
        return ResultData.newOK("资源上传成功，请等待审核", resourceTemp.getId());
    }

    @Override
    public Map search(ResourceTempSearch resourceTempSearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT rt.id, rt.author, rt.designation, rt.description, ");
        sql.append("rt.resource_size AS resourceSize, ");
        sql.append("rt.create_time AS createTime, ");
        sql.append("(SELECT c.designation FROM classify AS c WHERE c.id = rt.classify_id) ");
        sql.append("AS classify, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = rt.resource_type) ");
        sql.append("AS resourceType, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = a.operation) ");
        sql.append("AS operation ");
        sql.append("FROM resource_temp AS rt, ");
        sql.append("audit AS a WHERE rt.id = a.obj_id ");
        sql.append("AND a.status = '005003' ");
        if (!StringUtils.isEmpty(resourceTempSearch.getKeyword())){
            sql.append("AND rt.author LIKE ? AND rt.designation LIKE ? AND rt.description LIKE ? ");
            resourceTempSearch.setParamsCount(3);
        }
        Map result = this.entityManagerQuery.paginationSearch("resource_temp", sql, resourceTempSearch);
        return ResultData.newOK("查询待审核资源能成功", result);
    }
}
