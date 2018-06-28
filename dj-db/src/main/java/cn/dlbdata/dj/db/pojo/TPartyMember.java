package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_party_member")
public class TPartyMember {
    @Id
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    @Column(name = "sex_code")
    private String sexCode;

    /**
     * 出生年月
     */
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * 学历
     */
    @Column(name = "education_code")
    private String educationCode;

    /**
     * 行政职务
     */
    @Column(name = "adm_post")
    private String admPost;

    /**
     * 党内职务
     */
    @Column(name = "party_post_code")
    private String partyPostCode;

    /**
     * 党员当前总积分
     */
    private Float score;

    /**
     * 所属党支部
     */
    @Column(name = "party_branch_id")
    private Integer partyBranchId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别
     *
     * @return sex_code - 性别
     */
    public String getSexCode() {
        return sexCode;
    }

    /**
     * 设置性别
     *
     * @param sexCode 性别
     */
    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    /**
     * 获取出生年月
     *
     * @return birth_date - 出生年月
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * 设置出生年月
     *
     * @param birthDate 出生年月
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 获取学历
     *
     * @return education_code - 学历
     */
    public String getEducationCode() {
        return educationCode;
    }

    /**
     * 设置学历
     *
     * @param educationCode 学历
     */
    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }

    /**
     * 获取行政职务
     *
     * @return adm_post - 行政职务
     */
    public String getAdmPost() {
        return admPost;
    }

    /**
     * 设置行政职务
     *
     * @param admPost 行政职务
     */
    public void setAdmPost(String admPost) {
        this.admPost = admPost;
    }

    /**
     * 获取党内职务
     *
     * @return party_post_code - 党内职务
     */
    public String getPartyPostCode() {
        return partyPostCode;
    }

    /**
     * 设置党内职务
     *
     * @param partyPostCode 党内职务
     */
    public void setPartyPostCode(String partyPostCode) {
        this.partyPostCode = partyPostCode;
    }

    /**
     * 获取党员当前总积分
     *
     * @return score - 党员当前总积分
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置党员当前总积分
     *
     * @param score 党员当前总积分
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * 获取所属党支部
     *
     * @return party_branch_id - 所属党支部
     */
    public Integer getPartyBranchId() {
        return partyBranchId;
    }

    /**
     * 设置所属党支部
     *
     * @param partyBranchId 所属党支部
     */
    public void setPartyBranchId(Integer partyBranchId) {
        this.partyBranchId = partyBranchId;
    }
}