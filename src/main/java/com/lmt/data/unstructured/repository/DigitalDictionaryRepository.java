package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.DigitalDictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:11
 */
@Repository
public interface DigitalDictionaryRepository extends JpaRepository<DigitalDictionary, String> {

    /**
     * @apiNote 根据关键词模糊查找
     * @param code 编码
     * @param description 描述
     * @param designation 名称
     * @param creator 创建者
     * @param pageable 分页
     * @return Page
     */
    Page<DigitalDictionary> findByCodeLikeOrDescriptionLikeOrDesignationLikeOrCreatorLike
            (String code, String description, String designation, String creator, Pageable pageable);

    /**
     * @apiNote 根据父类编码查找
     * @param parentCode 父类编码
     * @return List
     */
    List<DigitalDictionary> findByParentCode(String parentCode);

    /**
     * @apiNote 查找一级
     * @return List
     */
    List<DigitalDictionary> findByParentCodeIsNull();

    /**
     * @apiNote 根据编码查询数据
     * @param Code 编码
     * @return Map
     */
    DigitalDictionary findByCode(String Code);
}
