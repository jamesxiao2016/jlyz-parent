package cn.dlbdata.dj.constant;

/**
 * Created by Administrator on 2018/5/22.
 */
public enum  ActiveSubTypeEnum {
    ACTIVE_SUB_A(1L,"政治学习参加活动"),
    ACTIVE_SUB_B(2L,"政治学习自主学习"),
    ACTIVE_SUB_C(3L,"组织生活参加活动"),
    ACTIVE_SUB_D(4L,"组织生活自主活动"),
    ACTIVE_SUB_E(5L,"驿站生活参加活动"),
    ACTIVE_SUB_F(6L,"驿站生活违纪违规"),
    ACTIVE_SUB_G(7L,"公益服务参加活动"),
    ACTIVE_SUB_H(8L,"公益服务自主活动"),
    ACTIVE_SUB_I(9L,"党费线上缴纳"),
    ACTIVE_SUB_J(10L,"党费线下缴纳"),
    ACTIVE_SUB_K(11L,"思想汇报自主汇报"),
    ACTIVE_SUB_L(12L,"思想汇报书面汇报"),
    ACTIVE_SUB_M(13L,"获得荣誉"),
    ACTIVE_SUB_N(14L,"先锋表彰"),
    ACTIVE_SUB_O(15L,"先锋模范"),
    ACTIVE_SUB_P(16L,"遵纪守法基础积分");


    ActiveSubTypeEnum(Long activeSubId, String desc) {
        this.activeSubId = activeSubId;
        this.desc = desc;
    }

    private Long activeSubId;
    private String desc;

    public Long getActiveSubId() {
        return activeSubId;
    }

    public void setActiveSubId(Long activeSubId) {
        this.activeSubId = activeSubId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
