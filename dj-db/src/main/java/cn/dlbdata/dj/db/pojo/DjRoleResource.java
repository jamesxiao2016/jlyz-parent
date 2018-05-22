package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_role_resource")
public class DjRoleResource {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "dj_role_id")
    private Long djRoleId;

    /**
     * 资源ID
     */
    @Column(name = "dj_resource_id")
    private Long djResourceId;

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
     * 获取角色ID
     *
     * @return dj_role_id - 角色ID
     */
    public Long getDjRoleId() {
        return djRoleId;
    }

    /**
     * 设置角色ID
     *
     * @param djRoleId 角色ID
     */
    public void setDjRoleId(Long djRoleId) {
        this.djRoleId = djRoleId;
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