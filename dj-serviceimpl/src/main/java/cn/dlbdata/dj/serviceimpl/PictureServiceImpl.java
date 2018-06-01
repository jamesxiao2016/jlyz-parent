package cn.dlbdata.dj.serviceimpl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.ConfigUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjActivePicMapper;
import cn.dlbdata.dj.db.mapper.DjPicMapper;
import cn.dlbdata.dj.db.pojo.DjActivePic;
import cn.dlbdata.dj.db.pojo.DjPic;
import cn.dlbdata.dj.service.IPictureService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.AccessTokenResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GetaAccessTokenParam;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GrantType;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.AccessService;
import cn.dlbdata.dj.thirdparty.mp.sdk.util.CommonUtil;
import cn.dlbdata.dj.thirdparty.mp.sdk.util.LocalCache;
import cn.dlbdata.dj.vo.PicVo;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import tk.mybatis.mapper.entity.Example;

@Service
public class PictureServiceImpl extends BaseServiceImpl implements IPictureService {

	private String PICTURE_PATH = ConfigUtil.get("rootPath");
	private final String PREVFIX = "thumbnail_";
	// 拼接请求地址
	private static String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	@Autowired
	private AccessService accessService;
	@Autowired
	private DjActivePicMapper activePicMapper;
	@Autowired
	private DjPicMapper picMapper;

	@Override
	public ResultVo<Long> insert(PicVo vo) {
		ResultVo<Long> result = new ResultVo<>();

		String path;
		Long picId = DigitUtil.generatorLongId();
		try {
			path = downloadMedia(picId, vo.getMediaId(), PICTURE_PATH, vo.getUserId());
			logger.info("downloadMedia success");
			thumbnailImage(path, 200, 200, PREVFIX, false);
			logger.info("thumbnailImage success");
		} catch (Exception e) {
			logger.error("保存图片失败", e);
			result.setMsg("保存图片失败");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		DjPic pic = new DjPic();
		pic.setId(picId);
		pic.setCreateTime(new Date());
		pic.setPicUrl(path);
		pic.setPicName(picId + "");
		pic.setStatus(1);
		int count = picMapper.insert(pic);
		if (count > 0) {
			long pictureId = pic.getId();
			DjActivePic record = new DjActivePic();
			record.setId(DigitUtil.generatorLongId());
			record.setDjActiveId(vo.getActiveId());
			record.setCreateTime(new Date());
			record.setDjPicId(pictureId);
			record.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
			record.setDjUserId(vo.getUserId());
			int res = activePicMapper.insert(record);
			if(res > 0) {
				result.setData(pictureId);
				result.setMsg("上传图片成功！");
				result.setCode(ResultCode.OK.getCode());
			}
			result.setCode(ResultCode.Forbidden.getCode());
		} else {
			result.setCode(ResultCode.Forbidden.getCode());
		}
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
			int month = calendar.get(Calendar.MONTH + 1);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			picturePath = File.separator + userId + File.separator + year + File.separator + month + File.separator
					+ day + File.separator + picId + fileExt;
			filePath = rootPath + picturePath;

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
		return picturePath;
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
	 * 获取缩略图的路径
	 */
	@Override
	public ResultVo<DjPic> selectThumbnailPath(long id) {
		ResultVo<DjPic> result = new ResultVo<>();
		DjPic pic = picMapper.selectByPrimaryKey(id);
		if (pic == null) {
			logger.info("picture is null" + id);
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关图片");
			return result;
		}
		String path = pic.getPicUrl();
		logger.info("imgPath = " + path);
		File imgFile = new File(PICTURE_PATH + path);
		String thumbnailPath = imgFile + "_thumbnail.jpg";
		if (!new File(thumbnailPath).exists()) {
			thumbnailImage(PICTURE_PATH + thumbnailPath, 200, 200, PREVFIX, false);
		}
		pic.setPicUrl(thumbnailPath);
		result.setCode(ResultCode.OK.getCode());
		result.setData(pic);

		return result;
	}

	/*
	 * (non-Javadoc) <p>Title: deleteActivePicById</p> <p>Description: 删除活动图片</p>
	 * 
	 * @param djActiveId, djPicId
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IPictureService#deleteActivePicById(java.lang.Long)
	 */
	@Override
	public int deleteActivePicById(Long djActiveId, Long djPicId) {
		if (djActiveId == null || djPicId == null) {
			return 0;
		}
		Example example = new Example(DjActivePic.class);
		example.createCriteria().andEqualTo("djActiveId", djActiveId).andEqualTo("djPicId", djPicId);
		int count = activePicMapper.deleteByExample(example);
		if (count > 0) {
			count = picMapper.deleteByPrimaryKey(djPicId);
		} else {
			return 0;
		}
		return count;
	}

}
