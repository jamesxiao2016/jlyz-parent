package cn.dlbdata.dj.db.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.resquest.PartyMemberLifeNotice;
import tk.mybatis.mapper.common.Mapper;

public interface DjActiveMapper extends Mapper<DjActive> {
	/**
	 * 获取用户参与活动次数
	 * @param userId
	 * @param activeType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    public int getUserActiveCountByActiveTypeAndTime(@Param("userId") Long userId, @Param("activeType") Long activeType,@Param("startTime") Date startTime,@Param("endTime") Date endTime);

	/**
	 * <p>Title: getRunningActive</p> 
	 * <p>Description:党员生活通知 </p> 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getRunningActive(PartyMemberLifeNotice partyMemberLifeNotice);
	
	/**
	 * 
	 * <p>Title: getParticipateActiveCount</p> 
	 * <p>Description: 党员生活通知总数</p> 
	 * @param PartyMemberLifeNotice
	 * @return
	 */
	public int getParticipateActiveCount(PartyMemberLifeNotice partyMemberLifeNotice);

}