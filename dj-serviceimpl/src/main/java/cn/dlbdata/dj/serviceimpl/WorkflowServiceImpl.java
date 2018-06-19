package cn.dlbdata.dj.serviceimpl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.StringUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.AuditStatusEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjApplyMapper;
import cn.dlbdata.dj.db.mapper.DjApproveMapper;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjDisciplineMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjPicRecordMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.mapper.DjSubTypeMapper;
import cn.dlbdata.dj.db.mapper.DjThoughtsMapper;
import cn.dlbdata.dj.db.mapper.DjTypeMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.mapper.DjVanguardMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.pojo.DjApprove;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjDiscipline;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjPicRecord;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.db.pojo.DjThoughts;
import cn.dlbdata.dj.db.pojo.DjType;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.db.pojo.DjVanguard;
import cn.dlbdata.dj.db.vo.ToDoVo;
import cn.dlbdata.dj.db.vo.apply.PioneeringApplyDetailVo;
import cn.dlbdata.dj.db.vo.apply.ScoreApplyVo;
import cn.dlbdata.dj.db.vo.apply.ScoreAuditDetailVo;
import cn.dlbdata.dj.db.vo.party.IdNameTotalScoreVo;
import cn.dlbdata.dj.db.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.dto.vangard.VanguardParamVo;
import cn.dlbdata.dj.dto.vangard.VanguardVo;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.AuditVo;
import cn.dlbdata.dj.vo.DisciplineVo;
import cn.dlbdata.dj.vo.ThoughtsVo;
import cn.dlbdata.dj.vo.UserVo;

@Service
public class WorkflowServiceImpl extends BaseServiceImpl implements IWorkflowService {
	@Autowired
	private DjApplyMapper applyMapper;
	@Autowired
	private DjApproveMapper approveMapper;
	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
	private DjSectionMapper sectionMapper;
	@Autowired
	private DjStudyMapper studyMapper;
	@Autowired
	private DjThoughtsMapper thoughtsMapper;
	@Autowired
	private DjDisciplineMapper disciplineMapper;
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjVanguardMapper vanguardMapper;
	@Autowired
	private DjPicRecordMapper picRecordMapper;
	@Autowired
	private DjTypeMapper typeMapper;
	@Autowired
	private DjSubTypeMapper subTypeMapper;
	@Autowired
	private DjScoreMapper scoreMapper;
	@Autowired
	private DjPartymemberMapper partymemberMapper;

	@Transactional
	@Override
	public String doApply(ApplyVo vo, UserVo user) {

		if (vo == null || user == null) {
			logger.error("参数错误");
			return CoreConst.MSG_FAIL_PARAM;
		}

		DjApply record = new DjApply();
		record.setApplyDesc(vo.getRemark());
		record.setApplyInfo(StringUtil.subString(vo.getContent(), 128));
		record.setCreateTime(new Date());
		record.setDjDeptId(user.getDeptId());
		record.setDjTypeId(vo.getDjTypeId());
		record.setDjSubTypeId(vo.getDjSubTypeId());
		record.setRecordId(vo.getRecordId());
		record.setTableName(vo.getTableName());
		record.setScore(vo.getScore());
		record.setStatus(0);
		record.setApplyId(user.getUserId());
		record.setApplyName(user.getUserName());
		record.setUserId(vo.getUserId());
		record.setUserName(vo.getUserName());
		record.setDjRoleId(vo.getRoleId());
		record.setApplyYear(vo.getApplyYear());
		DjSubType subType = subTypeMapper.selectByPrimaryKey(vo.getDjSubTypeId());
		if (vo.getScore() == null) {
			record.setScore(subType.getScore());
		}
		record.setSubTypeName(subType.getName());
		DjUser approver = null;

		if (vo.getDjTypeId() == ActiveTypeEnum.ACTIVE_A.getActiveId()
				|| vo.getDjTypeId() == ActiveTypeEnum.ACTIVE_B.getActiveId()
				|| vo.getDjTypeId() == ActiveTypeEnum.ACTIVE_F.getActiveId()) {
			approver = getApprover(user.getUserId());
		} else {
			approver = getHeaderApprover(user.getUserId());
		}
		if (approver != null) {
			record.setApproverId(approver.getId());
			record.setApproverName(approver.getUserName());
		}
		int count = applyMapper.insertSelective(record);
		if (count == 0) {
			return CoreConst.FAIL;
		}
		return CoreConst.SUCCESS;
	}

	/**
	 * 获取当前部门的党支书
	 * 
	 * @param deptId
	 * @return
	 */
	private DjUser getApprover(Long deptId) {
		if (deptId == null) {
			return null;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(deptId);

		if (dept != null) {
			DjUser user = userMapper.selectByPrimaryKey(dept.getPrincipalId());
			return user;
		}

		return null;
	}

	/**
	 * 获取当前片区的负责人
	 * 
	 * @param deptId
	 * @return
	 */
	private DjUser getHeaderApprover(Long deptId) {
		if (deptId == null) {
			return null;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(deptId);
		if (dept == null) {
			return null;
		}

		DjSection section = sectionMapper.selectByPrimaryKey(dept.getDjSectionId());
		if (section != null) {
			DjUser user = userMapper.selectByPrimaryKey(section.getPrincipalId());
			return user;
		}

		return null;
	}

	@Transactional
	@Override
	public ResultVo<String> doAudit(AuditVo auditVo, UserVo user) {
		ResultVo<String> resultVo = new ResultVo<>(ResultCode.ParameterError.getCode());
		if (auditVo == null || auditVo.getId() == null || auditVo.getResult() == null || user == null) {
			logger.error("id is null Or result is null Or user is null");
			resultVo.setMsg("参数不完整");
			return resultVo;
		}
		DjApply apply = applyMapper.selectByPrimaryKey(auditVo.getId());
		if (apply == null) {
			logger.error("申请记录查询失败" + auditVo.getId());
			resultVo.setCode(ResultCode.NotFound.getCode());
			resultVo.setMsg("审核失败");
			return resultVo;
		}
		if (apply.getStatus() == AuditStatusEnum.PASS.getValue()) {
			resultVo.setCode(ResultCode.BadRequest.getCode());
			resultVo.setMsg("请勿重复审核！");
			return resultVo;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
		// 自主学习的申请必须本人的部门领导才可以审核
		if (apply.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_B.getActiveSubId())
				|| apply.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_D.getActiveSubId())
				|| apply.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_H.getActiveSubId())) {
			if (!user.getUserId().equals(dept.getPrincipalId())) {
				resultVo.setCode(ResultCode.Forbidden.getCode());
				resultVo.setMsg("当前用户没有权限审核此数据");
				return resultVo;
			}
		}
		// 先锋作用、驿站生活违规的只有片区负责人才有资格审核
		DjSection section = sectionMapper.selectByPrimaryKey(dept.getDjSectionId());
		if (apply.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_M.getActiveSubId())
				|| apply.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_N.getActiveSubId())
				|| apply.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_O.getActiveSubId())
				|| apply.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_F.getActiveSubId())) {
			if (!user.getUserId().equals(section.getPrincipalId())) {
				resultVo.setCode(ResultCode.Forbidden.getCode());
				resultVo.setMsg("当前用户没有权限审核此数据");
				return resultVo;
			}
		}

		DjType type = typeMapper.selectByPrimaryKey(apply.getDjTypeId());
		if (type == null) {
			logger.error("类型记录查询失败" + apply.getDjTypeId());
			resultVo.setCode(ResultCode.NotFound.getCode());
			resultVo.setMsg("审核失败");
			return resultVo;
		}
		DjSubType subType = subTypeMapper.selectByPrimaryKey(apply.getDjSubTypeId());
		if (subType == null) {
			logger.error("二级分类记录查询失败" + apply.getDjSubTypeId());
			resultVo.setCode(ResultCode.NotFound.getCode());
			resultVo.setMsg("审核失败");
			return resultVo;
		}
		Integer result = auditVo.getResult();
		switch (apply.getTableName()) {
		case DlbConstant.TABLE_NAME_STUDY:
			DjStudy study = studyMapper.selectByPrimaryKey(apply.getRecordId());
			if (study != null) {
				study.setStatus(result);
				studyMapper.updateByPrimaryKeySelective(study);
			}
			break;
		case DlbConstant.TABLE_NAME_ACTIVE:
			DjActive active = activeMapper.selectByPrimaryKey(apply.getRecordId());
			if (active != null) {
				active.setStatus(result);
				activeMapper.updateByPrimaryKeySelective(active);
			}
			break;
		case DlbConstant.TABLE_NAME_THOUGHTS:// 思想汇报
			DjThoughts thoughts = thoughtsMapper.selectByPrimaryKey(apply.getRecordId());
			if (thoughts != null) {
				thoughts.setStatus(result);
				thoughtsMapper.updateByPrimaryKeySelective(thoughts);
			}
			break;
		case DlbConstant.TABLE_NAME_VANGUARD:// 先锋作用
			DjVanguard vanguard = vanguardMapper.selectByPrimaryKey(apply.getRecordId());
			if (vanguard != null) {
				vanguard.setStatus(result);
				vanguardMapper.updateByPrimaryKeySelective(vanguard);
			}
			break;
		case DlbConstant.TABLE_NAME_DISCIPLINE:// 遵章守纪
			DjDiscipline discipline = disciplineMapper.selectByPrimaryKey(apply.getRecordId());
			if (discipline != null) {
				discipline.setStatus(result);
				disciplineMapper.updateByPrimaryKeySelective(discipline);
			}
			break;
		default:
			break;
		}

		// 更新申请记录表
		apply.setStatus(auditVo.getResult());
		apply.setRemark(auditVo.getContent());
		apply.setApproveTime(new Date());
		apply.setApproverId(user.getUserId());
		apply.setApproverName(user.getUserName());
		applyMapper.updateByPrimaryKeySelective(apply);
		// 审批通过才走加分的逻辑
		if (apply.getStatus() == AuditStatusEnum.PASS.getValue()) {
			Float score = apply.getScore();
			if (score == null || score == 0) {
				score = subType.getScore();
			}
			// 处理分数，插入到积分明细表中
			handScore(apply.getDjSubTypeId(), apply.getUserId(), apply.getApplyId(), apply.getApplyName(),
					apply.getApproverId(), apply.getApproverName(), score, apply.getRecordId(), apply.getRemark(),
					apply.getApplyYear());
		}
		// 插入审批记录表
		DjApprove approve = new DjApprove();
		approve.setApproveResult(result);
		approve.setApproveDesc(auditVo.getContent());
		approve.setApproverId(user.getUserId());
		approve.setApproveTime(new Date());
		approve.setDjApplyId(auditVo.getId());
		approve.setStatus(1);
		approveMapper.insertSelective(approve);

		resultVo.setCode(ResultCode.OK.getCode());
		resultVo.setMsg("审核成功");
		return resultVo;
	}

	/**
	 * 处理积分的问题
	 * 
	 * @param subTypeId
	 * @param userId
	 * @param applyerId
	 * @param approverId
	 * @param applySocre
	 * @param recordId
	 * @param recordDesc
	 */
	public void handScore(Long subTypeId, Long userId, Long applyerId, String applyerName, Long approverId,
			String approverName, Float applySocre, Long recordId, String recordDesc, Integer year) {
		DjSubType subType = subTypeMapper.selectByPrimaryKey(subTypeId);
		if (subType == null) {
			return;
		}
		DjType type = typeMapper.selectByPrimaryKey(subType.getDjTypeId());
		if (type == null) {
			return;
		}
		// 写入积分记录表
		// 根据类型判断最大分数
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		Float subTypeMaxScore = subType.getMaxScore();
		if (subTypeMaxScore == null) {
			subTypeMaxScore = 0F;
		}
		Float typeMaxScore = type.getMaxScore();
		if (typeMaxScore == null) {
			typeMaxScore = type.getScore();
			if (typeMaxScore == null)
				typeMaxScore = 0F;
		}
		Float userSubTypeScore = scoreMapper.getSumScoreByUserIdAndType(userId, year, null, subTypeId);
		if (userSubTypeScore == null) {
			userSubTypeScore = 0F;
		}
		Float userTypeScore = scoreMapper.getSumScoreByUserIdAndType(userId, year, subType.getDjTypeId(), null);
		if (userTypeScore == null) {
			userTypeScore = 0F;
		}
		// 积分没有积满，则往积分表中插入记录
		if (userSubTypeScore < subTypeMaxScore && userTypeScore < typeMaxScore) {
			DjScore record = new DjScore();
			record.setAddStatus(DlbConstant.BASEDATA_STATUS_VALID);
			record.setAddTime(new Date());
			record.setAddYear(year);
			record.setUserId(userId);
			record.setApplyUserId(applyerId);
			record.setApproverId(approverId);
			record.setCreateTime(new Date());
			record.setDjSubTypeId(subTypeId);
			record.setDjTypeId(type.getId());
			record.setRecordId(recordId);
			record.setRecrodDesc(recordDesc);
			record.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
			record.setApplyUserName(applyerName);
			record.setApproverName(approverName);
			record.setScoreDesc(subType.getName());
			Float score = applySocre;
			// 公益服务,处理9分的问题
			if (type.getId() == ActiveTypeEnum.ACTIVE_F.getActiveId()) {
				if ((userTypeScore + applySocre) > typeMaxScore) {
					score = typeMaxScore - userTypeScore;
				}
			}

			record.setScore(score);
			scoreMapper.insertSelective(record);
		}
	}

	/**
	 * 驿站生活违纪违规扣分申请
	 *
	 * @param param
	 * @param user
	 * @return
	 */
	@Transactional
	@Override
	public ResultVo<Long> doApplyDiscipline(DisciplineVo param, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (param == null || user == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		// 校验党员信息
		DjPartymember partymember = partymemberMapper.selectByPrimaryKey(param.getUserId());
		if (partymember == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("当前选择的党员不存在!");
			return result;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(partymember.getDeptId());
		if (dept == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("当前选择的党员部门信息异常!");
			return result;
		}
		// 校验操作权限
		if (!user.getUserId().equals(dept.getPrincipalId())) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("您无权做本次操作!");
			return result;
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		Integer haveRecord = disciplineMapper.getOneByUserIdOrderByCreateTimeDesc(partymember.getId(), yearTimeStart,
				yearTimeEnd);
		if (haveRecord != null) {
			result.setCode(ResultCode.BadRequest.getCode());
			result.setMsg("请勿重复处理");
			return result;
		}
		DjDiscipline record = new DjDiscipline();
		record.setId(DigitUtil.generatorLongId());
		record.setCreateTime(new Date());
		record.setDjDeptId(partymember.getDeptId());
		record.setDjUserId(param.getUserId());
		record.setReason(param.getReason());
		record.setReasonDesc(param.getReasonDesc());
		record.setScore(-20F);
		record.setStatus(AuditStatusEnum.PASS.getValue());
		record.setApproverId(user.getUserId());
		record.setApproverName(user.getUserName());
		disciplineMapper.insertSelective(record);
		boolean exists = scoreMapper.existScore(partymember.getId(), year,
				ActiveSubTypeEnum.ACTIVE_SUB_F.getActiveSubId());
		if (!exists) {
			DjScore score = new DjScore();
			score.setAddStatus(DlbConstant.BASEDATA_STATUS_VALID);
			score.setAddTime(new Date());
			score.setAddYear(year);
			score.setUserId(partymember.getId());
			score.setApplyUserId(user.getUserId());
			score.setApplyUserName(user.getUserName());
			score.setApproverId(user.getUserId());
			score.setApproverName(user.getUserName());
			score.setCreateTime(new Date());
			score.setDjSubTypeId(ActiveSubTypeEnum.ACTIVE_SUB_F.getActiveSubId());
			score.setDjTypeId(ActiveTypeEnum.ACTIVE_E.getActiveId());
			score.setRecordId(record.getId());
			score.setRecrodDesc(record.getReason());
			score.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
			score.setScoreDesc(ActiveSubTypeEnum.ACTIVE_SUB_F.getDesc());
			score.setScore(-20F);
			scoreMapper.insertSelective(score);
		} else {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请勿重复驿站生活违纪扣分");
			return result;
		}
		// 保存图片
		savePics(record.getId(), DlbConstant.TABLE_NAME_DISCIPLINE, param.getPics());
		result.setCode(ResultCode.OK.getCode());
		result.setData(record.getId());
		return result;
	}

	@Transactional
	@Override
	public ResultVo<Long> doApplyVanguard(VanguardParamVo param, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (param == null || user == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		Long partyMemberId = param.getUserId();
		if (partyMemberId == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		DjPartymember partymember = partymemberMapper.selectByPrimaryKey(partyMemberId);
		if (partymember == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("当前选择的党员不存在!");
			return result;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(partymember.getDeptId());
		if (dept == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("当前选择的党员部门信息异常!");
			return result;
		}
		// 校验操作权限
		if (!user.getUserId().equals(dept.getPrincipalId())) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("您无权做本次操作!");
			return result;
		}
		VanguardVo honor = param.getHonor();// 获得荣誉
		if (honor != null) {
			if (!honor.getVanguardType().equals(ActiveSubTypeEnum.ACTIVE_SUB_M.getActiveSubId())
					|| "".equals(honor.getContent())) {
				result.setCode(ResultCode.ParameterError.getCode());
				result.setMsg("获得荣誉参数异常!");
				return result;
			}
			this.addVanguardAndApply(honor, user, partymember);
		}
		VanguardVo recognition = param.getRecognition();// 先锋表彰
		if (recognition != null) {
			if (!recognition.getVanguardType().equals(ActiveSubTypeEnum.ACTIVE_SUB_N.getActiveSubId())
					|| "".equals(recognition.getContent())) {
				result.setCode(ResultCode.ParameterError.getCode());
				result.setMsg("先锋表彰参数异常!");
				return result;
			}
			this.addVanguardAndApply(recognition, user, partymember);
		}
		VanguardVo vgd = param.getVgd();// 先锋模范
		if (vgd != null) {
			if (!vgd.getVanguardType().equals(ActiveSubTypeEnum.ACTIVE_SUB_O.getActiveSubId())
					|| "".equals(vgd.getContent())) {
				result.setCode(ResultCode.ParameterError.getCode());
				result.setMsg("先锋模范参数异常!");
				return result;
			}
			this.addVanguardAndApply(vgd, user, partymember);
		}

		result.setCode(ResultCode.OK.getCode());
		return result;
	}

	private void addVanguardAndApply(VanguardVo vo, UserVo user, DjPartymember partymember) {
		DjVanguard vanguard = new DjVanguard();
		vanguard.setId(DigitUtil.generatorLongId());
		vanguard.setCreateTime(new Date());
		vanguard.setDjDeptId(partymember.getDeptId());
		vanguard.setDjUserId(partymember.getId());
		vanguard.setContent(vo.getContent());
		vanguard.setCreateTime(new Date());
		if (vo.getVanguardType().equals(ActiveSubTypeEnum.ACTIVE_SUB_M.getActiveSubId())
				|| vo.getVanguardType().equals(ActiveSubTypeEnum.ACTIVE_SUB_N.getActiveSubId())) {
			vanguard.setScore(5F);
		} else {
			vanguard.setScore(vo.getScore() == null ? 0 : vo.getScore());
		}
		vanguard.setType(vo.getVanguardType());
		vanguard.setStatus(DlbConstant.BASEDATA_STATUS_INVALID);
		vanguardMapper.insertSelective(vanguard);
		// 保存图片
		savePics(vanguard.getId(), DlbConstant.TABLE_NAME_VANGUARD, vo.getPics());
		ApplyVo applyVo = new ApplyVo();
		applyVo.setContent(vanguard.getContent());
		applyVo.setDjSubTypeId(vanguard.getType());
		applyVo.setDjTypeId(ActiveTypeEnum.ACTIVE_D.getActiveId());
		applyVo.setRoleId(RoleEnum.HEADER_OF_DISTRICT.getId());
		applyVo.setRecordId(vanguard.getId());
		applyVo.setDjDeptId(user.getDeptId());
		applyVo.setUserId(partymember.getId());
		applyVo.setUserName(partymember.getName());
		applyVo.setApplyYear(Calendar.getInstance().get(Calendar.YEAR));
		applyVo.setRemark("获得荣誉申请");
		applyVo.setScore(vanguard.getScore());
		applyVo.setTableName(DlbConstant.TABLE_NAME_VANGUARD);
		String rs = doApply(applyVo, user);
		if (!CoreConst.SUCCESS.equals(rs)) {
			ResultVo<Long> result = new ResultVo<>();
			logger.info("提交申请失败");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("提交申请失败");
		}
	}

	@Transactional
	@Override
	public ResultVo<Long> doApplyThoughts(ThoughtsVo param, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (param == null || user == null || param.getReportTime() == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		DjPartymember partymember = partymemberMapper.selectByPrimaryKey(param.getUserId());
		if (partymember == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("当前选择的党员不存在!");
			return result;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(partymember.getDeptId());
		if (dept == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("当前选择的党员部门信息异常!");
			return result;
		}
		// 校验操作权限
		if (!user.getUserId().equals(dept.getPrincipalId())) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("您无权做本次操作!");
			return result;
		}
		if (param.getReportType() == null
				|| !param.getReportType().equals(ActiveSubTypeEnum.ACTIVE_SUB_K.getActiveSubId())
						&& !param.getReportType().equals(ActiveSubTypeEnum.ACTIVE_SUB_L.getActiveSubId())) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("选择的汇报类型错误!");
			return result;
		}
		// 获取二级分类信息
		DjSubType subType = subTypeMapper.selectByPrimaryKey(param.getReportType());
		if (subType == null) {
			logger.error("获取二级分类失败");
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("提交失败");
			return result;
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Date reportTime = DatetimeUtil.getDateByStr(param.getReportTime());
		DateFormat df = DateFormat.getDateInstance(DateFormat.YEAR_FIELD);
		int parmYear = Integer.parseInt(df.format(reportTime).substring(0, 4));
		if (parmYear != year) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("请选择正确的汇报时间");
			return result;
		}
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		// 不能重复思想汇报评分
		int exists = thoughtsMapper.checkExists(partymember.getId(), param.getReportType(), yearTimeStart, yearTimeEnd);
		if (exists > 0) {
			result.setCode(ResultCode.BadRequest.getCode());
			result.setMsg("该党员已评分，请勿重复评分!");
			return result;
		}
		DjThoughts record = new DjThoughts();
		record.setId(DigitUtil.generatorLongId());
		record.setCreateTime(new Date());
		record.setDjDeptId(partymember.getDeptId());
		record.setDjUserId(param.getUserId());
		record.setThoughtsType(param.getReportType());
		record.setThoughtsInfo(param.getContent());
		record.setThoughtsTime(DatetimeUtil.getDateByStr(param.getReportTime()));
		record.setScore(subType.getScore());
		record.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		record.setApproverId(user.getUserId());
		record.setApproverName(user.getUserName());

		thoughtsMapper.insertSelective(record);

		// 保存图片
		savePics(record.getId(), DlbConstant.TABLE_NAME_THOUGHTS, param.getPics());
		DjScore score = new DjScore();
		score.setId(DigitUtil.generatorLongId());
		score.setDjTypeId(ActiveTypeEnum.ACTIVE_C.getActiveId());
		score.setScore(subType.getScore());
		score.setDjSubTypeId(param.getReportType());
		score.setUserId(partymember.getId());
		score.setAddTime(new Date());
		score.setApplyUserId(user.getUserId());
		score.setApplyUserName(user.getUserName());
		score.setApproverId(user.getUserId());
		score.setApproverName(user.getUserName());
		score.setAddStatus(1);
		score.setAddYear(year);
		if (param.getReportType().equals(ActiveSubTypeEnum.ACTIVE_SUB_K.getActiveSubId())) {
			score.setScoreDesc(ActiveSubTypeEnum.ACTIVE_SUB_K.getDesc());
		} else {
			score.setScoreDesc(ActiveSubTypeEnum.ACTIVE_SUB_L.getDesc());
		}

		score.setRecordId(record.getId());
		score.setRecrodDesc(record.getThoughtsInfo());
		score.setStatus(1);
		score.setCreateTime(new Date());
		scoreMapper.insert(score);
		result.setCode(ResultCode.OK.getCode());
		result.setData(record.getId());
		return result;
	}

	/**
	 * 保存自主申报、先锋作用、遵章守纪、思想汇报图片
	 * 
	 * @param recordId
	 * @param tableName
	 * @param pics
	 * @return
	 */
	private String savePics(Long recordId, String tableName, String[] pics) {
		if (recordId == null || pics == null || pics.length == 0) {
			return CoreConst.MSG_FAIL_PARAM;
		}
		for (String str : pics) {
			DjPicRecord record = new DjPicRecord();
			record.setCreateTime(new Date());
			record.setDjPicId(DigitUtil.parseToLong(str));
			record.setRecordId(recordId);
			record.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
			record.setTableName(tableName);
			picRecordMapper.insertSelective(record);
		}

		return CoreConst.SUCCESS;
	}

	/**
	 * 支书查询待办列表
	 *
	 * @param deptId
	 *            支部ID
	 * @param subTypeId
	 *            活动类型Id
	 * @return
	 */
	@Override
	public Paged<PendingPtMemberVo> getPendingList(Long deptId, Long subTypeId, int pageNum, int pageSize) {
		Page<PendingPtMemberVo> page = PageHelper.startPage(pageNum, pageSize);
		applyMapper.getPendingList(deptId, subTypeId);
		return PageUtils.toPaged(page);
	}

	/**
	 * 查询积分审核列表
	 *
	 * @param user
	 *            user
	 * @param status
	 *            审核状态
	 * @return
	 */
	@Override
	public Paged<ScoreApplyVo> getScoreAuditList(UserVo user, Integer status, int pageNum, int pageSize, Long deptId) {
		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Page<ScoreApplyVo> page = PageHelper.startPage(pageNum, pageSize);
		applyMapper.getScoreAuditList(status, year, deptId);
		return PageUtils.toPaged(page);
	}

	/**
	 * 查询积分审核详情(先锋作用的三个)
	 *
	 * @param partyMemberId
	 *            党员Id
	 * @return
	 */
	@Override
	public PioneeringApplyDetailVo getPioneeringApplyDetail(Long partyMemberId) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		IdNameTotalScoreVo idNameTotalScoreVo = partymemberMapper.getTotalScoreById(partyMemberId, year);
		if (idNameTotalScoreVo == null) {
			return new PioneeringApplyDetailVo();
		}
		PioneeringApplyDetailVo pioneeringApplyDetailVo = new PioneeringApplyDetailVo();
		pioneeringApplyDetailVo.setPartyMemberName(idNameTotalScoreVo.getName());
		pioneeringApplyDetailVo.setTotalScore(convertNullToFloatZero(idNameTotalScoreVo.getTotalScore()));
		List<ScoreAuditDetailVo> voList = applyMapper.getScoreAuditDetailByPtMemberId(year, partyMemberId);
		for (ScoreAuditDetailVo vo : voList) {
			if (vo.getRecordId() != null) {
				List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(DlbConstant.TABLE_NAME_VANGUARD,
						vo.getRecordId());
				vo.setPicIds(picIds);
			}
		}
		pioneeringApplyDetailVo.setDetail(voList);

		return pioneeringApplyDetailVo;
	}

	/**
	 * 代办列表
	 *
	 * @param user
	 * @return
	 */
	@Override
	public List<ToDoVo> getTodoList(UserVo user) {
		if (user == null) {
			return new ArrayList<>();
		}
		if (!user.getRoleId().equals(RoleEnum.BRANCH_PARTY.getId())
				&& !user.getRoleId().equals(RoleEnum.HEADER_OF_DISTRICT.getId())) {
			return new ArrayList<>();
		}
		// 活动
		// 1.即将开始的活动
		List<ToDoVo> toDoVos = activeMapper.getUnStartedActive(user.getUserId());
		// 2.已开始/已结束未上传图片的活动
		List<ToDoVo> startedNoPic = activeMapper.getNoPicActive(user.getUserId());
		toDoVos.addAll(startedNoPic);
		// 代办
		// 当前登录人为党支书时
		DjDept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
		if (dept == null) {
			return toDoVos;
		}
		DjSection section = sectionMapper.selectByPrimaryKey(dept.getDjSectionId());
		if (section == null) {
			return toDoVos;
		}
		int tag = 2;
		if (user.getRoleId().equals(RoleEnum.BRANCH_PARTY.getId())) {
			Long zzxxId = ActiveSubTypeEnum.ACTIVE_SUB_B.getActiveSubId();
			int haveZzxx = applyMapper.countBySubTypeIdAndStatusAndDeptId(zzxxId, AuditStatusEnum.WAITING.getValue(),
					dept.getId());
			if (haveZzxx > 0) {
				ToDoVo vo = new ToDoVo();
				vo.setSubTypeId(zzxxId);
				vo.setTag(tag);
				vo.setName("政治学习积分加分确认");
				toDoVos.add(vo);
			}
			Long zzshId = ActiveSubTypeEnum.ACTIVE_SUB_D.getActiveSubId();
			int haveZzsh = applyMapper.countBySubTypeIdAndStatusAndDeptId(zzshId, AuditStatusEnum.WAITING.getValue(),
					dept.getId());
			if (haveZzsh > 0) {
				ToDoVo vo = new ToDoVo();
				vo.setSubTypeId(zzshId);
				vo.setTag(tag);
				vo.setName("组织生活积分加分确认");
				toDoVos.add(vo);
			}
			Long gyfwId = ActiveSubTypeEnum.ACTIVE_SUB_H.getActiveSubId();
			int haveGyfw = applyMapper.countBySubTypeIdAndStatusAndDeptId(gyfwId, AuditStatusEnum.WAITING.getValue(),
					dept.getId());
			if (haveGyfw > 0) {
				ToDoVo vo = new ToDoVo();
				vo.setSubTypeId(gyfwId);
				vo.setTag(tag);
				vo.setName("公益服务积分加分确认");
				toDoVos.add(vo);
			}

		} else {// 当前登录人是片区负责人
			// 查询片区负责人的下属支部Id
			List<Long> deptIdList = deptMapper.getDeptIdsBySectionId(section.getId());
			if (deptIdList.size() == 0) {
				return toDoVos;
			}
			Long[] array = deptIdList.toArray(new Long[0]);
			Long disId = ActiveSubTypeEnum.ACTIVE_SUB_F.getActiveSubId();
			int haveDis = applyMapper.countBySubTypeIdAndStatusAndDeptId(disId, AuditStatusEnum.WAITING.getValue(),
					array);
			if (haveDis > 0) {
				ToDoVo vo = new ToDoVo();
				vo.setSubTypeId(disId);
				vo.setTag(tag);
				vo.setName("驿站生活违纪违规积分扣分确认");
				toDoVos.add(vo);
			}
			Long honorId = ActiveSubTypeEnum.ACTIVE_SUB_M.getActiveSubId();
			int haveHonor = applyMapper.countBySubTypeIdAndStatusAndDeptId(honorId, AuditStatusEnum.WAITING.getValue(),
					array);
			if (haveHonor > 0) {
				ToDoVo vo = new ToDoVo();
				vo.setSubTypeId(honorId);
				vo.setTag(tag);
				vo.setName("获得荣誉积分加分确认");
				toDoVos.add(vo);
			}
			Long recId = ActiveSubTypeEnum.ACTIVE_SUB_N.getActiveSubId();
			int haveRec = applyMapper.countBySubTypeIdAndStatusAndDeptId(recId, AuditStatusEnum.WAITING.getValue(),
					array);
			if (haveRec > 0) {
				ToDoVo vo = new ToDoVo();
				vo.setSubTypeId(recId);
				vo.setTag(tag);
				vo.setName("先锋表彰积分加分确认");
				toDoVos.add(vo);
			}
			Long vgdId = ActiveSubTypeEnum.ACTIVE_SUB_O.getActiveSubId();
			int haveVgd = applyMapper.countBySubTypeIdAndStatusAndDeptId(vgdId, AuditStatusEnum.WAITING.getValue(),
					array);
			if (haveVgd > 0) {
				ToDoVo vo = new ToDoVo();
				vo.setSubTypeId(vgdId);
				vo.setTag(tag);
				vo.setName("先锋模范积分加分确认");
				toDoVos.add(vo);
			}

		}
		return toDoVos;
	}

	/**
	 * 获取自主活动流程中的积分
	 *
	 * @param user
	 * @return
	 */
	@Override
	public Float sumScoreInProcess(UserVo user) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		// 公益服务
		Float gyfwNow = scoreMapper.getSumScoreByUserIdAndType(user.getUserId(), year,
				ActiveTypeEnum.ACTIVE_F.getActiveId(), null);
		gyfwNow = gyfwNow == null ? 0L : gyfwNow;
		Float gyfwInprocess = 0F;
		if (gyfwNow < 10F) {
			gyfwInprocess = applyMapper.countScoreInProcess(user.getUserId(), year,
					ActiveTypeEnum.ACTIVE_F.getActiveId(), null);
			gyfwInprocess = gyfwInprocess == null ? 0L : gyfwInprocess;
			if ((10F - gyfwNow) < gyfwInprocess) {
				gyfwInprocess = 10F - gyfwNow;
			}
		}
		// 组织生活
		Float zzshNow = scoreMapper.getSumScoreByUserIdAndType(user.getUserId(), year,
				ActiveTypeEnum.ACTIVE_B.getActiveId(), ActiveSubTypeEnum.ACTIVE_SUB_D.getActiveSubId());
		zzshNow = zzshNow == null ? 0L : zzshNow;
		Float zzshInProcess = 0F;
		if (zzshNow < 2.5F) {
			zzshInProcess = applyMapper.countScoreInProcess(user.getUserId(), year,
					ActiveTypeEnum.ACTIVE_B.getActiveId(), ActiveSubTypeEnum.ACTIVE_SUB_D.getActiveSubId());
			zzshInProcess = zzshInProcess == null ? 0L : zzshInProcess;
			if ((2.5F - zzshNow) < zzshInProcess) {
				zzshInProcess = 10F - zzshNow;
			}
		}
		// 政治学习
		Float zzxxNow = scoreMapper.getSumScoreByUserIdAndType(user.getUserId(), year,
				ActiveTypeEnum.ACTIVE_A.getActiveId(), ActiveSubTypeEnum.ACTIVE_SUB_B.getActiveSubId());
		zzxxNow = zzxxNow == null ? 0L : zzxxNow;
		Float zzxxInProcess = 0F;
		if (zzxxNow < 5F) {
			zzxxInProcess = applyMapper.countScoreInProcess(user.getUserId(), year,
					ActiveTypeEnum.ACTIVE_A.getActiveId(), ActiveSubTypeEnum.ACTIVE_SUB_B.getActiveSubId());
			zzxxInProcess = zzxxInProcess == null ? 0L : zzxxInProcess;
			if ((5F - zzxxNow) < zzxxInProcess) {
				zzxxInProcess = 5F - zzxxNow;
			}
		}

		Float sumInProcess = gyfwInprocess + zzshInProcess + zzxxInProcess;
		return sumInProcess;
	}

	/**
	 * 新增基础分接口.
	 *
	 * @param year
	 * @return
	 */
	@Transactional
	@Override
	public ResultVo addBaseScore(int year) {
		ResultVo resultVo = new ResultVo();
		List<DjPartymember> partymembers = partymemberMapper.selectAll();

		for (DjPartymember partymember : partymembers) {
			boolean exists = scoreMapper.existScore(partymember.getId(), year,
					ActiveSubTypeEnum.ACTIVE_SUB_P.getActiveSubId());
			if (exists) {
				continue;
			}
			DjScore score = new DjScore();
			score.setId(DigitUtil.generatorLongId());
			score.setDjTypeId(ActiveTypeEnum.ACTIVE_E.getActiveId());
			score.setDjSubTypeId(ActiveSubTypeEnum.ACTIVE_SUB_P.getActiveSubId());
			score.setScore(20F);
			score.setUserId(partymember.getId());
			score.setAddTime(new Date());
			score.setApplyUserId(null);
			score.setApplyUserName("系统自动");
			score.setApproverId(null);
			score.setApproverName("系统自动");
			score.setAddYear(year);
			score.setAddStatus(1);
			score.setScoreDesc("遵纪守法基础积分");
			score.setCreateTime(new Date());
			scoreMapper.insert(score);
		}

		resultVo.setCode(ResultCode.OK.getCode());
		resultVo.setMsg("加分成功!");
		return resultVo;
	}

	private Float convertNullToFloatZero(Float score) {
		score = score == null ? 0F : score;
		return score;

	}

}
