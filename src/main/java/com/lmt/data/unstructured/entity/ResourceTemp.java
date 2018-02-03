package com.lmt.data.unstructured.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.lmt.data.unstructured.base.BaseEntity;

/**
 * @author MT-Lin
 * @date 2018/1/2 10:34
 * @apiNote 资源表（待审核）
 */
@Entity(name = "resource_temp")
public class ResourceTemp extends BaseEntity {

	private static final long serialVersionUID = -4230551596586829368L;

	/**
	 * 作者
	 */
	@Column(name = "author_id", nullable = false, length = 36)
	private String authorId;

	/**
	 * 所属分类ID
	 */
	@Column(name = "classify_id", nullable = false, length = 36)
	private String classifyId;

	/**
	 * 资源库资源ID
	 */
	@Column(name = "resource_id", length = 36)
	private String resourceId;

	/**
	 * 资源名称
	 */
	@Column(name = "designation", nullable = false, length = 32)
	private String designation;

	/**
	 * 资源描述
	 */
	@Column(name = "description", length = 300)
	private String description;

	/**
	 * 资源类型
	 */
	@Column(name = "resource_type", nullable = false, length = 6)
	private String resourceType;

	/**
	 * 资源大小（单位KB）
	 */
	@Column(name = "resource_size", nullable = false, columnDefinition = "double(10,2) default '0.00'")
	private double resourceSize;

	/**
	 * 资源 MD5 值
	 */
	@Column(name = "md5", nullable = false, length = 32)
	private String md5;

	/**
	 * 上传时间
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "upload_time", nullable = false, updatable = false)
	private Date uploadTime;

	@Transient
	private String operationCode;

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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public double getResourceSize() {
		return resourceSize;
	}

	public void setResourceSize(double resourceSize) {
		this.resourceSize = resourceSize;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
}
