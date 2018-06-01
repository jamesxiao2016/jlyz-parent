package cn.dlbdata.dj.web.controller.api.v1;

import java.awt.image.BufferedImage;
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
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Controller
@RequestMapping("/api/v1/picture")
public class PicController extends BaseController {

	private String PICTURE_PATH = ConfigUtil.get("rootPath");
	private final String PREVFIX = "thumbnail_";
	// 拼接请求地址
	private static String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

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
			result.setCode(ResultCode.Forbidden.getCode());
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
			thumbnailImage(PICTURE_PATH + path, 200, 200, PREVFIX, false);
			logger.info("thumbnailImage success");
		} catch (Exception e) {
			logger.error("保存图片失败", e);
			result.setMsg("保存图片失败");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		vo.setUserId(user.getUserId());
		vo.setPath(path);
		vo.setPictureId(picId);
		result = pictureService.insert(vo);

		return result;
	}

	
	/**
	 * 获取媒体文件
	 *
	 * @param mediaId
	 *            媒体文件id
	 * @param savePath
	 *            文件在本地服务器上的存储路径
	 */
	public String downloadMedia(Long picId, String mediaId, String rootPath, long userId) throws Exception {

		String filePath = null;
		String picturePath = null;
		String picPath = null;
		GetaAccessTokenParam getaAccessTokenParam = new GetaAccessTokenParam();
		// getaAccessTokenParam.setSecret("8d72463ffdf8a2232241985b442c1c93");
		// getaAccessTokenParam.setAppid("wxef4c83c01085bb38");
		getaAccessTokenParam.setAppid(ConfigUtil.get(DlbConstant.KEY_WX_APP_ID));
		getaAccessTokenParam.setSecret(ConfigUtil.get(DlbConstant.KEY_WX_SECRET));
		getaAccessTokenParam.setGrantType(GrantType.client_credential);
		String token = LocalCache.TICKET_CACHE.getIfPresent(DlbConstant.KEY_ACCESS_TOKEN);
		if (StringUtils.isEmpty(token)) {
			AccessTokenResponse accessTokenResponse = accessService.getAccessToken(getaAccessTokenParam);
			token = accessTokenResponse.getAccessToken();
			LocalCache.TICKET_CACHE.put(DlbConstant.KEY_ACCESS_TOKEN, token);
		}
		// 如果token为空
		if (StringUtils.isEmpty(token)) {
			logger.error("token获取为空");
			return "";
		}
		requestUrl = requestUrl.replace(DlbConstant.KEY_ACCESS_TOKEN, token).replace(DlbConstant.KEY_MEDIA_ID, mediaId);
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
			// 将mediaId作为文件名
			Calendar calendar = Calendar.getInstance();
			long date = System.currentTimeMillis();
			calendar.setTimeInMillis(date);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			picturePath = File.separator + userId + File.separator + year + File.separator + month + File.separator
					+ day + File.separator;
			picPath = picturePath + picId + fileExt;
			filePath = rootPath + picturePath + picId + fileExt;

			// 目录不存在，则创建目录
			File dir = new File(rootPath + picturePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			bis = new BufferedInputStream(conn.getInputStream());
			fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
		} finally {
			if (fos != null)
				fos.close();
			if (bis != null)
				bis.close();
			if (conn != null)
				conn.disconnect();

		}
		logger.info("下载媒体文件成功，filePath=" + filePath);

		return picPath;
	}

	/**
	 * <p>
	 * Title: thumbnailImage
	 * </p>
	 * <p>
	 * Description: 依据图片路径生成缩略图
	 * </p>
	 * 
	 * @param imagePath
	 *            原图片路径
	 * @param w
	 *            缩略图宽
	 * @param h
	 *            缩略图高
	 * @param prevfix
	 *            生成缩略图的前缀
	 * @param force
	 *            是否强制依照宽高生成缩略图(假设为false，则生成最佳比例缩略图)
	 */
	private void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force) {
		File imgFile = new File(imagePath);
		if (imgFile.exists()) {
			try {
				String p = imgFile.getCanonicalPath();
				File outFile = new File(p + "_thumbnail.jpg");
				compressPic(imgFile, outFile, w, h);

				logger.debug("缩略图在原路径下生成成功");
			} catch (Exception e) {
				logger.error("generate thumbnail image failed.", e);
			}
		} else {
			logger.warn("the image is not exist.");
		}
	}

	public boolean compressPic(File inputFile, File outputFile, int w, int h) {
		try {
			Thumbnails.Builder<File> fileBuilder = Thumbnails.of(inputFile).scale(1.0).outputQuality(1.0);
			BufferedImage src = fileBuilder.asBufferedImage();
			int height = src.getHeight();
			int width = src.getWidth();
			int square = width;
			if (width > height) {
				square = height;
			} else {
				square = width;
			}

			Thumbnails.of(inputFile).size(w, h).sourceRegion(Positions.CENTER, square, square).toFile(outputFile);
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
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
