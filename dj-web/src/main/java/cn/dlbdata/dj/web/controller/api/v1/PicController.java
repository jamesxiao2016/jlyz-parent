package cn.dlbdata.dj.web.controller.api.v1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.ConfigUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.ImageUtil;
import cn.dlbdata.dj.common.core.util.JsonUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.pojo.DjPic;
import cn.dlbdata.dj.service.IPictureService;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.AccessTokenResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GetaAccessTokenParam;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GrantType;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.AccessService;
import cn.dlbdata.dj.thirdparty.mp.sdk.util.CommonUtil;
import cn.dlbdata.dj.thirdparty.mp.sdk.util.LocalCache;
import cn.dlbdata.dj.vo.DjActivePicVo;
import cn.dlbdata.dj.vo.PicVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/api/v1/picture")
public class PicController extends BaseController {

	private String PICTURE_PATH = ConfigUtil.get("rootPath");
	private final String PREVFIX = "thumbnail_";
	// 拼接请求地址
	private static String REQUEST_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	// 重试次数
	private static int RETRY_NUM = 3;
	@Autowired
	private AccessService accessService;
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
		logger.info("upload->" + JsonUtil.toJsonString(vo));
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			result.setMsg("请重新登录");
			return result;
		}
		if (vo == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("参数不能为空");
			return result;
		}
		String path;
		Long picId = DigitUtil.generatorLongId();

		try {
			path = downloadMedia(picId, vo.getMediaId(), PICTURE_PATH, user.getUserId());
			logger.info("downloadMedia success");
			ImageUtil.thumbnailImage(PICTURE_PATH + path, 200, 200, PREVFIX, false);
			logger.info("thumbnailImage success");
		} catch (Exception e) {
			getAccessToken();

			try {
				// 失败后重新调用一次
				path = downloadMedia(picId, vo.getMediaId(), PICTURE_PATH, user.getUserId());
				logger.info("downloadMedia success");
				ImageUtil.thumbnailImage(PICTURE_PATH + path, 200, 200, PREVFIX, false);
				logger.info("thumbnailImage success");
			} catch (Exception ee) {
				logger.error("保存图片失败", e);
				result.setMsg("保存图片失败");
				result.setCode(ResultCode.Forbidden.getCode());
				return result;
			}
		}

		vo.setUserId(user.getUserId());
		vo.setPath(path);
		vo.setPictureId(picId);
		result = pictureService.insert(vo);

		return result;
	}

	int retryCount = 0;

	/**
	 * 获取媒体文件
	 *
	 * @param mediaId
	 *            媒体文件id
	 * @param savePath
	 *            文件在本地服务器上的存储路径
	 */
	public synchronized String downloadMedia(Long picId, String mediaId, String rootPath, Long userId) {
		if (picId == null) {
			picId = DigitUtil.generatorLongId();
		}
		if (mediaId == null || mediaId == "") {
			logger.error("mediaId为空");
			return "";
		}
		if (userId == null) {
			logger.error("没有用户id");
			return "";
		}
		retryCount = 0;
		String token = LocalCache.TICKET_CACHE.getIfPresent(DlbConstant.KEY_ACCESS_TOKEN);
		if (StringUtils.isEmpty(token)) {
			token = getAccessToken();
		}
		// 如果token为空
		if (StringUtils.isEmpty(token)) {
			logger.error("token获取为空");
			return "";
		}

		String picPath = requestInputStream(token, picId, mediaId, rootPath, userId);

		return picPath;
	}

	/**
	 * 请求微信接口下载文件
	 * 
	 * @param picId
	 * @param mediaId
	 * @param rootPath
	 * @param userId
	 * @return
	 */
	private String requestInputStream(String token, Long picId, String mediaId, String rootPath, Long userId) {
		String filePath = null;

		String requestUrl = REQUEST_URL.replace(DlbConstant.KEY_ACCESS_TOKEN, token).replace(DlbConstant.KEY_MEDIA_ID,
				mediaId);
		HttpURLConnection conn = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			// 根据内容类型获取扩展名
			String fileExt = CommonUtil.getFileExt(conn.getHeaderField("Content-Type"));
			// 错误的代码 凑活一下！！！！
			if (fileExt.isEmpty()) {
				fileExt = ".jpg";
			}

			// 处理下载目录路径
			String picPath = getPictureDir(rootPath, userId, picId, fileExt);
			filePath = rootPath + picPath;

			int responseCode = conn.getResponseCode();
			logger.info("responseCode->" + responseCode);
			// 请求成功
			if (responseCode == 200) {
				bis = new BufferedInputStream(conn.getInputStream());
				logger.info("InputStream-Count->" + bis.available());
				fos = new FileOutputStream(new File(filePath));
				byte[] buf = new byte[4096];
				int size = 0;
				while ((size = bis.read(buf)) != -1) {
					fos.write(buf, 0, size);
				}
				logger.info("下载媒体文件成功，filePath=" + filePath);
				return picPath;
			} else {// 请求失败，重新处理
				//
				token = getAccessToken();
				if (StringUtils.isNotEmpty(token)) {
					if (retryCount <= RETRY_NUM) {
						requestInputStream(token, picId, mediaId, rootPath, userId);
						retryCount++;
					}
				}
			}
		} catch (Exception e) {
			token = getAccessToken();
			if (StringUtils.isNotEmpty(token)) {
				if (retryCount <= RETRY_NUM) {
					requestInputStream(token, picId, mediaId, rootPath, userId);
					retryCount++;
				}
			}
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					// 忽略此异常
				}
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					// 忽略此异常
				}
			if (conn != null)
				conn.disconnect();
		}

		return "";
	}

	/**
	 * 获取access_token
	 * <p>
	 * Title: getAccessToken
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	private String getAccessToken() {
		String token = "";
		GetaAccessTokenParam getaAccessTokenParam = new GetaAccessTokenParam();
		// getaAccessTokenParam.setSecret("8d72463ffdf8a2232241985b442c1c93");
		// getaAccessTokenParam.setAppid("wxef4c83c01085bb38");
		getaAccessTokenParam.setAppid(ConfigUtil.get(DlbConstant.KEY_WX_APP_ID));
		getaAccessTokenParam.setSecret(ConfigUtil.get(DlbConstant.KEY_WX_SECRET));
		getaAccessTokenParam.setGrantType(GrantType.client_credential);
		try {
			AccessTokenResponse accessTokenResponse = accessService.getAccessToken(getaAccessTokenParam);
			token = accessTokenResponse.getAccessToken();
			LocalCache.TICKET_CACHE.put(DlbConstant.KEY_ACCESS_TOKEN, token);
		} catch (Exception e) {
			logger.error("获取AccessToken失败", e);
		}

		return token;
	}

	/**
	 * 获取下载目录的路径，目录不存在，则创建；目录按用户ID/年/月/日进行存放
	 * 
	 * @param rootPath
	 * @param userId
	 * @param picId
	 * @param fileExt
	 * @return
	 */
	private String getPictureDir(String rootPath, Long userId, Long picId, String fileExt) {
		// 将mediaId作为文件名
		Calendar calendar = Calendar.getInstance();
		long date = System.currentTimeMillis();
		calendar.setTimeInMillis(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String picturePath = File.separator + userId + File.separator + year + File.separator + month + File.separator
				+ day + File.separator;

		String picPath = picturePath + picId + fileExt;
		// 目录不存在，则创建目录
		File dir = new File(rootPath + picturePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return picPath;
	}

	/**
	 * 显示原图
	 * 
	 * @param vo
	 * @return
	 */
	@GetMapping(value = "/show")
	public void show(PicVo vo, HttpServletResponse response) {
		logger.info("showThumbnail->" + JsonUtil.toJsonString(vo));
		ResultVo<DjPic> result = new ResultVo<>();
		if (vo == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("参数不能为空！");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.println(JsonUtil.toJsonString(result));
			return;
		}
		result = pictureService.getPicInfoById(vo.getPictureId());
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(PICTURE_PATH + result.getData().getPicUrl());
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
				try {
					is.close();
				} catch (IOException e) {
					// 忽略此异常
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// 忽略此异常
				}
			}
		}

	}

	/**
	 * 显示缩略图
	 * 
	 * @param vo
	 * @return
	 */
	@GetMapping(value = "/showThumbnail")
	public void showThumbnail(PicVo vo, HttpServletResponse response) {
		logger.info("showThumbnail->" + JsonUtil.toJsonString(vo));
		ResultVo<DjPic> result = new ResultVo<>();
		if (vo == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("参数不能为空！");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.println(JsonUtil.toJsonString(result));
			return;
		}
		result = pictureService.selectThumbnailPath(vo.getPictureId());
		InputStream is = null;
		OutputStream os = null;
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
				try {
					is.close();
				} catch (IOException e) {
					// 忽略此异常
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// 忽略此异常
				}
			}
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
	@PostMapping(value = "/deleteActivePicById")
	@ResponseBody
	public ResultVo<Integer> deleteActivePicById(@RequestBody DjActivePicVo vo) {
		logger.info("deleteActivePicById->" + JsonUtil.toJsonString(vo));
		ResultVo<Integer> result = new ResultVo<>();
		int count = pictureService.deleteActivePicById(vo.getDjActiveId(), vo.getDjPicId());
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
