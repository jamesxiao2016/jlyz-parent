package cn.dlbdata.dj.db.vo.jlyz;

import javax.persistence.Column;
import javax.persistence.Id;

public class MemberStatVo {
    private Integer id;

    private String buildingId;

    private Float femaleRate;

    private Float maleRate;

    private Float aft60Rate;

    private Float aft70Rate;

    private Float aft80Rate;

    private Float aft90Rate;

    private Float leCollegeRate;

    private Float bachelorRate;

    private Float masterRate;

    private Float doctorRate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public Float getFemaleRate() {
		return femaleRate;
	}

	public void setFemaleRate(Float femaleRate) {
		this.femaleRate = femaleRate;
	}

	public Float getMaleRate() {
		return maleRate;
	}

	public void setMaleRate(Float maleRate) {
		this.maleRate = maleRate;
	}

	public Float getAft60Rate() {
		return aft60Rate;
	}

	public void setAft60Rate(Float aft60Rate) {
		this.aft60Rate = aft60Rate;
	}

	public Float getAft70Rate() {
		return aft70Rate;
	}

	public void setAft70Rate(Float aft70Rate) {
		this.aft70Rate = aft70Rate;
	}

	public Float getAft80Rate() {
		return aft80Rate;
	}

	public void setAft80Rate(Float aft80Rate) {
		this.aft80Rate = aft80Rate;
	}

	public Float getAft90Rate() {
		return aft90Rate;
	}

	public void setAft90Rate(Float aft90Rate) {
		this.aft90Rate = aft90Rate;
	}

	public Float getLeCollegeRate() {
		return leCollegeRate;
	}

	public void setLeCollegeRate(Float leCollegeRate) {
		this.leCollegeRate = leCollegeRate;
	}

	public Float getBachelorRate() {
		return bachelorRate;
	}

	public void setBachelorRate(Float bachelorRate) {
		this.bachelorRate = bachelorRate;
	}

	public Float getMasterRate() {
		return masterRate;
	}

	public void setMasterRate(Float masterRate) {
		this.masterRate = masterRate;
	}

	public Float getDoctorRate() {
		return doctorRate;
	}

	public void setDoctorRate(Float doctorRate) {
		this.doctorRate = doctorRate;
	}
    
    
}
