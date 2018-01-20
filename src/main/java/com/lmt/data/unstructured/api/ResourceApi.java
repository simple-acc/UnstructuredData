package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.search.ResourceSearch;
import com.lmt.data.unstructured.service.ResourceService;
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
}
