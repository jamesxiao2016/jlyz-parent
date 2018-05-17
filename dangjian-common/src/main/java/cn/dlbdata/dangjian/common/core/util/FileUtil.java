package cn.dlbdata.dangjian.common.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;

public class FileUtil {

	/**
	 * 写入文件（文件有读写和可执行的权限）
	 * 
	 * @param path
	 *            文件路径
	 * @param content
	 *            写入的内容
	 */
	public static void writeToFile(String dirPath, String fileName, String content) {
		FileWriter fw = null;
		try {
			File parent = new File(dirPath);
			if (!parent.exists()) {
				parent.mkdirs();
				parent.setWritable(true, false);
			}
			File file = new File(dirPath, fileName);
			if (!file.exists()) {
				file.createNewFile();
				file.setWritable(true, false);
				file.setExecutable(true, false);
			} else {
				if (!file.canWrite()) {
					file.setWritable(true, false);
				}
				if (!file.canExecute()) {
					file.setExecutable(true, false);
				}
			}

			fw = new FileWriter(file.getCanonicalPath());
			fw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 获取文件md5
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMd5Value(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static final int BUFSIZE = 1024 * 8;

	@SuppressWarnings("resource")
	public static void mergeFiles(String outFile, String[] files) throws Exception {
		FileChannel outChannel = null;
		try {
			outChannel = new FileOutputStream(outFile).getChannel();
			for (String f : files) {
				Charset charset = Charset.forName("utf-8");
				CharsetDecoder chdecoder = charset.newDecoder();
				CharsetEncoder chencoder = charset.newEncoder();
				FileChannel fc = new FileInputStream(f).getChannel();
				ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
				CharBuffer charBuffer = chdecoder.decode(bb);
				ByteBuffer nbuBuffer = chencoder.encode(charBuffer);
				while (fc.read(nbuBuffer) != -1) {
					bb.flip();
					nbuBuffer.flip();
					outChannel.write(nbuBuffer);
					bb.clear();
					nbuBuffer.clear();
				}
				fc.close();
			}
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
			}
		}
	}

}
