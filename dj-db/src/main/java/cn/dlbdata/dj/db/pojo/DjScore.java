package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_score")
public class DjScore {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 类型ID
     */
    @Column(name = "dj_type_id")
    private Long djTypeId;

    /**
     * 二级类型ID
     */
    @Column(name = "dj_sub_type_id")
    private Long djSubTypeId;

    /**
     * 分数
     */
    private Float score;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 加分时间
     */
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 加分人（自己或审批人）
     */
    @Column(name = "apply_user_id")
    private Long applyUserId;

    /**
     * 申请人姓名
     */
    @Column(name="apply_user_name")
    private String applyUserName;

    /**
     * 审批人ID
     */
    @Column(name = "approver_id")
    private Long approverId;


    /**
     * 审批人姓名
     */
    @Column(name="approver_name")
    private String approverName;

    /**
     * 加分年份
     */
    @Column(name = "add_year")
    private Integer addYear;

    /**
     * 加分状态
     */
    @Column(name = "add_status")
    private Integer addStatus;

    /**
     * 积分说明
     */
    @Column(name = "score_desc")
    private String scoreDesc;

    /**
     * 记录ID
     */
    @Column(name = "record_id")
    private Long recordId;

    /**
     * 记录描述
     */
    @Column(name = "recrod_desc")
    private String recrodDesc;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    
    /**
     * 审批人姓名
     */
    @Column(name = "user_name")
    @Transient
    private String userName;
    
    @Transient
    private String name;
    
    @Transient
    @Column(name = "max_score")
    private Double maxScore;

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
     * 获取类型ID
     *
     * @return dj_type_id - 类型ID
     */
    public Long getDjTypeId() {
        return djTypeId;
    }

    /**
     * 设置类型ID
     *
     * @param djTypeId 类型ID
     */
    public void setDjTypeId(Long djTypeId) {
        this.djTypeId = djTypeId;
    }

    /**
     * 获取二级类型ID
     *
     * @return dj_sub_type_id - 二级类型ID
     */
    public Long getDjSubTypeId() {
        return djSubTypeId;
    }

    /**
     * 设置二级类型ID
     *
     * @param djSubTypeId 二级类型ID
     */
    public void setDjSubTypeId(Long djSubTypeId) {
        this.djSubTypeId = djSubTypeId;
    }

    /**
     * 获取分数
     *
     * @return score - 分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置分数
     *
     * @param score 分数
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取加分时间
     *
     * @return add_time - 加分时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置加分时间
     *
     * @param addTime 加分时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取加分人（自己或审批人）
     *
     * @return apply_user_id - 加分人（自己或审批人）
     */
    public Long getApplyUserId() {
        return applyUserId;
    }

    /**
     * 设置加分人（自己或审批人）
     *
     * @param applyUserId 加分人（自己或审批人）
     */
    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
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

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    /**
     * 获取加分年份
     *
     * @return add_year - 加分年份
     */
    public Integer getAddYear() {
        return addYear;
    }

    /**
     * 设置加分年份
     *
     * @param addYear 加分年份
     */
    public void setAddYear(Integer addYear) {
        this.addYear = addYear;
    }

    /**
     * 获取加分状态
     *
     * @return add_status - 加分状态
     */
    public Integer getAddStatus() {
        return addStatus;
    }

    /**
     * 设置加分状态
     *
     * @param addStatus 加分状态
     */
    public void setAddStatus(Integer addStatus) {
        this.addStatus = addStatus;
    }

    /**
     * 获取积分说明
     *
     * @return score_desc - 积分说明
     */
    public String getScoreDesc() {
        return scoreDesc;
    }

    /**
     * 设置积分说明
     *
     * @param scoreDesc 积分说明
     */
    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc;
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
     * 获取记录描述
     *
     * @return recrod_desc - 记录描述
     */
    public String getRecrodDesc() {
        return recrodDesc;
    }

    /**
     * 设置记录描述
     *
     * @param recrodDesc 记录描述
     */
    public void setRecrodDesc(String recrodDesc) {
        this.recrodDesc = recrodDesc;
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
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}