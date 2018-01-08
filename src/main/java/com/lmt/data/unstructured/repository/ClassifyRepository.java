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
     * @apiNote 根据分类类型查找
     * @return List
     */
    List<Classify> findByClassifyType(String classifyType);

    /**
     * @apiNote 根据分类名称查找
     * @param classifyType 分类类型
     * @param designation 分类名称
     * @return Classify
     */
    Classify findByClassifyTypeAndDesignation(String classifyType, String designation);
}
