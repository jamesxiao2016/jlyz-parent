package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_statistic")
public class TStatistic {
    @Id
    private Integer id;

    @Column(name = "building_id")
    private String buildingId;

    @Column(name = "female_rate")
    private Float femaleRate;

    @Column(name = "male_rate")
    private Float maleRate;

    @Column(name = "aft60_rate")
    private Float aft60Rate;

    @Column(name = "aft70_rate")
    private Float aft70Rate;

    @Column(name = "aft80_rate")
    private Float aft80Rate;

    @Column(name = "aft90_rate")
    private Float aft90Rate;

    @Column(name = "le_college_rate")
    private Float leCollegeRate;

    @Column(name = "bachelor_rate")
    private Float bachelorRate;

    @Column(name = "master_rate")
    private Float masterRate;

    @Column(name = "doctor_rate")
    private Float doctorRate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return building_id
     */
    public String getBuildingId() {
        return buildingId;
    }

    /**
     * @param buildingId
     */
    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * @return female_rate
     */
    public Float getFemaleRate() {
        return femaleRate;
    }

    /**
     * @param femaleRate
     */
    public void setFemaleRate(Float femaleRate) {
        this.femaleRate = femaleRate;
    }

    /**
     * @return male_rate
     */
    public Float getMaleRate() {
        return maleRate;
    }

    /**
     * @param maleRate
     */
    public void setMaleRate(Float maleRate) {
        this.maleRate = maleRate;
    }

    /**
     * @return aft60_rate
     */
    public Float getAft60Rate() {
        return aft60Rate;
    }

    /**
     * @param aft60Rate
     */
    public void setAft60Rate(Float aft60Rate) {
        this.aft60Rate = aft60Rate;
    }

    /**
     * @return aft70_rate
     */
    public Float getAft70Rate() {
        return aft70Rate;
    }

    /**
     * @param aft70Rate
     */
    public void setAft70Rate(Float aft70Rate) {
        this.aft70Rate = aft70Rate;
    }

    /**
     * @return aft80_rate
     */
    public Float getAft80Rate() {
        return aft80Rate;
    }

    /**
     * @param aft80Rate
     */
    public void setAft80Rate(Float aft80Rate) {
        this.aft80Rate = aft80Rate;
    }

    /**
     * @return aft90_rate
     */
    public Float getAft90Rate() {
        return aft90Rate;
    }

    /**
     * @param aft90Rate
     */
    public void setAft90Rate(Float aft90Rate) {
        this.aft90Rate = aft90Rate;
    }

    /**
     * @return le_college_rate
     */
    public Float getLeCollegeRate() {
        return leCollegeRate;
    }

    /**
     * @param leCollegeRate
     */
    public void setLeCollegeRate(Float leCollegeRate) {
        this.leCollegeRate = leCollegeRate;
    }

    /**
     * @return bachelor_rate
     */
    public Float getBachelorRate() {
        return bachelorRate;
    }

    /**
     * @param bachelorRate
     */
    public void setBachelorRate(Float bachelorRate) {
        this.bachelorRate = bachelorRate;
    }

    /**
     * @return master_rate
     */
    public Float getMasterRate() {
        return masterRate;
    }

    /**
     * @param masterRate
     */
    public void setMasterRate(Float masterRate) {
        this.masterRate = masterRate;
    }

    /**
     * @return doctor_rate
     */
    public Float getDoctorRate() {
        return doctorRate;
    }

    /**
     * @param doctorRate
     */
    public void setDoctorRate(Float doctorRate) {
        this.doctorRate = doctorRate;
    }
}