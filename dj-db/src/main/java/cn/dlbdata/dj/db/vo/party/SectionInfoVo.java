package cn.dlbdata.dj.db.vo.party;

public class SectionInfoVo {
    private String name;
    private String partyCommittee;
    private int branchSum;
    private int peopleSum;
    private String leaderName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBranchSum() {
        return branchSum;
    }

    public void setBranchSum(int branchSum) {
        this.branchSum = branchSum;
    }

    public int getPeopleSum() {
        return peopleSum;
    }

    public void setPeopleSum(int peopleSum) {
        this.peopleSum = peopleSum;
    }

    public String getPartyCommittee() {
        return partyCommittee;
    }

    public void setPartyCommittee(String partyCommittee) {
        this.partyCommittee = partyCommittee;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
}
