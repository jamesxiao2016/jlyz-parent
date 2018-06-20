package cn.dlbdata.dj.db.vo.dept;


import cn.dlbdata.dj.db.vo.party.AllPartyMemberVo;

import java.util.ArrayList;
import java.util.List;

public class DeptAndPartyMemberVo {
    private String deptName;
    private Integer peopleSum;
    private List<AllPartyMemberVo> partyMembers = new ArrayList<>();

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getPeopleSum() {
        return peopleSum;
    }

    public void setPeopleSum(Integer peopleSum) {
        this.peopleSum = peopleSum;
    }

    public List<AllPartyMemberVo> getPartyMembers() {
        return partyMembers;
    }

    public void setPartyMembers(List<AllPartyMemberVo> partyMembers) {
        this.partyMembers = partyMembers;
    }
}
