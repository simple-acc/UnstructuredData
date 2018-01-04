package com.lmt.data.unstructured.entity;

import com.lmt.data.unstructured.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author MT-Lin
 * @date 2018/1/1 22:59
 * @apiNote 资源表
 */
@Entity(name = "resource")
public class Resource extends BaseEntity {

    /**
     * 作者
     */
    @Column(name="author_id",nullable = false, length = 36)
    private String authorId;

    /**
     * 所属分类ID
     */
    @Column(name="classify_id",nullable = false, length = 36)
    private String classifyId;

    /**
     * 所属专题ID
     */
    @Column(name="dissertation_id",nullable = false, length = 36)
    private String dissertationId;

    /**
     * 资源名称
     */
    @Column(name="resource_name",nullable = false, length = 32)
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
     * 下载数
     */
    @Column(name = "download_num")
    private int downloadNum;

    /**
     * 收藏数
     */
    @Column(name = "collection_num")
    private int collectionNum;

    /**
     * 上传时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "upload_time", nullable = false, updatable = false)
    private Date uploadTime;

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

    public String getDissertationId() {
        return dissertationId;
    }

    public void setDissertationId(String dissertationId) {
        this.dissertationId = dissertationId;
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

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}