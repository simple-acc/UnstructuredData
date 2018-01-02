package com.lmt.data.unstructured.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author MT-Lin
 * @date 2018/1/2 10:13
 * @apiNote 待审核资源标签表
 */
@Entity(name = "tag_temp")
public class TagTemp extends BaseEntity {

    /**
     * 待审核资源ID
     */
    @Column(name = "resource_temp_id", nullable = false, length = 36)
    private String resourceTempId;

    /**
     * 标签
     */
    @Column(name = "tag", length = 60)
    private String tag;

    /**
     * 创建者
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

    public String getResourceTempId() {
        return resourceTempId;
    }

    public void setResourceTempId(String resourceTempId) {
        this.resourceTempId = resourceTempId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
