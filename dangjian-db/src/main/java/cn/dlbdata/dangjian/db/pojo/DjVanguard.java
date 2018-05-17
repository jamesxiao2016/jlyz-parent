package cn.dlbdata.dangjian.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_vanguard")
public class DjVanguard {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 类型
     */
    private Integer type;

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
     * 分数
     */
    private Float score;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户ID
     */
    @Column(name = "dj_user_id")
    private Long djUserId;

    /**
     * 部门ID
     */
    @Column(name = "dj_dept_id")
    private Long djDeptId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取类型
     *
     * @return type - 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取分数
     *
     * @return score - 分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置分数
     *
     * @param score 分数
     */
    public void setScore(Float score) {
        this.score = score;
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
     * 获取用户ID
     *
     * @return dj_user_id - 用户ID
     */
    public Long getDjUserId() {
        return djUserId;
    }

    /**
     * 设置用户ID
     *
     * @param djUserId 用户ID
     */
    public void setDjUserId(Long djUserId) {
        this.djUserId = djUserId;
    }

    /**
     * 获取部门ID
     *
     * @return dj_dept_id - 部门ID
     */
    public Long getDjDeptId() {
        return djDeptId;
    }

    /**
     * 设置部门ID
     *
     * @param djDeptId 部门ID
     */
    public void setDjDeptId(Long djDeptId) {
        this.djDeptId = djDeptId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}