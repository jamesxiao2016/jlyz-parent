package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_activity")
public class TActivity {
    @Id
    private Integer id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动日期
     */
    private Date date;

    /**
     * 活动备注
     */
    private String note;

    /**
     * 活动配图
     */
    private String image;

    /**
     * 活动类型
0 驿站活动
其他 党支部活动
     */
    @Column(name = "type_code")
    private String typeCode;

    /**
     * 关联的驿站或党支部的ID
     */
    @Column(name = "relation_id")
    private Integer relationId;

    /**
     * 发起本次活动，对应党支部应得分数
     */
    private Float score;

    /**
     * 活动状态
I：待审核
S：审核通过
F：审核拒绝
     */
    private String status;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取活动日期
     *
     * @return date - 活动日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置活动日期
     *
     * @param date 活动日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 获取活动备注
     *
     * @return note - 活动备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置活动备注
     *
     * @param note 活动备注
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取活动配图
     *
     * @return image - 活动配图
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置活动配图
     *
     * @param image 活动配图
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取活动类型
0 驿站活动
其他 党支部活动
     *
     * @return type_code - 活动类型
0 驿站活动
其他 党支部活动
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 设置活动类型
0 驿站活动
其他 党支部活动
     *
     * @param typeCode 活动类型
0 驿站活动
其他 党支部活动
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取关联的驿站或党支部的ID
     *
     * @return relation_id - 关联的驿站或党支部的ID
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * 设置关联的驿站或党支部的ID
     *
     * @param relationId 关联的驿站或党支部的ID
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    /**
     * 获取发起本次活动，对应党支部应得分数
     *
     * @return score - 发起本次活动，对应党支部应得分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置发起本次活动，对应党支部应得分数
     *
     * @param score 发起本次活动，对应党支部应得分数
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * 获取活动状态
I：待审核
S：审核通过
F：审核拒绝
     *
     * @return status - 活动状态
I：待审核
S：审核通过
F：审核拒绝
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置活动状态
I：待审核
S：审核通过
F：审核拒绝
     *
     * @param status 活动状态
I：待审核
S：审核通过
F：审核拒绝
     */
    public void setStatus(String status) {
        this.status = status;
    }
}