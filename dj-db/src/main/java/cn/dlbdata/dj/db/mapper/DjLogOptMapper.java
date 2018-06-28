package cn.dlbdata.dj.db.mapper;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.pojo.DjLogOpt;
import tk.mybatis.mapper.common.Mapper;

public interface DjLogOptMapper extends Mapper<DjLogOpt> {
	
	List<Map<String, Object>> getTop5PartyBanch(int year);
	
	/**
	 * 
	 * <p>Title: getActiveJoin</p> 
	 * <p>Description: 获取党员参与活动情况</p> 
	 * @param year
	 * @param month
	 * @return
	 */
	List<Map<String, Object>> getActiveJoin(Map<String, Object> map);
}