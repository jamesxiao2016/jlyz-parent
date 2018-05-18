package com.meig.link.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_resource")
public class DjResource {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 资源名称
     */
    @Column(name = "res_name")
    private String resName;

    /**
     * 资源类型
            1:菜单 2:权限
     */
    @Column(name = "res_type")
    private Integer resType;

    /**
     * 资源URL
     */
    @Column(name = "res_url")
    private Integer resUrl;

    /**
     * 资源图标
     */
    @Column(name = "res_icon")
    private String resIcon;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 资源描述
     */
    @Column(name = "res_desc")
    private String resDesc;

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
     * 获取资源名称
     *
     * @return res_name - 资源名称
     */
    public String getResName() {
        return resName;
    }

    /**
     * 设置资源名称
     *
     * @param resName 资源名称
     */
    public void setResName(String resName) {
        this.resName = resName;
    }

    /**
     * 获取资源类型
            1:菜单 2:权限
     *
     * @return res_type - 资源类型
            1:菜单 2:权限
     */
    public Integer getResType() {
        return resType;
    }

    /**
     * 设置资源类型
            1:菜单 2:权限
     *
     * @param resType 资源类型
            1:菜单 2:权限
     */
    public void setResType(Integer resType) {
        this.resType = resType;
    }

    /**
     * 获取资源URL
     *
     * @return res_url - 资源URL
     */
    public Integer getResUrl() {
        return resUrl;
    }

    /**
     * 设置资源URL
     *
     * @param resUrl 资源URL
     */
    public void setResUrl(Integer resUrl) {
        this.resUrl = resUrl;
    }

    /**
     * 获取资源图标
     *
     * @return res_icon - 资源图标
     */
    public String getResIcon() {
        return resIcon;
    }

    /**
     * 设置资源图标
     *
     * @param resIcon 资源图标
     */
    public void setResIcon(String resIcon) {
        this.resIcon = resIcon;
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
     * 获取资源描述
     *
     * @return res_desc - 资源描述
     */
    public String getResDesc() {
        return resDesc;
    }

    /**
     * 设置资源描述
     *
     * @param resDesc 资源描述
     */
    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
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