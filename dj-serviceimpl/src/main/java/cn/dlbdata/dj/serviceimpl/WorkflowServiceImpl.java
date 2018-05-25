package cn.dlbdata.dj.serviceimpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
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
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.mapper.DjThoughtsMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.mapper.DjVanguardMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.pojo.DjApprove;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjDiscipline;
import cn.dlbdata.dj.db.pojo.DjPicRecord;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.pojo.DjThoughts;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.db.pojo.DjVanguard;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ApplyVo;
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

	@Override
	public String apply(ApplyVo vo, UserVo user) {

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
	public String audit(Long id, Integer result, String content, UserVo user) {
		if (id == null || result == null || user == null) {
			return CoreConst.MSG_FAIL_PARAM;
		}

		DjApply apply = applyMapper.selectByPrimaryKey(id);
		if (apply == null) {
			return CoreConst.FAIL;
		}

		switch (apply.getTableName()) {
		case DlbConstant.TABLE_NAME_STUDY:
			DjStudy study = studyMapper.selectByPrimaryKey(apply.getRecordId());
			if (study != null) {
				study.setStatus(result);
			}
			studyMapper.updateByPrimaryKeySelective(study);
			break;
		case DlbConstant.TABLE_NAME_ACTIVE:
			DjActive active = activeMapper.selectByPrimaryKey(apply.getRecordId());
			if (active != null) {
				active.setStatus(result);
			}
			activeMapper.updateByPrimaryKeySelective(active);
			break;
		case DlbConstant.TABLE_NAME_THOUGHTS:
			DjThoughts thoughts = thoughtsMapper.selectByPrimaryKey(apply.getRecordId());
			if (thoughts != null) {
				thoughts.setStatus(result);
			}
			thoughtsMapper.updateByPrimaryKeySelective(thoughts);
			break;
		case DlbConstant.TABLE_NAME_VANGUARD:
			DjVanguard vanguard = vanguardMapper.selectByPrimaryKey(apply.getRecordId());
			if (vanguard != null) {
				vanguard.setStatus(result);
			}
			vanguardMapper.updateByPrimaryKeySelective(vanguard);
			break;
		case DlbConstant.TABLE_NAME_DISCIPLINE:
			DjDiscipline discipline = disciplineMapper.selectByPrimaryKey(apply.getRecordId());
			if (discipline != null) {
				discipline.setStatus(result);
			}
			disciplineMapper.updateByPrimaryKeySelective(discipline);
			break;
		default:
			break;
		}

		// apply.setApproverId(user.getUserId());
		// apply.setApproverName(user.getUserName());

		// 插入审批记录表
		DjApprove approve = new DjApprove();
		approve.setApproveResult(result);
		approve.setApproveDesc(content);
		approve.setApproverId(user.getUserId());
		approve.setApproveTime(new Date());
		approve.setDjApplyId(id);
		approve.setStatus(1);
		approveMapper.insertSelective(approve);

		return CoreConst.SUCCESS;
	}

	@Override
	public ResultVo<Long> applyDiscipline(DisciplineVo param, UserVo user) {
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
		String rs = apply(vo, user);
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
	public ResultVo<Long> applyVanguard(VanguardVo[] params, UserVo user) {
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
			vo.setDjSubTypeId(ActiveSubTypeEnum.ACTIVE_SUB_P.getActiveSubId());
			vo.setDjTypeId(ActiveTypeEnum.ACTIVE_E.getActiveId());
			vo.setRecordId(record.getId());
			vo.setRemark("获得荣誉申请");
			
			vo.setTableName(DlbConstant.TABLE_NAME_DISCIPLINE);
			String rs = apply(vo, user);
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
	public ResultVo<Long> applyThoughts(ThoughtsVo param, UserVo user) {
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
}
