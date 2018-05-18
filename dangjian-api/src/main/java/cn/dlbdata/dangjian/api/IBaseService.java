package cn.dlbdata.dangjian.api;

import java.util.Map;

import cn.dlbdata.dangjian.common.core.bean.DataTableBean;
import cn.dlbdata.dangjian.common.core.bean.JqGridBean;
import cn.dlbdata.dangjian.common.core.bean.TableBean;

/**
 * 所有的service接口都继承此类，此类主要处理一些共用的业务逻辑
 * @author xiaowei
 *
 */
public interface IBaseService {
	public TableBean<Object> query(String fullSelectId, Map<String, Object> params, int offset, int limit);

	public TableBean<Object> query(Class<?> mapperClass, String selectId, Map<String, Object> params, int offset,
			int limit);

	/**
	 * dataTables通用查询
	 * 
	 * @param fullSelectId
	 * @param params
	 * @param offset
	 * @param limit
	 * @return
	 */
	public DataTableBean<Object> queryData(String fullSelectId, Map<String, Object> params, int offset, int limit);

	public DataTableBean<Object> queryDataEx(String fullSelectId, Map<String, Object> params, int offset, int limit);

	public JqGridBean<Object> queryJqData(String fullSelectId, Map<String, Object> params, int offset, int limit);
}
