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
	//思想汇报党员Id
	private Long userId;
	// 党员姓名
	// 汇报类型
	private Long reportType;
	// 汇报时间
	private String reportTime;
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


	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
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


}
