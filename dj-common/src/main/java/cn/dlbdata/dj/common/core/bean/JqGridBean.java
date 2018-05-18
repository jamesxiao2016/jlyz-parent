package cn.dlbdata.dj.common.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.dlbdata.dj.common.core.web.vo.MsgVo;

public class JqGridBean<T> extends MsgVo<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3109733900928271634L;
	private int page;// 当前页
	private int records;// 记录总数
	private int total;// 总页数
	private List<T> rows;// 显示数据

	public JqGridBean(String result) {
		super(result);
	}

	public int getTotal() {
		if (total == 0) {
			total = 1;
		}
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}
}
