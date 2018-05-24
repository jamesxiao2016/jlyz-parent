package cn.dlbdata.dj.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.StudyVo;
import cn.dlbdata.dj.vo.UserVo;

@Service
public class StudyServiceImpl extends BaseService implements IStudyService {
	@Autowired
	private DjStudyMapper studyMapper;

	@Autowired
	private IWorkflowService workflowService;

	@Override
	public Long saveStudy(StudyVo studyVo, UserVo user) {
		if (studyVo == null) {
			return 0L;
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

		// TODO 提交申请，写入到申请表中
		ApplyVo vo = new ApplyVo();
		vo.setContent(study.getContent());
		vo.setDjSubTypeId(study.getDjSubTypeId());
		vo.setDjTypeId(study.getDjTypeId());
		vo.setRecordId(study.getId());
		vo.setRemark("自主学习申请");
		// vo.setScore(score);
		vo.setTableName(DlbConstant.TABLE_NAME_STUDY);
		String result = workflowService.apply(vo, user);
		if (!CoreConst.SUCCESS.equals(result)) {
			logger.info("提交申请失败");
		}

		return study.getId();
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
