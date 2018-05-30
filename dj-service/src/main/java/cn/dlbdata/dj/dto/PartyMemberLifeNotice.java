package cn.dlbdata.dj.dto;

import java.util.Date;

public class PartyMemberLifeNotice {
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 活动开始时间
	 */
	private Date startTime;
	/**
	 * 活动结束时间
	 */
	private Date endTime;
	/**
	 * 支部id
	 */
	private Long departmentId;
	/**
	 * 0：代表未报名 1：代表报名
	 */
	private Integer signUp;
	
	private Integer pageNum;
	
	private Integer pageSize;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getSignUp() {
		return signUp;
	}
	public void setSignUp(Integer signUp) {
		this.signUp = signUp;
	}
	

}
