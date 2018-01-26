package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.ResourceDownload;
import com.lmt.data.unstructured.entity.search.ResourceSearch;
import com.lmt.data.unstructured.service.ResourceDownloadService;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.util.CheckResult;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.UdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/13 9:24
 */
@RestController
@RequestMapping("/ResourceApi")
public class ResourceApi {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceDownloadService resourceDownloadService;

    @Autowired
    private RedisCache redisCache;

    @RequestMapping("/getTopFiveByDissertation")
    public Map getTopFiveByDissertation(){
        return this.resourceService.getTopFiveByDissertation();
    }

    @RequestMapping("/search")
    public Map search(@RequestBody ResourceSearch resourceSearch){
        return this.resourceService.search(resourceSearch);
    }

    @RequestMapping("/modifyDissertation")
    public Map modifyDissertation(@RequestBody ResourceSearch resourceSearch){
        return this.resourceService.modifyDissertation(resourceSearch);
    }

    @RequestMapping("/updateDownloadNum")
    public void updateDownloadNum(@RequestBody ResourceSearch resourceSearch){
        ResourceDownload resourceDownload = new ResourceDownload();
        resourceDownload.setResourceId(resourceSearch.getId());
        resourceDownload.setUserId(this.redisCache.getUserId(resourceSearch));
        Map result = this.resourceDownloadService.save(resourceDownload);
        if (!CheckResult.isOK(result)){
            return;
        }
        this.resourceService.updateDownloadNum(resourceSearch.getId());
    }
}
