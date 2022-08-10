package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.List;

public class PageObject<T> implements Serializable {
    private static final long serialVersionUID = -5211378236830407072L;
    //总记录数（查询时获得）
    private Integer rowCount = 0;
    //当前页记录
    private List<T> records;
    //总页数（计算后获得）
    private Integer pageCount;
    //当前页码值
    private Integer pageCurrent;
    //页面大小（当前页最多显示记录）
    private Integer pageSize = 3;

    public PageObject() {
    }

//    public PageObject(Integer rowCount, List<T> records, Integer pageCount, Integer pageCurrent, Integer pageSize) {
public PageObject(Integer rowCount, List<T> records, Integer pageCurrent, Integer pageSize) {

        this.rowCount = rowCount;
        this.records = records;
        this.pageCount = (rowCount-1)/pageSize+1;
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
