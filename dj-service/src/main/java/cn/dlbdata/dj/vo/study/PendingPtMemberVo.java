package cn.dlbdata.dj.vo.study;

import java.sql.Timestamp;

public class PendingPtMemberVo {
    private Long studyId;//自主学习ID
    private String name;//党员姓名
    private Timestamp createTime;//创建时间
    private Integer status;//处理状态

    public Long getStudyId() {
        return studyId;
    }

    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
