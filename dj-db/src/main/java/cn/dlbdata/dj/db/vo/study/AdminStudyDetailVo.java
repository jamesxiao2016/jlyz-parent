/**
 *  <p>Title: AdminStudyDetailVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月14日 
 */
package cn.dlbdata.dj.db.vo.study;

import java.sql.Timestamp;

/**
 * <p>Title: AdminStudyDetailVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月14日  
 */
public class AdminStudyDetailVo {
	private Long id;
	private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer status;
    private String userName;
    private String deptName;
    private String typeName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
    
}
