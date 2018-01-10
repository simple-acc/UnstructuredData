package com.lmt.data.unstructured.repository;

import com.lmt.data.unstructured.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MT-Lin
 * @date 2018/1/3 0:06
 */
public interface AuditRepository extends JpaRepository<Audit, String> {

    /**
     * @apiNote 根据审核对象ID和审核的操作查询
     * @param objId 审核对象ID
     * @param operation 审核的操作
     * @return Audit
     */
    Audit findByObjIdAndOperation(String objId, String operation);

    /**
     * @apiNote 根据审核对象ID和审核状态查询
     * @param objId 审核对象ID
     * @param status 审核状态
     * @return Audit
     */
    Audit findByObjIdAndStatusEquals(String objId, String status);
}
