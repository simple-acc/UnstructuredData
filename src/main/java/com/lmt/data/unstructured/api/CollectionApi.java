package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.Collection;
import com.lmt.data.unstructured.service.CollectionService;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.util.CheckResult;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.UdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/25 9:42
 */
@RestController
@RequestMapping("/CollectionApi")
public class CollectionApi {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RedisCache redisCache;

    @RequestMapping("/collectResource")
    public Map collectResource(@RequestBody Collection collection){
        collection.setType(UdConstant.COLLECT_TYPE_RESOURCE);
        collection.setCreator(redisCache.getUserId(collection));
        Map result = this.collectionService.save(collection);
        if (CheckResult.isOK(result)) {
            this.resourceService.updateCollectionNum(collection.getObjId());
        }
        return result;
    }
}
