package cn.dlbdata.dj.db.vo.dept;

public class DeptAndApplyInfoVo {
    private Long deptId;
    private String deptName;
    private int haveApply;//是否有申请 0没有申请，1有申请

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getHaveApply() {
        return haveApply;
    }

    public void setHaveApply(int haveApply) {
        this.haveApply = haveApply;
    }
}
