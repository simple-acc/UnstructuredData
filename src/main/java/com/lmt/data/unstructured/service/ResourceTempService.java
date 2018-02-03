package com.lmt.data.unstructured.service;

import java.util.Map;

import com.lmt.data.unstructured.entity.ResourceTemp;
import com.lmt.data.unstructured.entity.search.ResourceTempSearch;

/**
 * @author MT-Lin
 * @date 2018/1/9 21:09
 */
@SuppressWarnings({ "rawtypes" })
public interface ResourceTempService {

	/**
	 * @apiNote 保存待审核资源
	 * @param resourceTemp
	 *            要保存的待审核资源
	 * @return Map
	 */
	Map save(ResourceTemp resourceTemp);

	/**
	 * @apiNote 搜索待审核资源
	 * @param resourceTempSearch
	 *            搜索条件
	 * @return Map
	 */
	Map search(ResourceTempSearch resourceTempSearch);

	/**
	 * @apiNote 根据Id查询待审核资源
	 * @param resourceTempId
	 *            待审核资源ID
	 * @return ResourceTemp
	 */
	ResourceTemp findOneById(String resourceTempId);

	/**
	 * @apiNote 查询某个作者上传资源数
	 * @param authorId
	 *            作者ID
	 * @return 上传资源数
	 */
	int getUploadNum(String authorId);
}
