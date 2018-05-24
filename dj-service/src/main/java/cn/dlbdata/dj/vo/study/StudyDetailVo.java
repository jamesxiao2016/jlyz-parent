package cn.dlbdata.dj.vo.study;

import java.util.Date;
import java.util.List;

public class StudyDetailVo {
    private String name;//党员姓名
    private Date startTime;//活动开始时间
    private Date endTime;//活动结束时间
    private String content;//主要内容
    private List<Long> picIds;//图片IdList
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
