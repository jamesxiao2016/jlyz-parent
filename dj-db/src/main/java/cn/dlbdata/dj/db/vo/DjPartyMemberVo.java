package cn.dlbdata.dj.db.vo;

import java.sql.Date;

public class DjPartyMemberVo {
	private String name;
	private Integer sex;
	private Float totalScore;
	private Integer educationCode;
	private Integer partyPostCode;
	private Date birthDate;
	
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getEducationCode() {
		return educationCode;
	}
	public void setEducationCode(Integer educationCode) {
		this.educationCode = educationCode;
	}
	public Integer getPartyPostCode() {
		return partyPostCode;
	}
	public void setPartyPostCode(Integer partyPostCode) {
		this.partyPostCode = partyPostCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Float getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}
}
