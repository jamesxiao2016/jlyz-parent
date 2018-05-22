package cn.dlbdata.dj.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.JsonUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<String> upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ResultVo<String> result = new ResultVo<String>();

		String userId = getHeader("userId");
		logger.info("=====开始上传=====" + userId);
		if (StringUtils.isEmpty(userId)) {
			userId = DigitUtil.generatorLongId() + "";
		}
		
		// 文件保存路径（可以考虑存放到固定目录下）
		String path = request.getSession().getServletContext().getRealPath("upload") + File.separator + userId;
		String fileName = DigitUtil.generatorLongId() + "";
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			logger.error("保存文件失败", e);
			result.setMsg(e.getMessage());
			result.setCode(ResultCode.BadRequest.getCode());
			return result;
		}

		// 处理成功，返回文件相对路径
		String rootUrl = getRootUrl();
		String filePath = rootUrl + "/upload/" + userId + "/" + fileName;

		result.setCode(ResultCode.OK.getCode());
		result.setData(filePath);
		logger.info("=====上传成功=====" + JsonUtil.toJsonString(result));
		return result;
	}
}
