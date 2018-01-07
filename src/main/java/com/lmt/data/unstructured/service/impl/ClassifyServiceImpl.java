package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.repository.ClassifyRepository;
import com.lmt.data.unstructured.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MT-Lin
 * @date 2018/1/7 7:20
 */
@Service("ClassifyServiceImpl")
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyRepository classifyRepository;

}
