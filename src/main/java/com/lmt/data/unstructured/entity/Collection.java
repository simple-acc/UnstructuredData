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
 * @date 2018/1/2 10:07
 * @apiNote 收藏表
 */
@Entity(name = "collection")
public class Collection extends BaseEntity {

	/**
	 * 收藏夹ID
	 */
	@Column(name = "collection_folder_id", nullable = false, length = 36)
	private String collectionFolderId;

	/**
	 * 收藏对象ID
	 */
	@Column(name = "obj_id", nullable = false, length = 36)
	private String objId;

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

	public String getCollectionFolderId() {
		return collectionFolderId;
	}

	public void setCollectionFolderId(String collectionFolderId) {
		this.collectionFolderId = collectionFolderId;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
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
