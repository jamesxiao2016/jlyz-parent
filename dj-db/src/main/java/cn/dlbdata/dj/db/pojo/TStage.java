package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_stage")
public class TStage {
    @Id
    private Integer id;

    /**
     * 驿站名称
     */
    private String name;

    /**
     * 驿站配图
     */
    private String image;

    /**
     * 下属党支部的名称前缀
     */
    @Column(name = "party_branch_prefix")
    private String partyBranchPrefix;

    /**
     * 驿站所在建筑ID
     */
    @Column(name = "building_id")
    private String buildingId;

    /**
     * 驿站所在楼层
     */
    private String floor;

    /**
     * 驿站简介
     */
    private String summary;

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
     * 获取驿站名称
     *
     * @return name - 驿站名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置驿站名称
     *
     * @param name 驿站名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取驿站配图
     *
     * @return image - 驿站配图
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置驿站配图
     *
     * @param image 驿站配图
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取下属党支部的名称前缀
     *
     * @return party_branch_prefix - 下属党支部的名称前缀
     */
    public String getPartyBranchPrefix() {
        return partyBranchPrefix;
    }

    /**
     * 设置下属党支部的名称前缀
     *
     * @param partyBranchPrefix 下属党支部的名称前缀
     */
    public void setPartyBranchPrefix(String partyBranchPrefix) {
        this.partyBranchPrefix = partyBranchPrefix;
    }

    /**
     * 获取驿站所在建筑ID
     *
     * @return building_id - 驿站所在建筑ID
     */
    public String getBuildingId() {
        return buildingId;
    }

    /**
     * 设置驿站所在建筑ID
     *
     * @param buildingId 驿站所在建筑ID
     */
    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * 获取驿站所在楼层
     *
     * @return floor - 驿站所在楼层
     */
    public String getFloor() {
        return floor;
    }

    /**
     * 设置驿站所在楼层
     *
     * @param floor 驿站所在楼层
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * 获取驿站简介
     *
     * @return summary - 驿站简介
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置驿站简介
     *
     * @param summary 驿站简介
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}