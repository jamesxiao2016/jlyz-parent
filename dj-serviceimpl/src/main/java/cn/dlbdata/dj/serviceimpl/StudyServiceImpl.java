package cn.dlbdata.dj.serviceimpl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.dlbdata.dj.db.mapper.DjApplyMapper;
import cn.dlbdata.dj.db.mapper.DjPicRecordMapper;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.pojo.DjPicRecord;
import cn.dlbdata.dj.vo.study.StudyDetailVo;
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

	@Autowired
	private DjApplyMapper applyMapper;

	@Autowired
	private DjPicRecordMapper picRecordMapper;

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
		study.setCreateUserId(user.getUserId());
		study.setUserName(user.getUserName());
		study.setDjDeptId(user.getDeptId());
		if (isSave) {
			studyMapper.insertSelective(study);
		} else {
			studyMapper.updateByPrimaryKeySelective(study);
			
			//删除原来的图片
			DjPicRecord record = new DjPicRecord();
			record.setRecordId(study.getId());
			record.setTableName(DlbConstant.TABLE_NAME_STUDY);
			picRecordMapper.delete(record);
		}

		//保存图片
		savePics(study.getId(), DlbConstant.TABLE_NAME_STUDY, studyVo.getPics());

		// 当前提交分数
		Float score = subType.getScore();

		// TODO 提交申请，写入到申请表中
		ApplyVo vo = new ApplyVo();
		vo.setContent(study.getContent());
		vo.setDjSubTypeId(study.getDjSubTypeId());
		vo.setDjTypeId(study.getDjTypeId());
		vo.setRecordId(study.getId());
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
			result.setCode(ResultCode.BadRequest.getCode());
			result.setMsg("提交申请失败");
		}

		result.setCode(ResultCode.OK.getCode());
		result.setData(study.getId());
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

	/**
	 * 获取自主学习详情
	 *
	 * @param applyId 申请Id
	 * @return
	 */
	@Override
	public StudyDetailVo getStudyDetail(Long applyId) {
		DjApply djApply = applyMapper.selectByPrimaryKey(applyId);
		if (djApply == null) {
			return new StudyDetailVo();
		}
		StudyDetailVo detailVo = new StudyDetailVo();
		detailVo.setName(djApply.getUserName());
		detailVo.setStatus(djApply.getStatus());
		if (djApply.getRecordId() == null) {
			return detailVo;
		}
		DjStudy djStudy = studyMapper.selectByPrimaryKey(djApply.getRecordId());
		if (djStudy == null) {
			return detailVo;
		}
		detailVo.setStartTime(djStudy.getStartTime() == null?null:new Timestamp(djStudy.getStartTime().getTime()));
		detailVo.setEndTime(djStudy.getEndTime() == null?null:new Timestamp(djStudy.getEndTime().getTime()));
		detailVo.setContent(djStudy.getContent());
		List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(DlbConstant.TABLE_NAME_STUDY,
					djStudy.getId());
		detailVo.setPicIds(picIds);
		return detailVo;
	}

}
