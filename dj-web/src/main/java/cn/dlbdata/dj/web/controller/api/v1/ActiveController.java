package cn.dlbdata.dj.web.controller.api.v1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.dto.ActiveSignUpRequest;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.service.IActiveUserService;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.PageVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 处理活动相关的controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1/active")
public class ActiveController extends BaseController {

	@Autowired
	private IActiveService activeService;
	@Autowired
	private IActiveUserService activeUserService;

	/**
	 * 党员生活通知接口
	 * <p>
	 * Title: getParticipateActive
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param partyMemberLifeNotice
	 * @return
	 */
	@GetMapping(value = "/getParticipateActive")
	@ResponseBody
	public PageVo<List<Map<String, Object>>> getParticipateActive(PartyMemberLifeNotice partyMemberLifeNotice) {
		PageVo<List<Map<String, Object>>> result = new PageVo<>();
		UserVo data = getCurrentUserFromCache();
		partyMemberLifeNotice.setUserId(data.getUserId());
		partyMemberLifeNotice.setDepartmentId(data.getDeptId());
		result = activeService.getParticipateActive(partyMemberLifeNotice);
		if (result.getData() == null || result.getData().isEmpty()) {
			result.setCode(ResultCode.Forbidden.getCode());
		} else {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}

	/**
	 * 金领驿站活动报名
	 * <p>
	 * Title: participate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param activeId
	 * @return
	 */
	@PostMapping(value = "/participate")
	@ResponseBody
	public ResultVo<String> participate(@RequestBody ActiveSignUpRequest activeSignUpRequest) {
		ResultVo<String> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		activeSignUpRequest.setUserId(data.getUserId());
		result = activeUserService.insertActiveSignUp(activeSignUpRequest);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: getEnjoyActiveByUserId
	 * </p>
	 * <p>
	 * Description:查看自己参加的活动
	 * </p>
	 * 
	 * @param userId
	 * @param all
	 * @return
	 */
	@GetMapping(value = "/getEnjoyActiveByUserId")
	@ResponseBody
	public ResultVo<List<DjActive>> getEnjoyActiveByUserId() {
		ResultVo<List<DjActive>> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		DjActiveUser djActiveUser = new DjActiveUser();
		djActiveUser.setStatus(1);
		List<DjActive> list = activeUserService.getMyJoinActive(data.getUserId(), djActiveUser.getStatus());
		if (list == null || list.size() == 0) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("数据为空");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}

	/**
	 * 
	 * <p>Title: showQrCode</p> 
	 * <p>Description:显示二维码 </p> 
	 * @param activeId
	 * @param response
	 */
	@GetMapping(value = "/showQrCode")
	public void showQrCode(Long activeId, HttpServletResponse response) {
		String content = "http://dj.dlbdata.cn/#/active/activeSign/" + activeId;
		OutputStream out = null;
		BufferedImage image;
		try {
			image = genPic(content);
			response.setContentType("image/jpeg");
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			out = response.getOutputStream();// 取得响应输出流
			ImageIO.write(image, "JPEG", out);
			out.flush();
		} catch (Exception e) {
//			log.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {

				}
			}
		}
		
	}
	
	private BufferedImage genPic(String content) throws Exception {
		// int qr_size = 400;
		// int qr_size = 213;
		int qr_size = 150;
		Object errorCorrectionLevel = ErrorCorrectionLevel.H;
		Map hints = new HashMap();
		hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
		// hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1);
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, qr_size, qr_size, hints);
		BufferedImage image = toBufferedImage(deleteWhite(bitMatrix));
		return image;
	}

	private BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		return image;
	}
	
	/**
	 * 删除白边
	 */
	private static BitMatrix deleteWhite(BitMatrix matrix) {
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;

		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++) {
			for (int j = 0; j < resHeight; j++) {
				if (matrix.get(i + rec[0], j + rec[1]))
					resMatrix.set(i, j);
			}
		}
		return resMatrix;
	}
	

	@PostMapping(value = "/create")
	@ResponseBody
	public ResultVo<Long> createActive(ActiveVo vo)
	{
		ResultVo<Long> result = new ResultVo<>();
		
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		result = activeService.createActive(vo, user);
		
		return result;
	}
}
