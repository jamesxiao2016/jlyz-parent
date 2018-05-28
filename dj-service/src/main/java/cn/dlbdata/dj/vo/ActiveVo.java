package cn.dlbdata.dj.vo;

import java.util.Date;

public class ActiveVo {
	/**
	 * 报名表id
	 */
	private Long id;
	/**
	 * 活动id
	 */
	private Long djActiveId;
	/**
	 * 用户id
	 */
	private Long djUserId;
	/**
	 * 活动是否签到：0代表未签到，1代表签到
	 */
	private Integer status;
	/**
	 * 报名时间
	 */
	private Date createTime;
	/**
	 * 签到时间
	 */
	private Date signTime;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 活动类型id
	 */
	private Long typeId;
	/**
	 * 活动二级分类id
	 */
	private Long subTypeId;
	/**
	 * 活动地址
	 */
	private String address;
	/**
	 * 活动负责人名称
	 */
	private String principalName;
	/**
	 * 活动是否审核
	 */
	private Integer hasAudit;
	/**
	 * 活动开始时间
	 */
	private Date startActiveTime;
	/**
	 * 活动结束时间
	 */
	private Date endActiveTime;
	/**
	 * 活动内容
	 */
	private String content;
	/**
	 * 活动创建人id
	 */
	private Long createUserId;
	/**
	 * 活动海报id
	 */
	private Long picId;
	/**
	 * 活动状态
	 */
	private Integer ActiveStatus;
	/**
	 * 创建活动时间
	 */
	private Date createActiveTime;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 活动类型名称
	 */
	private String activeTypeName;
	/**
	 * 活动二级分类名称
	 */
	private String subActiveTypeName;
	/**
	 * 活动二级分类内容
	 */
	private String subActiveTypeContent;
	/**
	 * 活动图片集
	 */
	private Long[] picIds;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDjActiveId() {
		return djActiveId;
	}
	public void setDjActiveId(Long djActiveId) {
		this.djActiveId = djActiveId;
	}
	public Long getDjUserId() {
		return djUserId;
	}
	public void setDjUserId(Long djUserId) {
		this.djUserId = djUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Long getSubTypeId() {
		return subTypeId;
	}
	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public Integer getHasAudit() {
		return hasAudit;
	}
	public void setHasAudit(Integer hasAudit) {
		this.hasAudit = hasAudit;
	}
	public Date getStartActiveTime() {
		return startActiveTime;
	}
	public void setStartActiveTime(Date startActiveTime) {
		this.startActiveTime = startActiveTime;
	}
	public Date getEndActiveTime() {
		return endActiveTime;
	}
	public void setEndActiveTime(Date endActiveTime) {
		this.endActiveTime = endActiveTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getPicId() {
		return picId;
	}
	public void setPicId(Long picId) {
		this.picId = picId;
	}
	public Integer getActiveStatus() {
		return ActiveStatus;
	}
	public void setActiveStatus(Integer activeStatus) {
		ActiveStatus = activeStatus;
	}
	public Date getCreateActiveTime() {
		return createActiveTime;
	}
	public void setCreateActiveTime(Date createActiveTime) {
		this.createActiveTime = createActiveTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getActiveTypeName() {
		return activeTypeName;
	}
	public void setActiveTypeName(String activeTypeName) {
		this.activeTypeName = activeTypeName;
	}
	public String getSubActiveTypeName() {
		return subActiveTypeName;
	}
	public void setSubActiveTypeName(String subActiveTypeName) {
		this.subActiveTypeName = subActiveTypeName;
	}
	public String getSubActiveTypeContent() {
		return subActiveTypeContent;
	}
	public void setSubActiveTypeContent(String subActiveTypeContent) {
		this.subActiveTypeContent = subActiveTypeContent;
	}
	public Long[] getPicIds() {
		return picIds;
	}
	public void setPicIds(Long[] picIds) {
		this.picIds = picIds;
	}
	

}
