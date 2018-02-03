package com.lmt.data.unstructured.service;

import java.util.List;
import java.util.Map;

import com.lmt.data.unstructured.entity.Classify;
import com.lmt.data.unstructured.entity.search.ClassifySearch;

/**
 * @author MT-Lin
 * @date 2018/1/7 7:20
 */
@SuppressWarnings({ "rawtypes" })
public interface ClassifyService {

	/**
	 * @apiNote 获取父类树的选项
	 * @return Map
	 * @param classifyType
	 */
	Map getParentTree(String classifyType);

	/**
	 * @apiNote 保存分类
	 * @param classify
	 *            要保存的分类
	 * @return Map
	 */
	Map save(Classify classify);

	/**
	 * @apiNote 搜索分类
	 * @param classifySearch
	 *            搜索条件
	 * @return Map
	 */
	Map search(ClassifySearch classifySearch);

	/**
	 * @apiNote 根据ID查找分类
	 * @param id
	 *            要查找的分类的ID
	 * @return Map
	 */
	Map findOneById(String id);

	/**
	 * @apiNote 更新分类
	 * @param classify
	 *            要更新的分类
	 * @return Map
	 */
	Map update(Classify classify);

	/**
	 * @apiNote 删除分类
	 * @param classifies
	 *            要删除的分类集合
	 * @return Map
	 */
	Map delete(List<Classify> classifies);
}
