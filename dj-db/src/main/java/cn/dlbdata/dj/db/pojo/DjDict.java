package cn.dlbdata.dj.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_dict")
public class DjDict {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 字典类型
     */
    @Column(name = "dict_type")
    private String dictType;

    /**
     * 字典类型描述
     */
    @Column(name = "dict_type_name")
    private String dictTypeName;

    /**
     * 字典代码
     */
    @Column(name = "dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @Column(name = "dict_name")
    private String dictName;

    /**
     * 字典描述
     */
    private String remark;

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
     * 获取字典类型
     *
     * @return dict_type - 字典类型
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * 设置字典类型
     *
     * @param dictType 字典类型
     */
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    /**
     * 获取字典类型描述
     *
     * @return dict_type_name - 字典类型描述
     */
    public String getDictTypeName() {
        return dictTypeName;
    }

    /**
     * 设置字典类型描述
     *
     * @param dictTypeName 字典类型描述
     */
    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    /**
     * 获取字典代码
     *
     * @return dict_code - 字典代码
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * 设置字典代码
     *
     * @param dictCode 字典代码
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 获取字典名称
     *
     * @return dict_name - 字典名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 设置字典名称
     *
     * @param dictName 字典名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取字典描述
     *
     * @return remark - 字典描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置字典描述
     *
     * @param remark 字典描述
     */
    public void setRemark(String remark) {
        this.remark = remark;
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