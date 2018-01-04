package com.lmt.data.unstructured.service;

import com.lmt.data.unstructured.base.BasePageRequest;
import com.lmt.data.unstructured.entity.DigitalDictionary;
import com.lmt.data.unstructured.entity.search.DigitalDictionarySearch;

import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/3 16:43
 */
public interface DigitalDictionaryService {

    /**
     * @apiNote 保存数据字典
     * @param digitalDictionary 要保存的数据字典的实体
     * @return Map
     */
    Map save(DigitalDictionary digitalDictionary);

    /**
     * @apiNote 查询所有数据字典
     * @return Map
     * @param basePageRequest 查询条件
     */
    Map findAll(BasePageRequest basePageRequest);

    /**
     * @apiNote 根据ID获取字典
     * @return Map
     */
    Map findOneById(String id);

    /**
     * @apiNote 更新数据字典
     * @param digitalDictionary 要更新的数据字典实体
     * @return Map
     */
    Map update(DigitalDictionary digitalDictionary);

    /**
     * @apiNote 根据父类编码获取子类，根据子类懒加载父类编码选择器
     * @param parentCode 父类编码
     * @return Map
     */
    Map findChildrenForTree(String parentCode);

    /**
     * @apiNote 根据关键词查找
     * @param digitalDictionarySearch 查找条件
     * @return Map
     */
    Map search(DigitalDictionarySearch digitalDictionarySearch);

    /**
     * @apiNote 删除数据字典
     * @param digitalDictionaries 要删除的数据字典
     * @return Map
     */
    Map delete(List<DigitalDictionary> digitalDictionaries);
}
