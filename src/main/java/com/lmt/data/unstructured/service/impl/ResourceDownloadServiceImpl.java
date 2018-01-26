package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.ResourceDownload;
import com.lmt.data.unstructured.repository.ResourceDownloadRepository;
import com.lmt.data.unstructured.service.ResourceDownloadService;
import com.lmt.data.unstructured.util.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Map save(ResourceDownload resourceDownload) {
        this.resourceDownloadRepository.save(resourceDownload);
        if (null == resourceDownload.getId()){
            logger.error("下载记录保存失败，用户ID={}，资源ID={}。",
                    resourceDownload.getUserId(), resourceDownload.getResourceId());
            return ResultData.newError("下载记录保存失败");
        }
        return ResultData.newOK("下载记录保存成功");
    }

    @Override
    public int getDownloadNum(String userId) {
        return this.resourceDownloadRepository.countByUserId(userId);
    }
}
