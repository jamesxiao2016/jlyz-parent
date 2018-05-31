package cn.dlbdata.dj.db.vo.score;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

public class ScoreVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private Long id;

	/**
	 * 二级类型ID
	 */
	@Transient
	private Long djSubTypeId;

	/**
	 * 积分类型
	 */
	@Transient
	private String typeName;

	/**
	 * 分数
	 */
	@Transient
	private Float score;

	/**
	 * 用户ID
	 */
	@Transient
	private Long userId;

	/**
	 * 加分时间
	 */
	@Transient
	private Date addTime;

	/**
	 * 审批人ID
	 */
	@Transient
	private Long approverId;

	/**
	 * 审批人姓名
	 */
	@Transient
	private String approverName;
	/**
	 * 积分说明
	 */
	@Transient
	private String scoreDesc;
	
	@Transient
	private String recrodDesc;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDjSubTypeId() {
		return djSubTypeId;
	}
	public void setDjSubTypeId(Long djSubTypeId) {
		this.djSubTypeId = djSubTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Long getApproverId() {
		return approverId;
	}
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getScoreDesc() {
		return scoreDesc;
	}
	public void setScoreDesc(String scoreDesc) {
		this.scoreDesc = scoreDesc;
	}
	public String getRecrodDesc() {
		return recrodDesc;
	}
	public void setRecrodDesc(String recrodDesc) {
		this.recrodDesc = recrodDesc;
	}

}
