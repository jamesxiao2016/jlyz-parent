package cn.dlbdata.dj.vo;

public class UserVo {
	//用户ID
	private Long userId;
	//党员ID
	private Long memeberId;
	//账号
	private String name;
	//token
	private String token;
	//用户姓名
	private String userName;
	//部门ID
	private Long deptId;
	//部门名称
	private String deptName;
	//党支书姓名
	private String partyBranchName;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMemeberId() {
		return memeberId;
	}
	public void setMemeberId(Long memeberId) {
		this.memeberId = memeberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPartyBranchName() {
		return partyBranchName;
	}
	public void setPartyBranchName(String partyBranchName) {
		this.partyBranchName = partyBranchName;
	}
	
	
}
