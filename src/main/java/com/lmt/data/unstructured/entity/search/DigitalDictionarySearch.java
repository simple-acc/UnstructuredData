package com.lmt.data.unstructured.entity.search;

import com.lmt.data.unstructured.base.BaseSearch;

/**
 * @author MT-Lin
 * @date 2018/1/4 9:50
 */
public class DigitalDictionarySearch extends BaseSearch {

    private String id;

    private String parentCode;

    private String keyword;

    public String getKeyword() {
        return "%" + keyword + "%";
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
