package cn.dlbdata.dj.db.vo;

import java.util.Date;

public class ScoreActiveVo {
	private Float score;
	private Date createTime;
	private String scoreDesc;
	private String subTypeName;
	private Long djTypeId;
	private Integer status;
	private Long recordId;
	
	
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getDjTypeId() {
		return djTypeId;
	}
	public void setDjTypeId(Long djTypeId) {
		this.djTypeId = djTypeId;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getScoreDesc() {
		return scoreDesc;
	}
	public void setScoreDesc(String scoreDesc) {
		this.scoreDesc = scoreDesc;
	}

	
}
