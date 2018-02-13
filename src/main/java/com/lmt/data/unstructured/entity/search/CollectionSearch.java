package com.lmt.data.unstructured.entity.search;

import com.lmt.data.unstructured.base.BaseSearch;

/**
 * @author MT-Lin
 * @date 2018/1/28 15:46
 */
public class CollectionSearch extends BaseSearch {

    private String creator;

    private String objId;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
