package com.lmt.data.unstructured.entity.es;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/22 9:35
 */
public class ResourceEsUser {

    /**
     * ES id
     */
    private String id;

    /**
     * 资源ID
     */
    private String resourceId;

    /**
     * 上传者
     */
    private String author;

    /**
     * 资源名称
     */
    private String designation;

    /**
     * 资源文件名
     */
    private String resourceFileName;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 资源标签
     */
    private List<String> tags;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 资源内容
     */
    private String content;

    /**
     * 资源文件大小
     */
    private double resourceSize;

    /**
     * 下载数
     */
    private int downloadNum;

    /**
     * 收藏数
     */
    private int collectionNum;

    /**
     * 返回时存储高亮字段
     */
    private String highlight;

    /**
     * 返回时标记该资源是否已经收藏过
     */
    private boolean collected;

    /**
     * 上传时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getResourceFileName() {
        return resourceFileName;
    }

    public void setResourceFileName(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getResourceSize() {
        return resourceSize;
    }

    public void setResourceSize(double resourceSize) {
        this.resourceSize = resourceSize;
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

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }


}
