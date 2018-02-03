package com.lmt.data.unstructured.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmt.data.unstructured.entity.Classify;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:08
 */
public interface ClassifyRepository extends JpaRepository<Classify, String> {

	/**
	 * @apiNote 根据分类类型查找
	 * @return List
	 */
	List<Classify> findByClassifyType(String classifyType);

	/**
	 * @apiNote 根据分类名称查找
	 * @param classifyType
	 *            分类类型
	 * @param designation
	 *            分类名称
	 * @param parentId
	 *            父类ID
	 * @return Classify
	 */
	Classify findByClassifyTypeAndDesignationAndParentId(String classifyType, String designation, String parentId);
}
