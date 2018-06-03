/**
 *  <p>Title: ImageUtil.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月28日 
 */
package cn.dlbdata.dj.common.core.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * <p>Title: ImageUtil</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年5月28日  
 */
public class ImageUtil {
	private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

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
	public static void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force) {
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

	public static boolean compressPic(File inputFile, File outputFile, int w, int h) {
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
	
	
	
	
	
	
	
	
}
