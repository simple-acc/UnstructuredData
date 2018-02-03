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
 * @date 2018/1/11 22:33
 */
@Entity(name = "login_log")
public class LoginLog extends BaseEntity {

	private static final long serialVersionUID = -3002650188183948661L;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id", nullable = false, length = 36)
	private String userId;

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
	 * 登录结果（代码表009）
	 */
	@Column(name = "result", nullable = false, length = 6)
	private String result;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_time", nullable = false, updatable = false)
	private Date loginTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "exit_time")
	private Date exitTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
}
