package com.lmt.data.unstructured.base;

import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/4 14:36
 */
public class BasePageRequest extends BaseToString{

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 当前页的记录数目
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 排序
     */
    private List<String> sort;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
