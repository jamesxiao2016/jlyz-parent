package cn.dlbdata.dj.constant;

/**
 * Created by Administrator on 2018/5/24.
 */
public enum  AuditStatusEnum {
    UNDONE(-1,"未审核"),
    WAITING(0,"待审核"),
    PASS(1,"已审核"),
    REJECT(2,"已驳回");


     AuditStatusEnum(int value, String desc) {
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
