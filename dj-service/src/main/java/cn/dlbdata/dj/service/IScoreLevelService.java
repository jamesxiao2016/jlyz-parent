/**
 *  <p>Title: IScoreLevelService.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.service;

import cn.dlbdata.dj.db.pojo.DjScoreLevel;

/**
 * <p>Title: IScoreLevelService</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
public interface IScoreLevelService {
	
	/**
	 * 
	 * <p>Title: deleteById</p> 
	 * <p>Description: 删除分数等级</p> 
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);
	
	/**
	 * 保存或更新
	 * 
	 * @param section
	 * @return
	 */
	public Long saveOrUpdate(DjScoreLevel djScoreLevel);
	
	/**
	 * 
	 * <p>Title: getRoleInfoById</p> 
	 * <p>Description: 根据id获取积分等级</p> 
	 * @param id
	 * @return
	 */
	public DjScoreLevel getScoreLevelInfoById(Long id);
}
