package cn.dlbdata.dj.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class StudyVo implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 申请ID
     */
    @Id
    private Long id;

    /**
     * 分类ID
     */
    @Column(name = "dj_type_id")
    private Long djTypeId;

    /**
     * 二级分类ID
     */
    @Column(name = "dj_sub_type_id")
    private Long djSubTypeId;
    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 学习内容
     */
    private String content;
    
    /**
     * 党员ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 党员姓名
     */
    @Column(name = "user_name")
    private String userName;
    
    /**
     * 图片
     */
    private String[] pics ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDjTypeId() {
		return djTypeId;
	}

	public void setDjTypeId(Long djTypeId) {
		this.djTypeId = djTypeId;
	}

	public Long getDjSubTypeId() {
		return djSubTypeId;
	}

	public void setDjSubTypeId(Long djSubTypeId) {
		this.djSubTypeId = djSubTypeId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}
    
}
