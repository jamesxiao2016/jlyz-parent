package cn.dlbdata.dj.serviceimpl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.mapper.DjApplyMapper;
import cn.dlbdata.dj.db.mapper.DjPicRecordMapper;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.mapper.DjSubTypeMapper;
import cn.dlbdata.dj.db.pojo.DjActivePic;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.pojo.DjPicRecord;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.db.vo.study.ReviewScheduleListVo;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.StudyVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.study.StudyDetailVo;
import tk.mybatis.mapper.entity.Example;

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
		if (studyVo == null || studyVo.getDjSubTypeId() == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		if (!studyVo.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_B.getActiveSubId()) &&
				!studyVo.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_D.getActiveSubId()) &&
				!studyVo.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_H.getActiveSubId())) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		Long typeId;
		if (studyVo.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_B.getActiveSubId())) {
			typeId = ActiveTypeEnum.ACTIVE_A.getActiveId();

		} else if (studyVo.getDjSubTypeId().equals(ActiveSubTypeEnum.ACTIVE_SUB_D.getActiveSubId())) {
			typeId = ActiveTypeEnum.ACTIVE_B.getActiveId();
		} else {
			typeId = ActiveTypeEnum.ACTIVE_F.getActiveId();
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
		study.setDjTypeId(typeId);
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
		vo.setDjTypeId(typeId);
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
		detailVo.setAuditorName(djApply.getApproverName());
		List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(DlbConstant.TABLE_NAME_STUDY,
					djStudy.getId());
		detailVo.setPicIds(picIds);
		return detailVo;
	}

	/* (non-Javadoc)
	 * <p>Title: getReviewScheduleList</p>
	 * <p>Description: 获取审核进度列表</p> 
	 * @param subTypeId
	 * @return  
	 * @see cn.dlbdata.dj.service.IStudyService#getReviewScheduleList(java.lang.Long)
	 */
	@Override
	public List<ReviewScheduleListVo> getReviewScheduleList(Long subTypeId, Long userId) {
		if(subTypeId == null || userId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subTypeId", subTypeId);
		map.put("userId", userId);
		List<ReviewScheduleListVo> list = studyMapper.getReviewScheduleList(map);
		if( list.size() > 0 && list != null) {
			Long[] picIds = null;
			for (ReviewScheduleListVo djStudy : list) {
				Example example1 = new Example(DjPicRecord.class);
				example1.createCriteria().andEqualTo("recordId", djStudy.getId());
				List<DjPicRecord> picList = picRecordMapper.selectByExample(example1);
				/* 将与该活动相关的图片的id加入数组中 */
				if (picList != null && picList.size() > 0) {
					picIds = new Long[picList.size()];
					for (int i = 0, count = picList.size(); i < count; i++) {
						picIds[i] = picList.get(i).getDjPicId();
					}
					djStudy.setPicIds(picIds);
				}
			}
		}
		
		return list;
	}

}
