package com.lmt.data.unstructured.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.lmt.data.unstructured.base.BaseEntity;

/**
 * @author MT-Lin
 * @date 2018/1/2 10:29
 * @apiNote 资源标签表
 */
@Entity(name = "tag")
public class Tag extends BaseEntity {

	private static final long serialVersionUID = -4741298463953801402L;

	/**
	 * 所属资源ID
	 */
	@Column(name = "resource_id", nullable = false, length = 36)
	private String resourceId;

	/**
	 * 标签
	 */
	@Column(name = "tag", length = 300)
	private String tag;

	/**
	 * 创建时间
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, updatable = false)
	private Date createTime;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}
}
