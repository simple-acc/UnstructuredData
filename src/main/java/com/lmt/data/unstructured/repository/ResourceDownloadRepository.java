package com.lmt.data.unstructured.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.ResourceDownload;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:14
 */
public interface ResourceDownloadRepository extends JpaRepository<ResourceDownload, String> {

	/**
	 * @apiNote 根据资源ID和用户ID查找下载记录
	 * @param resourceId
	 *            资源ID
	 * @param userId
	 *            用户ID
	 * @return ResourceDownload
	 */
	ResourceDownload findByResourceIdAndAndUserId(String resourceId, String userId);

	/**
	 * @apiNote 获取用户的下载次数
	 * @param userId
	 *            用户ID
	 * @return 下载次数
	 */
	int countByUserId(String userId);
}
