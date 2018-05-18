package cn.dlbdata.dj.common.core.util.security;

import java.security.MessageDigest;

public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String encode(String str) {
		return encode(str, "UTF-8");
	}

	public static String encode(String str, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static void main(String[] args)
	{
		String str = "106:192.168.0.166:123456"; //ha1
		String str1 = "106@192.168.0.166:192.168.0.166:123456"; //ha1b
		System.out.println(encode(str));
		System.out.println(encode(str1));
		//System.out.println("329fc383fe43cca5c20cd7f29a7f5485");
		//System.out.println(encode(str).equals("329fc383fe43cca5c20cd7f29a7f5485"));
	}
}
