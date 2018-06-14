package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.vo.study.ReviewScheduleListVo;
import cn.dlbdata.dj.dto.study.StudyResubmitDto;
import cn.dlbdata.dj.vo.StudyVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.study.StudyDetailVo;

public interface IStudyService {

	/**
	 * 保存自主学习
	 * 
	 * @param typeId
	 *            分类ID
	 * @param subTypeId
	 *            二级分类ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param content
	 *            学习内容
	 * @param picIds
	 *            学习图片数组
	 * @return
	 */
	public ResultVo<Long> saveStudy(StudyVo studyVo, UserVo user);

	/**
	 * 自主活动重新提交
	 * @param resubmitDto
	 * @return
	 */
	ResultVo<Long> studyResubmit(StudyResubmitDto resubmitDto,UserVo user);

	/**
	 * 根据ID获取自主学习详细信息
	 * 
	 * @param studyId
	 *            记录ID
	 * @return
	 */
	public DjStudy getStudyInfoById(Long studyId);
	
	/**
	 * 删除自主学习内容
	 * 
	 * @param studyId
	 *            学习ID
	 * @return
	 */
	public int deleteStudyById(Long studyId);

	/**
	 * 获取自主学习详情
	 *
	 * @param applyId 申请Id
	 * @return
	 */
	StudyDetailVo getStudyDetail(Long applyId);
	
	/**
	 * 后台获取自主学习详情
	 *
	 * @param applyId 申请Id
	 * @return
	 */
	StudyDetailVo getAdminStudyDetail(Long applyId);
	
	/**
	 * 
	 * <p>Title: getReviewScheduleList</p> 
	 * <p>Description: 获取审核进度列表</p> 
	 * @param subTypeId
	 * @return
	 */
	List<ReviewScheduleListVo> getReviewScheduleList(Long subTypeId, Long userId);
	/**
	 * 
	 * <p>Title: deleteById</p> 
	 * <p>Description: 删除自主学习</p> 
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);
}
