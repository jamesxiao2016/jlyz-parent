package cn.dlbdata.dangjian.db.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dj_partymember")
public class DjPartymember {
    /**
     * 记录ID
     */
    @Id
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 所在支部
     */
    @Column(name = "deptId")
    private Long deptid;

    /**
     * 转入支部
     */
    @Column(name = "from_dept_id")
    private Long fromDeptId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 职位ID
     */
    @Column(name = "post_id")
    private Integer postId;

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
     * @return sex - 性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取身份证号码
     *
     * @return idcard - 身份证号码
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * 设置身份证号码
     *
     * @param idcard 身份证号码
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * 获取所在支部
     *
     * @return deptId - 所在支部
     */
    public Long getDeptid() {
        return deptid;
    }

    /**
     * 设置所在支部
     *
     * @param deptid 所在支部
     */
    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    /**
     * 获取转入支部
     *
     * @return from_dept_id - 转入支部
     */
    public Long getFromDeptId() {
        return fromDeptId;
    }

    /**
     * 设置转入支部
     *
     * @param fromDeptId 转入支部
     */
    public void setFromDeptId(Long fromDeptId) {
        this.fromDeptId = fromDeptId;
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取职位ID
     *
     * @return post_id - 职位ID
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * 设置职位ID
     *
     * @param postId 职位ID
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
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