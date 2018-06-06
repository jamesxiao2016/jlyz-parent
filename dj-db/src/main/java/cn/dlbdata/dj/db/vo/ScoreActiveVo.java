package cn.dlbdata.dj.db.vo;

import java.util.Date;

public class ScoreActiveVo {
	private Float score;
	private Date createTime;
	private String scoreDesc;
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
