package cn.dlbdata.dj.constant;

public enum ActiveTypeEnum {
    ACTIVE_A(1, "政治学习"),
    ACTIVE_B(2, "组织生活"),
    ACTIVE_C(3, "思想汇报"),
    ACTIVE_D(4, "先锋作用"),
    ACTIVE_E(5, "遵纪守法"),
    ACTIVE_F(6, "公益服务"),
    ACTIVE_G(7, "缴纳党费");
    private int activeId;
    private String desc;

     ActiveTypeEnum(int activeId, String desc) {
        this.activeId = activeId;
        this.desc = desc;
    }

    public int getActiveId() {
        return activeId;
    }

    public void setActiveId(int activeId) {
        this.activeId = activeId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
