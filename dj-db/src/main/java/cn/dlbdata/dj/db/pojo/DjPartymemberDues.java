package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_partymember_dues")
public class DjPartymemberDues {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 党员ID
     */
    @Column(name = "dj_partymember_id")
    private Long djPartymemberId;

    /**
     * 待缴纳年份
     */
    @Column(name = "dues_year")
    private Integer duesYear;

    /**
     * 待缴纳月份
     */
    @Column(name = "dues_month")
    private Integer duesMonth;

    /**
     * 待缴纳金额
     */
    @Column(name = "dues_money")
    private Float duesMoney;

    /**
     * 实际缴纳金额
     */
    @Column(name = "real_money")
    private Float realMoney;

    /**
     * 缴纳时间
     */
    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * 状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取记录ID
     *
     * @return id - 记录ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置记录ID
     *
     * @param id 记录ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取党员ID
     *
     * @return dj_partymember_id - 党员ID
     */
    public Long getDjPartymemberId() {
        return djPartymemberId;
    }

    /**
     * 设置党员ID
     *
     * @param djPartymemberId 党员ID
     */
    public void setDjPartymemberId(Long djPartymemberId) {
        this.djPartymemberId = djPartymemberId;
    }

    /**
     * 获取待缴纳年份
     *
     * @return dues_year - 待缴纳年份
     */
    public Integer getDuesYear() {
        return duesYear;
    }

    /**
     * 设置待缴纳年份
     *
     * @param duesYear 待缴纳年份
     */
    public void setDuesYear(Integer duesYear) {
        this.duesYear = duesYear;
    }

    /**
     * 获取待缴纳月份
     *
     * @return dues_month - 待缴纳月份
     */
    public Integer getDuesMonth() {
        return duesMonth;
    }

    /**
     * 设置待缴纳月份
     *
     * @param duesMonth 待缴纳月份
     */
    public void setDuesMonth(Integer duesMonth) {
        this.duesMonth = duesMonth;
    }

    /**
     * 获取待缴纳金额
     *
     * @return dues_money - 待缴纳金额
     */
    public Float getDuesMoney() {
        return duesMoney;
    }

    /**
     * 设置待缴纳金额
     *
     * @param duesMoney 待缴纳金额
     */
    public void setDuesMoney(Float duesMoney) {
        this.duesMoney = duesMoney;
    }

    /**
     * 获取实际缴纳金额
     *
     * @return real_money - 实际缴纳金额
     */
    public Float getRealMoney() {
        return realMoney;
    }

    /**
     * 设置实际缴纳金额
     *
     * @param realMoney 实际缴纳金额
     */
    public void setRealMoney(Float realMoney) {
        this.realMoney = realMoney;
    }

    /**
     * 获取缴纳时间
     *
     * @return payment_time - 缴纳时间
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 设置缴纳时间
     *
     * @param paymentTime 缴纳时间
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}