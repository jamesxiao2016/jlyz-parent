package cn.dlbdata.dj.db.pojo;

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
     * 申请类型
     */
    @Column(name = "audit_type")
    private Long auditType;

    /**
     * 记录ID
     */
    @Column(name = "record_id")
    private Long recordId;

    /**
     * 申请内容
     */
    @Column(name = "apply_info")
    private String applyInfo;

    /**
     * 申请人
     */
    @Column(name = "apply_id")
    private Long applyId;

    /**
     * 申请说明
     */
    @Column(name = "apply_desc")
    private String applyDesc;

    /**
     * 审批人ID
     */
    @Column(name = "approver_id")
    private Long approverId;

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
     * 获取申请类型
     *
     * @return audit_type - 申请类型
     */
    public Long getAuditType() {
        return auditType;
    }

    /**
     * 设置申请类型
     *
     * @param auditType 申请类型
     */
    public void setAuditType(Long auditType) {
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
     * 获取申请内容
     *
     * @return apply_info - 申请内容
     */
    public String getApplyInfo() {
        return applyInfo;
    }

    /**
     * 设置申请内容
     *
     * @param applyInfo 申请内容
     */
    public void setApplyInfo(String applyInfo) {
        this.applyInfo = applyInfo;
    }

    /**
     * 获取申请人
     *
     * @return apply_id - 申请人
     */
    public Long getApplyId() {
        return applyId;
    }

    /**
     * 设置申请人
     *
     * @param applyId 申请人
     */
    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取申请说明
     *
     * @return apply_desc - 申请说明
     */
    public String getApplyDesc() {
        return applyDesc;
    }

    /**
     * 设置申请说明
     *
     * @param applyDesc 申请说明
     */
    public void setApplyDesc(String applyDesc) {
        this.applyDesc = applyDesc;
    }

    /**
     * 获取审批人ID
     *
     * @return approver_id - 审批人ID
     */
    public Long getApproverId() {
        return approverId;
    }

    /**
     * 设置审批人ID
     *
     * @param approverId 审批人ID
     */
    public void setApproverId(Long approverId) {
        this.approverId = approverId;
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