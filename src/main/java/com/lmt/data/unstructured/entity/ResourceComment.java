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
 * @date 2018/02/12 21:41:35
 * @apiNote 资源评论表
 */
@Entity(name = "resource_comment")
public class ResourceComment extends BaseEntity {

    @Column(name = "resource_id", length = 36, nullable = false)
    private String resourceId;

    @Column(name = "observer_id", length = 36, nullable = false)
    private String observerId;

    @Column(name = "content", length = 300, nullable = false)
    private String content;

    @Column(name = "comment_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date commentTime;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getObserverId() {
        return observerId;
    }

    public void setObserverId(String observerId) {
        this.observerId = observerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }
}
