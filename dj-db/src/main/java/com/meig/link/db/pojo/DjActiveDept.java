package com.meig.link.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_active_dept")
public class DjActiveDept {
    /**
     * 记录ID
     */
    @Id
    private Integer id;

    /**
     * 活动ID
     */
    @Column(name = "dj_active_id")
    private Integer djActiveId;

    /**
     * 部门ID
     */
    @Column(name = "dj_dept_id")
    private Integer djDeptId;

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
    public Integer getId() {
        return id;
    }

    /**
     * 设置记录ID
     *
     * @param id 记录ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取活动ID
     *
     * @return dj_active_id - 活动ID
     */
    public Integer getDjActiveId() {
        return djActiveId;
    }

    /**
     * 设置活动ID
     *
     * @param djActiveId 活动ID
     */
    public void setDjActiveId(Integer djActiveId) {
        this.djActiveId = djActiveId;
    }

    /**
     * 获取部门ID
     *
     * @return dj_dept_id - 部门ID
     */
    public Integer getDjDeptId() {
        return djDeptId;
    }

    /**
     * 设置部门ID
     *
     * @param djDeptId 部门ID
     */
    public void setDjDeptId(Integer djDeptId) {
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