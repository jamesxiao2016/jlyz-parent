package cn.dlbdata.dj.dto.active;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/22.
 */
public class ReportAddScoreRequest {
    private String reportTime;
    private Long subTypeId;//子活动ID
    private Long id;//党员ID

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
}
