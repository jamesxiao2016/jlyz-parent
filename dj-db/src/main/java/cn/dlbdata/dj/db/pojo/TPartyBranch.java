package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_party_branch")
public class TPartyBranch {
    /**
     * 党支部编号
     */
    @Id
    private Integer id;

    /**
     * 党支部名称
     */
    private String name;

    /**
     * 党支部书记ID
     */
    @Column(name = "secretary_id")
    private Integer secretaryId;

    /**
     * 上级党支部
     */
    @Column(name = "superior_id")
    private Integer superiorId;

    /**
     * 党支部定级
     */
    @Column(name = "level_code")
    private String levelCode;

    /**
     * 党支部当前总积分
     */
    private Float score;

    @Column(name = "party_member_count")
    private Integer partyMemberCount;

    /**
     * 党支部所在建筑ID
     */
    @Column(name = "building_id")
    private String buildingId;

    /**
     * 党支部所在楼层
     */
    private String floor;

    /**
     * 获取党支部编号
     *
     * @return id - 党支部编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置党支部编号
     *
     * @param id 党支部编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取党支部名称
     *
     * @return name - 党支部名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置党支部名称
     *
     * @param name 党支部名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取党支部书记ID
     *
     * @return secretary_id - 党支部书记ID
     */
    public Integer getSecretaryId() {
        return secretaryId;
    }

    /**
     * 设置党支部书记ID
     *
     * @param secretaryId 党支部书记ID
     */
    public void setSecretaryId(Integer secretaryId) {
        this.secretaryId = secretaryId;
    }

    /**
     * 获取上级党支部
     *
     * @return superior_id - 上级党支部
     */
    public Integer getSuperiorId() {
        return superiorId;
    }

    /**
     * 设置上级党支部
     *
     * @param superiorId 上级党支部
     */
    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    /**
     * 获取党支部定级
     *
     * @return level_code - 党支部定级
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * 设置党支部定级
     *
     * @param levelCode 党支部定级
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * 获取党支部当前总积分
     *
     * @return score - 党支部当前总积分
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置党支部当前总积分
     *
     * @param score 党支部当前总积分
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * @return party_member_count
     */
    public Integer getPartyMemberCount() {
        return partyMemberCount;
    }

    /**
     * @param partyMemberCount
     */
    public void setPartyMemberCount(Integer partyMemberCount) {
        this.partyMemberCount = partyMemberCount;
    }

    /**
     * 获取党支部所在建筑ID
     *
     * @return building_id - 党支部所在建筑ID
     */
    public String getBuildingId() {
        return buildingId;
    }

    /**
     * 设置党支部所在建筑ID
     *
     * @param buildingId 党支部所在建筑ID
     */
    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * 获取党支部所在楼层
     *
     * @return floor - 党支部所在楼层
     */
    public String getFloor() {
        return floor;
    }

    /**
     * 设置党支部所在楼层
     *
     * @param floor 党支部所在楼层
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }
}