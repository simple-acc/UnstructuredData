package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.entity.Dissertation;
import com.lmt.data.unstructured.entity.search.DissertationSearch;

import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/8 16:28
 */
public interface DissertationService {

    /**
     * @apiNote 保存专题
     * @param dissertation 要保存的专题
     * @return Map
     */
    Map save(Dissertation dissertation);

    /**
     * @apiNote 搜索专题
     * @param dissertationSearch 搜索条件
     * @return Map
     */
    Map search(DissertationSearch dissertationSearch);

    /**
     * @apiNote 根据ID查找
     * @param id 要查找的ID
     * @return Map
     */
    Map findOneById(String id);

    /**
     * @apiNote 删除专题
     * @param dissertations 要删除的专题
     * @return Map
     */
    Map delete(List<Dissertation> dissertations);

    /**
     * @apiNote 更新专题
     * @param dissertation 要更新的专题
     * @return Map
     */
    Map update(Dissertation dissertation);

    /**
     * @apiNote 获取父类专题选择数据
     * @return Map
     */
    Map getParentTree();

    /**
     * @apiNote 获取一级专题以下所有子专题的ID
     * @return Map
     */
    Map<Dissertation, List<Object>> getDissertationIdsGroup();
}
