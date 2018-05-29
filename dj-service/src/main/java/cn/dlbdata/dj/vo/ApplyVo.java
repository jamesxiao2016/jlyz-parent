package cn.dlbdata.dj.vo;

import java.io.Serializable;

public class ApplyVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 分类ID
	private Long djTypeId;
	// 二级分类ID
	private Long djSubTypeId;
	// 分类名称
	private String typeName;
	// 二级分类名称
	private String subTypeName;
	// 加分分数
	private Float score;
	// 记录ID
	private Long recordId;
	// 记录表名称
	private String tableName;
	// 申请内容
	private String content;
	// 申请说明
	private String remark;
	// 加分人
	private Long userId;
	// 加分人姓名
	private String userName;
	//申请人所在部门
	private Long djDeptId;
	// 审批角色ID
	private Long roleId;

	public Long getDjTypeId() {
		return djTypeId;
	}

	public void setDjTypeId(Long djTypeId) {
		this.djTypeId = djTypeId;
	}

	public Long getDjSubTypeId() {
		return djSubTypeId;
	}

	public void setDjSubTypeId(Long djSubTypeId) {
		this.djSubTypeId = djSubTypeId;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDjDeptId() {
		return djDeptId;
	}

	public void setDjDeptId(Long djDeptId) {
		this.djDeptId = djDeptId;
	}

}
