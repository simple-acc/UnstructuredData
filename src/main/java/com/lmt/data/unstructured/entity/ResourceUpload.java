package com.lmt.data.unstructured.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lmt.data.unstructured.base.BaseEntity;

/**
 * @author MT-Lin
 * @date 2018/2/1 13:28
 */
@Entity(name = "resource_upload")
public class ResourceUpload extends BaseEntity {

	/**
	 * 用户ID
	 */
	@Column(name = "user_id", nullable = false, length = 36)
	private String userId;

	/**
	 * 待审核资源ID
	 */
	@Column(name = "resource_temp_id", nullable = false, length = 36)
	private String resourceTempId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResourceTempId() {
		return resourceTempId;
	}

	public void setResourceTempId(String resourceTempId) {
		this.resourceTempId = resourceTempId;
	}
}
