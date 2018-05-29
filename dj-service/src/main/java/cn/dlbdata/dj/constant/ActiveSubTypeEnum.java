package cn.dlbdata.dj.constant;

/**
 * Created by Administrator on 2018/5/22.
 */
public enum  ActiveSubTypeEnum {
    ACTIVE_SUB_A(11L,"政治学习参加活动"),
    ACTIVE_SUB_B(12L,"政治学习自主学习"),
    ACTIVE_SUB_C(21L,"组织生活参加活动"),
    ACTIVE_SUB_D(22L,"组织生活自主活动"),
    ACTIVE_SUB_E(23L,"驿站生活参加活动"),
    ACTIVE_SUB_F(52L,"驿站生活违纪违规"),
    ACTIVE_SUB_P(51L,"遵纪守法基础积分"),
    ACTIVE_SUB_G(61L,"公益服务参加活动"),
    ACTIVE_SUB_H(62L,"公益服务自主活动"),
    ACTIVE_SUB_I(71L,"党费线上缴纳"),
    ACTIVE_SUB_J(72L,"党费线下缴纳"),
    ACTIVE_SUB_K(31L,"思想汇报自主汇报"),
    ACTIVE_SUB_L(32L,"思想汇报书面汇报"),
    ACTIVE_SUB_M(41L,"获得荣誉"),
    ACTIVE_SUB_N(42L,"先锋表彰"),
    ACTIVE_SUB_O(43L,"先锋模范");


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
