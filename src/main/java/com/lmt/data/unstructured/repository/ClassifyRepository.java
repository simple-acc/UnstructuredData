package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Classify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:08
 */
public interface ClassifyRepository extends JpaRepository<Classify, String> {

    /**
     * @apiNote 获取父ID为空的数据
     * @return List
     */
    List<Classify> findByParentIdIsNull();

    /**
     * @apiNote 根据父节点ID获取数据
     * @param parentId 父节点ID
     * @return List
     */
    List<Classify> findByParentId(String parentId);

    /**
     * @apiNote 根据分类名称查找
     * @param classifyType 分类类型
     * @param designation 分类名称
     * @return Classify
     */
    Classify findByClassifyTypeAndDesignation(String classifyType, String designation);
}
