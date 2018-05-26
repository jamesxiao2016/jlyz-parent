package cn.dlbdata.dj.common.core.util;

import java.util.List;

public class Paged<T> {
    private int pageSize;//每页数据量
    private int pageIndex;//第几页
    private long totalElements;//总数据量
    private int totalPages;//总页数
    private List<T> content;

    public Paged(int pageSize, int pageIndex, long totalElements, int totalPages, List<T> content) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
    public static int normalizePageSize(Integer pageSize) {
        return pageSize == null ? 10 : pageSize;
    }
    public static int normalizePageIndex(Integer pageIndex) {
        return pageIndex ==null ? 1 :pageIndex;

    }
}
