/**
 *  <p>Title: ActiveSignUpRequest.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月23日 
 */
package cn.dlbdata.dj.db.resquest;

import java.sql.Date;

/**
 * <p>Title: ActiveSignUpRequest</p>
 * @author zhouxuan
 * <p>Description: 金领驿站活动报名接口参数映射类</p>
 * @date 2018年5月23日  
 */
public class ActiveSignUpRequest {
	/**
	 * 党员id
	 */
	private Long userId;
	/**
	 * 活动id
	 */
	private Long activeId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 签到时间
	 */
	private Date signTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getActiveId() {
		return activeId;
	}
	public void setActiveId(Long activeId) {
		this.activeId = activeId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
}
