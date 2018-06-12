/**
 *  <p>Title: LogLoginServiceImpl.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月4日 
 */
package cn.dlbdata.dj.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.db.mapper.DjLogLoginMapper;
import cn.dlbdata.dj.db.pojo.DjLogLogin;
import cn.dlbdata.dj.service.ILogLoginService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

/**
 * <p>Title: LogLoginServiceImpl</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月4日  
 */
@Service
public class LogLoginServiceImpl extends BaseServiceImpl implements ILogLoginService{
	
	@Autowired
	private DjLogLoginMapper logLoginMapper;
	/* (non-Javadoc)
	 * <p>Title: insertLoginLogger</p>
	 * <p>Description: 记录登录日志</p> 
	 * @param djLogLogin
	 * @return  
	 * @see cn.dlbdata.dj.service.ILogLoginService#insertLoginLogger(cn.dlbdata.dj.db.pojo.DjLogLogin)
	 */
	@Override
	public int insertLoginLogger(DjLogLogin djLogLogin) {
		if(djLogLogin == null) {
			return 0;
		}
		return logLoginMapper.insertSelective(djLogLogin);
	}
	
	/* (non-Javadoc)
	 * <p>Title: deleteById</p>
	 * <p>Description: 删除登录日志</p> 
	 * @param id
	 * @return  
	 * @see cn.dlbdata.dj.service.ILogLoginService#deleteById(java.lang.Long)
	 */
	@Override
	public Long deleteById(Long id) {
		if (id == null) {
			logger.error("id is null");
			return null;
		}
		logLoginMapper.deleteByPrimaryKey(id);
		return id;
	}

}
