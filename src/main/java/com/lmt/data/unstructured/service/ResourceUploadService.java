package com.lmt.data.unstructured.service;

import java.util.Map;

import com.lmt.data.unstructured.entity.search.ResourceUploadSearch;

/**
 * @author MT-Lin
 * @date 2018/2/1 13:58
 */
@SuppressWarnings({ "rawtypes" })
public interface ResourceUploadService {

	/**
	 * @apiNote 查询用户上传记录
	 * @param resourceUploadSearch
	 *            查询条件
	 * @return Map
	 */
	Map search(ResourceUploadSearch resourceUploadSearch);
}
