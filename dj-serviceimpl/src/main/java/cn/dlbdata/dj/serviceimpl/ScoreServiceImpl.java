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

import cn.dlbdata.dj.db.mapper.DjSubTypeMapper;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.service.IScoreService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;

/**
 * <p>Title: ScoreServiceImpl</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年5月24日  
 */
@Service
public class ScoreServiceImpl  extends BaseService implements IScoreService{
	
	@Autowired
	private DjSubTypeMapper subTypeMapper;
	
	
	/* (non-Javadoc)
	 * <p>Title: getActiveTypeList</p>
	 * <p>Description: 活动类型列表</p> 
	 * @return  
	 * @see cn.dlbdata.dj.service.IScoreService#getActiveTypeList()
	 */
	@Override
	public List<DjSubType> getActiveTypeList() {
		return subTypeMapper.getActiveTypeList();
	}

}
