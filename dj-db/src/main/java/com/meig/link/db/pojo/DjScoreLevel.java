package com.meig.link.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_score_level")
public class DjScoreLevel {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 等级名称
     */
    private String name;

    /**
     * 等级描述
     */
    private String content;

    /**
     * 最小值
     */
    @Column(name = "level_min")
    private Float levelMin;

    /**
     * 最大值
     */
    @Column(name = "level_max")
    private Float levelMax;

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
     * 获取等级名称
     *
     * @return name - 等级名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置等级名称
     *
     * @param name 等级名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取等级描述
     *
     * @return content - 等级描述
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置等级描述
     *
     * @param content 等级描述
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取最小值
     *
     * @return level_min - 最小值
     */
    public Float getLevelMin() {
        return levelMin;
    }

    /**
     * 设置最小值
     *
     * @param levelMin 最小值
     */
    public void setLevelMin(Float levelMin) {
        this.levelMin = levelMin;
    }

    /**
     * 获取最大值
     *
     * @return level_max - 最大值
     */
    public Float getLevelMax() {
        return levelMax;
    }

    /**
     * 设置最大值
     *
     * @param levelMax 最大值
     */
    public void setLevelMax(Float levelMax) {
        this.levelMax = levelMax;
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