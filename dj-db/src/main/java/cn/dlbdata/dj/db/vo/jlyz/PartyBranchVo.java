package cn.dlbdata.dj.db.vo.jlyz;

public class PartyBranchVo {
	// 活动次数
	Integer activity_count;
	//
	String advance;
	// 楼层
	Integer floor;
	// id
	Long id;
	// 党支部评级
	String level;
	// 评级编号
	String level_code;
	// 党支部名称
	String name;
	// 下一级别名称
	String next_level;
	// 下一级别编号
	String next_level_code;
	// 党员数量
	Integer number;
	// 实际管理党员数量
	Integer real_pm_count;
	// 积分
	Float score;
	// 党支书ID
	Long secretary_id;
	// 党支书姓名
	String sj_name;
	Long superior_id;

	public Integer getActivity_count() {
		return activity_count;
	}

	public void setActivity_count(Integer activity_count) {
		this.activity_count = activity_count;
	}

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel_code() {
		return level_code;
	}

	public void setLevel_code(String level_code) {
		this.level_code = level_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNext_level() {
		return next_level;
	}

	public void setNext_level(String next_level) {
		this.next_level = next_level;
	}

	public String getNext_level_code() {
		return next_level_code;
	}

	public void setNext_level_code(String next_level_code) {
		this.next_level_code = next_level_code;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getReal_pm_count() {
		return real_pm_count;
	}

	public void setReal_pm_count(Integer real_pm_count) {
		this.real_pm_count = real_pm_count;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getSecretary_id() {
		return secretary_id;
	}

	public void setSecretary_id(Long secretary_id) {
		this.secretary_id = secretary_id;
	}

	public String getSj_name() {
		return sj_name;
	}

	public void setSj_name(String sj_name) {
		this.sj_name = sj_name;
	}

	public Long getSuperior_id() {
		return superior_id;
	}

	public void setSuperior_id(Long superior_id) {
		this.superior_id = superior_id;
	}

}
