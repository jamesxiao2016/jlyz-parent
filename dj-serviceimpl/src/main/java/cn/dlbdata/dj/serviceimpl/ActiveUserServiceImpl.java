/**
 *  <p>Title: ActiveUserServiceImpl.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月23日 
 */
package cn.dlbdata.dj.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjActivePicMapper;
import cn.dlbdata.dj.db.mapper.DjActiveUserMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActivePic;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.dto.ActiveSignUpRequest;
import cn.dlbdata.dj.service.IActiveUserService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * Title: ActiveUserServiceImpl
 * </p>
 * 
 * @author zhouxuan
 *         <p>
 *         Description:
 *         </p>
 * @date 2018年5月23日
 */
@Service
public class ActiveUserServiceImpl extends BaseServiceImpl implements IActiveUserService {
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveUserMapper activeUserMapper;
	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjActivePicMapper activePicMapper;

	/*
	 * (non-Javadoc) <p>Title: insertActiveSignUp</p> <p>Description: 金领驿站活动报名接口</p>
	 * 
	 * @param activeSignUpRequest
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IActiveUserService#insertActiveSignUp(cn.dlbdata.dj.db.resquest.ActiveSignUpRequest)
	 */
	@Override
	public ResultVo<String> insertActiveSignUp(ActiveSignUpRequest activeSignUpRequest) {
		ResultVo<String> result = new ResultVo<>();
		if (activeSignUpRequest == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请求参数不能为空");
			return result;
		}
		DjActive djActive = activeMapper.selectByPrimaryKey(activeSignUpRequest.getActiveId());
		if ( djActive == null) {
			result.setMsg("活动不存在！");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		DjUser djUser = userMapper.selectByPrimaryKey(activeSignUpRequest.getUserId());
		if ( djUser == null) {
			result.setMsg("用户不存在！");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		List<DjActiveUser> list = selectByExample(activeSignUpRequest.getUserId(), activeSignUpRequest.getActiveId());
		
		if (list.size() > 0) {
			logger.error("请勿重复报名");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请勿重复报名");
			return result;
		}
		DjActiveUser record = new DjActiveUser();
		record.setDjActiveId(activeSignUpRequest.getActiveId());
		record.setDjUserId(activeSignUpRequest.getUserId());
		record.setStatus(DlbConstant.BASEDATA_STATUS_INVALID);
		activeUserMapper.insertSelective(record);
		result.setCode(ResultCode.OK.getCode());
		result.setMsg("报名成功");
		return result;
	}

	/*
	 * (non-Javadoc) <p>Title: selectByExample</p> <p>Description: 查询用户某个活动是否报名</p>
	 * 
	 * @param userId
	 * 
	 * @param activeId
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IActiveUserService#selectByExample(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<DjActiveUser> selectByExample(Long userId, Long activeId) {
		if (userId == null || activeId == null) {
			return null;
		}
		Example example = new Example(DjActiveUser.class);
		example.createCriteria().andEqualTo("djUserId", userId).andEqualTo("djActiveId", activeId);
		return activeUserMapper.selectByExample(example);
	}

	/*
	 * (non-Javadoc) <p>Title: getMyJoinActive</p> <p>Description: 已参与党员生活列表</p>
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IActiveUserService#getMyJoinActive()
	 */
	@Override
	public List<DjActive> getMyJoinActive(Long userId, Integer status) {
		if (userId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("status", status);
		List<DjActive> list = activeUserMapper.getMyJoinActive(map);
		if (list == null || list.isEmpty()) {
			logger.error("没有查询到活动信息.userId->" + userId + " status->" + status);
			return list;
		}
		Example example = new Example(DjActivePic.class);
		Long[] picIds = null;
		for (DjActive djActive : list) {
			example.createCriteria().andEqualTo("djActiveId", djActive.getId());
			List<DjActivePic> picList = activePicMapper.selectByExample(example);
			/* 将与该活动相关的图片的id加入数组中 */
			if (picList != null && picList.size() > 0) {
				picIds = new Long[picList.size()];
				for (int i = 0, count = picList.size(); i < count; i++) {
					picIds[i] = picList.get(i).getDjPicId();
				}
				djActive.setPicIds(picIds);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc) <p>Title: queryActivePicByActiveId</p> <p>Description: 查询活动相关的图片集</p>
	 * 
	 * @param activeId
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IActiveUserService#queryActivePicByActiveId(java.lang.Long)
	 */
	@Override
	public List<DjActivePic> queryActivePicByActiveId(Long activeId) {
		if (activeId == null) {
			logger.error("参数获取失败");
			return null;
		}
		Example example = new Example(DjActivePic.class);
		example.createCriteria().andEqualTo("djActiveId", activeId);
		List<DjActivePic> list = activePicMapper.selectByExample(example);
		return list;
	}

}
