package cn.dlbdata.dj.serviceimpl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.mapper.DjSubTypeMapper;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.StudyVo;
import cn.dlbdata.dj.vo.UserVo;

@Service
public class StudyServiceImpl extends BaseServiceImpl implements IStudyService {
	@Autowired
	private DjStudyMapper studyMapper;
	@Autowired
	private DjSubTypeMapper subTypeMapper;

	@Autowired
	private IWorkflowService workflowService;

	@Override
	@Transactional
	public ResultVo<Long> saveStudy(StudyVo studyVo, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (studyVo == null || studyVo.getDjTypeId() == null || studyVo.getDjSubTypeId() == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}

		DjSubType subType = subTypeMapper.selectByPrimaryKey(studyVo.getDjSubTypeId());
		if (subType == null) {
			logger.error("subType is not found->" + studyVo.getDjSubTypeId());
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}

		DjStudy study = null;
		if (studyVo.getId() != null) {
			study = studyMapper.selectByPrimaryKey(studyVo.getId());
		}
		boolean isSave = false;
		if (study == null) {
			isSave = true;
			study = new DjStudy();
			study.setId(DigitUtil.generatorLongId());
		}
		study.setStartTime(studyVo.getStartTime());
		study.setEndTime(studyVo.getEndTime());
		study.setDjTypeId(studyVo.getDjTypeId());
		study.setDjSubTypeId(studyVo.getDjSubTypeId());
		study.setContent(studyVo.getContent());
		study.setCreateUserId(studyVo.getCreateUserId());
		study.setUserName(studyVo.getUserName());
		if (isSave) {
			studyMapper.insertSelective(study);
		} else {
			studyMapper.updateByPrimaryKeySelective(study);
		}

		Float score = subType.getScore();

		// TODO 提交申请，写入到申请表中
		ApplyVo vo = new ApplyVo();
		vo.setContent(study.getContent());
		vo.setDjSubTypeId(study.getDjSubTypeId());
		vo.setDjTypeId(study.getDjTypeId());
		vo.setRecordId(study.getId());
		vo.setRemark("自主学习申请");
		// vo.setScore(score);
		vo.setTableName(DlbConstant.TABLE_NAME_STUDY);
		vo.setUserId(user.getUserId());
		vo.setUserName(user.getUserName());
		vo.setRoleId(RoleEnum.BRANCH_PARTY.getId());
		vo.setDjDeptId(user.getDeptId());
		vo.setScore(score);
		vo.setApplyYear(Calendar.getInstance().get(Calendar.YEAR));
		String rs = workflowService.doApply(vo, user);
		if (!CoreConst.SUCCESS.equals(rs)) {
			logger.info("提交申请失败");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("提交申请失败");
		}

		result.setCode(ResultCode.OK.getCode());
		result.setData(study.getId());
		return result;
	}

	@Override
	public DjStudy getStudyInfoById(Long studyId) {
		if (studyId == null) {
			return null;
		}
		return studyMapper.selectByPrimaryKey(studyId);
	}

	@Override
	public int deleteStudyById(Long studyId) {
		if (studyId == null) {
			return 0;
		}
		return studyMapper.deleteByPrimaryKey(studyId);
	}

}
