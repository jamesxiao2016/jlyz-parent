package cn.dlbdata.dj.constant;


public enum DlbConstant  {
    AUDIT_STATUS_YES(2,"已审核"),
    AUDIT_STATUS_NO(0,"未审核"),
    AUDIT_STATUS_WAIT(1,"待审核"),
    BASEDATA_STATUS_VALID(1,"有效"),
    BASEDATA_STATUS_INVALID(0,"作废"),
    /** 先锋作用**/
    PIONEERING_STATUS_A(0,"未审核"),
    PIONEERING_STATUS_B(1,"已驳回"),
    PIONEERING_STATUS_C(2,"已通过"),
    PIONEERING_TYPE_A(1,"获得荣誉"),
    PIONEERING_TYPE_B(2,"先锋表彰"),
    PIONEERING_TYPE_C(3,"先锋模范"),
    PIONEERING_TYPE_D(4,"遵章守纪");

     DlbConstant(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private int value;
    private String desc;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
