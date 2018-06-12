/**
 *  <p>Title: AdminActiveVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月11日 
 */
package cn.dlbdata.dj.db.vo.active;

import java.util.Date;

/**
 * <p>Title: AdminActiveVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月11日  
 */
public class AdminActiveVo {
	/**
	 * 活动id
	 */
	private Long id;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 活动类型
	 */
	private String activeTypeName;
	/**
	 * 活动状态
	 */
	private Integer status;
	/**
	 * 活动开始时间
	 */
	private Date startTime;
	/**
	 * 活动结束时间
	 */
	private Date endTime;
	/**
	 * 活动负责人
	 */
	private String principalName;
	/**
	 * 活动创建人
	 */
	private String createUser;
	/**
	 * 支部名称
	 */
	private String deptName;
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getActiveTypeName() {
		return activeTypeName;
	}
	public void setActiveTypeName(String activeTypeName) {
		this.activeTypeName = activeTypeName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
}
