package com.lmt.data.unstructured.entity;

import com.lmt.data.unstructured.base.BaseEntity;

import javax.persistence.*;

/**
 * @author MT-Lin
 * @date 2018/1/1 21:41
 * @apiNote 数字字典表
 */
@Entity(name = "digital_dictionary")
public class DigitalDictionary extends BaseEntity {

    /**
     * 名称
     */
    @Column(name="designation", length=32)
    private String designation;

    /**
     * 编码
     */
    @Column(name="code",nullable=false, length=6)
    private String code;

    /**
     * 父类编码
     */
    @Column(name="parent_code",nullable=false, length=6)
    private String parentCode;

    /**
     * 描述
     */
    @Column(name="description",nullable=false, length=100)
    private String description;

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
}
