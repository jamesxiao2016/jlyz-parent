package cn.dlbdata.dj.dto.vangard;


import java.io.Serializable;

/**
 * 先锋作用VO
 * 
 * @author xiaowei
 *
 */
public class VanguardParamVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241975440130813359L;

	// 用户ID
	private Long userId;

	private VanguardVo honor;//获得荣誉
	private VanguardVo recognition;//先锋表彰
	private VanguardVo vgd;//先锋模范

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public VanguardVo getHonor() {
		return honor;
	}

	public void setHonor(VanguardVo honor) {
		this.honor = honor;
	}

	public VanguardVo getRecognition() {
		return recognition;
	}

	public void setRecognition(VanguardVo recognition) {
		this.recognition = recognition;
	}

	public VanguardVo getVgd() {
		return vgd;
	}

	public void setVgd(VanguardVo vgd) {
		this.vgd = vgd;
	}
}
