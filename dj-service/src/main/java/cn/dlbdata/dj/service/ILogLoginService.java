/**
 *  <p>Title: ILogLoginService.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月4日 
 */
package cn.dlbdata.dj.service;

import cn.dlbdata.dj.db.pojo.DjLogLogin;

/**
 * <p>Title: ILogLoginService</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月4日  
 */
public interface ILogLoginService {
	/**
	 * 	
	 * <p>Title: insertLoginLogger</p> 
	 * <p>Description: 登录记录日志</p> 
	 * @param djLogLogin
	 * @return
	 */
	int insertLoginLogger(DjLogLogin djLogLogin);
	
	/**
	 * 
	 * <p>Title: deleteById</p> 
	 * <p>Description: 删除登录日志</p> 
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);
}
