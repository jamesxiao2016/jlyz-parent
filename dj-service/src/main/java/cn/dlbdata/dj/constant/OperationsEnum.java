package cn.dlbdata.dj.constant;

public enum  OperationsEnum {
    ACTIVE_SIGNUP(1,"金领驿站活动报名"),
    CREATE_ACTIVE(2,"发起活动"),
    ACTIVE_SIGNIN(3,"活动签到"),
    ACTIVE_CANCEL(4,"取消活动"),
    PIC_UPLOAD(5,"上传图片"),
    PIC_DELETE(6,"删除图片"),
    APPLY_STUDY(7,"自主活动申请"),
    ONE_VOTE_VETO(8,"驿站生活违纪违规扣分申请"),
    VANGUARD_APPLY(9,"先锋作用申请"),
    THOUGHTS_GRADE(10,"思想汇报评分"),
    AUDIT(11,"审批");

    OperationsEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;
    private String desc;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
