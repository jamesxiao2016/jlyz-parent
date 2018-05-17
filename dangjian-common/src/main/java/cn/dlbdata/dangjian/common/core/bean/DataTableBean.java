package cn.dlbdata.dangjian.common.core.bean;

import java.io.Serializable;
import java.util.List;

public class DataTableBean<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4355856683849576709L;
	private int draw;// 绘制次数
	private int pageNum;// 当前页
	private int pageSize;// 每页显示数量
	private int recordsTotal;// 记录总数
	private int recordsFiltered;// 过滤后的记录数
	private List<T> data;// 显示数据
	private String msg;// 异常消息

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
