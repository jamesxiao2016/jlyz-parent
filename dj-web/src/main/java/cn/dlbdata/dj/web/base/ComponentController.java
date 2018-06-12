package cn.dlbdata.dj.web.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.bean.JqGridBean;
import cn.dlbdata.dj.common.core.util.JsonUtil;
import cn.dlbdata.dj.common.core.web.vo.SelectResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.service.base.IComponentService;

@Controller
@RequestMapping("/api/v1/component")
public class ComponentController extends BaseController {

	@Autowired
	protected IComponentService componentService;

	@ResponseBody
	@RequestMapping("/query")
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
		JqGridBean<Object> result = componentService.queryJqData(sId, param, page, rows);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getDictList")
	public List<SelectVo> getDictListByDictType(String dictType) {
		return componentService.getDictListByDictType(dictType);
	}
	
	@ResponseBody
	@RequestMapping("/getDynamicDictList")
	public SelectResultVo getDynamicDictListByDictType(String dictType) {
		SelectResultVo result = new SelectResultVo();
		result.setResults(componentService.getDictListByDictType(dictType));
		return result;
	}
}
