package cn.dlbdata.dj.common.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableBean<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1263869776107945188L;
	// private int pageNo;// 当前页
	// private int limit;// 每页显示数量
	private int total;// 记录总数
	private List<T> rows;// 显示数据

	// public int getPageNo() {
	// return pageNo;
	// }
	//
	// public void setPageNo(int pageNo) {
	// this.pageNo = pageNo;
	// }
	//
	// public int getLimit() {
	// return limit;
	// }
	//
	// public void setLimit(int limit) {
	// this.limit = limit;
	// }

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		if (rows == null) {
			rows = new ArrayList<T>();
		}
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
