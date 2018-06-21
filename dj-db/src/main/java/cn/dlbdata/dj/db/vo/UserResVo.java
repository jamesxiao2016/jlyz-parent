/**
 *  <p>Title: UserResVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月21日 
 */
package cn.dlbdata.dj.db.vo;

/**
 * <p>Title: UserResVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月21日  
 */
public class UserResVo {
	
	private Long userId;//   用户id
	private String userName;//  用户姓名
	private String userNo ;//    工号
	private String password ;//   密码
	private String userPhone;//   手机号
	private String headerImg ;//   头像
	private String partyWork  ;//  党内职务
	private String isMiandeng ;//  是否免登 1.是 0.否
	private String deptId ;//所属科室            
	private String idcard ;//身份证号           
	private String political;// 政治面貌          
	private String joinTime ;//入党时间             
	private String sex ;//性别             
	private String nation;// 民族        
	private String deptName ;//科室名称 
	private String title    ;//职称
	private String station  ;// 岗位
	private Float integral   ;// 积分

	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getHeaderImg() {
		return headerImg;
	}
	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}
	public String getPartyWork() {
		return partyWork;
	}
	public void setPartyWork(String partyWork) {
		this.partyWork = partyWork;
	}
	public String getIsMiandeng() {
		return isMiandeng;
	}
	public void setIsMiandeng(String isMiandeng) {
		this.isMiandeng = isMiandeng;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPolitical() {
		return political;
	}
	public void setPolitical(String political) {
		this.political = political;
	}
	public String getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public Float getIntegral() {
		return integral;
	}
	public void setIntegral(Float integral) {
		this.integral = integral;
	}

	
}
