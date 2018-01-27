package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.ResourceDownload;
import com.lmt.data.unstructured.service.ResourceDownloadService;
import com.lmt.data.unstructured.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/27 21:38
 */
@RestController
@RequestMapping("/ResourceDownloadApi")
public class ResourceDownloadApi {

    @Autowired
    private ResourceDownloadService resourceDownloadService;

    @Autowired
    private RedisCache redisCache;

    @RequestMapping("/save")
    public Map save(@RequestBody ResourceDownload resourceDownload){
        resourceDownload.setUserId(redisCache.getUserId(resourceDownload));
        return this.resourceDownloadService.save(resourceDownload);
    }

}
