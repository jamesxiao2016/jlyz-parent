package cn.dlbdata.dj.db.vo.apply;

public class ScoreTypeVo {
	// 类型ID
	private Long id;
	// 类型名称
	private String name;
	// 最大分数
	private Float score;
	// 已获积分
	private Float total;
	// 待处理数量
	private Integer pendingNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Integer getPendingNum() {
		return pendingNum;
	}

	public void setPendingNum(Integer pendingNum) {
		this.pendingNum = pendingNum;
	}

}
