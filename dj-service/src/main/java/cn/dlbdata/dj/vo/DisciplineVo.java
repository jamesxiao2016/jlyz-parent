package cn.dlbdata.dj.vo;

import java.io.Serializable;

/**
 * 遵章守纪VO
 * 
 * @author xiaowei
 *
 */
public class DisciplineVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 党员ID
	private Long userId;
	// 扣分原因
	private String reason;
	// 原因描述
	private String reasonDesc;
	// 图片ID数组
	private String[] pics;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}
}
