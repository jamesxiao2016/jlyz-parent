package cn.dlbdata.dj.db.vo.admin;

public class AdminStatVo {
	// 党支部数量
	private int partybanchNum;
	// 党员数量
	private int partyNum;
	// 活动次数
	private int activeNum;
	// 自主学习次数
	private int studyNum;
	// 党费金额
	private Float partydusNum;

	public int getPartybanchNum() {
		return partybanchNum;
	}

	public void setPartybanchNum(int partybanchNum) {
		this.partybanchNum = partybanchNum;
	}

	public int getPartyNum() {
		return partyNum;
	}

	public void setPartyNum(int partyNum) {
		this.partyNum = partyNum;
	}

	public int getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(int activeNum) {
		this.activeNum = activeNum;
	}

	public int getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(int studyNum) {
		this.studyNum = studyNum;
	}

	public Float getPartydusNum() {
		return partydusNum;
	}

	public void setPartydusNum(Float partydusNum) {
		this.partydusNum = partydusNum;
	}

}
