package com.lmt.data.unstructured.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.lmt.data.unstructured.base.BaseEntity;

/**
 * @author MT-Lin
 * @date 2018/1/1 21:41
 * @apiNote 数字字典表
 */
@Entity(name = "digital_dictionary")
public class DigitalDictionary extends BaseEntity {

	private static final long serialVersionUID = -1633270402884142916L;

	/**
	 * 名称
	 */
	@Column(name = "designation", length = 32)
	private String designation;

	/**
	 * 编码
	 */
	@Column(name = "code", nullable = false, length = 6, unique = true)
	private String code;

	/**
	 * 父类编码
	 */
	@Column(name = "parent_code", length = 6)
	private String parentCode;

	/**
	 * 描述
	 */
	@Column(name = "description", nullable = false, length = 100)
	private String description;

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

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 修改时间
	 */
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_time")
	private Date modificationTime;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
}
