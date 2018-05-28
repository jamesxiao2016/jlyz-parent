package cn.dlbdata.dj.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 思想汇报VO
 * 
 * @author xiaowei
 *
 */
public class ThoughtsVo implements Serializable {
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
	// 汇报类型
	private Long reportType;
	// 汇报时间
	private Date reportTime;
	// 汇报内容
	private String content;
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

	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
