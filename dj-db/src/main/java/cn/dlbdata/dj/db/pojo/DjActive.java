package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_active")
public class DjActive {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动分类ID
     */
    @Column(name = "dj_type_id")
    private Long djTypeId;

    /**
     * 二级分类ID
     */
    @Column(name = "dj_sub_type_id")
    private Long djSubTypeId;

    /**
     * 活动地点
     */
    private String address;

    /**
     * 负责人姓名
     */
    @Column(name = "principal_name")
    private String principalName;

    /**
     * 是否审核
     */
    @Column(name = "has_audit")
    private Integer hasAudit;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 活动内容
     */
    private String content;

    /**
     * 活动发起人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 海报图片ID
     */
    @Column(name = "dj_pic_id")
    private Long djPicId;

    /**
     * 状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 部门ID
     */
    @Column(name = "dj_dept_id")
    private Long djDeptId;

    /**
     * 发起人姓名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 活动图集
     */
    private Long[] picIds;
    public Long[] getPicIds() {
		return picIds;
	}

	public void setPicIds(Long[] picIds) {
		this.picIds = picIds;
	}

	/**
     * 获取记录ID
     *
     * @return id - 记录ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置记录ID
     *
     * @param id 记录ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取活动分类ID
     *
     * @return dj_type_id - 活动分类ID
     */
    public Long getDjTypeId() {
        return djTypeId;
    }

    /**
     * 设置活动分类ID
     *
     * @param djTypeId 活动分类ID
     */
    public void setDjTypeId(Long djTypeId) {
        this.djTypeId = djTypeId;
    }

    /**
     * 获取二级分类ID
     *
     * @return dj_sub_type_id - 二级分类ID
     */
    public Long getDjSubTypeId() {
        return djSubTypeId;
    }

    /**
     * 设置二级分类ID
     *
     * @param djSubTypeId 二级分类ID
     */
    public void setDjSubTypeId(Long djSubTypeId) {
        this.djSubTypeId = djSubTypeId;
    }

    /**
     * 获取活动地点
     *
     * @return address - 活动地点
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置活动地点
     *
     * @param address 活动地点
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取负责人姓名
     *
     * @return principal_name - 负责人姓名
     */
    public String getPrincipalName() {
        return principalName;
    }

    /**
     * 设置负责人姓名
     *
     * @param principalName 负责人姓名
     */
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    /**
     * 获取是否审核
     *
     * @return has_audit - 是否审核
     */
    public Integer getHasAudit() {
        return hasAudit;
    }

    /**
     * 设置是否审核
     *
     * @param hasAudit 是否审核
     */
    public void setHasAudit(Integer hasAudit) {
        this.hasAudit = hasAudit;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取活动内容
     *
     * @return content - 活动内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置活动内容
     *
     * @param content 活动内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取活动发起人
     *
     * @return create_user_id - 活动发起人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置活动发起人
     *
     * @param createUserId 活动发起人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取海报图片ID
     *
     * @return dj_pic_id - 海报图片ID
     */
    public Long getDjPicId() {
        return djPicId;
    }

    /**
     * 设置海报图片ID
     *
     * @param djPicId 海报图片ID
     */
    public void setDjPicId(Long djPicId) {
        this.djPicId = djPicId;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取部门ID
     *
     * @return dj_dept_id - 部门ID
     */
    public Long getDjDeptId() {
        return djDeptId;
    }

    /**
     * 设置部门ID
     *
     * @param djDeptId 部门ID
     */
    public void setDjDeptId(Long djDeptId) {
        this.djDeptId = djDeptId;
    }

    /**
     * 获取发起人姓名
     *
     * @return user_name - 发起人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置发起人姓名
     *
     * @param userName 发起人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}