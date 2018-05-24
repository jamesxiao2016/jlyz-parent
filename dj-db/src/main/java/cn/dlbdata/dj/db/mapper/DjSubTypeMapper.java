package cn.dlbdata.dj.db.mapper;

import java.util.List;

import cn.dlbdata.dj.db.pojo.DjSubType;
import tk.mybatis.mapper.common.Mapper;

public interface DjSubTypeMapper extends Mapper<DjSubType> {
	/**
	 * 
	 * <p>Title: getActiveTypeList</p> 
	 * <p>Description: 活动类型列表</p> 
	 * @return
	 */
	public List<DjSubType> getActiveTypeList();
}