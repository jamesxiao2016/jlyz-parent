package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.service.base.IBaseService;

public interface IDictService extends IBaseService {
	/**
	 * 根据字典类型获取字典列表
	 * 
	 * @param dictType
	 * @return
	 */
	public List<SelectVo> getDictListByDictType(String dictType);
	
	/**
	 * 获取所有的字典列表
	 * @return
	 */
	public List<SelectVo> getAllDictList();
}
