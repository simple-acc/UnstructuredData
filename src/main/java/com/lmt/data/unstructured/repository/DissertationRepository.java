package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Dissertation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:12
 */
public interface DissertationRepository extends JpaRepository<Dissertation, String> {

    /**
     * @apiNote 根据所属分类和专题名称查找
     * @param classifyId 所属分类ID
     * @param designation 专题名称
     * @return 查找结果
     */
    Dissertation findByClassifyIdAndDesignation(String classifyId, String designation);
}
