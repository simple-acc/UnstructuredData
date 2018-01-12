package com.lmt.data.unstructured.entity;

import com.lmt.data.unstructured.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/2 10:39
 * @apiNote 审核表
 */
@Entity(name = "audit")
public class Audit extends BaseEntity {

    /**
     * 审核人ID
     */
    @Column(name = "auditor_id", length = 36)
    private String auditorId;

    /**
     * 审核对象ID
     */
    @Column(name = "obj_id", nullable = false, length = 36)
    private String objId;

    /**
     * 审核操作
     */
    @Column(name = "operation", nullable = false, length = 36)
    private String operation;

    /**
     * 审核状态
     */
    @Column(name = "status", nullable = false, length = 6)
    private String status;

    /**
     * 备注
     */
    @Column(name = "remark", length = 300)
    private String remark;

    /**
     * 审核时间
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "audit_time")
    private Date auditTime;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @Transient
    private List<ResourceTemp> resourceTemps;

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ResourceTemp> getResourceTemps() {
        return resourceTemps;
    }

    public void setResourceTemps(List<ResourceTemp> resourceTemps) {
        this.resourceTemps = resourceTemps;
    }
}
