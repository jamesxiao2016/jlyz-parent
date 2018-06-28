package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_activity_rule")
public class TActivityRule {
    @Id
    private Integer id;

    @Column(name = "type_code")
    private String typeCode;

    /**
     * 活动类型
     */
    private String type;

    @Column(name = "kind_code")
    private String kindCode;

    /**
     * 类型所属大类
     */
    private String kind;

    /**
     * 党员参与该类型活动每次可获取积分
     */
    @Column(name = "score_per_time_m")
    private Float scorePerTimeM;

    /**
     * 党员参与该类型活动获取积分次数限制
     */
    @Column(name = "time_limit_m")
    private Integer timeLimitM;

    /**
     * 党支部发起该类型活动每次可获取积分
     */
    @Column(name = "score_per_time_b")
    private Float scorePerTimeB;

    /**
     * 党支部发起该类型活动获取积分次数限制
     */
    @Column(name = "time_limit_b")
    private Integer timeLimitB;

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
     * @return type_code
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取活动类型
     *
     * @return type - 活动类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置活动类型
     *
     * @param type 活动类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return kind_code
     */
    public String getKindCode() {
        return kindCode;
    }

    /**
     * @param kindCode
     */
    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    /**
     * 获取类型所属大类
     *
     * @return kind - 类型所属大类
     */
    public String getKind() {
        return kind;
    }

    /**
     * 设置类型所属大类
     *
     * @param kind 类型所属大类
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * 获取党员参与该类型活动每次可获取积分
     *
     * @return score_per_time_m - 党员参与该类型活动每次可获取积分
     */
    public Float getScorePerTimeM() {
        return scorePerTimeM;
    }

    /**
     * 设置党员参与该类型活动每次可获取积分
     *
     * @param scorePerTimeM 党员参与该类型活动每次可获取积分
     */
    public void setScorePerTimeM(Float scorePerTimeM) {
        this.scorePerTimeM = scorePerTimeM;
    }

    /**
     * 获取党员参与该类型活动获取积分次数限制
     *
     * @return time_limit_m - 党员参与该类型活动获取积分次数限制
     */
    public Integer getTimeLimitM() {
        return timeLimitM;
    }

    /**
     * 设置党员参与该类型活动获取积分次数限制
     *
     * @param timeLimitM 党员参与该类型活动获取积分次数限制
     */
    public void setTimeLimitM(Integer timeLimitM) {
        this.timeLimitM = timeLimitM;
    }

    /**
     * 获取党支部发起该类型活动每次可获取积分
     *
     * @return score_per_time_b - 党支部发起该类型活动每次可获取积分
     */
    public Float getScorePerTimeB() {
        return scorePerTimeB;
    }

    /**
     * 设置党支部发起该类型活动每次可获取积分
     *
     * @param scorePerTimeB 党支部发起该类型活动每次可获取积分
     */
    public void setScorePerTimeB(Float scorePerTimeB) {
        this.scorePerTimeB = scorePerTimeB;
    }

    /**
     * 获取党支部发起该类型活动获取积分次数限制
     *
     * @return time_limit_b - 党支部发起该类型活动获取积分次数限制
     */
    public Integer getTimeLimitB() {
        return timeLimitB;
    }

    /**
     * 设置党支部发起该类型活动获取积分次数限制
     *
     * @param timeLimitB 党支部发起该类型活动获取积分次数限制
     */
    public void setTimeLimitB(Integer timeLimitB) {
        this.timeLimitB = timeLimitB;
    }
}