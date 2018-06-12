/**
 *  <p>Title: OperationLogVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.db.vo.operationlog;

import java.util.Date;

/**
 * <p>Title: OperationLogVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
public class OperationLogVo {
	/**
	 * 操作日志id
	 */
	private Long id;
	/**
	 * 操作人
	 */
	private String userName;
	/**
	 * 操作位置
	 */
	private String optName;
	/**
	 * 状态码
	 */
	private Integer status;
	/**
	 * 操作信息
	 */
	private String errorMsg;
	/**
	 * 支部名称
	 */
	private String deptName;
	/**
	 * 操作时间
	 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
