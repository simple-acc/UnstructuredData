package com.lmt.data.unstructured.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author MT-Lin
 * @date 2018/1/2 10:01
 * @apiNote 收藏分类表
 */
@Entity(name = "collect_classify")
public class CollectClassify extends BaseEntity {

    /**
     * 分类名称
     */
    @Column(name = "designation", nullable = false, length = 32)
    private String designation;

    /**
     * 父级分类ID
     */
    @Column(name = "parent_id", length = 36)
    private String parentId;

    /**
     * 创建人
     */
    @Column(name = "creator", nullable = false, length = 36)
    private String creator;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
