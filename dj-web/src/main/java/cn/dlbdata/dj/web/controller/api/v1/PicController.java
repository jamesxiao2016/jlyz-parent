package cn.dlbdata.dj.web.controller.api.v1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.JsonUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjPic;
import cn.dlbdata.dj.service.IPictureService;
import cn.dlbdata.dj.vo.PicVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/api/v1/picture")
public class PicController extends BaseController {
	@Autowired
	private IPictureService pictureService;

	/**
	 * 上传图片
	 * 
	 * @param vo
	 * @return pictureId
	 */
	@GetMapping(value = "/upload")
	@ResponseBody
	public ResultVo<Long> upload(PicVo vo) {
		ResultVo<Long> result = new ResultVo<>();
		if (vo == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("参数不能为空");
			return result;
		}
		result = pictureService.insert(vo);

		return result;
	}

	/**
	 * 显示缩略图
	 * 
	 * @param vo
	 * @return
	 */
	@GetMapping(value = "/showThumbnail")
	public void showThumbnail(PicVo vo, HttpServletResponse response) {
		UserVo currentUser = getCurrentUserFromCache();
		ResultVo<DjPic> result = new ResultVo<>();
		if (vo == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("参数不能为空！");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println(JsonUtil.toJsonString(result));
			return;
		}
		vo.setUserId(currentUser.getUserId());
		result = pictureService.selectThumbnailPath(vo.getPictureId());
		InputStream is = null;
		OutputStream os = null;
		try {
			try {
				is = new FileInputStream(result.getData().getPicUrl());
				response.setContentType("image/jpeg");
				// 设置页面不缓存
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				os = response.getOutputStream();// 取得响应输出流

				int count;
				byte[] buffer = new byte[1024 * 1024];
				while ((count = is.read(buffer)) != -1) {
					os.write(buffer, 0, count);
					os.flush();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: deleteActivePicById
	 * </p>
	 * <p>
	 * Description: 删除活动图片
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deleteActivePicById")
	@ResponseBody
	public ResultVo<Integer> deleteActivePicById(Long id) {
		ResultVo<Integer> result = new ResultVo<>();
		int count = pictureService.deleteActivePicById(id);
		if (count <= 0) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("删除失败");
		} else {
			result.setCode(ResultCode.OK.getCode());
			result.setMsg("删除成功");
		}
		return result;
	}

}
