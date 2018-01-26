package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.entity.search.ResourceEsSearch;

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

    /**
     * @apiNote 根据关键字从ES查询资源
     * @param resourceEsSearch 查询条件
     * @return Map
     */
    Map searchFromEs(ResourceEsSearch resourceEsSearch);

    /**
     * @apiNote 更新Es资源下载次数
     * @param esId EsId
     * @param downloadNum 下载次数
     */
    void updateDownloadNum(String esId, int downloadNum);

    /**
     * @apiNote 更新Es资源的收藏次数
     * @param esId EsId
     * @param collectionNum 收藏次数
     */
    void updateCollectionNum(String esId, int collectionNum);
}
