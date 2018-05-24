/**
 *  <p>Title: ActiveUserServiceImpl.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月23日 
 */
package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjActiveUserMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.dto.active.ActiveSignUpRequest;
import cn.dlbdata.dj.service.IActiveUserService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>Title: ActiveUserServiceImpl</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年5月23日  
 */
@Service
public class ActiveUserServiceImpl extends BaseService implements IActiveUserService{
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveUserMapper activeUserMapper;
	@Autowired
	private DjUserMapper userMapper;
	
	/* (non-Javadoc)
	 * <p>Title: insertActiveSignUp</p>
	 * <p>Description: 金领驿站活动报名接口</p> 
	 * @param activeSignUpRequest
	 * @return  
	 * @see cn.dlbdata.dj.service.IActiveUserService#insertActiveSignUp(cn.dlbdata.dj.db.resquest.ActiveSignUpRequest)
	 */
	@Override
	public ResultVo<String> insertActiveSignUp(ActiveSignUpRequest activeSignUpRequest) {
		ResultVo<String> result = new ResultVo<>();
		if(activeSignUpRequest == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请求参数不能为空");
			return result;
		}
		if (activeMapper.selectByPrimaryKey(activeSignUpRequest.getActiveId()) == null) {
			result.setMsg("活动不存在！");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		if (userMapper.selectByPrimaryKey(activeSignUpRequest.getUserId()) == null) {
			result.setMsg("用户不存在！");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		if (selectByExample(activeSignUpRequest.getUserId(),activeSignUpRequest.getActiveId()).size() >0) {
			result.setMsg("请勿重复报名");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		DjActiveUser record = new DjActiveUser();
		record.setDjActiveId(activeSignUpRequest.getActiveId());
		record.setDjUserId(activeSignUpRequest.getUserId());
		record.setStatus(0);		
		activeUserMapper.insertSelective(record);
		result.setCode(ResultCode.OK.getCode());
		result.setMsg("报名成功");
		return result;
	}

	/* (non-Javadoc)
	 * <p>Title: selectByExample</p>
	 * <p>Description: 查询用户某个活动是否报名</p> 
	 * @param userId
	 * @param activeId
	 * @return  
	 * @see cn.dlbdata.dj.service.IActiveUserService#selectByExample(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<DjActiveUser> selectByExample(Long userId, Long activeId) {
		if(userId == null || activeId == null) {
			return null;
		}
		Example example = new Example(DjActiveUser.class);
		example.createCriteria().andEqualTo("djUserId", userId).andEqualTo("djActiveId",activeId);
		return activeUserMapper.selectByExample(example);
	}
	
	
	

}
