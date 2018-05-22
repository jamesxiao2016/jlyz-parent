package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_section")
public class DjSection {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 片区名称
     */
    private String name;

    /**
     * 片区人数
     */
    @Column(name = "people_num")
    private Integer peopleNum;

    /**
     * 地址
     */
    private Integer address;

    /**
     * 状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 片区负责人ID
     */
    @Column(name = "principal_id")
    private Long principalId;

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
     * 获取片区人数
     *
     * @return people_num - 片区人数
     */
    public Integer getPeopleNum() {
        return peopleNum;
    }

    /**
     * 设置片区人数
     *
     * @param peopleNum 片区人数
     */
    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public Integer getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(Integer address) {
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
     * 获取片区负责人ID
     *
     * @return principal_id - 片区负责人ID
     */
    public Long getPrincipalId() {
        return principalId;
    }

    /**
     * 设置片区负责人ID
     *
     * @param principalId 片区负责人ID
     */
    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }
}