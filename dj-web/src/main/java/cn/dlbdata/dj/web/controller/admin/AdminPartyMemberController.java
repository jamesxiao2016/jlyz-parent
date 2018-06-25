package cn.dlbdata.dj.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.dto.partymember.PartyMemberAddOrUpdateDto;
import cn.dlbdata.dj.db.vo.dept.SelectTreeVo;
import cn.dlbdata.dj.db.vo.party.PartyMemberDetailVo;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 党员管理Controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin/partymember")
public class AdminPartyMemberController extends BaseController {

    @Autowired
    private IPartyMemberService partyMemberService;
    @Autowired
    private IDeptService deptService;
	/**
	 * 查询列表党员列表
	 * 
	 * @return
	 */
	@RequestMapping("partymember_list.html")
	public String list() {
		return "partymember/partymember_list.html";
	}

	/**
	 * 查询积分列表
	 *
	 * @return
	 */
	@RequestMapping("scorehistory_list.html")
	public String scorehistoryList() {
		return "partymember/scorehistory_list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/partymember_detail.html")
	public ModelAndView detail(Long id) {
		ModelAndView view = new ModelAndView("partymember/partymember_detail.html");
		PartyMemberDetailVo record = partyMemberService.getPartyMemberDetailById(id);
		List<SelectTreeVo> tree = deptService.getSectionAndDeptTree();
		view.addObject("record", record);
		view.addObject("tree", tree);
		return view;
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("partymember_add.html")
	public String add() {
		return "partymember/partymember_add.html";
	}

	/**
	 * 新增党员
	 * @param dto
	 * @return
	 */
	@PostMapping("/addPartyMember")
    @ResponseBody
    public ResultVo<Long> addPartyMember(@RequestBody PartyMemberAddOrUpdateDto dto) {
        UserVo user = getCurrentUserFromCache();
        ResultVo<Long> resultVo = new ResultVo<>(CoreConst.ResultCode.OK.getCode());
//        if (user == null) {
//            logger.error("用户未登录");
//            resultVo.setCode(CoreConst.ResultCode.NOT_LOGIN.getCode());
//            resultVo.setMsg("用户未登录或用户已退出");
//            return resultVo;
//        }
        partyMemberService.addPartyMember(dto,user);
        resultVo.setCode(CoreConst.ResultCode.OK.getCode());
        resultVo.setMsg("新增党员成功!");
        return resultVo;
    }

	/**
	 * 更新党员信息
	 * @param dto
	 * @param id
	 * @return
	 */
	@PostMapping("/updatePartyMember/{id}")
	@ResponseBody
	public ResultVo<Long> updatePartyMember(@RequestBody PartyMemberAddOrUpdateDto dto,
									  @PathVariable Long id) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<Long> resultVo = new ResultVo<>();
		if (user == null) {
			logger.error("用户未登录");
			resultVo.setCode(CoreConst.ResultCode.NOT_LOGIN.getCode());
			resultVo.setMsg("用户未登录或用户已退出");
			return resultVo;
		}
		partyMemberService.updatePartyMember(id,dto,user);
		resultVo.setCode(CoreConst.ResultCode.OK.getCode());
		resultVo.setMsg("修改党员信息成功!");
		return resultVo;
	}

	/**
	 *作废党员
	 * @param id
	 * @return
	 */
	@PostMapping("/invalidPartyMember/{id}")
	@ResponseBody
	public ResultVo<Long> invalidPartyMember(@PathVariable Long id) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<Long> resultVo = new ResultVo<>();
		if (user == null) {
			logger.error("用户未登录");
			resultVo.setCode(CoreConst.ResultCode.NOT_LOGIN.getCode());
			resultVo.setMsg("用户未登录或用户已退出");
			return resultVo;
		}
		partyMemberService.invalidPartyMember(id,user);
		resultVo.setCode(CoreConst.ResultCode.OK.getCode());
		resultVo.setMsg("作废党员成功!");
		return resultVo;
	}
}
