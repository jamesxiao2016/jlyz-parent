package cn.dlbdata.dj.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.vo.StudyVo;

@Service
public class StudyServiceImpl implements IStudyService {
	@Autowired
	private DjStudyMapper studyMapper;

	@Autowired
	private IWorkflowService workflowService;

	@Override
	public Long saveStudy(StudyVo studyVo) {
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
