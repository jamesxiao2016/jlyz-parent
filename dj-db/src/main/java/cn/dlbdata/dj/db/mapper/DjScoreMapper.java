package cn.dlbdata.dj.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.db.pojo.DjScore;
import tk.mybatis.mapper.common.Mapper;

public interface DjScoreMapper extends Mapper<DjScore> {
	
	/**
	 * 获取每个类型的总积分
	 * @param projectId
	 * @param userId
	 * @param year
	 * @return
	 */
    Double getSumScoreByTypeIdAndUserId(@Param("projectId")Integer projectId, @Param("userId")Integer  userId , @Param("year") Integer year);

    /**
     * 获取用户的总积分
     * @param userId
     * @param year
     * @return
     */
    Double getSumScoreByUserId(@Param("userId")Long  userId , @Param("year") Integer year);
    
    /**
     * 获取积分明细
     * @param userId
     * @param year
     * @return
     */
    List<DjScore> getScoreListByUserId(@Param("userId")Long  userId , @Param("year") Integer year);
    
    /**
     * 获取每个项目的积分及总积分
     * @param userId
     * @param year
     * @return
     */
    List<DjScore> getTypeScoreListByUserId(@Param("userId")Long  userId , @Param("year") Integer year);

    List<Long> getByDeptIdAndTypeIdAndSubTypeIdAndYear(@Param("deptId")long deptId,
                                                          @Param("typeId") int typeId,
                                                          @Param("subTypeId") int subTypeId,
                                                          @Param("year") int year);
    
}