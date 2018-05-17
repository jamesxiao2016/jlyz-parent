package cn.dlbdata.dangjian.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_active_pic")
public class DjActivePic {
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
     * 参与人ID
     */
    @Column(name = "dj_user_id")
    private Integer djUserId;

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
     * 获取参与人ID
     *
     * @return dj_user_id - 参与人ID
     */
    public Integer getDjUserId() {
        return djUserId;
    }

    /**
     * 设置参与人ID
     *
     * @param djUserId 参与人ID
     */
    public void setDjUserId(Integer djUserId) {
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
}