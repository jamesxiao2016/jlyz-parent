package cn.dlbdata.dj.vo;

public class PartyVo {
	// 党员ID
	private Long memberId;
	// 党员评级
	private String partyLevelName;
	// 年度积分
	private Float score;
	// 活动次数
	private int activeNum;
	// 金领驿站活动次数
	private int jlyzActiveNum;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public int getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(int activeNum) {
		this.activeNum = activeNum;
	}

	public int getJlyzActiveNum() {
		return jlyzActiveNum;
	}

	public void setJlyzActiveNum(int jlyzActiveNum) {
		this.jlyzActiveNum = jlyzActiveNum;
	}

	public String getPartyLevelName() {
		return partyLevelName;
	}

	public void setPartyLevelName(String partyLevelName) {
		this.partyLevelName = partyLevelName;
	}

}
