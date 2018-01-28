package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.ResourceDownload;
import com.lmt.data.unstructured.entity.search.ResourceDownloadSearch;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/25 10:18
 */
public interface ResourceDownloadService {

    /**
     * @apiNote 保存资源下载记录
     * @param resourceDownload 下载记录
     * @return Map
     */
    Map save(ResourceDownload resourceDownload);

    /**
     * @apiNote 获取用户下载次数
     * @param userId 用户ID
     * @return 下载次数
     */
    int getDownloadNum(String userId);

    /**
     * @apiNote 查询下载记录
     * @param resourceDownloadSearch 查询参数
     * @return Map
     */
    Map search(ResourceDownloadSearch resourceDownloadSearch);
}
