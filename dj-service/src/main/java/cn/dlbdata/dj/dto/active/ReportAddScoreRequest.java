package cn.dlbdata.dj.dto.active;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */
public class ReportAddScoreRequest {
    private String reportTime;//思想汇报时间
    private Long subTypeId;//子活动ID
    private Long id;//党员ID
    private String content;//思想汇报内容
    private List<Long>picIds = new ArrayList<>();
    private Long deptId;

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Long getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(Long subTypeId) {
        this.subTypeId = subTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
