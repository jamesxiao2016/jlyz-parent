package cn.dlbdata.dj.vo;

public class ApplyVo {
	// 分类ID
	private Long djTypeId;
	// 二级分类ID
	private Long djSubTypeId;
	// 加分分数
	private Float score;
	// 记录ID
	private Long recordId;
	// 记录表名称
	private String tableName;
	// 申请内容
	private String content;
	// 申请说明
	private String remark;

	public Long getDjTypeId() {
		return djTypeId;
	}

	public void setDjTypeId(Long djTypeId) {
		this.djTypeId = djTypeId;
	}

	public Long getDjSubTypeId() {
		return djSubTypeId;
	}

	public void setDjSubTypeId(Long djSubTypeId) {
		this.djSubTypeId = djSubTypeId;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
