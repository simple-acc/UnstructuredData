package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.CollectionFolder;
import com.lmt.data.unstructured.entity.search.CollectionFolderSearch;
import com.lmt.data.unstructured.repository.CollectionFloderRepository;
import com.lmt.data.unstructured.service.CollectionFolderService;
import com.lmt.data.unstructured.util.CheckResult;
import com.lmt.data.unstructured.util.EntityManagerQuery;
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
 * @date 2018/1/26 14:23
 */
@Service("CollectionFolderServiceImpl")
public class CollectionFolderServiceImpl implements CollectionFolderService {

    @Autowired
    private CollectionFloderRepository collectionFloderRepository;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public Map save(CollectionFolder collectionFolder) {
        CollectionFolder exist = this.collectionFloderRepository
                .findByCreatorAndDesignationAndParentId(
                        collectionFolder.getCreator(),
                        collectionFolder.getDesignation(),
                        collectionFolder.getParentId());
        if (null != exist){
            return ResultData.newError("所属收藏夹下已有该名称的收藏夹");
        }
        this.collectionFloderRepository.save(collectionFolder);
        if (null == collectionFolder.getId()) {
            return ResultData.newError("收藏夹创建失败");
        }
        return ResultData.newOK("收藏夹创建成功");
    }

    @Override
    public Map search(CollectionFolderSearch collectionFolderSearch) {
        StringBuffer sql = new StringBuffer();
        List<Object> parameters = new ArrayList<>();
        sql.append("SELECT cf.id, cf.designation, cf.description, cf.creator, ");
        sql.append("cf.parent_id AS parentId, ");
        sql.append("cf.create_time AS createTime, ");
        sql.append("cf.resource_num AS resourceNum, ");
        sql.append("(SELECT cft.designation FROM collection_folder AS cft WHERE cft.id = cf.parent_id) ");
        sql.append("AS parent ");
        sql.append("FROM collection_folder AS cf ");
        sql.append("WHERE 1 = 1 ");
        if (!StringUtils.isEmpty(collectionFolderSearch.getDesignation())){
            sql.append("AND cf.designation = ? ");
            parameters.add(collectionFolderSearch.getDesignation());
        }
        sql.append("ORDER BY cf.create_time DESC ");
        Map<String, Object> result = this.entityManagerQuery.paginationSearch(sql, parameters, collectionFolderSearch);
        return ResultData.newOK("收藏夹信息查询成功", result);
    }

    @Override
    public Map getParentTree() {
        List result = this.getOptions();
        return ResultData.newOK("成功获取父节点选择树", result);
    }

    @Override
    public Map delete(List<CollectionFolder> collectionFolders) {
        for (CollectionFolder collectionFolder : collectionFolders) {
            List<CollectionFolder> children = this
                    .collectionFloderRepository.findByParentId(collectionFolder.getId());
            if (children.size() > 0){
                this.delete(children);
            }
            this.collectionFloderRepository.delete(collectionFolder.getId());
        }
        return ResultData.newOK("成功删除收藏夹");
    }

    @Override
    public Map update(CollectionFolder collectionFolder) {
        CollectionFolder exist = this.collectionFloderRepository.findOne(collectionFolder.getId());
        if (null == exist) {
            return ResultData.newError("修改的收藏夹不存在");
        }
        this.collectionFloderRepository.save(collectionFolder);
        return ResultData.newOK("收藏夹修改成功");
    }

    @SuppressWarnings("unchecked")
    private List getOptions() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<CollectionFolder> firstLevel = new ArrayList<>();
        List<CollectionFolder> children = new ArrayList<>();
        List<String> parentId = new ArrayList<>();
        List<CollectionFolder> all = this.collectionFloderRepository.findAll();
        Map<String, Map<String, Object>> temp = new HashMap<>(all.size());
        for (CollectionFolder collectionFolder : all) {
            Map<String, Object> tempOption = new HashMap<>(9);
            tempOption.put(UdConstant.PROPS_VALUE, collectionFolder.getId());
            tempOption.put(UdConstant.PROPS_ID, collectionFolder.getId());
            tempOption.put(UdConstant.PROPS_LABEL, collectionFolder.getDesignation());
            tempOption.put(UdConstant.PROPS_CHILDREN, new ArrayList<>());
            temp.put(collectionFolder.getId(), tempOption);
            if (collectionFolder.getParentId() != null){
                parentId.add(collectionFolder.getParentId());
            }
            if (null == collectionFolder.getParentId()){
                firstLevel.add(collectionFolder);
            } else {
                children.add(collectionFolder);
            }
        }
        for (CollectionFolder child : children) {
            ((List)temp.get(child.getParentId()).get(UdConstant.PROPS_CHILDREN)).add(temp.get(child.getId()));
            if (!parentId.contains(child.getId())){
                temp.get(child.getId()).remove(UdConstant.PROPS_CHILDREN);
            }
        }
        for (CollectionFolder collectionFolder : firstLevel) {
            result.add(temp.get(collectionFolder.getId()));
        }
        return result;
    }
}
