package cn.dlbdata.dj.db.vo.apply;

import java.util.ArrayList;
import java.util.List;

public class PioneeringApplyDetailVo {
    private String partyMemberName;//党员姓名
    private Float totalScore;//总分
    private List<ScoreAuditDetailVo> detail = new ArrayList<>();

    public String getPartyMemberName() {
        return partyMemberName;
    }

    public void setPartyMemberName(String partyMemberName) {
        this.partyMemberName = partyMemberName;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    public List<ScoreAuditDetailVo> getDetail() {
        return detail;
    }

    public void setDetail(List<ScoreAuditDetailVo> detail) {
        this.detail = detail;
    }
}
