package com.xiniunet.request;

import java.io.Serializable;

public class BaseRequest implements Serializable {
    private static final long serialVersionUID = 1l;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer currentPageNumber;

    private Integer currentPageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    //pageNumber :1 pageSize 10  limit 1,10
    //pageNumber :2 pageSize 10  limit 11,20
    //pageNumber :3 pageSize 10  limit 21,30
    //综上 limit (pageNumber-1)*pageSize+1, (pageNumber-1)*pageSize+1+pageSize


    public Integer getPageSize() {
        return pageSize;
    }



    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPageNumber() {
        return (pageNumber-1)*pageSize;
    }

    public Integer getCurrentPageSize() {
        return pageSize;
    }
}
