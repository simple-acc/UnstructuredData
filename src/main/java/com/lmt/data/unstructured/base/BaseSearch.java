package com.lmt.data.unstructured.base;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/4 14:36
 */
public class BaseSearch extends BaseToString{

    /**
     * 实体ID
     */
    private String id;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 用户tokenId
     */
    private String tokenId;

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
     * 数据总数
     */
    private int totalElements;

    /**
     * EntityManager查询参数数目
     */
    private int paramsCount;

    /**
     * 排序
     */
    private List<String> sort;

    public String getKeyword() {
        if (!StringUtils.isEmpty(this.keyword)){
            return "%" + this.keyword + "%";
        } else {
            return this.keyword;
        }
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getParamsCount() {
        return paramsCount;
    }

    public void setParamsCount(int paramsCount) {
        this.paramsCount = paramsCount;
    }
}
