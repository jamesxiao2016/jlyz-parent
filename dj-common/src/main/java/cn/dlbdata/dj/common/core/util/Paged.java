package cn.dlbdata.dj.common.core.util;

import java.util.List;

public class Paged<T> {
	private int pageSize;// 每页数据量
	private int pageNum;// 第几页
	private long total;// 总数据量
	private int pages;// 总页数
	private List<T> data;

	public Paged(int pageSize, int pageNum, long total, int pages, List<T> data) {
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.total = total;
		this.pages = pages;
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public static int normalizePageSize(Integer pageSize) {
		return pageSize == null ? 20 : pageSize;
	}

	public static int normalizePageIndex(Integer pageIndex) {
		return pageIndex == null ? 1 : pageIndex;

	}
}
