/**
 *  <p>Title: LogOptServiceImpl.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.db.mapper.DjLogOptMapper;
import cn.dlbdata.dj.service.ILogOptService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

/**
 * <p>Title: LogOptServiceImpl</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
@Service
public class LogOptServiceImpl extends BaseServiceImpl implements ILogOptService{
	
	@Autowired
	private DjLogOptMapper logOptMapper;
	
	/* (non-Javadoc)
	 * <p>Title: deleteById</p>
	 * <p>Description: 删除操作日志</p> 
	 * @param id
	 * @return  
	 * @see cn.dlbdata.dj.service.ILogOptService#deleteById(java.lang.Long)
	 */
	@Override
	public Long deleteById(Long id) {
		if (id == null) {
			logger.error("id is null");
			return null;
		}
		logOptMapper.deleteByPrimaryKey(id);
		return id;
	}

	/* (non-Javadoc)
	 * <p>Title: getTop5PartyBanch</p>
	 * <p>Description: 获取使用排名前5的党支部</p> 
	 * @param year
	 * @return  
	 * @see cn.dlbdata.dj.service.ILogOptService#getTop5PartyBanch(int)
	 */
	@Override
	public List<Map<String, Object>> getTop5PartyBanch(Integer year) {
		if(year == null) {
			logger.error("year ->"+ year);
			return null;
		}
		return logOptMapper.getTop5PartyBanch(year);
	}

	/* (non-Javadoc)
	 * <p>Title: getActiveJoin</p>
	 * <p>Description: 获取党员参与活动情况 </p> 
	 * @param year
	 * @param month
	 * @return  
	 * @see cn.dlbdata.dj.service.ILogOptService#getActiveJoin(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> getActiveJoin(Integer year, Integer month) {
		if(year == null && month == null) {
			logger.error("year ->"+ year + "month->"+ month);
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("month", month);
		return logOptMapper.getActiveJoin(map);
	}

}
