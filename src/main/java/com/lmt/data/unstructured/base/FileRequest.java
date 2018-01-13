package com.lmt.data.unstructured.base;

/**
 * @author MT-Lin
 * @date 2018/1/12 10:05
 */
public class FileRequest extends BaseToString{

    private String resourceName;

    private String resourceFileName;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceFileName() {
        return resourceFileName;
    }

    public void setResourceFileName(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }
}
