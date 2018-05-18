package cn.dlbdata.dj.common.core.web.vo;

import java.util.Map;

public class QueryParamVo {
	// 查询ID
	private String sId;
	// 查询参数
	private Map<String, Object> qryParam;
	// 偏移
	private int offset;
	// 每页显示数量
	private int limit;

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public Map<String, Object> getQryParam() {
		return qryParam;
	}

	public void setQryParam(Map<String, Object> qryParam) {
		this.qryParam = qryParam;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
