package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_thoughts")
public class DjThoughts {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 思想汇报内容
     */
    @Column(name = "thoughts_info")
    private String thoughtsInfo;

    /**
     * 汇报时间
     */
    @Column(name = "thoughts_time")
    private Integer thoughtsTime;

    /**
     * 加分分数
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
     * 获取思想汇报内容
     *
     * @return thoughts_info - 思想汇报内容
     */
    public String getThoughtsInfo() {
        return thoughtsInfo;
    }

    /**
     * 设置思想汇报内容
     *
     * @param thoughtsInfo 思想汇报内容
     */
    public void setThoughtsInfo(String thoughtsInfo) {
        this.thoughtsInfo = thoughtsInfo;
    }

    /**
     * 获取汇报时间
     *
     * @return thoughts_time - 汇报时间
     */
    public Integer getThoughtsTime() {
        return thoughtsTime;
    }

    /**
     * 设置汇报时间
     *
     * @param thoughtsTime 汇报时间
     */
    public void setThoughtsTime(Integer thoughtsTime) {
        this.thoughtsTime = thoughtsTime;
    }

    /**
     * 获取加分分数
     *
     * @return score - 加分分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置加分分数
     *
     * @param score 加分分数
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