package cn.dlbdata.dj.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.db.vo.vo.apply.ScoreApplyVo;
import cn.dlbdata.dj.thirdparty.mp.sdk.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.PageVo;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjApplyMapper;
import cn.dlbdata.dj.db.mapper.DjApproveMapper;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjDisciplineMapper;
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
import cn.dlbdata.dj.db.pojo.DjPicRecord;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.db.pojo.DjThoughts;
import cn.dlbdata.dj.db.pojo.DjType;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.db.pojo.DjVanguard;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.AuditVo;
import cn.dlbdata.dj.vo.DisciplineVo;
import cn.dlbdata.dj.vo.ThoughtsVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.VanguardVo;

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
	// @Autowired
	// private DjQueryMapper queryMapper;
	@Autowired
	private DjTypeMapper typeMapper;
	@Autowired
	private DjSubTypeMapper subTypeMapper;
	@Autowired
	private DjScoreMapper scoreMapper;

	@Override
	@Transactional
	public String doApply(ApplyVo vo, UserVo user) {

		if (vo == null || user == null) {
			logger.error("参数错误");
			return CoreConst.MSG_FAIL_PARAM;
		}

		DjApply record = new DjApply();
		record.setApplyDesc(vo.getRemark());
		record.setApplyInfo(vo.getContent());
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

	@Override
	@Transactional
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
		applyMapper.updateByPrimaryKeySelective(apply);

		// 写入积分记录表
		// 根据类型判断最大分数
		int year = Calendar.getInstance().get(Calendar.YEAR);
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
		Float userSubTypeScore = scoreMapper.getSumScoreByUserIdAndType(apply.getUserId(), year, null,
				apply.getDjSubTypeId());
		if (userSubTypeScore == null) {
			userSubTypeScore = 0F;
		}
		Float userTypeScore = scoreMapper.getSumScoreByUserIdAndType(apply.getUserId(), year, apply.getDjTypeId(),
				null);
		if (userTypeScore == null) {
			userTypeScore = 0F;
		}
		// 积分没有积满，则往积分表中插入记录
		if (userSubTypeScore < subTypeMaxScore && userTypeScore < typeMaxScore) {
			DjScore record = new DjScore();
			record.setAddStatus(1);
			record.setAddTime(new Date());
			record.setAddYear(year);
			record.setApplyUserId(apply.getApplyId());
			record.setApproverId(user.getUserId());
			record.setCreateTime(new Date());
			record.setDjSubTypeId(apply.getDjSubTypeId());
			record.setDjTypeId(apply.getDjTypeId());
			record.setRecordId(apply.getRecordId());
			// record.setRecrodDesc(apply.get);
			// record.setScoreDesc(scoreDesc);
			record.setStatus(1);
			record.setUserId(apply.getUserId());
			// record.setUserName(apply.getUserName());
			Float score = apply.getScore();
			// 公益服务,处理9分的问题
			if (apply.getDjTypeId() == ActiveTypeEnum.ACTIVE_F.getActiveId()) {
				if ((userTypeScore + apply.getScore()) > typeMaxScore) {
					score = typeMaxScore - userTypeScore;
				}
			}

			record.setScore(score);
			scoreMapper.insertSelective(record);
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

	@Override
	@Transactional
	public ResultVo<Long> doApplyDiscipline(DisciplineVo param, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (param == null || user == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		DjDiscipline record = null;
		if (param.getId() != null) {
			record = disciplineMapper.selectByPrimaryKey(param.getId());
		}
		boolean isSave = false;
		if (record == null) {
			isSave = true;
			record = new DjDiscipline();
			record.setId(DigitUtil.generatorLongId());
		}
		record.setCreateTime(new Date());
		record.setDjDeptId(param.getDeptId());
		record.setDjUserId(param.getUserId());
		record.setReason(param.getReason());
		record.setReasonDesc(param.getReasonDesc());
		record.setScore(20F);
		record.setStatus(0);
		if (isSave) {
			disciplineMapper.insertSelective(record);
		} else {
			disciplineMapper.updateByPrimaryKeySelective(record);
		}

		// 保存图片
		savePics(record.getId(), DlbConstant.TABLE_NAME_DISCIPLINE, param.getPics());

		// 判断是否需要审批，需要审批，提交申请，写入到申请表中
		ApplyVo vo = new ApplyVo();
		vo.setContent(record.getReason());
		vo.setDjSubTypeId(ActiveSubTypeEnum.ACTIVE_SUB_P.getActiveSubId());
		vo.setDjTypeId(ActiveTypeEnum.ACTIVE_E.getActiveId());
		vo.setRecordId(record.getId());
		vo.setRemark("遵章守纪申请");
		// vo.setScore(score);
		vo.setTableName(DlbConstant.TABLE_NAME_DISCIPLINE);
		String rs = doApply(vo, user);
		if (!CoreConst.SUCCESS.equals(rs)) {
			logger.info("提交申请失败");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("提交申请失败");
		}

		result.setCode(ResultCode.OK.getCode());
		result.setData(record.getId());
		return result;
	}

	@Override
	@Transactional
	public ResultVo<Long> doApplyVanguard(VanguardVo[] params, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (params == null || user == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		DjVanguard record = null;
		for (VanguardVo param : params) {
			if (param.getId() != null) {
				record = vanguardMapper.selectByPrimaryKey(param.getId());
			}
			boolean isSave = false;
			if (record == null) {
				isSave = true;
				record = new DjVanguard();
				record.setId(DigitUtil.generatorLongId());
			}
			record.setCreateTime(new Date());
			record.setDjDeptId(param.getDeptId());
			record.setDjUserId(param.getUserId());
			record.setContent(param.getContent());
			record.setCreateTime(new Date());
			record.setScore(param.getScore());
			record.setType(param.getVanguardType());
			record.setStatus(DlbConstant.BASEDATA_STATUS_INVALID);
			if (isSave) {
				vanguardMapper.insertSelective(record);
			} else {
				vanguardMapper.updateByPrimaryKeySelective(record);
			}
			// 保存图片
			savePics(record.getId(), DlbConstant.TABLE_NAME_VANGUARD, param.getPics());

			// TODO 提交申请，写入到申请表中
			ApplyVo vo = new ApplyVo();
			vo.setContent(record.getContent());
			vo.setDjSubTypeId(param.getVanguardType());
			vo.setDjTypeId(ActiveTypeEnum.ACTIVE_D.getActiveId());
			vo.setRecordId(record.getId());
			vo.setRemark("获得荣誉申请");

			vo.setTableName(DlbConstant.TABLE_NAME_VANGUARD);
			String rs = doApply(vo, user);
			if (!CoreConst.SUCCESS.equals(rs)) {
				logger.info("提交申请失败");
				result.setCode(ResultCode.Forbidden.getCode());
				result.setMsg("提交申请失败");
			}
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(record.getId());
		return result;
	}

	@Override
	@Transactional
	public ResultVo<Long> doApplyThoughts(ThoughtsVo param, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (param == null || user == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		DjThoughts record = null;
		if (param.getId() != null) {
			record = thoughtsMapper.selectByPrimaryKey(param.getId());
		}
		boolean isSave = false;
		if (record == null) {
			isSave = true;
			record = new DjThoughts();
			record.setId(DigitUtil.generatorLongId());
		}
		record.setCreateTime(new Date());
		record.setDjDeptId(param.getDeptId());
		record.setDjUserId(param.getUserId());
		record.setThoughtsType(param.getReportType());
		record.setThoughtsInfo(param.getContent());
		record.setThoughtsTime(param.getReportTime());
		record.setScore(5F);
		record.setStatus(1);
		if (isSave) {
			thoughtsMapper.insertSelective(record);
		} else {
			thoughtsMapper.updateByPrimaryKeySelective(record);
		}
		// 保存图片
		savePics(record.getId(), DlbConstant.TABLE_NAME_THOUGHTS, param.getPics());

		// 判断是否需要审批，不需要审批，直接加分

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

	@Override
	public PageVo<DjApply> getPendingList(Long userId, Long deptId, Long typeId, Long roleId, Integer pageNum,
			Integer pageSize) {
		if (pageNum == null) {
			pageNum = 1;
		}

		if (pageSize == null) {
			pageSize = 10;
		}
		PageVo<DjApply> result = new PageVo<>();
		Page<DjApply> page = PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("deptId", deptId);
		paramMap.put("typeId", typeId);
		paramMap.put("roleId", roleId);
		List<DjApply> dataList = applyMapper.getPendingList(paramMap);

		if (!page.isEmpty()) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(dataList);
			result.setPageNum(page.getPageNum());
			result.setTotal(page.getTotal());
			result.setPageSize(page.getPageSize());
			result.setPageTotal(page.getPages());
		}
		return result;
	}

	/**
	 * 查询积分审核列表
	 *
	 * @param user   user
	 * @param status 审核状态
	 * @return
	 */
	@Override
	public Paged<ScoreApplyVo> getScoreAuditList(UserVo user, Integer status,int pageNum,int pageSize,Long deptId) {
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		Page<ScoreApplyVo> page = PageHelper.startPage(pageNum, pageSize);
		//TODO 為了便於測試，userId先定為1106
		List<ScoreApplyVo> voList = applyMapper.getScoreAuditList(1106L,status,yearTimeStart,yearTimeEnd,deptId);
		return PageUtils.toPaged(page);
	}

}
