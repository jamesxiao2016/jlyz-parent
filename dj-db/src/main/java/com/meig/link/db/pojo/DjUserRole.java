package com.meig.link.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_user_role")
public class DjUserRole {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "dj_user_id")
    private Long djUserId;

    /**
     * 角色ID
     */
    @Column(name = "dj_role_id")
    private Long djRoleId;

    /**
     * 是否默认角色
     */
    @Column(name = "has_default")
    private Integer hasDefault;

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
     * 获取角色ID
     *
     * @return dj_role_id - 角色ID
     */
    public Long getDjRoleId() {
        return djRoleId;
    }

    /**
     * 设置角色ID
     *
     * @param djRoleId 角色ID
     */
    public void setDjRoleId(Long djRoleId) {
        this.djRoleId = djRoleId;
    }

    /**
     * 获取是否默认角色
     *
     * @return has_default - 是否默认角色
     */
    public Integer getHasDefault() {
        return hasDefault;
    }

    /**
     * 设置是否默认角色
     *
     * @param hasDefault 是否默认角色
     */
    public void setHasDefault(Integer hasDefault) {
        this.hasDefault = hasDefault;
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