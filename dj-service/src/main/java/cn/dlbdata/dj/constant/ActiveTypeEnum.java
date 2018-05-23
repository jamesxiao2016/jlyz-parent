package cn.dlbdata.dj.constant;

public enum ActiveTypeEnum {
    ACTIVE_A(1L, "政治学习"),
    ACTIVE_B(2L, "组织生活"),
    ACTIVE_C(3L, "思想汇报"),
    ACTIVE_D(4L, "先锋作用"),
    ACTIVE_E(5L, "遵纪守法"),
    ACTIVE_F(6L, "公益服务"),
    ACTIVE_G(7L, "缴纳党费");
    private Long activeId;
    private String desc;

    ActiveTypeEnum(Long activeId, String desc) {
        this.activeId = activeId;
        this.desc = desc;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
