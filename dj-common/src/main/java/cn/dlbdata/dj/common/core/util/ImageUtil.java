/**
 *  <p>Title: ImageUtil.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月28日 
 */
package cn.dlbdata.dj.common.core.util;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * <p>Title: ImageUtil</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年5月28日  
 */
public class ImageUtil {

	public static BufferedImage genPic(String content) throws Exception {
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

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
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
	public static BitMatrix deleteWhite(BitMatrix matrix) {
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
}
