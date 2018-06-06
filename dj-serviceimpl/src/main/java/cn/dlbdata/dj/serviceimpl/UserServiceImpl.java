package cn.dlbdata.dj.serviceimpl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.JwtTokenUtil;
import cn.dlbdata.dj.common.core.util.cache.CacheManager;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.util.security.MD5Util;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.util.PingyinUtil;
import cn.dlbdata.dj.common.util.StringUtil;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.LoginVo;
import cn.dlbdata.dj.vo.UserVo;

@Service
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
	private DjSectionMapper sectionMapper;
	@Autowired
	private DjPartymemberMapper partyMemberMapper;
	@Autowired
	private DjScoreMapper scoreMapper;

	@Override
	public DjUser getUserInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public DjUser getUserInfoByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		DjUser user = new DjUser();
		user.setName(name);
		List<DjUser> list = userMapper.select(user);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public DjUser getUserInfoByPartyMemberId(Long memberId) {
		if (memberId == null) {
			return null;
		}
		DjUser user = new DjUser();
		user.setDjPartymemberId(memberId);
		List<DjUser> list = userMapper.select(user);
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	@Override
	public DjPartymember getPartyMemberInfoByUserId(Long userId) {
		if (userId == null) {
			return null;
		}

		DjUser user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return null;
		}
		DjPartymember member = partyMemberMapper.selectByPrimaryKey(user.getDjPartymemberId());

		return member;
	}

	@Override
	public DjDept getUserDeptByUser(Long userId) {
		if (userId == null) {
			return null;
		}

		DjUser user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return null;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(user.getDeptId());

		return dept;
	}

	@Override
	public ResultVo<UserVo> login(LoginVo vo) {
		ResultVo<UserVo> result = new ResultVo<>(ResultCode.Forbidden.getCode());
		if (StringUtils.isEmpty(vo.getName()) || StringUtils.isEmpty(vo.getPwd())) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("请输入用户名或密码");
			return result;
		}
		long startTime = System.currentTimeMillis();
		// 用户检查
		DjUser user = getUserInfoByName(vo.getName());
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("用户名或密码错误");
			return result;
		}
		long endUserTime = System.currentTimeMillis();
		logger.info("查询用户->" + (endUserTime - startTime));

		// 密码验证
		if (!vo.getPwd().equalsIgnoreCase(user.getPwd())) {
			logger.error("密码错误");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("用户名或密码错误");
			return result;
		}

		// TODO 角色权限判断，判断登录的角色是否匹配(后续处理）
		UserVo data = getUserDetailByUser(user, 1, user.getRoleId());
		if (data == null) {
			logger.error("获取用户信息失败");
			return result;
		}

		long endQueryUserInfoTime = System.currentTimeMillis();
		logger.info("查询用户详细信息->" + (endQueryUserInfoTime - endUserTime));
		// 生成token
		String token = JwtTokenUtil.createToken(user.getId() + "", user.getName(), user.getDeptId() + "",
				user.getRoleId() + "", 0);
		data.setToken(token);
		JwtTokenUtil.USER_TICKET_CACHE.put(MD5Util.encode(token), token);
		long endTime = System.currentTimeMillis();
		logger.info("create token time->" + (endTime - endQueryUserInfoTime));

		CacheManager.getInstance().put(user.getId() + "", data);
		logger.info("total time->" + (endTime - startTime));
		// 返回结果
		result.setCode(ResultCode.OK.getCode());
		result.setMsg("登录成功");
		result.setData(data);
		return result;
	}

	@Override
	public ResultVo<String> updatePwd(LoginVo vo) {
		ResultVo<String> result = new ResultVo<>(ResultCode.Forbidden.getCode());
		if (StringUtils.isEmpty(vo.getName()) || StringUtils.isEmpty(vo.getPwd())) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("请输入用户名或密码");
			return result;
		}

		// 用户检查
		DjUser user = getUserInfoByName(vo.getName());
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("用户名或密码错误");
			return result;
		}

		// 密码验证
		if (!vo.getPwd().equalsIgnoreCase(user.getPwd())) {
			logger.error("密码错误");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("用户名或密码错误");
			return result;
		}

		user.setPwd(vo.getNewPwd());
		int count = userMapper.updateByPrimaryKeySelective(user);
		if (count > 0) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(user.getId() + "");
		} else {
			result.setMsg("修改密码失败");
		}
		return result;
	}

	@Override
	public UserVo getUserDetailById(Long id, Integer isShowScore, Long roleId) {
		if (id == null) {
			return null;
		}

		DjUser user = userMapper.selectByPrimaryKey(id);
		if (user == null) {
			return null;
		}
		UserVo data = getUserDetailByUser(user, isShowScore, roleId);
		return data;
	}

	/**
	 * 根据用户对象获取用户的详细信息
	 * 
	 * @param user
	 * @return
	 */
	private UserVo getUserDetailByUser(DjUser user, Integer isShowScore, Long roleId) {
		if (user == null) {
			return null;
		}
		if(roleId == null) {
			roleId = user.getRoleId();
		}
		UserVo data = new UserVo();
		data.setDeptId(user.getDeptId());
		data.setMemeberId(user.getDjPartymemberId());
		data.setName(user.getName());
		data.setUserId(user.getId());
		data.setAvatar(user.getAvatar());
		data.setRoleId(user.getRoleId());
		data.setUserName(user.getUserName());

		// 获取党员信息
		DjPartymember member = partyMemberMapper.selectByPrimaryKey(user.getDjPartymemberId());
		if (member != null) {
			data.setUserName(member.getName());
			data.setSex(member.getSexCode());
		}
		DjDept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
		if (dept != null) {
			data.setSectionId(dept.getDjSectionId());
			String name = dept.getPrincipalName();
			if (StringUtils.isEmpty(name)) {
				DjUser principal = userMapper.selectByPrimaryKey(dept.getPrincipalId());
				if (principal != null) {
					name = principal.getUserName();
				}
			}
			data.setPartyBranchName(name);
			data.setDeptName(dept.getName());
			// 党委
			data.setPartyCommittee(dept.getAddress());
			
			//党支书角色
			if (RoleEnum.BRANCH_PARTY.getId().equals(roleId)) {
				data.setHonor(dept.getHonor());
				data.setPeopleNum(dept.getPeopleNum());
				DjSection section = sectionMapper.selectByPrimaryKey(dept.getDjSectionId());
				if (section != null) {
					data.setSectionName(section.getName());
				}
			}

			// 片区负责人，显示片区总人数
			if (RoleEnum.HEADER_OF_DISTRICT.getId().equals(roleId)) {
				DjDept deptCondition = new DjDept();
				deptCondition.setDjSectionId(dept.getDjSectionId());
				Integer deptNum = deptMapper.selectCount(deptCondition);
				data.setDeptNum(deptNum);
				Integer peopleNum = deptMapper.getSectionPeopleNum(dept.getDjSectionId());
				data.setCommitteeNum(peopleNum);
			}
		}
		if (isShowScore != null && isShowScore == 1) {
			Float totalScore = getSumScoreByUserId(user.getId(), Calendar.getInstance().get(Calendar.YEAR));
			data.setTotalScore(totalScore);
		}
		// CacheManager.getInstance().put(user.getId() + "", data);
		return data;
	}

	@Override
	public Float getSumScoreByUserId(Long userId, Integer year) {
		if (userId == null) {
			return 0F;
		}
		return scoreMapper.getSumScoreByUserId(userId, year);
	}

	@Override
	public List<DjUser> getALlUser() {
		List<DjPartymember> list = partyMemberMapper.selectAll();
		if (list != null) {
			logger.info("总数" + list.size());
			DjUser user = null;
			for (DjPartymember partyMember : list) {
				String name = partyMember.getName();

				String pinyingName = name;
				try {
					pinyingName = PingyinUtil.cn2SpellNoBlank(name);
				} catch (Exception e) {
					// 不处理此异常
				}

				// 处理账号重复的情况
				pinyingName = checkName(pinyingName);

				String password = StringUtil.getMD5Digest32("12345678");

				user = new DjUser();
				user.setId(partyMember.getId());
				user.setDjPartymemberId(partyMember.getId());
				user.setDeptId(partyMember.getDeptId());
				user.setPwd(password);
				user.setName(pinyingName);
				user.setUserName(name);
				user.setRoleId(0L);

				userMapper.insertSelective(user);
				logger.info(name + ":" + pinyingName);
			}
		}
		return null;
	}

	/**
	 * 递归处理账号问题，重复的自动在后面加1
	 * 
	 * @param name
	 * @return
	 */
	String str = "";

	private String checkName(String name) {
		str = name;
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		DjUser user = new DjUser();
		user.setName(name);
		int count = userMapper.selectCount(user);
		if (count > 0) {
			String endNumber = StringUtil.getEndDigit(name);
			if (StringUtils.isEmpty(endNumber)) {
				name = name + "1";
			} else {
				name = name.substring(0, name.length() - endNumber.length()) + (DigitUtil.parseToInt(endNumber) + 1);
			}
			checkName(name);
		}

		return str;
	}
}
