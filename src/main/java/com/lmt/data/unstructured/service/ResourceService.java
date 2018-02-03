package com.lmt.data.unstructured.service;

import java.util.Map;

import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.entity.ResourceTemp;
import com.lmt.data.unstructured.entity.search.ResourceSearch;

/**
 * @author MT-Lin
 * @date 2018/1/11 11:26
 */
@SuppressWarnings({ "rawtypes" })
public interface ResourceService {

	/**
	 * @apiNote 保存审核通过的资源
	 * @param resource
	 *            要保存的资源
	 * @return Map
	 */
	Map save(Resource resource);

	/**
	 * @apiNote 搜索资源
	 * @param resourceSearch
	 *            搜索条件
	 * @return Map
	 */
	Map search(ResourceSearch resourceSearch);

	/**
	 * @apiNote 修改资源所属专题
	 * @param resourceSearch
	 *            修改的数据
	 * @return Map
	 */
	Map modifyDissertation(ResourceSearch resourceSearch);

	/**
	 * @apiNote 从待审核资源添加资源信息
	 * @param resourceTemp
	 *            待审核资源
	 * @param auditRemark
	 *            审核备注
	 * @return Map
	 */
	Map addResourceFromResourceTemp(ResourceTemp resourceTemp, String auditRemark);

	/**
	 * @apiNote 获取每个专题的前五个热门资源
	 * @return Map
	 */
	Map getTopFiveByDissertation();

	/**
	 * @apiNote 根据资源ID查找资源
	 * @param resourceId
	 *            资源ID
	 * @return Resource
	 */
	Resource findOneById(String resourceId);
}
