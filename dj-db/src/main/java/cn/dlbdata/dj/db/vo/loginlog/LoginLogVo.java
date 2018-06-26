/**
 *  <p>Title: LoginLogVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.db.vo.loginlog;

import java.util.Date;

/**
 * <p>Title: LoginLogVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
public class LoginLogVo {
	/**
	 * 登录日志id
	 */
	private Long id;
	
	/**
	 * 登录账号
	 */
	private String userAccount;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 登录提示信息
	 */
	private String errorMsg;
	
	/**
	 * 登录时间
	 */
	private Date createTime;
	
	/**
	 * 支部名称
	 */
	private String deptName;
	
	/**
	 * 登录状态码
	 */
	private Integer status;
	
	/**
	 * ip地址
	 */
	private String ip;
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
