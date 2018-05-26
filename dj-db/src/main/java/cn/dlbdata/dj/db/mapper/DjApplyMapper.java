package cn.dlbdata.dj.db.mapper;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.pojo.DjApply;
import tk.mybatis.mapper.common.Mapper;

public interface DjApplyMapper extends Mapper<DjApply> {
	/**
	 * 获取待办列表
	 * 
	 * @param map
	 * @return
	 */
	public List<DjApply> getPendingList(Map<String, Object> map);
}