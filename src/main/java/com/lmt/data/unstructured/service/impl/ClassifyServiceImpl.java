package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Classify;
import com.lmt.data.unstructured.entity.search.ClassifySearch;
import com.lmt.data.unstructured.repository.ClassifyRepository;
import com.lmt.data.unstructured.service.ClassifyService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    
    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public Map getParentTree(String classifyType) {
        List result = this.getOptions(classifyType);
        return ResultData.newOK("成功获取父节点选择树", result);
    }

    @Override
    public Map save(Classify classify) {
        Classify existClassify = this.classifyRepository
                .findByClassifyTypeAndDesignationAndParentId(
                        classify.getClassifyType(), classify.getDesignation(), classify.getParentId());
        if (null != existClassify){
            return ResultData.newError("父类中已存在该名称或者添加的分类类型中已存在该名称");
        }
        classify.setCreator(redisCache.getUserName(classify));
        this.classifyRepository.save(classify);
        if (null == classify.getId()){
            return ResultData.newError("添加分类失败");
        }
        return ResultData.newOK("添加分类成功");
    }

    @Override
    public Map search(ClassifySearch classifySearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT c.id, c.designation, c.description, c.creator, ");
        sql.append("c.collection_num AS collectionNum, "); 
        sql.append("c.download_num AS downloadNum, ");
        sql.append("c.upload_num AS uploadNum, ");
        sql.append("c.create_time AS createTime, ");
        sql.append("(SELECT tc.designation FROM classify AS tc WHERE tc.id = c.parent_id) ");
        sql.append("AS parent, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = c.classify_type) ");
        sql.append("AS classifyType ");
        sql.append("FROM classify AS c WHERE 1=1 ");
        if (!StringUtils.isEmpty(classifySearch.getKeyword())){
            sql.append("AND (c.designation LIKE ? OR c.description LIKE ? OR c.creator LIKE ?) ");
            classifySearch.setParamsCount(3);
        }
        Map<String, Object> result = entityManagerQuery.paginationSearch(sql, classifySearch);
        return ResultData.newOK("查询成功", result);
    }

    @Override
    public Map findOneById(String id) {
        Classify result = this.classifyRepository.findOne(id);
        if (null == result){
            return ResultData.newError("该分类不存在");
        }
        return ResultData.newOK("查询成功", result);
    }

    @Override
    public Map update(Classify classify) {
        Classify old = this.classifyRepository.findOne(classify.getId());
        if (null == old){
            ResultData.newError("修改的分类不存在");
        }
        this.classifyRepository.save(classify);
        return ResultData.newOK("修改成功");
    }

    @Override
    public Map delete(List<Classify> classifies) {
        for (Classify classify : classifies) {
            this.classifyRepository.delete(classify.getId());
        }
        return ResultData.newOK("删除成功");
    }

    // TODO 获取选择数据

    @SuppressWarnings("unchecked")
    private List getOptions(String classifyType) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Classify> firstLevel = new ArrayList<>();
        List<Classify> children = new ArrayList<>();
        List<String> parentId = new ArrayList<>();
        List<Classify> all = this.classifyRepository.findByClassifyType(classifyType);
        Map<String, Map<String, Object>> temp = new HashMap<>(all.size());
        for (Classify classify : all) {
            Map<String, Object> tempOption = new HashMap<>(6);
            tempOption.put(UdConstant.PROPS_VALUE, classify.getId());
            tempOption.put(UdConstant.PROPS_ID, classify.getId());
            tempOption.put(UdConstant.PROPS_LABEL, classify.getDesignation());
            tempOption.put(UdConstant.PROPS_CHILDREN, new ArrayList<>());
            temp.put(classify.getId(), tempOption);
            if (classify.getParentId() != null){
                parentId.add(classify.getParentId());
            }
            if (null == classify.getParentId()){
                firstLevel.add(classify);
            } else {
                children.add(classify);
            }
        }
        for (Classify child : children) {
            ((List)temp.get(child.getParentId()).get(UdConstant.PROPS_CHILDREN)).add(temp.get(child.getId()));
            if (!parentId.contains(child.getId())){
                temp.get(child.getId()).remove(UdConstant.PROPS_CHILDREN);
            }
        }
        for (Classify classify : firstLevel) {
            result.add(temp.get(classify.getId()));
        }
        return result;
    }

}
