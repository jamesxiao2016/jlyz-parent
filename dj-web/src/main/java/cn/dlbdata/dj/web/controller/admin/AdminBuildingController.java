package cn.dlbdata.dj.web.controller.admin;

import cn.dlbdata.dj.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/building")
public class AdminBuildingController extends BaseController {

    /**
     *查询楼宇列表
     * @return
     */
    @RequestMapping("/building_list.html")
    public String list() {
        return "building/building_list.html";
    }
}
