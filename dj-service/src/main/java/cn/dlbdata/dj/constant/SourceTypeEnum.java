/**
 *  <p>Title: SourceTypeEnum.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月25日 
 */
package cn.dlbdata.dj.constant;

/**
 * <p>Title: SourceTypeEnum</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月25日  
 */
public enum SourceTypeEnum {
	LOCAL_LOGIN(1,"本地登录"),
	THIRD_LOGIN(2,"第三方登陆"),
	FREE_LOGIN(3,"免登陆");
	
	SourceTypeEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
