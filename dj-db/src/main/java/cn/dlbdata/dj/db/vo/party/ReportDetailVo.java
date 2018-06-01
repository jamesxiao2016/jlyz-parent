package cn.dlbdata.dj.db.vo.party;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReportDetailVo {
    private String partyMemberName;//党员姓名
    private Timestamp reportTime;//思想汇报时间
    private String typeName;//汇报类型
    private String content;//思想汇报主要内容
    private List<Long> picIds = new ArrayList<>();//思想汇报图片
    private Long id;//思想汇报Id

    public String getPartyMemberName() {
        return partyMemberName;
    }

    public void setPartyMemberName(String partyMemberName) {
        this.partyMemberName = partyMemberName;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Long> getPicIds() {
        return picIds;
    }

    public void setPicIds(List<Long> picIds) {
        this.picIds = picIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
