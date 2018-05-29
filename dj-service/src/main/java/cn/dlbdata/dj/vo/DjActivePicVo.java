package cn.dlbdata.dj.vo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_active_pic")
public class DjActivePicVo {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 活动ID
     */
    private Long djActiveId;

    /**
     * 参与人ID
     */
    private Long djUserId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 图片id
     */
    private Long djPicId;

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
     * 获取图片id
     *
     * @return dj_pic_id - 图片id
     */
    public Long getDjPicId() {
        return djPicId;
    }

    /**
     * 设置图片id
     *
     * @param djPicId 图片id
     */
    public void setDjPicId(Long djPicId) {
        this.djPicId = djPicId;
    }
}