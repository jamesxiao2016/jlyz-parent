/**
 *  <p>Title: IScoreService.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月24日 
 */
package cn.dlbdata.dj.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.db.pojo.DjType;
import cn.dlbdata.dj.db.vo.ScoreActiveVo;

/**
 * <p>
 * Title: IScoreService
 * </p>
 * 
 * @author zhouxuan
 *         <p>
 * 		Description:
 *         </p>
 * @date 2018年5月24日
 */
public interface IScoreService {
	/**
	 * 
	 * <p>
	 * Title: getActiveTypeList
	 * </p>
	 * <p>
	 * Description: 获取活动类型列表
	 * </p>
	 * 
	 * @return
	 */
	public List<DjSubType> getActiveTypeList();

	/**
	 * 获取分类信息
	 * 
	 * @param id
	 * @return
	 */
	public DjType getTypeInfoById(Long id);

	/**
	 * 获取二级分类信息
	 * 
	 * @param id
	 * @return
	 */
	public DjSubType getSubTypeInfoById(Long id);
	
	public List<ScoreActiveVo> getScoreAndActiveList(@Param("userId") Integer userId);
}
