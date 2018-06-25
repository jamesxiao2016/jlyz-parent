/**
 *  <p>Title: AdminPartymemberDuesController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.web.controller.admin;

import cn.dlbdata.dj.common.core.util.PoiUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.poi.ExcelReplaceDataVO;
import cn.dlbdata.dj.common.core.util.poi.ExcelUtil;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.PartyMemberDueService;
import cn.dlbdata.dj.web.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.dlbdata.dj.web.base.BaseController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: AdminPartymemberDuesController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
@Controller
@RequestMapping("/admin/partymemberdues")
public class AdminPartymemberDuesController extends BaseController {
    @Autowired
    private PartyMemberDueService partyMemberDueService;
	
	
	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "partymemberdues/list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public String detail() {
		return "partymemberdues/detail.html";
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "partymemberdues/add.html";
	}

/*	@RequestMapping("/save")
	@ResponseBody
	public ResultVo<Long> saveOrUpdate(DjSection section) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = sectionService.saveOrUpdate(section);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public ResultVo<Long> deleteById(Long id) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = sectionService.deleteById(id);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}*/

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<String> upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		String userId = getHeader("userId");
		ResultVo<String> result = new ResultVo<>();
		//将文件保存，并返回文件所在地址
		String path = UploadUtil.upload(file,request,response,userId);
		if (path == null) {
			result.setCode(CoreConst.ResultCode.InternalServerError.getCode());
			result.setMsg("上传失败");
			return result;
		}
		//根据文件的地址读取文件，返回文件中的数据
		List<List<Object>> lists = PoiUtil.readExecl(path);

		//若文件中内容校验发现错误则返回错误信息VO
        Map<String, List<ExcelReplaceDataVO>> map = null;
		if (lists!= null && lists.size()>1) {//第一行是表格头部，list.size>1才是表格中有数据
            System.out.println(lists.toString());
		    map = partyMemberDueService.saveDues(lists);
        } else {
            result.setMsg("请上传有数据的表格");
            result.setCode(CoreConst.ResultCode.InternalServerError.getCode());
            return result;
        }
        if (map != null) {
            List<ExcelReplaceDataVO> errorList= map.get("error");
            StringBuilder sb = new StringBuilder();
            sb.append(path).insert(path.lastIndexOf("."),"err");
            String newpath = sb.toString();
            ExcelUtil.replaceModel(errorList,path,newpath);
            result.setCode(CoreConst.ResultCode.NotFound.getCode());//TODO 错误Code后面需要调整
            result.setData(newpath);
            result.setMsg("文件中发现错误，请下载后查看");
            return result;
        } else {
            result.setCode(CoreConst.ResultCode.OK.getCode());
            result.setMsg("成功");
            return result;
        }

	}

	/**
	 * 下载错误文件
	 * @param path 错误文件路径
	 */
	@RequestMapping(value = "/uploadErrorExcel", method = RequestMethod.POST)
	@ResponseBody
	public void uploadErrorExcel(@RequestParam("path") String path, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


}
