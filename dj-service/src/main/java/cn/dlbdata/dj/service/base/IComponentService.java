package cn.dlbdata.dj.service.base;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.common.core.bean.JqGridBean;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;

public interface IComponentService {

	/**
	 * 获取表格数据
	 * 
	 * @param fullSelectId
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public JqGridBean<Object> queryJqData(String fullSelectId, Map<String, Object> params, Integer pageNum,
			Integer pageSize);

	/**
	 * 根据字典类型获取字典列表
	 * 
	 * @param dictType
	 * @return
	 */
	public List<SelectVo> getDictListByDictType(String dictType);
}
