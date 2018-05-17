package cn.dlbdata.dangjian.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_apply")
public class DjApply {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 审批类型
     */
    @Column(name = "audit_type")
    private Integer auditType;

    /**
     * 记录ID
     */
    @Column(name = "record_id")
    private Long recordId;

    /**
     * 审批人
     */
    @Column(name = "approver_id")
    private Long approverId;

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
     * 获取审批类型
     *
     * @return audit_type - 审批类型
     */
    public Integer getAuditType() {
        return auditType;
    }

    /**
     * 设置审批类型
     *
     * @param auditType 审批类型
     */
    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    /**
     * 获取记录ID
     *
     * @return record_id - 记录ID
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * 设置记录ID
     *
     * @param recordId 记录ID
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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