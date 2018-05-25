package cn.dlbdata.dj.db.mapper;

import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.db.pojo.DjQuery;
import tk.mybatis.mapper.common.Mapper;

public interface DjQueryMapper extends Mapper<DjQuery> {

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
	public Float getSumScoreByUserIdAndYear(@Param("userId") Long userId, @Param("year") Integer year,
			@Param("djTypeId") Long djTypeId, @Param("djSubTypeId") Long djSubTypeId);
}