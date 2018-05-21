package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_discipline")
public class DjDiscipline {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 扣分原因
     */
    private String name;

    /**
     * 原因描述
     */
    @Column(name = "people_num")
    private Integer peopleNum;

    /**
     * 扣分分数
     */
    private Float score;

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
     * 状态
     */
    private Integer status;

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
     * 获取扣分原因
     *
     * @return name - 扣分原因
     */
    public String getName() {
        return name;
    }

    /**
     * 设置扣分原因
     *
     * @param name 扣分原因
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取原因描述
     *
     * @return people_num - 原因描述
     */
    public Integer getPeopleNum() {
        return peopleNum;
    }

    /**
     * 设置原因描述
     *
     * @param peopleNum 原因描述
     */
    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    /**
     * 获取扣分分数
     *
     * @return score - 扣分分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置扣分分数
     *
     * @param score 扣分分数
     */
    public void setScore(Float score) {
        this.score = score;
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
}