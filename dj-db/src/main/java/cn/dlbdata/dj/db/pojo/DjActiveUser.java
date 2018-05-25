package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_active_user")
public class DjActiveUser {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 活动ID
     */
    @Column(name = "dj_active_id")
    private Long djActiveId;

    /**
     * 参与人ID
     */
    @Column(name = "dj_user_id")
    private Long djUserId;
    
    /**
     * 参与人部门ID
     */
    @Column(name = "dj_dept_id")
    private Long djDeptId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 签到时间
     */
    @Column(name = "sign_time")
    private Date signTime;

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
     * 获取活动ID
     *
     * @return dj_active_id - 活动ID
     */
    public Long getDjActiveId() {
        return djActiveId;
    }

    /**
     * 设置活动ID
     *
     * @param djActiveId 活动ID
     */
    public void setDjActiveId(Long djActiveId) {
        this.djActiveId = djActiveId;
    }

    /**
     * 获取参与人ID
     *
     * @return dj_user_id - 参与人ID
     */
    public Long getDjUserId() {
        return djUserId;
    }

    /**
     * 设置参与人ID
     *
     * @param djUserId 参与人ID
     */
    public void setDjUserId(Long djUserId) {
        this.djUserId = djUserId;
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

    /**
     * 获取签到时间
     *
     * @return sign_time - 签到时间
     */
    public Date getSignTime() {
        return signTime;
    }

    /**
     * 设置签到时间
     *
     * @param signTime 签到时间
     */
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

	public Long getDjDeptId() {
		return djDeptId;
	}

	public void setDjDeptId(Long djDeptId) {
		this.djDeptId = djDeptId;
	}
}