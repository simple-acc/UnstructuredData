package com.lmt.data.unstructured.entity;

import com.lmt.data.unstructured.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author MT-Lin
 * @date 2018/1/2 10:01
 * @apiNote 收藏夹
 */
@Entity(name = "collection_folder")
public class CollectionFolder extends BaseEntity {

    /**
     * 收藏夹名称
     */
    @Column(name = "designation", nullable = false, length = 32)
    private String designation;

    /**
     * 收藏的资源数目
     */
    @Column(name = "resource_num")
    private int resourceNum;

    /**
     * 收藏夹描述
     */
    @Column(name = "description", nullable = false, length = 300)
    private String description;

    /**
     * 父收藏夹ID
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

    public int getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(int resourceNum) {
        this.resourceNum = resourceNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
