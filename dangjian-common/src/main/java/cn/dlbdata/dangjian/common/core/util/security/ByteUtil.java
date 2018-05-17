package cn.dlbdata.dangjian.common.core.util.security;

public class ByteUtil {
	/**
	 * 字节数组转16进制字符串
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 16进制字符串
	 */
	public static String bytes2HexString(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		String hex;
		for (int i = 0; i < bytes.length; i++) {
			hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result.append(hex.toUpperCase());
		}
		return result.toString();
	}

	/**
	 * 16进制字符串转字节数组
	 * 
	 * @param src
	 *            16进制字符串
	 * @return 字节数组
	 */
	public static byte[] hexString2Bytes(String str) {
		if (str == null) {
			return new byte[0];
		}
		int length = str.length() / 2;
		byte[] ret = new byte[length];
		for (int i = 0; i < length; i++) {
			ret[i] = (byte) Integer.valueOf(str.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return ret;
	}

	/**
	 * 字符串转16进制字符串
	 * 
	 * @param strPart
	 *            字符串
	 * @return 16进制字符串
	 */
	public static String string2HexString(String strPart) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0, count = strPart.length(); i < count; i++) {
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			hexString.append(strHex);
		}
		return hexString.toString();
	}

	/**
	 * 16进制字符串转字符串
	 * 
	 * @param src
	 *            16进制字符串
	 * @return 字节数组
	 */
	public static String hexString2String(String str) {
		if (str == null) {
			return "";
		}
		String temp = "";
		for (int i = 0, count = str.length(); i < count / 2; i++) {
			temp = temp + (char) Integer.valueOf(str.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return temp;
	}

	/**
	 * 字符转成字节数据char-->integer-->byte
	 * 
	 * @param src
	 * @return
	 */
	public static Byte char2Byte(Character src) {
		return Integer.valueOf((int) src).byteValue();
	}

	public static String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	/**
	 * 简化的string转16进制
	 * 
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}

	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * 
	 * @param hexStr
	 * @return
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}
}
