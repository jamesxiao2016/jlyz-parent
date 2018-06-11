/**
 *  <p>Title: AdminStudyVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月11日 
 */
package cn.dlbdata.dj.db.vo.study;

import java.util.Date;

/**
 * <p>Title: AdminStudyVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月11日  
 */
public class AdminStudyVo {
	/**
	 * 自主学习id
	 */
	private Long id;
	/**
	 * 支部名称
	 */
	private String  deptName;
	/**
	 * 提交时间
	 */
	private Date createTime;
	/**
	 * 自主学习开始时间
	 */
	private Date startTime;
	/**
	 * 自主学习结束时间
	 */
	private Date endTime;
	/**
	 * 自主学习类型
	 */
	private String typeName;
	/**
	 * 自主学习发起人
	 */
	private String userName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
