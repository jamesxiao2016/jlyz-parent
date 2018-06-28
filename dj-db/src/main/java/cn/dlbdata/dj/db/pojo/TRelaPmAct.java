package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_rela_pm_act")
public class TRelaPmAct {
    /**
     * 序号
     */
    @Id
    private Integer id;

    /**
     * 党员id
     */
    @Column(name = "party_member_id")
    private Integer partyMemberId;

    /**
     * 党员参与的活动id
     */
    @Column(name = "activity_id")
    private Integer activityId;

    /**
     * 参加本次活动，党员应得分数
     */
    private Float score;

    /**
     * 获取序号
     *
     * @return id - 序号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号
     *
     * @param id 序号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取党员id
     *
     * @return party_member_id - 党员id
     */
    public Integer getPartyMemberId() {
        return partyMemberId;
    }

    /**
     * 设置党员id
     *
     * @param partyMemberId 党员id
     */
    public void setPartyMemberId(Integer partyMemberId) {
        this.partyMemberId = partyMemberId;
    }

    /**
     * 获取党员参与的活动id
     *
     * @return activity_id - 党员参与的活动id
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * 设置党员参与的活动id
     *
     * @param activityId 党员参与的活动id
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * 获取参加本次活动，党员应得分数
     *
     * @return score - 参加本次活动，党员应得分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置参加本次活动，党员应得分数
     *
     * @param score 参加本次活动，党员应得分数
     */
    public void setScore(Float score) {
        this.score = score;
    }
}