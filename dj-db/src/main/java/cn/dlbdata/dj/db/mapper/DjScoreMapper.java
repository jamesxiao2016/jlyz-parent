package cn.dlbdata.dj.db.mapper;

import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.db.pojo.DjScore;
import tk.mybatis.mapper.common.Mapper;

public interface DjScoreMapper extends Mapper<DjScore> {
	
    Double getSumScoreByProjectIdAndUserId(@Param("projectId")Integer projectId, @Param("userId")Integer  userId , @Param("year") Integer year);

    Double getSumScoreByUserId(@Param("userId")Long  userId , @Param("year") Integer year);
}