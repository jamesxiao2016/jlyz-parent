package cn.dlbdata.dj.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.db.vo.ScoreActiveVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.db.vo.score.ScoreVo;
import tk.mybatis.mapper.common.Mapper;

public interface DjScoreMapper extends Mapper<DjScore> {
	
	/**
	 * 获取每个类型的总积分
	 * @param projectId
	 * @param userId
	 * @param year
	 * @return
	 */
    Float getSumScoreByTypeIdAndUserId(@Param("userId")Integer  userId , @Param("year") Integer year);

    /**
     * 获取用户的总积分
     * @param userId
     * @param year
     * @return
     */
    Float getSumScoreByUserId(@Param("userId")Long  userId , @Param("year") Integer year);
    
    /**
     * 获取积分明细
     * @param userId
     * @param year
     * @return
     */
    List<ScoreVo> getScoreListByUserId(@Param("userId")Long  userId , @Param("year") Integer year);
    
    /**
     * 获取每个项目的积分及总积分
     * @param userId
     * @param year
     * @return
     */
    List<ScoreTypeVo> getTypeScoreListByUserId(@Param("userId")Long  userId , @Param("year") Integer year);


	/**
	 * 获取用户的总积分
	 * 
	 * @param userId
	 *            用户ID
	 * @param year
	 *            年份
	 * @param djTypeId
	 *            分类ID
	 * @param djSubTypeId
	 *            二级分类ID
	 * @return
	 */
	public Float getSumScoreByUserIdAndType(@Param("userId") Long userId, @Param("year") Integer year,
			@Param("djTypeId") Long djTypeId, @Param("djSubTypeId") Long djSubTypeId);

	boolean existBaseScore(@Param("userId") Long userId,@Param("year") int year);
	
	public List<ScoreTypeVo> getRadarChartByUserId(@Param("userId")Long userId, @Param("year") Integer year);
	
	public List<ScoreActiveVo> getScoreAndActiveList(@Param("userId") Integer userId);
}