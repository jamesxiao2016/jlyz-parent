/**
 *  <p>Title: ILogOptService.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: ILogOptService</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
public interface ILogOptService {
	
	/**
	 * 
	 * <p>Title: deleteById</p> 
	 * <p>Description: 删除操作日志</p> 
	 * @param id
	 * @return
	 */
	public Long  deleteById(Long id);
	
	List<Map<String, Object>> getTop5PartyBanch(Integer year);
}
