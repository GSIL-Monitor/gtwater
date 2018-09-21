package com.gt.manager.util;

/**
 * author : han 2017-12-12
 */

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("unchecked")
public class JdbcPage<T> implements Serializable {
    private List<T> elements;
    private int pageSize;
    private int curPage;
    private long totalElements = 0;
    private long pages = 0;

    public JdbcPage(List<T> elements, long total,long pages, int pageNumber, int pageSize) {
        this.curPage = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = total;
        this.pages = pages;
        int lastPageNumber = this.getLastPageNumber();
        if (Integer.MAX_VALUE == this.curPage || this.curPage > lastPageNumber) {
            this.curPage = lastPageNumber;
        }
        if (this.curPage < 1) {
            this.curPage = 1;
        }
        this.elements = elements;

    }


    public boolean isFirstPage() {
        return getCurPage() == 1;
    }

    public boolean isLastPage() {
        return getCurPage() >= getLastPageNumber();
    }

    public boolean hasNextPage() {
        return getLastPageNumber() > getCurPage();
    }

    public boolean hasPreviousPage() {
        return getCurPage() > 1;
    }

    public int getLastPageNumber() {
        return (int) (totalElements % this.pageSize == 0 ? totalElements / this.pageSize : totalElements / this.pageSize + 1);
    }

    /**
     * 返回List类型数据
     *
     * @return List数据源
     */
    public List<T> getElements() {
        return elements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public long getPages() {
        return pages;
    }

    public int getThisPageFirstElementNumber() {
        return (getCurPage() - 1) * getPageSize() + 1;
    }

    public long getThisPageLastElementNumber() {
        int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
        return getTotalElements() < fullPage ? getTotalElements() : fullPage;
    }

    public int getNextPage() {
        return getCurPage() + 1;
    }

    public int getPreviousPage() {
        return getCurPage() - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurPage() {
        return curPage;
    }
}
