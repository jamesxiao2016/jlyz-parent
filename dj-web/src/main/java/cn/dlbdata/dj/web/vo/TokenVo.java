package cn.dlbdata.dj.web.vo;

public class TokenVo {
	// 用户ID
	private Long userId;
	// 用户姓名
	private String userName;
	// 部门ID
	private Long deptId;

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

}
