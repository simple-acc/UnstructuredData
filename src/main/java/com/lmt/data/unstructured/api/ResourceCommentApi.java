package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.ResourceComment;
import com.lmt.data.unstructured.entity.search.ResourceCommentSearch;
import com.lmt.data.unstructured.service.ResourceCommentService;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/2/12 22:26
 */
@RestController
@RequestMapping("/ResourceCommentApi")
public class ResourceCommentApi {

    @Autowired
    private ResourceCommentService resourceCommentService;

    @Autowired
    private RedisCache redisCache;

    @RequestMapping("/save")
    public Map save(@RequestBody ResourceComment resourceComment){
        resourceComment.setObserverId(this.redisCache.getUserId(resourceComment));
        return this.resourceCommentService.save(resourceComment);
    }

    @RequestMapping("/getCommentByResourceId")
    public Map getCommentByResourceId(@RequestBody ResourceCommentSearch resourceCommentSearch){
        if (StringUtils.isEmpty(resourceCommentSearch.getResourceId())){
            return ResultData.newError("资源Id不能为空");
        }
        return this.resourceCommentService.getCommentByResourceId(resourceCommentSearch);
    }
}
