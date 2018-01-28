package com.lmt.data.unstructured.entity.search;

import com.lmt.data.unstructured.base.BaseSearch;

/**
 * @author MT-Lin
 * @date 2018/1/28 20:00
 */
public class ResourceDownloadSearch extends BaseSearch {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
