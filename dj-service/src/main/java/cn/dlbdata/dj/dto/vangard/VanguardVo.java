package cn.dlbdata.dj.dto.vangard;

import java.io.Serializable;

/**
 * 先锋作用VO
 * 
 * @author xiaowei
 *
 */
public class VanguardVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241975440130813359L;
	// 类型（subTypeId)
	private Long vanguardType;
	// 分数
	private Float score;
	// 汇报内容
	private String content;
	// 图片ID数组
	private String[] pics;

	public Long getVanguardType() {
		return vanguardType;
	}

	public void setVanguardType(Long vanguardType) {
		this.vanguardType = vanguardType;
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

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
