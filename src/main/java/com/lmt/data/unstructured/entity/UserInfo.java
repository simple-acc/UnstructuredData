package com.lmt.data.unstructured.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author MT-Lin
 * @date 2018/1/1 22:24
 * @apiNote 用户信息表
 */
@Entity(name = "user_info")
public class UserInfo extends BaseEntity{

    /**
     * 地址
     */
    @Column(name="address_id", nullable = false, length = 36)
    private String addressId;

    /**
     * 用户名
     */
    @Column(name="user_name", nullable = false, length = 8)
    private String userName;

    /**
     * 密码
     */
    @Column(name="user_password", nullable = false, length = 16)
    private String userPassword;

    /**
     * 个人简介
     */
    @Column(name="description", nullable = false, length = 100)
    private String description;

    /**
     * 性别
     */
    @Column(name="sex", nullable = false, length = 6)
    private String sex;

    /**
     * 出生日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name="birthday")
    private Date birthday;

    /**
     * 职位
     */
    @Column(name="post", nullable = false, length = 6)
    private String post;

    /**
     * 邮箱
     */
    @Column(name="email", nullable = false, length = 32)
    private String email;

    /**
     * 电话号码
     */
    @Column(name="phone_number", nullable = false, length = 11)
    private String phoneNumber;

    /**
     * 注册时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name="register_time", nullable = false, updatable = false)
    private Date registerTime;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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
}
