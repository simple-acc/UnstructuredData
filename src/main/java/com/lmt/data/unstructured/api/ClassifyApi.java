package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MT-Lin
 * @date 2018/1/7 7:21
 */
@RestController
@RequestMapping("ClassifyApi")
public class ClassifyApi {

    @Autowired
    private ClassifyService classifyService;


}
