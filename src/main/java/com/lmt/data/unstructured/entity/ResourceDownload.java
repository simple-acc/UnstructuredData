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
 * @date 2018/1/1 23:25
 * @apiNote 资源下载表
 */
@Entity(name = "resource_download")
public class ResourceDownload extends BaseEntity {

    /**
     * 资源ID
     */
    @Column(name = "resource_id", nullable = false, length = 36)
    private String resourceId;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name="create_time", nullable = false, updatable = false)
    private Date createTime;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
