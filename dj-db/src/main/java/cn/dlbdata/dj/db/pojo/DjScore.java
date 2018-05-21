package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_score")
public class DjScore {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 类型ID
     */
    @Column(name = "dj_type_id")
    private Long djTypeId;

    /**
     * 二级类型ID
     */
    @Column(name = "dj_sub_type_id")
    private Long djSubTypeId;

    /**
     * 分数
     */
    private Float score;

    /**
     * 加分人（自己或审批人）
     */
    @Column(name = "add_user_id")
    private Long addUserId;

    /**
     * 加分时间
     */
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 积分说明
     */
    @Column(name = "score_desc")
    private String scoreDesc;

    /**
     * 记录ID
     */
    @Column(name = "record_id")
    private Long recordId;

    /**
     * 记录描述
     */
    @Column(name = "recrod_desc")
    private String recrodDesc;

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
     * 获取类型ID
     *
     * @return dj_type_id - 类型ID
     */
    public Long getDjTypeId() {
        return djTypeId;
    }

    /**
     * 设置类型ID
     *
     * @param djTypeId 类型ID
     */
    public void setDjTypeId(Long djTypeId) {
        this.djTypeId = djTypeId;
    }

    /**
     * 获取二级类型ID
     *
     * @return dj_sub_type_id - 二级类型ID
     */
    public Long getDjSubTypeId() {
        return djSubTypeId;
    }

    /**
     * 设置二级类型ID
     *
     * @param djSubTypeId 二级类型ID
     */
    public void setDjSubTypeId(Long djSubTypeId) {
        this.djSubTypeId = djSubTypeId;
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
     * 获取加分人（自己或审批人）
     *
     * @return add_user_id - 加分人（自己或审批人）
     */
    public Long getAddUserId() {
        return addUserId;
    }

    /**
     * 设置加分人（自己或审批人）
     *
     * @param addUserId 加分人（自己或审批人）
     */
    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    /**
     * 获取加分时间
     *
     * @return add_time - 加分时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置加分时间
     *
     * @param addTime 加分时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取积分说明
     *
     * @return score_desc - 积分说明
     */
    public String getScoreDesc() {
        return scoreDesc;
    }

    /**
     * 设置积分说明
     *
     * @param scoreDesc 积分说明
     */
    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc;
    }

    /**
     * 获取记录ID
     *
     * @return record_id - 记录ID
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * 设置记录ID
     *
     * @param recordId 记录ID
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取记录描述
     *
     * @return recrod_desc - 记录描述
     */
    public String getRecrodDesc() {
        return recrodDesc;
    }

    /**
     * 设置记录描述
     *
     * @param recrodDesc 记录描述
     */
    public void setRecrodDesc(String recrodDesc) {
        this.recrodDesc = recrodDesc;
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