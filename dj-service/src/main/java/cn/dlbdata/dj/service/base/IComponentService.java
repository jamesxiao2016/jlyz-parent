package cn.dlbdata.dj.service.base;

import java.util.Map;

import cn.dlbdata.dj.common.core.bean.JqGridBean;

public interface IComponentService {
	public JqGridBean<Object> queryJqData(String fullSelectId, Map<String, Object> params, Integer pageNum, Integer pageSize);
}
