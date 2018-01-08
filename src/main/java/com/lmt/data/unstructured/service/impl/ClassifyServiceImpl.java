package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Classify;
import com.lmt.data.unstructured.entity.search.ClassifySearch;
import com.lmt.data.unstructured.repository.ClassifyRepository;
import com.lmt.data.unstructured.service.ClassifyService;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author MT-Lin
 * @date 2018/1/7 7:20
 */
@Service("ClassifyServiceImpl")
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyRepository classifyRepository;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map getParentTree() {
        List result = this.getChildren();
        return ResultData.newOk("成功获取父节点选择树", result).toMap();
    }

    @Override
    public Map save(Classify classify) {
        Classify existClassify = this.classifyRepository
                .findByClassifyTypeAndDesignation(classify.getClassifyType(), classify.getDesignation());
        if (null != existClassify){
            return ResultData.newError("添加的分类类型中已存在该名称").toMap();
        }
        classify.setCreator(redisCache.getUserName(classify));
        this.classifyRepository.save(classify);
        return ResultData.newOK("添加成功").toMap();
    }

    @Override
    public Map search(ClassifySearch classifySearch) {
        return null;
    }

    // TODO 获取树形数据

    @SuppressWarnings("unchecked")
    private List getChildren() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Classify> firstLevel = new ArrayList<>();
        List<Classify> children = new ArrayList<>();
        List<Classify> all = this.classifyRepository.findAll();
        Map<String, Map<String, Object>> temp = new HashMap<>(all.size());
        for (Classify classify : all) {
            Map<String, Object> a = new HashMap<>(3);
            a.put(UdConstant.TREE_PROPS_ID, classify.getId());
            a.put(UdConstant.TREE_PROPS_LABEL, classify.getDesignation());
            a.put(UdConstant.TREE_PROPS_CHILDREN, new ArrayList<>());
            temp.put(classify.getId(), a);
            if (null == classify.getParentId()){
                firstLevel.add(classify);
            } else {
                children.add(classify);
            }
        }
        for (Classify child : children) {
            ((List)temp.get(child.getParentId()).get(UdConstant.TREE_PROPS_CHILDREN)).add(temp.get(child.getId()));
        }
        for (Classify classify : firstLevel) {
            result.add(temp.get(classify.getId()));
        }
        return result;
    }

}
