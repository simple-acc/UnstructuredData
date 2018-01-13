package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.entity.search.ResourceSearch;
import com.lmt.data.unstructured.repository.ResourceRepository;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.FileUtil;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 11:26
 */
@Service("ResourceServiceImpl")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public Map save(Resource resource) {
        if (!fileUtil.existResourceFile(resource.getResourceFileName())){
            return ResultData.newError("该资源文件不存在");
        }
        Resource exist = this.resourceRepository.findByResourceFileName(resource.getResourceFileName());
        if (null != exist){
            return ResultData.newError("该资源文件 ["+resource.getResourceFileName()+"] 在资源中已存在");
        }
        this.resourceRepository.save(resource);
        if (null == resource.getId()){
            return ResultData.newError("资源信息保存失败");
        }
        return ResultData.newOK("资源信息保存成功", resource.getId());
    }

    @Override
    public Map search(ResourceSearch resourceSearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT r.id, r.designation, r.description, ");
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
        if (!StringUtils.isEmpty(resourceSearch.getKeyword())){
            sql.append("AND (r.designation LIKE ? OR r.description LIKE ? ");
            sql.append("OR ui.user_name LIKE ? OR c.designation LIKE ?) ");
            resourceSearch.setParamsCount(4);
        }
        sql.append("ORDER BY r.upload_time DESC, r.designation DESC ");
        Map result = this.entityManagerQuery.paginationSearch("resource", sql, resourceSearch);
        return ResultData.newOK("查询资源成功", result);
    }

    @Override
    public Map modifyDissertation(ResourceSearch resourceSearch) {
        List<Resource> resources = resourceSearch.getResources();
        for (Resource resource : resources) {
            Resource exist = this.resourceRepository.findOne(resource.getId());
            if (null == exist){
                return ResultData.newError("修改的资源不存在，ID：" + resource.getId());
            }
            exist.setDissertationId(resourceSearch.getDissertationId());
            this.resourceRepository.save(exist);
            if (null == exist.getDissertationId()){
                return ResultData.newError("修改专题出错，ID：" + resource.getId());
            }
        }
        return ResultData.newOK("专题修改成功");
    }
}
