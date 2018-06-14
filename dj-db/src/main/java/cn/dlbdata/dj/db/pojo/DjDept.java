package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_dept")
public class DjDept {
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
     * 楼宇ID
     */
    @Column(name = "dj_building_id")
    private Long djBuildingId;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门人数
     */
    @Column(name = "people_num")
    private Integer peopleNum;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 上级部门ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门层级
     */
    @Column(name = "dept_level")
    private String deptLevel;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 部门负责人ID
     */
    @Column(name = "principal_id")
    private Long principalId;
    
    /**
     * 部门负责人姓名
     */
    @Column(name = "principal_name")
    private String principalName;
    
    /**
     * 党支部评级
     */
    private String honor;

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
     * 获取楼宇ID
     *
     * @return dj_building_id - 楼宇ID
     */
    public Long getDjBuildingId() {
        return djBuildingId;
    }

    /**
     * 设置楼宇ID
     *
     * @param djBuildingId 楼宇ID
     */
    public void setDjBuildingId(Long djBuildingId) {
        this.djBuildingId = djBuildingId;
    }

    /**
     * 获取楼层
     *
     * @return floor - 楼层
     */
    public Integer getFloor() {
        return floor;
    }

    /**
     * 设置楼层
     *
     * @param floor 楼层
     */
    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    /**
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取部门人数
     *
     * @return people_num - 部门人数
     */
    public Integer getPeopleNum() {
        return peopleNum;
    }

    /**
     * 设置部门人数
     *
     * @param peopleNum 部门人数
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
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * 获取上级部门ID
     *
     * @return parent_id - 上级部门ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置上级部门ID
     *
     * @param parentId 上级部门ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取部门层级
     *
     * @return dept_level - 部门层级
     */
    public String getDeptLevel() {
        return deptLevel;
    }

    /**
     * 设置部门层级
     *
     * @param deptLevel 部门层级
     */
    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
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
     * 获取部门负责人ID
     *
     * @return principal_id - 部门负责人ID
     */
    public Long getPrincipalId() {
        return principalId;
    }

    /**
     * 设置部门负责人ID
     *
     * @param principalId 部门负责人ID
     */
    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}
}