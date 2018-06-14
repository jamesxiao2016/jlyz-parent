/**
 *  <p>Title: ScoreLevelServiceImpl.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.db.mapper.DjScoreLevelMapper;
import cn.dlbdata.dj.db.pojo.DjScoreLevel;
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

	/* (non-Javadoc)
	 * <p>Title: saveOrUpdate</p>
	 * <p>Description: 添加或者更新</p> 
	 * @param djRole
	 * @return  
	 * @see cn.dlbdata.dj.service.IScoreLevelService#saveOrUpdate(cn.dlbdata.dj.db.pojo.DjRole)
	 */
	@Override
	public Long saveOrUpdate(DjScoreLevel djScoreLevel) {
		if (djScoreLevel == null) {
			return null;
		}
		DjScoreLevel record = null;
		Long id = djScoreLevel.getId();
		if (id != null) {
			record = scoreLevelMapper.selectByPrimaryKey(id);
		}
		boolean isSave = false;
		if (record == null) {
			isSave = true;
			record = new DjScoreLevel();
		}
		BeanUtils.copyProperties(djScoreLevel, record);
		if (isSave) {
			id = DigitUtil.generatorLongId();
			record.setId(id);
			scoreLevelMapper.insertSelective(record);
		} else {
			scoreLevelMapper.updateByPrimaryKey(record);
		}

		return id;
	}

	/* (non-Javadoc)
	 * <p>Title: getScoreLevelInfoById</p>
	 * <p>Description: 根据id获取积分等级</p> 
	 * @param id
	 * @return  
	 * @see cn.dlbdata.dj.service.IScoreLevelService#getScoreLevelInfoById(java.lang.Long)
	 */
	@Override
	public DjScoreLevel getScoreLevelInfoById(Long id) {
		if(id == null) {
			return null;
		}
		return scoreLevelMapper.selectByPrimaryKey(id);
	}

}
