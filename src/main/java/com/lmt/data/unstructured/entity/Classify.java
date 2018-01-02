package com.lmt.data.unstructured.entity;

import com.lmt.data.unstructured.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author MT-Lin
 * @date 2018/1/1 23:13
 * @apiNote 分类表
 */
@Entity(name = "classify")
public class Classify extends BaseEntity {

    /**
     * 父级分类ID
     */
    @Column(name = "parent_id", length = 36)
    private String parentId;

    /**
     * 创建者
     */
    @Column(name = "creator", nullable = false, length = 36)
    private String creator;

    /**
     * 分类名称
     */
    @Column(name = "designation", nullable = false, length = 32)
    private String designation;

    /**
     * 备注
     */
    @Column(name = "remark", length = 300)
    private String remark;

    /**
     * 分类类型（代码表006）
     */
    @Column(name = "classifyType", nullable = false, length = 6)
    private String classifyType;

    /**
     * 收藏数
     */
    @Column(name = "collection_num")
    private int collectionNum;

    /**
     * 下载数
     */
    @Column(name = "download_num")
    private int downloadNum;

    /**
     * 上传数
     */
    @Column(name = "upload_num")
    private int uploadNum;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name="create_time", nullable = false, updatable = false)
    private Date createTime;

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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClassifyType() {
        return classifyType;
    }

    public void setClassifyType(String classifyType) {
        this.classifyType = classifyType;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public int getUploadNum() {
        return uploadNum;
    }

    public void setUploadNum(int uploadNum) {
        this.uploadNum = uploadNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
