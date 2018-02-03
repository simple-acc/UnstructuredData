package com.lmt.data.unstructured.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.alibaba.fastjson.annotation.JSONField;
import com.lmt.data.unstructured.base.BaseEntity;

/**
 * @author MT-Lin
 * @date 2018/1/1 22:24
 * @apiNote 用户信息表
 */
@Entity(name = "user_info")
public class UserInfo extends BaseEntity {

	private static final long serialVersionUID = 8551521706061982617L;

	/**
	 * 地址
	 */
	@Column(name = "address_code", nullable = false, length = 36)
	private String addressCode;

	/**
	 * 用户名
	 */
	@Column(name = "user_name", nullable = false, unique = true, length = 8)
	private String userName;

	/**
	 * 密码
	 */
	@Column(name = "user_password", nullable = false, length = 32)
	@JSONField(serialize = false)
	private String userPassword;

	/**
	 * 用户状态
	 */
	@Column(name = "status", nullable = false, length = 6)
	private String status;

	/**
	 * 密码错误次数
	 */
	@Column(name = "password_error_time")
	private int passwordErrorTime;

	/**
	 * 性别
	 */
	@Column(name = "sex", nullable = false, length = 6)
	private String sex;

	/**
	 * 出生日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	private Date birthday;

	/**
	 * 个人简介
	 */
	@Column(name = "description", length = 100)
	private String description;

	/**
	 * 用户类型
	 */
	@Column(name = "user_type", nullable = false, length = 6)
	private String userType;

	/**
	 * 职业
	 */
	@Column(name = "profession", nullable = false, length = 6)
	private String profession;

	/**
	 * 邮箱
	 */
	@Column(name = "email", nullable = false, length = 32)
	private String email;

	/**
	 * 电话号码
	 */
	@Column(name = "phone_number", length = 11)
	private String phoneNumber;

	/**
	 * 注册时间
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_time", nullable = false, updatable = false)
	private Date registerTime;

	@Transient
	private String sessionId;

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPasswordErrorTime() {
		return passwordErrorTime;
	}

	public void setPasswordErrorTime(int passwordErrorTime) {
		this.passwordErrorTime = passwordErrorTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
