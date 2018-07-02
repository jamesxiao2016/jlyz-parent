package cn.dlbdata.dj.service;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.vo.building.SelectBuildingVo;
import cn.dlbdata.dj.db.vo.party.*;
import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectResultVo;
import cn.dlbdata.dj.db.dto.partymember.PartyMemberAddOrUpdateDto;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.vo.DjPartyMemberVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.db.vo.score.ScoreVo;
import cn.dlbdata.dj.vo.PartyVo;
import cn.dlbdata.dj.vo.UserVo;

/**
 * 党员相关的业务逻辑
 * 
 * @author xiaowei
 *
 */
public interface IPartyMemberService {

	/**
	 * 根据id获取党员信息
	 * 
	 * @param id
	 *            党员ID
	 * @return
	 */
	public DjPartymember getInfoById(Long id);

	/**
	 * 根据部门ID获取党员列表
	 * 
	 * @param deptId
	 *            部门ID
	 * @return
	 */
	public List<DjPartymember> getPartyMemberListByDeptId(Long deptId);

	/**
	 * 根据党员ID获取党员的积分及参加活动的次数
	 * 
	 * @param memberId
	 *            党员ID
	 * @return
	 */
	public PartyVo getScoreAndNumByMemberId(Long memberId, Integer year);

	/**
	 * 获取积分明细
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	public List<ScoreVo> getScoreListByUserId(Long userId, Integer year);

	/**
	 * 获取每个项目的积分及总积分
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	public List<ScoreTypeVo> getTypeScoreListByUserId(Long userId, Integer year);

	/**
	 *
	 * @param deptId
	 *            支部ID
	 * @param subTypeId
	 *            子活动ID 11："思想汇报自主汇报"，12："思想汇报书面汇报"
	 * @return 思想汇报评分查询党员列表
	 */
	Paged<ReportPartyMemberVo> getReportPartyMember(long deptId, long subTypeId, int pageNum, int pageSize);

	/**
	 * 思想汇报详情
	 * 
	 * @param id
	 *            党员ID
	 * @param subTypeId
	 *            活动二级分类ID
	 * @return
	 */
	ReportDetailVo getReportDetail(Long id, Long subTypeId);

	/**
	 * 先锋作用评分党员列表
	 * 
	 * @param deptId
	 * @return
	 */
	Paged<PioneeringPartyMemberVo> getPioneeringPartyMembers(Long deptId, int pageNum, int pageSize);

	/**
	 * 
	 * <p>
	 * Title: queryAllPartyMembersByDeptId
	 * </p>
	 * <p>
	 * Description: 查询支部全部党员的信息以及分数
	 * </p>
	 * 
	 * @param deptId
	 * @return
	 */
	public List<AllPartyMemberVo> queryAllPartyMembersByDeptId(Long deptId);

	/**
	 * 违章守纪评分党员列表
	 * 
	 * @param deptId
	 *            支部Id
	 * @return
	 */
	Paged<ObserveLowPartyMemberVo> getObserveLowPartyMember(Long deptId, int pageIndex, int pageSize);

	/**
	 * 遵章守纪详情 支部书记使用
	 *
	 * @param partyMemberId
	 *            党员Id
	 * @return
	 */
	ObserveLowDetailVo getObserveLowDetailForDept(Long partyMemberId);

	/**
	 * 
	 * <p>
	 * Title: getSumScoreByIdCard
	 * </p>
	 * <p>
	 * Description: 根据身份证查询党员总积分
	 * </p>
	 * 
	 * @param idCard
	 * @return
	 */
	public ResultVo<Float> getSumScoreByIdCard(String idCard);

	/**
	 * 获取党员年度活动信息
	 * 
	 * @return
	 */
	AnnualActiveInfo getAnnualActiveInfo(UserVo user, Integer year);

	/**
	 * 
	 * <p>
	 * Title: selectPartymemberByDeptId
	 * </p>
	 * <p>
	 * Description: 提供给外部使用的部门党员信息
	 * </p>
	 * 
	 * @param deptId
	 * @return
	 */
	public List<DjPartyMemberVo> selectPartymemberByDeptId(@Param("deptId") Long deptId, String deptName);

	public List<ScoreTypeVo> getRadarChartByUserId(Long userId, Integer year);

	/**
	 * 新增党员账号
	 * 
	 * @param dto
	 * @param user
	 */
	void addPartyMember(PartyMemberAddOrUpdateDto dto, UserVo user);

	/**
	 * 更新党员信息
	 * 
	 * @param id
	 * @param dto
	 * @param userVo
	 * @return
	 */
	boolean updatePartyMember(Long id, PartyMemberAddOrUpdateDto dto, UserVo userVo);

	/**
	 * 作废党员
	 * 
	 * @param id
	 * @return
	 */
	boolean invalidPartyMember(Long id, UserVo user);
	
	/**
	 * 获取片区下的所有党员信息
	 * @param sectionId
	 * @return
	 */
	public SelectResultVo getPartyMembersBySectionId(Long sectionId);

	/**
	 * 获取支部下的所有党员信息
	 * @param deptId
	 * @return
	 */
	public SelectResultVo getPartyMembersByDeptId(Long deptId);

	/**
	 * 查询党员详细信息.
	 * @param id
	 * @return
	 */
	PartyMemberDetailVo getPartyMemberDetailById(long id);
	
	/**
	 * 
	 * <p>Title: getTop5Score</p> 
	 * <p>Description: 获取积分最多的五个人</p> 
	 * @param year
	 * @return
	 */
	List<Map<String, Object>> getTop5Score(Integer year);

	/**
	 *获取片区内的党支部以及党支部成员
	 * @param sectionId
	 * @return
	 */
	List<SelectBuildingVo> getDeptAndPtMemberTree(long sectionId);
}
