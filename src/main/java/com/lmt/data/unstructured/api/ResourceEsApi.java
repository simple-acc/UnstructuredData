package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.search.ResourceEsSearch;
import com.lmt.data.unstructured.service.ResourceEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/20 10:14
 */
@RestController
@RequestMapping("/ResourceEsApi")
public class ResourceEsApi {

    @Autowired
    private ResourceEsService resourceEsService;

    @RequestMapping("/searchFromEs")
    public Map sarchFromEs(@RequestBody ResourceEsSearch resourceEsSearch){
        return this.resourceEsService.searchFromEs(resourceEsSearch);
    }
}
