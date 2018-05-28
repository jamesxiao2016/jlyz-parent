package cn.dlbdata.dj.vo;

import java.io.Serializable;

public class StatVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 活动次数
	private int activeNum;
	// 驿站活动次数
	private int stageNum;
	// 年度总积分
	private Float score;

	public int getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(int activeNum) {
		this.activeNum = activeNum;
	}

	public int getStageNum() {
		return stageNum;
	}

	public void setStageNum(int stageNum) {
		this.stageNum = stageNum;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
