package com.yonyou.mcloud.pojo;

import java.util.List;

/**
 * Created by hubo on 2016/2/27
 */
public class Page<T> {

    private boolean isAutoCount;

    private long totalCount;

    private boolean isFirstSetted;

    private int first;

    private int pageSize;

    private String order;

    private String orderBy;

    private boolean isPageSizeSetted;

    private boolean isOrderBySetted;

    private List<T> result;

    public boolean isAutoCount() {
        return isAutoCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isFirstSetted() {
        return isFirstSetted;
    }

    public int getFirst() {
        return first;
    }

    public boolean isPageSizeSetted() {
        return isPageSizeSetted;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public boolean isOrderBySetted() {
        return isOrderBySetted;
    }

    public String getOrder() {
        return order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setAutoCount(boolean autoCount) {
        isAutoCount = autoCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setFirstSetted(boolean firstSetted) {
        isFirstSetted = firstSetted;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setPageSizeSetted(boolean pageSizeSetted) {
        isPageSizeSetted = pageSizeSetted;
    }

    public void setOrderBySetted(boolean orderBySetted) {
        isOrderBySetted = orderBySetted;
    }

    public List<T> getResult() {
        return result;
    }
}
