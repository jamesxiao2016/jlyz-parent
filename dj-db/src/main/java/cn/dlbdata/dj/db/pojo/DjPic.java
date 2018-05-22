package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_pic")
public class DjPic {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 图片名称
     */
    @Column(name = "pic_name")
    private String picName;

    /**
     * 图片路径
     */
    @Column(name = "pic_url")
    private String picUrl;

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
     * 获取图片名称
     *
     * @return pic_name - 图片名称
     */
    public String getPicName() {
        return picName;
    }

    /**
     * 设置图片名称
     *
     * @param picName 图片名称
     */
    public void setPicName(String picName) {
        this.picName = picName;
    }

    /**
     * 获取图片路径
     *
     * @return pic_url - 图片路径
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置图片路径
     *
     * @param picUrl 图片路径
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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