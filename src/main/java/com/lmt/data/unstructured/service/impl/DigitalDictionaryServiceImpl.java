package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.DigitalDictionary;
import com.lmt.data.unstructured.entity.search.DigitalDictionarySearch;
import com.lmt.data.unstructured.repository.DigitalDictionaryRepository;
import com.lmt.data.unstructured.service.DigitalDictionaryService;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/3 16:44
 */
@Service("DigitalDictionaryServiceImpl")
public class DigitalDictionaryServiceImpl implements DigitalDictionaryService{

    @Autowired
    private DigitalDictionaryRepository digitalDictionaryRepository;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map save(DigitalDictionary digitalDictionary) {
        digitalDictionary.setCreator(redisCache.getUserName(digitalDictionary));
        if (null != this.digitalDictionaryRepository.findByCode(digitalDictionary.getCode())){
            return ResultData.newError("该编码已经存在：" + digitalDictionary.getCode()).toMap();
        }
        this.digitalDictionaryRepository.save(digitalDictionary);
        if (null != digitalDictionary.getId()){
            return ResultData.newOK("添加数据字典成功").toMap();
        } else {
            return ResultData.newError("添加数据字典失败").toMap();
        }
    }

    @Override
    public Map findAll(DigitalDictionarySearch digitalDictionarySearch) {
        int currentPage = digitalDictionarySearch.getCurrentPage() - 1;
        int pageSize = digitalDictionarySearch.getPageSize();
        Order codeOrder = new Order(Sort.Direction.ASC, "code");
        List<Order> orders = new ArrayList<>();
        orders.add(codeOrder);
        Sort sort = new Sort(orders);
        PageRequest pageRequest = new PageRequest(currentPage, pageSize, sort);
        Page<DigitalDictionary> page = this.digitalDictionaryRepository.findAll(pageRequest);
        return ResultData.newOk("查询成功", page).toMap();
    }

    @Override
    public Map findOneById(String id) {
        DigitalDictionary result = this.digitalDictionaryRepository.findOne(id);
        if (null == result){
            return ResultData.newError("该数据字典不存在").toMap();
        } else {
            return ResultData.newOk("成功获取数据字典", result).toMap();
        }
    }

    @Override
    public Map update(DigitalDictionary digitalDictionary) {
        DigitalDictionary old = this.digitalDictionaryRepository.findOne(digitalDictionary.getId());
        if (null == old){
            return ResultData.newError("修改的数据字典不存在").toMap();
        }
        this.digitalDictionaryRepository.save(digitalDictionary);
        return ResultData.newOK("修改数据字典成功").toMap();
    }

    @Override
    public Map search(DigitalDictionarySearch digitalDictionarySearch) {
        String keyword = digitalDictionarySearch.getKeyword();
        int currentPage = digitalDictionarySearch.getCurrentPage() - 1;
        int pageSize = digitalDictionarySearch.getPageSize();
        Order codeOrder = new Order(Sort.Direction.ASC, "code");
        List<Order> orders = new ArrayList<>();
        orders.add(codeOrder);
        Sort sort = new Sort(orders);
        PageRequest pageRequest = new PageRequest(currentPage, pageSize, sort);
        if (!StringUtils.isEmpty(keyword)){
            Page result = this.digitalDictionaryRepository.findByCodeLikeOrDescriptionLikeOrDesignationLikeOrCreatorLike
                    (keyword, keyword, keyword, keyword, pageRequest);
            return ResultData.newOk("查询成功", result).toMap();
        }
        return this.findAll(digitalDictionarySearch);
    }

    @Override
    public Map delete(List<DigitalDictionary> digitalDictionaries) {
        this.digitalDictionaryRepository.delete(digitalDictionaries);
        return ResultData.newOK("删除数据字典成功").toMap();
    }

    @Override
    public Map getParentCodeTree() {
        List<Map<String, Object>> result = this.getChildren();
        return ResultData.newOk("成功获取父节点选择树", result).toMap();
    }

    @Override
    public Map getChildrenForSelect(String parentCode) {
        List<DigitalDictionary> options = this.digitalDictionaryRepository.findByParentCode(parentCode);
        return ResultData.newOk("成功获取选项", options).toMap();
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getChildren() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<DigitalDictionary> firstLevel = new ArrayList<>();
        List<DigitalDictionary> children = new ArrayList<>();
        List<DigitalDictionary> all = this.digitalDictionaryRepository.findAll();
        Map<String, Map<String, Object>> temp = new HashMap<>(all.size());
        for (DigitalDictionary classify : all) {
            Map<String, Object> tempOption = new HashMap<>(3);
            tempOption.put(UdConstant.TREE_PROPS_CODE, classify.getCode());
            tempOption.put(UdConstant.TREE_PROPS_LABEL, classify.getDesignation());
            tempOption.put(UdConstant.TREE_PROPS_CHILDREN, new ArrayList<>());
            temp.put(classify.getCode(), tempOption);
            if (null == classify.getParentCode()){
                firstLevel.add(classify);
            } else {
                children.add(classify);
            }
        }
        for (DigitalDictionary child : children) {
            ((List)temp.get(child.getParentCode()).get(UdConstant.TREE_PROPS_CHILDREN)).add(temp.get(child.getCode()));
        }
        for (DigitalDictionary classify : firstLevel) {
            result.add(temp.get(classify.getCode()));
        }
        return result;
    }
}
