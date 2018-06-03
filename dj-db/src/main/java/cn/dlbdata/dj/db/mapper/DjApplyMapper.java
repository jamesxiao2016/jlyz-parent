package cn.dlbdata.dj.db.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.vo.apply.ScoreApplyVo;
import cn.dlbdata.dj.db.vo.apply.ScoreAuditDetailVo;
import cn.dlbdata.dj.db.vo.party.ObserveLowDetailVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface DjApplyMapper extends Mapper<DjApply> {
	/**
	 * 获取待办列表
	 * 
	 * @param map
	 * @return
	 */
	public List<DjApply> getPendingList(Map<String, Object> map);

	/**
	 *查询积分审核列表
	 * @param approverId 审批人Id
	 * @param status 审批状态
	 * @param yearTimeStart 今年开始的时间
	 * @param yearTimeEnd 今年结束的时间
	 * @param deptId 支部ID
	 * @return
	 */
	List<ScoreApplyVo> getScoreAuditList(
										 @Param("status") int status,
										 @Param("yearTimeStart")Date yearTimeStart,
										 @Param("yearTimeEnd") Date yearTimeEnd,
										 @Param("deptId") Long deptId);

    /**
     * 查询积分审核详情(先锋作用的三个)
     *
     * @param partyMemberId 党员Id
     * @return
     */
    List<ScoreAuditDetailVo> getScoreAuditDetailByPtMemberId( @Param("yearTimeStart")Date yearTimeStart,
                                                              @Param("yearTimeEnd") Date yearTimeEnd,
                                                              @Param("partyMemberId") Long partyMemberId);

	/**
	 *查询党员是否有传入的一级分类的审核申请
	 */
	int countUnAuditByPtMemberIdAndType(@Param("partyMemberId") Long partyMemberId,@Param("typeId") Long typeId,
										@Param("year") int year);

	/**
	 * 查询党员是否有传入状态和传入一级分类的审核申请
	 * @param partyMemberId
	 * @param status
	 * @param typeId
	 * @return
	 */
	int countByPtMemberIdStatus(@Param("partyMemberId") Long partyMemberId,
								@Param("status") int status,
								@Param("typeId") Long typeId,
								@Param("year") int year);

	/**
	 * 遵章守纪详情-->片区负责人使用
	 *
	 * @param applyId 申请Id
	 * @return
	 */
	ObserveLowDetailVo getObserveLowDetailByApplyId(Long applyId);

    /**
     * 遵章守纪详情-->支部书记使用
     *
     * @param partyMemberId 党员Id
     * @return
     */
    ObserveLowDetailVo getObserveLowDetailByPartyMemberId(@Param("partyMemberId") Long partyMemberId,
                                                          @Param("yearTimeStart")Date yearTimeStart,
                                                          @Param("yearTimeEnd") Date yearTimeEnd);

    /**
     *获取申请最近的一条记录的状态
     *
     * @param userId
     * @param yearTimeStart
     * @param yearTimeEnd
     * @return 申请状态
     */
    Integer getOneByUserIdOrderByCreateTimeDesc(@Param("userId") Long userId,
                                                @Param("yearTimeStart")Date yearTimeStart,
                                                @Param("yearTimeEnd") Date yearTimeEnd,
                                                @Param("subTypeId") Long subTypeId);

    int countByPartyMemberIdAndSubTypeIdAndYear(@Param("partyMemberId") Long partyMemberId,
                                                @Param("year") int year,
                                                @Param("subTypeId") Long subTypeId);

    int countBySubTypeIdAndStatusAndDeptId(@Param("subTypeId") Long subTypeId,
                                  @Param("status") Integer status,
								  @Param("deptIds") Long... deptIds);

    Float countScoreInProcess(@Param("userId") Long userId,
							@Param("year") int year,
							@Param("typeId") Long typeId,
							@Param("subTypeId") Long subTypeId);
}