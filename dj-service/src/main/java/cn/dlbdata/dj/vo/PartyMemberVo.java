/**
 *  <p>Title: MemberVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月24日 
 */
package cn.dlbdata.dj.vo;

import java.io.Serializable;

/**
 * <p>Title: MemberVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年5月24日  
 */
public class PartyMemberVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 党员id
	 */
	private Long id;
	/**
	 * 党员姓名
	 */
	private String name;
	/**
	 * 党员性别
	 */
	private Integer sexCode;
	/**
	 * 党员年龄
	 */
	private Integer age;
	/**
	 * 职位id
	 */
	private Integer postId;
	/**
	 * 总分数
	 */
	private float totalScore;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSexCode() {
		return sexCode;
	}
	public void setSexCode(Integer sexCode) {
		this.sexCode = sexCode;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public float getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}
	

}
