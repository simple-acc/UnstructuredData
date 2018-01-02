package com.lmt.data.unstructured.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author MT-Lin
 * @date 2018/1/2 10:39
 * @apiNote 审核表
 */
@Entity(name = "audit")
public class Audit extends BaseEntity {

    /**
     * 审核人
     */
    @Column(name = "auditor", nullable = false, length = 36)
    private String auditor;

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
    @Column(name = "state", nullable = false, length = 6)
    private String state;

    /**
     * 备注
     */
    @Column(name = "remark", length = 300)
    private String remark;

    /**
     * 审核时间
     */
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

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
