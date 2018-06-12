/**
 *  <p>Title: DeptController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月24日 
 */
package cn.dlbdata.dj.web.controller.api.v1;

import cn.dlbdata.dj.db.vo.dept.DeptIdNameDto;
import cn.dlbdata.dj.db.vo.dept.DeptTreeVo;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
import cn.dlbdata.dj.db.vo.party.SectionInfoVo;
import cn.dlbdata.dj.util.TreeUtil;
import cn.dlbdata.dj.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.web.base.BaseController;
import cn.dlbdata.dj.web.vo.TokenVo;

import java.util.List;

/**
 * <p>
 * Title: DeptController
 * </p>
 * 
 * @author zhouxuan
 *         <p>
 *         Description:
 *         </p>
 * @date 2018年5月24日
 */
@Controller
@RequestMapping("/api/v1/dept")
public class DeptController extends BaseController {
	@Autowired
	private IDeptService deptService;

	@GetMapping(value = "/queryById")
	@ResponseBody
	public ResultVo<DjDept> queryById(Long deptId) {
		ResultVo<DjDept> result = new ResultVo<>();
		TokenVo vo = getTokenUserInfo();
		if (vo == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (deptId == null || deptId == 0) {
			deptId = vo.getDeptId();
		}
		DjDept djDept = deptService.getDeptMessage(deptId);
		if (djDept == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有该支部的相关信息");
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(djDept);
		return result;
	}

	/**
	 * 片区负责人获取支部信息列表
	 * 
	 * @param sectionId
	 *            片区Id
	 * @return
	 */
	@GetMapping("/getDeptListBySectionId")
	@ResponseBody
	public ResultVo<List<BranchDeptInfoVo>> getBranchDeptInfo() {
		ResultVo<List<BranchDeptInfoVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		List<BranchDeptInfoVo> voList = deptService.getBranchDeptInfo(user);
		result.setData(voList);
		return result;
	}

	/**
	 * 获取片区信息（片区负责人登录时首页）
	 * 
	 * @param userId
	 *            id
	 * @return
	 */
	@GetMapping("/getSectionByUserId")
	@ResponseBody
	public ResultVo<SectionInfoVo> getSectionInfo(@RequestParam("userId") Long userId) {
		ResultVo<SectionInfoVo> result = new ResultVo<>(ResultCode.OK.getCode());
		SectionInfoVo vo = deptService.getSectionInfo(userId);
		result.setData(vo);
		return result;
	}

	/**
	 * 查询片区内的党支部Id和Name.
	 * 
	 * @param sectionId
	 *            片区Id
	 * @return
	 */
	@GetMapping("/branchIdNameInit")
	@ResponseBody
	public ResultVo<List<DeptIdNameDto>> getBranchDeptNameAndId(@RequestParam("sectionId") Long sectionId) {
		List<DeptIdNameDto> list = deptService.getBranchDeptNameAndId(sectionId);
		ResultVo<List<DeptIdNameDto>> result = new ResultVo<>(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}

	@GetMapping("/deptTree")
	@ResponseBody
	public List<DeptTreeVo> getDeptTree(@RequestParam("sectionId") Long sectionId) {
		List<DeptTreeVo> depts = deptService.getDeptTree(sectionId);
		List<DeptTreeVo> tree = TreeUtil.RecursiveAddress(depts);
		return tree;
	}
}
