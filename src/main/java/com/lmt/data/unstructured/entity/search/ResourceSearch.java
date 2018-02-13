package com.lmt.data.unstructured.entity.search;

import com.lmt.data.unstructured.base.BaseSearch;
import com.lmt.data.unstructured.entity.Resource;

import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/13 9:25
 */
public class ResourceSearch extends BaseSearch {

    private List<Resource> resources;

    private String dissertationId;

    private String resourceId;

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getDissertationId() {
        return dissertationId;
    }

    public void setDissertationId(String dissertationId) {
        this.dissertationId = dissertationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
