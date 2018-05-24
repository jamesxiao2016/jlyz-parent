/**
 *  <p>Title: DeptController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月24日 
 */
package cn.dlbdata.dj.web.controller.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>Title: DeptController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年5月24日  
 */
@Controller
@RequestMapping("/api/v1/dept")
public class DeptController extends BaseController {
	@Autowired
	private IDeptService deptService;

    @GetMapping(value="/queryById")
    @ResponseBody
    public ResultVo<DjDept> queryById(Long deptId){
    	ResultVo<DjDept> result = new ResultVo<>();
    	DjDept djDept = deptService.getDeptMessage(deptId);
    	if(djDept == null)
    	{
    		result.setCode(ResultCode.Forbidden.getCode());
    		result.setMsg("没有该支部的相关信息");
    	}
        result.setCode(ResultCode.OK.getCode());
        result.setData(djDept);
        return result;
    }
}
