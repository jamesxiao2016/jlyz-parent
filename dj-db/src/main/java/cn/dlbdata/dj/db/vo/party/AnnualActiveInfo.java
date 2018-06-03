package cn.dlbdata.dj.db.vo.party;

/**
 * 党员年度活动信息
 */
public class AnnualActiveInfo {
    // 年度积分
    private Float score;
    // 活动次数
    private int activeNum;
    // 金领驿站活动次数
    private int jlyzActiveNum;

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public int getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(int activeNum) {
        this.activeNum = activeNum;
    }

    public int getJlyzActiveNum() {
        return jlyzActiveNum;
    }

    public void setJlyzActiveNum(int jlyzActiveNum) {
        this.jlyzActiveNum = jlyzActiveNum;
    }
}
