package com.lmt.data.unstructured.service.impl;

import com.alibaba.fastjson.JSON;
import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.entity.es.ResourceEs;
import com.lmt.data.unstructured.service.ResourceEsService;
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.EntityUtils;
import com.lmt.data.unstructured.util.FileUtil;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/17 8:52
 */
@Service("ResourceEsServiceImpl")
public class ResourceEsServiceImpl implements ResourceEsService {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private TransportClient client;

    @Override
    public Map saveResourceES(Resource resource, String auditRemark) {
        ResourceEs resourceEs = new ResourceEs();
        BeanUtils.copyProperties(resource, resourceEs, EntityUtils.getNullPropertyNames(resource));
        resourceEs.setId(null);
        resourceEs.setResourceId(resource.getId());
        resourceEs.setAuthor(this.userInfoService.getUserNameById(resource.getAuthorId()));
        resourceEs.setContent(fileUtil.getFileContent(resource.getResourceFileName()));
        resourceEs.setAuditRemark(auditRemark);
        IndexResponse response = client.prepareIndex(UdConstant.ES_INDEX, UdConstant.ES_RESOURCE_TYPE)
                .setSource(JSON.toJSONString(resourceEs), XContentType.JSON)
                .get();
        if (null == resource.getId()){
            return ResultData.newError("返回的es_id为空");
        }
        resource.setEsId(response.getId());
        return ResultData.newOK("数据成功保存到ES");
    }
}
