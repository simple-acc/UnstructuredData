package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.ResourceDownload;
import com.lmt.data.unstructured.entity.search.ResourceDownloadSearch;
import com.lmt.data.unstructured.repository.ResourceDownloadRepository;
import com.lmt.data.unstructured.service.ResourceDownloadService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/25 10:19
 */
@Service("ResourceDownloadServiceImpl")
public class ResourceDownloadServiceImpl implements ResourceDownloadService {

    private Logger logger = LoggerFactory.getLogger(ResourceDownloadServiceImpl.class);

    @Autowired
    private ResourceDownloadRepository resourceDownloadRepository;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public Map save(ResourceDownload resourceDownload) {
        this.resourceDownloadRepository.save(resourceDownload);
        if (null == resourceDownload.getId()){
            logger.error("下载记录保存失败，用户ID={}，资源ID={}。",
                    resourceDownload.getUserId(), resourceDownload.getResourceId());
            return ResultData.newError("下载记录保存失败");
        }
        return ResultData.newOK("");
    }

    @Override
    public int getDownloadNum(String userId) {
        return this.resourceDownloadRepository.countByUserId(userId);
    }

    @Override
    public Map search(ResourceDownloadSearch resourceDownloadSearch) {
        StringBuffer sql = new StringBuffer();
        List<Object> parameters = new ArrayList<>();
        sql.append("SELECT rd.create_time AS createTime, ");
        sql.append("r.designation AS resourceName, ");
        sql.append("cl.designation AS classify, ");
        sql.append("d.designation AS dissertation, ");
        sql.append("(SELECT CASE WHEN COUNT(co.id) >= 1 THEN '未收藏' ELSE '已收藏' END FROM collection AS co ");
        sql.append("WHERE co.obj_id = r.id) AS collected ");
        sql.append("FROM resource_download AS rd, resource AS r, classify AS cl, dissertation AS d ");
        sql.append("WHERE rd.resource_id = r.id AND r.classify_id = cl.id AND r.dissertation_id = d.id ");
        sql.append("AND rd.user_id = ? ");
        parameters.add(resourceDownloadSearch.getUserId());
        if (!StringUtils.isEmpty(resourceDownloadSearch.getDesignation())){
            sql.append("AND r.designation LIKE ? ");
            parameters.add("%" + resourceDownloadSearch.getDesignation() + "%");
        }
        sql.append("ORDER BY rd.create_time DESC ");
        Map<String, Object> result = this.entityManagerQuery.paginationSearch(sql, parameters, resourceDownloadSearch);
        return ResultData.newOK("下载记录查询成功", result);
    }
}
