package com.meig.link.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_approve")
public class DjApprove {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 申请记录ID
     */
    @Column(name = "dj_apply_id")
    private Long djApplyId;

    /**
     * 审批类型
     */
    @Column(name = "audit_type")
    private Long auditType;

    /**
     * 审批人
     */
    @Column(name = "approver_id")
    private Long approverId;

    /**
     * 审批时间
     */
    @Column(name = "approve_time")
    private Date approveTime;

    /**
     * 审批意见
     */
    @Column(name = "approve_result")
    private Integer approveResult;

    /**
     * 审批说明
     */
    @Column(name = "approve_desc")
    private String approveDesc;

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
     * 获取申请记录ID
     *
     * @return dj_apply_id - 申请记录ID
     */
    public Long getDjApplyId() {
        return djApplyId;
    }

    /**
     * 设置申请记录ID
     *
     * @param djApplyId 申请记录ID
     */
    public void setDjApplyId(Long djApplyId) {
        this.djApplyId = djApplyId;
    }

    /**
     * 获取审批类型
     *
     * @return audit_type - 审批类型
     */
    public Long getAuditType() {
        return auditType;
    }

    /**
     * 设置审批类型
     *
     * @param auditType 审批类型
     */
    public void setAuditType(Long auditType) {
        this.auditType = auditType;
    }

    /**
     * 获取审批人
     *
     * @return approver_id - 审批人
     */
    public Long getApproverId() {
        return approverId;
    }

    /**
     * 设置审批人
     *
     * @param approverId 审批人
     */
    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    /**
     * 获取审批时间
     *
     * @return approve_time - 审批时间
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 设置审批时间
     *
     * @param approveTime 审批时间
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 获取审批意见
     *
     * @return approve_result - 审批意见
     */
    public Integer getApproveResult() {
        return approveResult;
    }

    /**
     * 设置审批意见
     *
     * @param approveResult 审批意见
     */
    public void setApproveResult(Integer approveResult) {
        this.approveResult = approveResult;
    }

    /**
     * 获取审批说明
     *
     * @return approve_desc - 审批说明
     */
    public String getApproveDesc() {
        return approveDesc;
    }

    /**
     * 设置审批说明
     *
     * @param approveDesc 审批说明
     */
    public void setApproveDesc(String approveDesc) {
        this.approveDesc = approveDesc;
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