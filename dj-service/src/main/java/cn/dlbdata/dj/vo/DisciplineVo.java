package cn.dlbdata.dj.vo;

import java.io.Serializable;

/**
 * 遵章守纪VO
 * 
 * @author xiaowei
 *
 */
public class DisciplineVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	// 用户ID
	private Long userId;
	// 用户姓名
	private String userName;
	// 用户所在部门
	private Long deptId;
	// 扣分原因
	private String reason;
	// 原因描述
	private String reasonDesc;
	// 图片ID数组
	private String[] pics;

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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
