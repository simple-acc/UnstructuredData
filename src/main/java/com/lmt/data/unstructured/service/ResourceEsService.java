package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Resource;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/17 8:52
 */
public interface ResourceEsService {

    /**
     * @apiNote 资源信息保存到ES
     * @param resource 资源ID
     * @param auditRemark 资源审核备注
     * @return Map
     */
    Map saveResourceES(Resource resource, String auditRemark);
}
