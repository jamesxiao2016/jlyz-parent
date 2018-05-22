package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_sub_type")
public class DjSubType {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 分类ID
     */
    @Column(name = "dj_type_id")
    private Long djTypeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String content;

    /**
     * 单次分数
     */
    private Float score;

    /**
     * 最大分数
     */
    @Column(name = "max_score")
    private Float maxScore;

    /**
     * 加分次数
            0:不限制 大于0:限制的次数
     */
    @Column(name = "add_num")
    private Integer addNum;

    /**
     * 获取方式
     */
    @Column(name = "get_way")
    private Integer getWay;

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
     * 获取分类ID
     *
     * @return dj_type_id - 分类ID
     */
    public Long getDjTypeId() {
        return djTypeId;
    }

    /**
     * 设置分类ID
     *
     * @param djTypeId 分类ID
     */
    public void setDjTypeId(Long djTypeId) {
        this.djTypeId = djTypeId;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取描述
     *
     * @return content - 描述
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置描述
     *
     * @param content 描述
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取单次分数
     *
     * @return score - 单次分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置单次分数
     *
     * @param score 单次分数
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * 获取最大分数
     *
     * @return max_score - 最大分数
     */
    public Float getMaxScore() {
        return maxScore;
    }

    /**
     * 设置最大分数
     *
     * @param maxScore 最大分数
     */
    public void setMaxScore(Float maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * 获取加分次数
            0:不限制 大于0:限制的次数
     *
     * @return add_num - 加分次数
            0:不限制 大于0:限制的次数
     */
    public Integer getAddNum() {
        return addNum;
    }

    /**
     * 设置加分次数
            0:不限制 大于0:限制的次数
     *
     * @param addNum 加分次数
            0:不限制 大于0:限制的次数
     */
    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    /**
     * 获取获取方式
     *
     * @return get_way - 获取方式
     */
    public Integer getGetWay() {
        return getWay;
    }

    /**
     * 设置获取方式
     *
     * @param getWay 获取方式
     */
    public void setGetWay(Integer getWay) {
        this.getWay = getWay;
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