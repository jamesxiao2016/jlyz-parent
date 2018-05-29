package cn.dlbdata.dj.db.vo.vo.apply;

public class ScoreApplyVo {
    private String partyMemberName;//党员姓名
    private String leaderName;//支部书记姓名
    private Float totalScore;//总分数
    private Long partyMemberId;//党员ID

    public String getPartyMemberName() {
        return partyMemberName;
    }

    public void setPartyMemberName(String partyMemberName) {
        this.partyMemberName = partyMemberName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    public Long getPartyMemberId() {
        return partyMemberId;
    }

    public void setPartyMemberId(Long partyMemberId) {
        this.partyMemberId = partyMemberId;
    }
}
