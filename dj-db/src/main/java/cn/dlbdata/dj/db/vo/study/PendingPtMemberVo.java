package cn.dlbdata.dj.db.vo.study;

import java.sql.Timestamp;

public class PendingPtMemberVo {
    private Long applyId;//申请ID
    private String name;//党员姓名
    private Timestamp createTime;//创建时间
    private Integer status;//处理状态

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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
