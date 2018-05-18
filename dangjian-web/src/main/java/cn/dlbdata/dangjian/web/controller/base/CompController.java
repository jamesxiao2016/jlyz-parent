package cn.dlbdata.dangjian.web.controller.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dangjian.api.IBaseService;
import cn.dlbdata.dangjian.common.core.bean.DataTableBean;
import cn.dlbdata.dangjian.common.core.bean.JqGridBean;
import cn.dlbdata.dangjian.common.core.bean.TableBean;
import cn.dlbdata.dangjian.common.core.util.JsonUtil;
import cn.dlbdata.dangjian.common.core.web.vo.QueryParamVo;


@Controller
@RequestMapping("/compCtl")
public class CompController extends BaseController {

	@Autowired
	protected IBaseService baseService;

	@ResponseBody
	@RequestMapping("/query")
	public TableBean<Object> query(@RequestBody QueryParamVo param) {
		String sId = param.getsId();
		Map<String, Object> params = param.getQryParam();
		int offset = param.getOffset();
		int limit = param.getLimit();
		TableBean<Object> result = baseService.query(sId, params, offset, limit);
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryDataTable")
	public DataTableBean<Object> queryDataTable(Integer draw, Integer start, Integer length, String params,
			String columns, String order, String search) {
		QueryParamVo param = JsonUtil.toObject(params, QueryParamVo.class);
		if (param == null) {
			return new DataTableBean<>();
		}
		String sId = param.getsId();
		Map<String, Object> p = param.getQryParam();
		DataTableBean<Object> result = baseService.queryData(sId, p, start, length);
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryJqData")
	public JqGridBean<Object> queryJqData(Integer page, Integer rows, String sidx, String sord, String search,
			String sId, String params) {
		if (page == null || page <= 0) {
			page = 1;
		}
		if (rows == null || rows <= 0) {
			rows = 10;
		}
		Map<String, Object> param = JsonUtil.toMapObject(params);
		if (param == null) {
			param = new HashMap<>();
		}
		
		if (StringUtils.isNotEmpty(sidx) && StringUtils.isNotEmpty(sord)) {
			String orderBy = sidx + " " + sord;
			param.put("orderBy", orderBy);
		}
		JqGridBean<Object> result = baseService.queryJqData(sId, param, page, rows);
		return result;
	}

}
