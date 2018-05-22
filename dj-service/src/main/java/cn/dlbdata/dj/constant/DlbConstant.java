package cn.dlbdata.dj.constant;


public enum DlbConstant  {
    AUDIT_STATUS_YES(1,"有效"),
    AUDIT_STATUS_NO(0,"未审核");

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
