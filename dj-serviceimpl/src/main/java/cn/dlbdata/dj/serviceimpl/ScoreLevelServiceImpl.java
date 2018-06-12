/**
 *  <p>Title: ScoreLevelServiceImpl.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.db.mapper.DjScoreLevelMapper;
import cn.dlbdata.dj.service.IScoreLevelService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

/**
 * <p>Title: ScoreLevelServiceImpl</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
@Service
public class ScoreLevelServiceImpl extends BaseServiceImpl implements IScoreLevelService{
	
	@Autowired
	private DjScoreLevelMapper scoreLevelMapper;
	
	/* (non-Javadoc)
	 * <p>Title: deleteById</p>
	 * <p>Description: 删除积分等级</p> 
	 * @param id
	 * @return  
	 * @see cn.dlbdata.dj.service.IScoreLevelService#deleteById(java.lang.Long)
	 */
	@Override
	public Long deleteById(Long id) {
		if (id == null) {
			logger.error("id is null");
			return null;
		}
		scoreLevelMapper.deleteByPrimaryKey(id);
		return id;
	}

}
