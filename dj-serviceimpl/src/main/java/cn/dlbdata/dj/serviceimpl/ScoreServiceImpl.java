/**
 *  <p>Title: ScoreServiceImpl.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月24日 
 */
package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.constant.ActiveStatusEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.mapper.DjSubTypeMapper;
import cn.dlbdata.dj.db.mapper.DjTypeMapper;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.db.pojo.DjType;
import cn.dlbdata.dj.db.vo.ScoreActiveVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.service.IScoreService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

/**
 * <p>
 * Title: ScoreServiceImpl
 * </p>
 * 
 * @author zhouxuan
 *         <p>
 * 		Description:
 *         </p>
 * @date 2018年5月24日
 */
@Service
public class ScoreServiceImpl extends BaseServiceImpl implements IScoreService {
	@Autowired
	private DjTypeMapper typeMapper;

	@Autowired
	private DjSubTypeMapper subTypeMapper;
	
	@Autowired
	private DjScoreMapper scoreMapper;

	/*
	 * (non-Javadoc) <p>Title: getActiveTypeList</p> <p>Description: 活动类型列表</p>
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IScoreService#getActiveTypeList()
	 */
	@Override
	public List<DjSubType> getActiveTypeList() {
		return subTypeMapper.getActiveTypeList();
	}

	@Override
	public DjType getTypeInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return typeMapper.selectByPrimaryKey(id);
	}

	@Override
	public DjSubType getSubTypeInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return subTypeMapper.selectByPrimaryKey(id);
	}

	/* (non-Javadoc)
	 * <p>Title: getScoreAndActiveList</p>
	 * <p>Description: </p> 
	 * @param userId
	 * @return  
	 * @see cn.dlbdata.dj.service.IScoreService#getScoreAndActiveList(java.lang.Integer)
	 */
	@Override
	public List<ScoreActiveVo> getScoreAndActiveList(Long userId) {
		List<ScoreActiveVo> list = scoreMapper.getScoreAndActiveList(userId);
		if(list.size() != 0 && list != null ) {
			for (ScoreActiveVo scoreActiveVo : list) {
				if(scoreActiveVo.getDjTypeId() == 11 || scoreActiveVo.getDjTypeId() == 21 || scoreActiveVo.getDjTypeId() == 23 || scoreActiveVo.getDjTypeId() == 61 ) {
					scoreActiveVo.setStatus(ActiveStatusEnum.ACTIVE_VALID.getValue());
				}else {
					scoreActiveVo.setStatus(ActiveStatusEnum.ACTIVE_INVALID.getValue());
				}
			}
		}
		
		return list;
	}


}
