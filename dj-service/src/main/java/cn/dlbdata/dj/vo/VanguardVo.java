package cn.dlbdata.dj.vo;

/**
 * 先锋作用VO
 * 
 * @author xiaowei
 *
 */
public class VanguardVo {
	private Long id;
	// 用户ID
	private Long userId;
	// 用户姓名
	private Long userName;
	// 用户所在部门
	private Long deptId;
	// 类型（subTypeId)
	private Long vanguardType;
	// 分数
	private Float score;
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

	public Long getUserName() {
		return userName;
	}

	public void setUserName(Long userName) {
		this.userName = userName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
