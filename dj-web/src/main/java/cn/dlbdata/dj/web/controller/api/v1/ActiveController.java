package cn.dlbdata.dj.web.controller.api.v1;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.dto.active.ReportAddScoreRequest;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;

import java.util.Calendar;

/**
 * 处理活动相关的controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1/active")
public class ActiveController extends BaseController {

}
