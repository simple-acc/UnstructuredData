package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.CollectionFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:10
 */
public interface CollectionFloderRepository extends JpaRepository<CollectionFolder, String> {

    /**
     * @apiNote 根据创建者，名称和所属收藏夹查询
     * @param creator 创建者
     * @param designation 名称
     * @param parentId 所属收藏夹ID
     * @return CollectionFolder
     */
    CollectionFolder findByCreatorAndDesignationAndParentId(
            String creator, String designation, String parentId);

    /**
     * @apiNote 根据所属收藏夹查询
     * @param parentId 父级收藏夹
     * @return 子收藏夹
     */
    List<CollectionFolder> findByParentId(String parentId);
}
