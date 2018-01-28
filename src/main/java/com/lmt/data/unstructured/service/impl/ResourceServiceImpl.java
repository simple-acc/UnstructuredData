package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Dissertation;
import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.entity.ResourceTemp;
import com.lmt.data.unstructured.entity.search.ResourceSearch;
import com.lmt.data.unstructured.repository.ResourceRepository;
import com.lmt.data.unstructured.repository.ResourceTempRepository;
import com.lmt.data.unstructured.service.DissertationService;
import com.lmt.data.unstructured.service.ResourceEsService;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.service.TagService;
import com.lmt.data.unstructured.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 11:26
 */
@Service("ResourceServiceImpl")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private TagService tagService;

    @Autowired
    private ResourceEsService resourceEsService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceTempRepository resourceTempRepository;

    @Autowired
    private DissertationService dissertationService;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Autowired
    private FileUtil fileUtil;

    private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Override
    public Map save(Resource resource) {
        if (!fileUtil.existResourceFile(resource.getResourceFileName())) {
            return ResultData.newError("该资源文件不存在");
        }
        Resource exist = this.resourceRepository.findByResourceFileName(resource.getResourceFileName());
        if (null != exist) {
            return ResultData.newError("该资源文件 [" + resource.getResourceFileName() + "] 在资源中已存在");
        }
        this.resourceRepository.save(resource);
        if (null == resource.getId()) {
            return ResultData.newError("资源信息保存失败");
        }
        return ResultData.newOK("资源信息保存成功", resource.getId());
    }

    @Override
    public Map search(ResourceSearch resourceSearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT r.id, r.designation, r.description, ");
        sql.append("r.es_id AS esId, ");
        sql.append("r.resource_file_name AS resourceFileName, ");
        sql.append("r.resource_size AS resourceSize, ");
        sql.append("r.upload_time AS uploadTime, ");
        sql.append("r.download_num AS downloadNum, ");
        sql.append("r.collection_num AS collectionNum, ");
        sql.append("ui.user_name AS author, ");
        sql.append("c.designation AS classify, ");
        sql.append("dd.designation AS resourceType, ");
        sql.append("(SELECT d.designation FROM dissertation AS d WHERE d.id = r.dissertation_id) ");
        sql.append("AS dissertation ");
        sql.append("FROM resource AS r, user_info AS ui, classify AS c, digital_dictionary AS dd ");
        sql.append("WHERE ui.id = r.author_id ");
        sql.append("AND c.id = r.classify_id ");
        sql.append("AND dd.code = r.resource_type ");
        if (!StringUtils.isEmpty(resourceSearch.getKeyword())) {
            sql.append("AND (r.designation LIKE ? OR r.description LIKE ? ");
            sql.append("OR ui.user_name LIKE ? OR c.designation LIKE ?) ");
            resourceSearch.setParamsCount(4);
        }
        sql.append("ORDER BY r.upload_time DESC, r.designation DESC ");
        Map result = this.entityManagerQuery.paginationSearch(sql, resourceSearch);
        return ResultData.newOK("查询资源成功", result);
    }

    @Override
    public Map modifyDissertation(ResourceSearch resourceSearch) {
        List<Resource> resources = resourceSearch.getResources();
        for (Resource resource : resources) {
            Resource exist = this.resourceRepository.findOne(resource.getId());
            if (null == exist) {
                return ResultData.newError("修改的资源不存在，ID：" + resource.getId());
            }
            exist.setDissertationId(resourceSearch.getDissertationId());
            this.resourceRepository.save(exist);
            if (null == exist.getDissertationId()) {
                return ResultData.newError("修改专题出错，ID：" + resource.getId());
            }
        }
        return ResultData.newOK("专题修改成功");
    }

    @Override
    public Map addResourceFromResourceTemp(ResourceTemp resourceTemp, String auditRemark) {
        logger.info("开始将待审核资源 [ID={}]信息复制到资源表", resourceTemp.getId());
        ResourceTemp exist = this.resourceTempRepository.findOne(resourceTemp.getId());
        if (null == exist) {
            return ResultData.newError("审核的资源 [ID=" + resourceTemp.getId() + "] 在数据库中不存在");
        }
        // 复制待审核资源
        Resource resource = new Resource();
        BeanUtils.copyProperties(exist, resource, EntityUtils.getNullPropertyNames(exist));
        String expandedName = exist.getDesignation().substring(exist.getDesignation().lastIndexOf("."));
        String resourceFileName = exist.getId() + expandedName;
        resource.setResourceFileName(resourceFileName);
        resource.setId(null);
        Map result = this.save(resource);
        if (!CheckResult.isOK(result)) {
            return result;
        }
        // 更新待审核资源记录的 resource_id
        exist.setResourceId(resource.getId());
        this.resourceTempRepository.save(exist);
        // 将待审核资源的标签添加到资源标签
        result = this.tagService.addTag(resourceTemp.getId(), resource.getId());
        if (!CheckResult.isOK(result)) {
            return result;
        }
        logger.info("待审核资源 [ID={}] 信息复制结束", resourceTemp.getId());
        result = this.resourceEsService.saveResourceES(resource, auditRemark);
        if (!CheckResult.isOK(result)) {
            return result;
        }
        // 更新es_id
        this.resourceRepository.save(resource);
        return ResultData.newOK("待审核资源 [ID=" + resourceTemp.getId() + "] 添加成功", resource.getId());
    }

    @Override
    public Map getTopFiveByDissertation() {
        Map<Dissertation, List<Object>> dissertationIds = this.dissertationService.getHasResourceDissertationIdsGroup();
        List<Object> result = new ArrayList<>();
        Map<String, Object> temp;
        StringBuffer sql = new StringBuffer();
        for (Dissertation dissertation : dissertationIds.keySet()) {
            List<Object> paramters = dissertationIds.get(dissertation);
            if (paramters.size() == 0) {
                continue;
            }
            sql.append("SELECT r.id, r.designation, ");
            sql.append("r.upload_time AS uploadTime ");
            sql.append("FROM resource AS r ");
            sql.append("WHERE r.dissertation_id IN (");
            for (int i = 0; i < paramters.size(); i++) {
                sql.append("?, ");
            }
            sql.setLength(sql.length() - 2);
            sql.append(") ORDER BY r.upload_time DESC, r.download_num DESC, r.collection_num DESC ");
            temp = new HashMap<>(2);
            temp.put("dissertation", dissertation);
            temp.put("resource", this.entityManagerQuery.nativeSqlSearch(sql, paramters, 6));
            result.add(temp);
            sql.setLength(0);
        }
        return ResultData.newOK("主题前五个热门资源查询成功", result);
    }

    @Override
    public Resource findOneById(String id) {
        return this.resourceRepository.findOne(id);
    }
}
