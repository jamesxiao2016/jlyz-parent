package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_log_login")
public class DjLogLogin {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "dj_user_id")
    private Long djUserId;

    /**
     * 账号
     */
    @Column(name = "user_account")
    private String userAccount;

    /**
     * 姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 错误消息
     */
    @Column(name = "error_msg")
    private String errorMsg;

    /**
     * 部门ID
     */
    @Column(name = "dj_dept_id")
    private Long djDeptId;

    /**
     * 创建时间
     */
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
     * 获取用户ID
     *
     * @return dj_user_id - 用户ID
     */
    public Long getDjUserId() {
        return djUserId;
    }

    /**
     * 设置用户ID
     *
     * @param djUserId 用户ID
     */
    public void setDjUserId(Long djUserId) {
        this.djUserId = djUserId;
    }

   
    public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
     * 获取姓名
     *
     * @return user_name - 姓名
     */
    public String getUserName() {
        return userName;
    }

	/**
     * 设置姓名
     *
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取open_id
     *
     * @return open_id - open_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置open_id
     *
     * @param openId open_id
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取错误消息
     *
     * @return error_msg - 错误消息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误消息
     *
     * @param errorMsg 错误消息
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取部门ID
     *
     * @return dj_dept_id - 部门ID
     */
    public Long getDjDeptId() {
        return djDeptId;
    }

    /**
     * 设置部门ID
     *
     * @param djDeptId 部门ID
     */
    public void setDjDeptId(Long djDeptId) {
        this.djDeptId = djDeptId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}