package cn.dlbdata.dangjian.db.pojo;

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
     * 活动名称
     */
    private String name;

    /**
     * 所属项目
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 所属二级模块
     */
    @Column(name = "module_id")
    private Integer moduleId;

    /**
     * 地点
     */
    private String address;

    /**
     * 负责人
     */
    @Column(name = "principal_id")
    private String principalId;

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
    private String conent;

    /**
     * 活动发起人
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 活动图片ID
     */
    @Column(name = "pic_id")
    private Long picId;

    /**
     * 审批人ID
     */
    @Column(name = "approve_id")
    private Integer approveId;

    /**
     * 部门ID
     */
    @Column(name = "dj_dept_id")
    private Integer djDeptId;

    /**
     * 状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

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
     * 获取所属项目
     *
     * @return project_id - 所属项目
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置所属项目
     *
     * @param projectId 所属项目
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取所属二级模块
     *
     * @return module_id - 所属二级模块
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * 设置所属二级模块
     *
     * @param moduleId 所属二级模块
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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
     * 获取负责人
     *
     * @return principal_id - 负责人
     */
    public String getPrincipalId() {
        return principalId;
    }

    /**
     * 设置负责人
     *
     * @param principalId 负责人
     */
    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
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
     * @return conent - 活动内容
     */
    public String getConent() {
        return conent;
    }

    /**
     * 设置活动内容
     *
     * @param conent 活动内容
     */
    public void setConent(String conent) {
        this.conent = conent;
    }

    /**
     * 获取活动发起人
     *
     * @return create_user_id - 活动发起人
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置活动发起人
     *
     * @param createUserId 活动发起人
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
    public Integer getApproveId() {
        return approveId;
    }

    /**
     * 设置审批人ID
     *
     * @param approveId 审批人ID
     */
    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    /**
     * 获取部门ID
     *
     * @return dj_dept_id - 部门ID
     */
    public Integer getDjDeptId() {
        return djDeptId;
    }

    /**
     * 设置部门ID
     *
     * @param djDeptId 部门ID
     */
    public void setDjDeptId(Integer djDeptId) {
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
}