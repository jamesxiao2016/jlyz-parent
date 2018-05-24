/**
 *  <p>Title: IScoreService.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月24日 
 */
package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.db.pojo.DjSubType;

/**
 * <p>Title: IScoreService</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年5月24日  
 */
public interface IScoreService {
	/**
	 * 
	 * <p>Title: getActiveTypeList</p> 
	 * <p>Description: 获取活动类型列表</p> 
	 * @return
	 */
	public List<DjSubType> getActiveTypeList();
}
