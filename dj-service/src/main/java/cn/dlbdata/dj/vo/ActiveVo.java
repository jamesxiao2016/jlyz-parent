package cn.dlbdata.dj.vo;

import java.util.Date;

public class ActiveVo {
	private Integer offSet;
	private Integer set;
	/**
	 * 活动id
	 */
	private Long activeId;
	/**
	 * 活动名称
	 */
	private String activeName;
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
	 * 地点
	 */
	private String address;
	/**
	 * 活动发起人id
	 */
	private Long createUserId;
	/**
	 * 活动内容
	 */
	private String content;
	/**
	 * 活动负责人
	 */
	private String principalId;
	/**
	 * 支部id
	 */
	private Long departmentId;
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public Integer getOffSet() {
		return offSet;
	}
	public void setOffSet(Integer offSet) {
		this.offSet = offSet;
	}
	public Integer getSet() {
		return set;
	}
	public void setSet(Integer set) {
		this.set = set;
	}
	public Long getActiveId() {
		return activeId;
	}
	public void setActiveId(Long activeId) {
		this.activeId = activeId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	


}
