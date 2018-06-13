package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_building")
public class DjBuilding {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 片区ID
     */
    @Column(name = "dj_section_id")
    private Long djSectionId;

    /**
     * 片区名称
     */
    private String name;

    /**
     * 楼层数
     */
    @Column(name = "floor_num")
    private Integer floorNum;

    /**
     * 党支部数
     */
    @Column(name = "party_num")
    private Integer partyNum;

    /**
     * 党员人数
     */
    @Column(name = "people_num")
    private Integer peopleNum;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 楼宇负责人ID
     */
    @Column(name = "principal_id")
    private Long principalId;

    /**
     * 楼宇编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 获取记录ID
     *
     * @return id - 记录ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置记录ID
     *
     * @param id 记录ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取片区ID
     *
     * @return dj_section_id - 片区ID
     */
    public Long getDjSectionId() {
        return djSectionId;
    }

    /**
     * 设置片区ID
     *
     * @param djSectionId 片区ID
     */
    public void setDjSectionId(Long djSectionId) {
        this.djSectionId = djSectionId;
    }

    /**
     * 获取片区名称
     *
     * @return name - 片区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置片区名称
     *
     * @param name 片区名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取楼层数
     *
     * @return floor_num - 楼层数
     */
    public Integer getFloorNum() {
        return floorNum;
    }

    /**
     * 设置楼层数
     *
     * @param floorNum 楼层数
     */
    public void setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
    }

    /**
     * 获取党支部数
     *
     * @return party_num - 党支部数
     */
    public Integer getPartyNum() {
        return partyNum;
    }

    /**
     * 设置党支部数
     *
     * @param partyNum 党支部数
     */
    public void setPartyNum(Integer partyNum) {
        this.partyNum = partyNum;
    }

    /**
     * 获取党员人数
     *
     * @return people_num - 党员人数
     */
    public Integer getPeopleNum() {
        return peopleNum;
    }

    /**
     * 设置党员人数
     *
     * @param peopleNum 党员人数
     */
    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取楼宇负责人ID
     *
     * @return principal_id - 楼宇负责人ID
     */
    public Long getPrincipalId() {
        return principalId;
    }

    /**
     * 设置楼宇负责人ID
     *
     * @param principalId 楼宇负责人ID
     */
    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}