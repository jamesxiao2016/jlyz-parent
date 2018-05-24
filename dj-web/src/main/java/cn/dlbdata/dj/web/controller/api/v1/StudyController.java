package cn.dlbdata.dj.web.controller.api.v1;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.vo.study.PendingPtMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理自主学习的controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1/study")
public class StudyController extends BaseController {
    @Autowired
    private IActiveService activeService;


    /**
     * 点击支书查询待办列表项进去的查询
     * @param deptId 支部ID
     * @param subTypeId 活动类型Id
     * @return
     */
    @GetMapping("/getPendingListByDeptId")
    @ResponseBody
    public ResultVo<List<PendingPtMemberVo>> getPendingList(@RequestParam("deptId") Long deptId,
                                                      @RequestParam("subTypeId") Long subTypeId) {
        ResultVo<List<PendingPtMemberVo>> result = new ResultVo<>(CoreConst.ResultCode.OK.getCode());
        List<PendingPtMemberVo> voList = activeService.getPendingList(deptId,subTypeId);
        result.setData(voList);
        return result;
    }

}
