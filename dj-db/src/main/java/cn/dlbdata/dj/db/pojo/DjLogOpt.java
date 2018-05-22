package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_log_opt")
public class DjLogOpt {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "dj_user_id")
    private Long djUserId;

    /**
     * 资源ID
     */
    @Column(name = "dj_resource_id")
    private Long djResourceId;

    /**
     * 操作位置
     */
    @Column(name = "opt_location")
    private String optLocation;

    /**
     * 操作说明
     */
    @Column(name = "opt_desc")
    private String optDesc;

    /**
     * 状态
     */
    private Integer status;

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
     * 获取角色ID
     *
     * @return dj_user_id - 角色ID
     */
    public Long getDjUserId() {
        return djUserId;
    }

    /**
     * 设置角色ID
     *
     * @param djUserId 角色ID
     */
    public void setDjUserId(Long djUserId) {
        this.djUserId = djUserId;
    }

    /**
     * 获取资源ID
     *
     * @return dj_resource_id - 资源ID
     */
    public Long getDjResourceId() {
        return djResourceId;
    }

    /**
     * 设置资源ID
     *
     * @param djResourceId 资源ID
     */
    public void setDjResourceId(Long djResourceId) {
        this.djResourceId = djResourceId;
    }

    /**
     * 获取操作位置
     *
     * @return opt_location - 操作位置
     */
    public String getOptLocation() {
        return optLocation;
    }

    /**
     * 设置操作位置
     *
     * @param optLocation 操作位置
     */
    public void setOptLocation(String optLocation) {
        this.optLocation = optLocation;
    }

    /**
     * 获取操作说明
     *
     * @return opt_desc - 操作说明
     */
    public String getOptDesc() {
        return optDesc;
    }

    /**
     * 设置操作说明
     *
     * @param optDesc 操作说明
     */
    public void setOptDesc(String optDesc) {
        this.optDesc = optDesc;
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