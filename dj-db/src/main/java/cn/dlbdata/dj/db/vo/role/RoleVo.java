/**
 *  <p>Title: RoleVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月13日 
 */
package cn.dlbdata.dj.db.vo.role;

/**
 * <p>Title: RoleVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月13日  
 */
public class RoleVo {
	/**
	 * 角色id
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色状态
	 */
	private Integer status;
	/**
	 * 备注信息
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
