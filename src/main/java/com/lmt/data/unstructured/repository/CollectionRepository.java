package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:09
 */
@SuppressWarnings("SpringDataMethodInconsistencyInspection")
public interface CollectionRepository extends JpaRepository<Collection, String> {

    /**
     * @apiNote 根据收藏对象ID查询
     * @param objId 收藏的对象
     * @return Collection
     */
    Collection findByObjId(String objId);

    /**
     * @apiNote 根据对象ID和创建者查询
     * @param objId 对象Id
     * @param creator 创建者
     * @return Collection
     */
    Collection findByObjIdAndAndCreator(String objId, String creator);

    /**
     * @apiNote 计算用户的收藏资源数
     * @param userId 用户ID
     * @return int
     */
    int countByCreator(String userId);
}
