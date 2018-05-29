package cn.dlbdata.dj.db.vo.party;

/**
 * Created by Administrator on 2018/5/23.
 */
public class PioneeringPartyMemberVo {
    private String name;
    private Long id;
    private Integer auditStatus;
    private Long deptId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
