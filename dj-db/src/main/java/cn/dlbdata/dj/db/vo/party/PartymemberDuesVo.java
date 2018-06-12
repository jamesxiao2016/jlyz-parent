/**
 *  <p>Title: PartymemberDuesVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.db.vo.party;

import java.util.Date;

/**
 * <p>Title: PartymemberDuesVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
public class PartymemberDuesVo {
	/**
	 * 党费缴纳id
	 */
	private Long id;
	/**
	 * 代缴纳年份
	 */
	private Integer duesYear;
	/**
	 * 代缴纳月份
	 */
	private Integer duesMonth; 
	/**
	 * 代缴纳金额
	 */
	private Float duesMoney;
	/**
	 * 实际缴纳金额
	 */
	private Float realMoney;
	/**
	 * 缴纳时间
	 */
	private Date paymentTime;
	/**
	 * 党员姓名
	 */
	private String userName;
	
	private Integer status;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getDuesYear() {
		return duesYear;
	}
	public void setDuesYear(Integer duesYear) {
		this.duesYear = duesYear;
	}
	public Integer getDuesMonth() {
		return duesMonth;
	}
	public void setDuesMonth(Integer duesMonth) {
		this.duesMonth = duesMonth;
	}
	public Float getDuesMoney() {
		return duesMoney;
	}
	public void setDuesMoney(Float duesMoney) {
		this.duesMoney = duesMoney;
	}
	public Float getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(Float realMoney) {
		this.realMoney = realMoney;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
}
