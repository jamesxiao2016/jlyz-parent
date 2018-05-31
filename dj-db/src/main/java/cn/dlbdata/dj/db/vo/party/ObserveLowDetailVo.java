package cn.dlbdata.dj.db.vo.party;

import java.util.List;

public class ObserveLowDetailVo {
    private String partyMemberName;//党员姓名
    private String deptName;//所属支部
    private String reason;//扣分原因
    private String reasonDesc;//原因描述
    private List<Long> picIds;//图片Ids
    private Long disId;//遵章守纪Id

    public String getPartyMemberName() {
        return partyMemberName;
    }

    public void setPartyMemberName(String partyMemberName) {
        this.partyMemberName = partyMemberName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public List<Long> getPicIds() {
        return picIds;
    }

    public void setPicIds(List<Long> picIds) {
        this.picIds = picIds;
    }

    public Long getDisId() {
        return disId;
    }

    public void setDisId(Long disId) {
        this.disId = disId;
    }
}
