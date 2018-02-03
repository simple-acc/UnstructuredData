package com.lmt.data.unstructured.service;

import java.util.Map;

import com.lmt.data.unstructured.entity.Audit;
import com.lmt.data.unstructured.entity.search.AuditSearch;

/**
 * @author MT-Lin
 * @date 2018/1/10 20:50
 */
@SuppressWarnings({ "rawtypes" })
public interface AuditService {

	/**
	 * @apiNote 保存审核数据
	 * @param audit
	 *            要保存的审核数据
	 * @return Map
	 */
	Map save(Audit audit);

	/**
	 * @apiNote 更新审核信息
	 * @param audit
	 *            要更新的审核数据，包含待审核资源List
	 * @return Map
	 */
	Map update(Audit audit);

	/**
	 * @apiNote 搜索审核表
	 * @param auditSearch
	 *            搜索条件
	 * @return Map
	 */
	Map search(AuditSearch auditSearch);
}
