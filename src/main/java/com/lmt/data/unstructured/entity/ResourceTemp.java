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
 * @date 2018/1/2 10:34
 * @apiNote 资源表（待审核）
 */
@Entity(name = "resource_temp")
public class ResourceTemp extends BaseEntity {

    /**
     * 作者
     */
    @Column(name="author_id", nullable = false, length = 36)
    private String authorId;

    /**
     * 所属分类ID
     */
    @Column(name="classify_id", nullable = false, length = 36)
    private String classifyId;

    /**
     * 资源库资源ID
     */
    @Column(name="resource_id", length = 36)
    private String resourceId;

    /**
     * 资源名称
     */
    @Column(name="resource_name", nullable = false, length = 32)
    private String resourceName;

    /**
     * 资源描述
     */
    @Column(name="description", length = 100)
    private String description;

    /**
     * 资源类型
     */
    @Column(name="resource_type",nullable = false, length = 6)
    private String resourceType;

    /**
     * 上传时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
