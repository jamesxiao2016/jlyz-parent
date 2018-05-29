package cn.dlbdata.dj.db.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.vo.apply.ScoreApplyVo;
import cn.dlbdata.dj.db.vo.apply.ScoreAuditDetailVo;
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
}