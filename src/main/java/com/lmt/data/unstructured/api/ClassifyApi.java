package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.Classify;
import com.lmt.data.unstructured.entity.search.ClassifySearch;
import com.lmt.data.unstructured.service.ClassifyService;
import com.lmt.data.unstructured.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/7 7:21
 */
@RestController
@RequestMapping("/ClassifyApi")
public class ClassifyApi {

    @Autowired
    private ClassifyService classifyService;

    @RequestMapping("/save")
    public Map save(@RequestBody Classify classify){
        return this.classifyService.save(classify);
    }

    @RequestMapping("/getParentTree")
    public Map getParentTree(){
        return this.classifyService.getParentTree();
    }

    @RequestMapping("/search")
    public Map search(@RequestBody ClassifySearch classifySearch){
        return this.classifyService.search(classifySearch);
    }

}
