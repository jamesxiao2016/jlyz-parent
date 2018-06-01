/**
 *  <p>Title: ReviewScheduleListVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月1日 
 */
package cn.dlbdata.dj.db.vo.study;

import java.util.Date;

/**
 * <p>Title: ReviewScheduleListVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月1日  
 */
public class ReviewScheduleListVo {
	private Long id;
	/**
	 * 审批人名字
	 */
	private String approveName;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 审批状态
	 */
	private Integer status;
	
	private Long[] picIds;

	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public Long[] getPicIds() {
		return picIds;
	}
	public void setPicIds(Long[] picIds) {
		this.picIds = picIds;
	}
	
}
