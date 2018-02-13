package com.lmt.data.unstructured.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.ResourceTemp;
import org.springframework.stereotype.Repository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:13
 */
@Repository
public interface ResourceTempRepository extends JpaRepository<ResourceTemp, String> {

	/**
	 * @apiNote 根据文件MD5值查找
	 * @param md5
	 *            MD5
	 * @return ResourceTemp
	 */
	ResourceTemp findByMd5(String md5);

	/**
	 * @apiNote 获取用户上传资源数
	 * @param authorId
	 *            作者ID
	 * @return 上传资源数
	 */
	int countByAuthorId(String authorId);
}
