package cn.dlbdata.dj.constant;

/**
 * Created by Administrator on 2018/5/22.
 */
public enum  ActiveSubTypeEnum {
    ACTIVE_SUB_A(1,"政治学习参加活动"),
    ACTIVE_SUB_B(2,"政治学习自主学习"),
    ACTIVE_SUB_C(3,"组织生活参加活动"),
    ACTIVE_SUB_D(4,"组织生活自主活动"),
    ACTIVE_SUB_E(5,"驿站生活参加活动"),
    ACTIVE_SUB_F(6,"驿站生活违纪违规"),
    ACTIVE_SUB_G(7,"公益服务参加活动"),
    ACTIVE_SUB_H(8,"公益服务自主活动"),
    ACTIVE_SUB_I(9,"党费线上缴纳"),
    ACTIVE_SUB_J(10,"党费线下缴纳"),
    ACTIVE_SUB_K(11,"思想汇报自主汇报"),
    ACTIVE_SUB_L(12,"思想汇报书面汇报"),
    ACTIVE_SUB_M(13,"获得荣誉"),
    ACTIVE_SUB_N(14,"先锋表彰"),
    ACTIVE_SUB_O(15,"先锋模范"),
    ACTIVE_SUB_P(16,"遵纪守法基础积分");


    ActiveSubTypeEnum(int activeSubId, String desc) {
        this.activeSubId = activeSubId;
        this.desc = desc;
    }

    private int activeSubId;
    private String desc;

    public int getActiveSubId() {
        return activeSubId;
    }

    public void setActiveSubId(int activeSubId) {
        this.activeSubId = activeSubId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
