package com.lmt.data.unstructured.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.Tag;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:15
 */
public interface TagRepository extends JpaRepository<Tag, String> {

	/**
	 * @apiNote 根据系统资源ID查找
	 * @param resourceId
	 *            系统资源ID
	 * @return Tag
	 */
	Tag findByResourceId(String resourceId);
}
