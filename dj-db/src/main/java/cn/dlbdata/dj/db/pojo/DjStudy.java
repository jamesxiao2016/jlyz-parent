package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_study")
public class DjStudy {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 分类ID
     */
    @Column(name = "dj_type_id")
    private Long djTypeId;

    /**
     * 二级分类ID
     */
    @Column(name = "dj_sub_type_id")
    private Long djSubTypeId;

    /**
     * 地点
     */
    private String address;

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
     * 学习主题
     */
    private String name;

    /**
     * 学习内容
     */
    private String content;

    /**
     * 党员ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 党员姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 活动图片ID
     */
    @Column(name = "pic_id")
    private Long picId;

    /**
     * 审批人ID
     */
    @Column(name = "approve_id")
    private Long approveId;

    /**
     * 部门ID
     */
    @Column(name = "dj_dept_id")
    private Long djDeptId;

    /**
     * 状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;
    /**
     * 活动图片集
     */
    @Transient
    private Long[] picIds;
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
     * 获取分类ID
     *
     * @return dj_type_id - 分类ID
     */
    public Long getDjTypeId() {
        return djTypeId;
    }

    /**
     * 设置分类ID
     *
     * @param djTypeId 分类ID
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
     * 获取地点
     *
     * @return address - 地点
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地点
     *
     * @param address 地点
     */
    public void setAddress(String address) {
        this.address = address;
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
     * 获取学习主题
     *
     * @return name - 学习主题
     */
    public String getName() {
        return name;
    }

    /**
     * 设置学习主题
     *
     * @param name 学习主题
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取学习内容
     *
     * @return content - 学习内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置学习内容
     *
     * @param content 学习内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取党员ID
     *
     * @return create_user_id - 党员ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置党员ID
     *
     * @param createUserId 党员ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取党员姓名
     *
     * @return user_name - 党员姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置党员姓名
     *
     * @param userName 党员姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取活动图片ID
     *
     * @return pic_id - 活动图片ID
     */
    public Long getPicId() {
        return picId;
    }

    /**
     * 设置活动图片ID
     *
     * @param picId 活动图片ID
     */
    public void setPicId(Long picId) {
        this.picId = picId;
    }

    /**
     * 获取审批人ID
     *
     * @return approve_id - 审批人ID
     */
    public Long getApproveId() {
        return approveId;
    }

    /**
     * 设置审批人ID
     *
     * @param approveId 审批人ID
     */
    public void setApproveId(Long approveId) {
        this.approveId = approveId;
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

	public Long[] getPicIds() {
		return picIds;
	}

	public void setPicIds(Long[] picIds) {
		this.picIds = picIds;
	}
    
}