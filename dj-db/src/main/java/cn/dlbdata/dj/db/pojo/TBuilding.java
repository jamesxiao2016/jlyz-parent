package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_building")
public class TBuilding {
    /**
     * 建筑主键，也对应页面上模型ID
     */
    @Id
    private String id;

    /**
     * 建筑名称
     */
    private String name;

    /**
     * 总楼层数
     */
    @Column(name = "floors_total")
    private Integer floorsTotal;

    @Column(name = "party_branche_count")
    private Integer partyBrancheCount;

    @Column(name = "party_member_count")
    private Integer partyMemberCount;

    /**
     * 建筑所属片区ID
     */
    @Column(name = "section_id")
    private Integer sectionId;

    /**
     * 界面显示需要
     */
    private String offset;

    /**
     * 获取建筑主键，也对应页面上模型ID
     *
     * @return id - 建筑主键，也对应页面上模型ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置建筑主键，也对应页面上模型ID
     *
     * @param id 建筑主键，也对应页面上模型ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取建筑名称
     *
     * @return name - 建筑名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置建筑名称
     *
     * @param name 建筑名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取总楼层数
     *
     * @return floors_total - 总楼层数
     */
    public Integer getFloorsTotal() {
        return floorsTotal;
    }

    /**
     * 设置总楼层数
     *
     * @param floorsTotal 总楼层数
     */
    public void setFloorsTotal(Integer floorsTotal) {
        this.floorsTotal = floorsTotal;
    }

    /**
     * @return party_branche_count
     */
    public Integer getPartyBrancheCount() {
        return partyBrancheCount;
    }

    /**
     * @param partyBrancheCount
     */
    public void setPartyBrancheCount(Integer partyBrancheCount) {
        this.partyBrancheCount = partyBrancheCount;
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
     * 获取建筑所属片区ID
     *
     * @return section_id - 建筑所属片区ID
     */
    public Integer getSectionId() {
        return sectionId;
    }

    /**
     * 设置建筑所属片区ID
     *
     * @param sectionId 建筑所属片区ID
     */
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * 获取界面显示需要
     *
     * @return offset - 界面显示需要
     */
    public String getOffset() {
        return offset;
    }

    /**
     * 设置界面显示需要
     *
     * @param offset 界面显示需要
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }
}