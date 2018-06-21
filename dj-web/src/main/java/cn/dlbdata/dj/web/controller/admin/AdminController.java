package cn.dlbdata.dj.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.service.IDictService;

/**
 * 处理后台管理员登录的登录
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IDictService dictService;

	@RequestMapping("/getAllDictList")
	@ResponseBody
	public ResultVo<List<SelectVo>> getAllDictList() {
		ResultVo<List<SelectVo>> result = new ResultVo<>();
		List<SelectVo> data = dictService.getAllDictList();
		result.setData(data);
		return result;
	}

	@RequestMapping("/getDictListByDictType")
	@ResponseBody
	public ResultVo<List<SelectVo>> getDictListByDictType(String dictType) {
		ResultVo<List<SelectVo>> result = new ResultVo<>();
		List<SelectVo> data = dictService.getDictListByDictType(dictType);
		result.setData(data);
		return result;
	}
}
