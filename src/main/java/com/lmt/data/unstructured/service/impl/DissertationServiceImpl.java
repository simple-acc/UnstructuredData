package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Dissertation;
import com.lmt.data.unstructured.entity.search.DissertationSearch;
import com.lmt.data.unstructured.repository.DissertationRepository;
import com.lmt.data.unstructured.service.DissertationService;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/8 16:28
 */
@Service("DissertationServiceImpl")
public class DissertationServiceImpl implements DissertationService{

    @Autowired
    private DissertationRepository dissertationRepository;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map save(Dissertation dissertation) {
        Dissertation exist = this.dissertationRepository
                .findByClassifyIdAndDesignation(dissertation.getClassifyId(), dissertation.getDesignation());
        if (null != exist){
            return ResultData.newError("该分类下该专题名称已存在");
        }
        dissertation.setCreator(redisCache.getUserName(dissertation));
        this.dissertationRepository.save(dissertation);
        if (null == dissertation.getId()){
            return ResultData.newError("添加专题失败");
        }
        return ResultData.newOK("成功添加专题");
    }

    @Override
    public Map search(DissertationSearch dissertationSearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT d.id, d.designation, d.description, d.creator, ");
        sql.append("d.collection_num AS collectionNum, ");
        sql.append("d.upload_num AS uploadNum, ");
        sql.append("d.download_num AS downloadNum, ");
        sql.append("d.create_time AS createTime, ");
        sql.append("(SELECT c.designation FROM classify AS c WHERE c.id = d.classify_id) ");
        sql.append("AS classify, ");
        sql.append("(SELECT td.designation FROM dissertation AS td WHERE td.id = d.parent_id) ");
        sql.append("AS parent ");
        sql.append("FROM dissertation AS d WHERE 1 = 1 ");
        if (!StringUtils.isEmpty(dissertationSearch.getKeyword())){
            sql.append("AND (d.designation LIKE ? OR d.description LIKE ? OR d.creator LIKE ?) ");
            dissertationSearch.setParamsCount(3);
        }
        Map<String, Object> result =
                entityManagerQuery.paginationSearch(sql, dissertationSearch);
        return ResultData.newOK("查询专题成功", result);
    }

    @Override
    public Map findOneById(String id) {
        Dissertation result = this.dissertationRepository.findOne(id);
        if (null == result){
            return ResultData.newError("该专题不存在");
        }
        return ResultData.newOK("查询专题成功", result);
    }

    @Override
    public Map delete(List<Dissertation> dissertations) {
        for (Dissertation dissertation : dissertations) {
            this.dissertationRepository.delete(dissertation.getId());
        }
        return ResultData.newOK("删除成功");
    }

    @Override
    public Map update(Dissertation dissertation) {
        Dissertation old = this.dissertationRepository.findOne(dissertation.getId());
        if (null == old){
            ResultData.newError("更新的专题不存在");
        }
        this.dissertationRepository.save(dissertation);
        return ResultData.newOK("专题更新成功");
    }

    @Override
    public Map getParentTree() {
        List result = this.getTreeOptions();
        return ResultData.newOK("父主题树选项获取成功", result);
    }

    @SuppressWarnings({"unchecked"})
    private List getTreeOptions() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Dissertation> firstLevel = new ArrayList<>();
        List<Dissertation> children = new ArrayList<>();
        List<Dissertation> all = this.dissertationRepository.findAll();
        Map<String, Map<String, Object>> temp = new HashMap<>(all.size());
        for (Dissertation dissertation : all) {
            Map<String, Object> tempOption = new HashMap<>(4);
            tempOption.put(UdConstant.PROPS_ID, dissertation.getId());
            tempOption.put(UdConstant.PROPS_LABEL, dissertation.getDesignation());
            tempOption.put("classifyId", dissertation.getClassifyId());
            tempOption.put(UdConstant.PROPS_CHILDREN, new ArrayList<>());
            temp.put(dissertation.getId(), tempOption);
            if (null == dissertation.getParentId()){
                firstLevel.add(dissertation);
            } else {
                children.add(dissertation);
            }
        }
        for (Dissertation child : children) {
            ((List)temp.get(child.getParentId()).get(UdConstant.PROPS_CHILDREN)).add(temp.get(child.getId()));
        }
        for (Dissertation dissertation : firstLevel) {
            result.add(temp.get(dissertation.getId()));
        }
        return result;
    }


}
