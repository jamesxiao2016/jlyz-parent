package cn.dlbdata.dangjian.common.core.util.security;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSACryptography {
	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 53;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}
	// public static String modulusString =
	// "95701876885335270857822974167577168764621211406341574477817778908798408856077334510496515211568839843884498881589280440763139683446418982307428928523091367233376499779842840789220784202847513854967218444344438545354682865713417516385450114501727182277555013890267914809715178404671863643421619292274848317157";
	// public static String privateExponentString =
	// "15118200884902819158506511612629910252530988627643229329521452996670429328272100404155979400725883072214721713247384231857130859555987849975263007110480563992945828011871526769689381461965107692102011772019212674436519765580328720044447875477151172925640047963361834004267745612848169871802590337012858580097";
	// E
	public static String publicExponentString = "65537";
	public static String N = "A4CA0B245186B6B89FB1DE0B1B5024904A1CB582FA7B442F24328154578CA135CA152CC5FAAB26333899F69F4A4F8F36C8023EDE40CAB298D2653093F46D0959";
	public static String D = "8711882263A46C9E555EBFE610A416E421AAF867FC110F29289D30FE45A861F557AE6027853AEFCD1D1782B48CBF7FC3537BCC9AB929C02597A39BDA6C3E1011";
	// D
	public static String privateString = "7074110484728605503039318450321474693815084435144517773806267317881222456683547229305982607580883291764454976307314933459037413631595416392372761502027793";
	// N = P*Q
	public static String modulusString = "8630712415765188280296513455306857922232425277399450105961638609974154851232936628738656365674332883922600323312414038878959307949013570791049563978139993";

	// 将base64编码后的公钥字符串转成PublicKey实例
	public static PublicKey getPublicKey(String modulusStr, String exponentStr) throws Exception {
		BigInteger modulus = new BigInteger(modulusStr);
		BigInteger exponent = new BigInteger(exponentStr);
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(publicKeySpec);
	}

	// 将base64编码后的私钥字符串转成PrivateKey实例
	public static PrivateKey getPrivateKey(String modulusStr, String exponentStr) throws Exception {

		BigInteger modulus = new BigInteger(modulusStr);
		BigInteger exponent = new BigInteger(exponentStr);
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(privateKeySpec);
	}

	// 公钥加密
	public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	// 私钥解密
	public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
		// return cipher.doFinal(content);
	}

	public static void main(String[] args) {
		String data = "{\r\n" + "\"imei\": \"12\",\r\n" + "\"sw_ver\": \"34\",\r\n" + "\"sn\": \"56\",\r\n"
				+ "\"iccid\": \"78\",\r\n" + "\"hw_ver\": \"34\",\r\n" + "\"prod_id\": \"910\",\r\n"
				+ "\"cust_id\": \"1112\",\r\n" + "\"batch\": \"1314\",\r\n" + "\"ext\": \"1516\",\r\n"
				+ "\"remark\": \"\",\r\n" + "\"mcc\": \"460\",\r\n" + "\"mnc\": \"00\",\r\n" + "\"tac\": \"12345\",\r\n"
				+ "\"cid\": \"65536\",\r\n" + "\"dbm\": \"-70\"\r\n" + "}";
		data = "hello";

		try {
			System.out.println(modulusString);
			// modulusString = string2HexString(N);
			// System.out.println(modulusString);
			// 由n和e获取公钥
			PublicKey publicKey = getPublicKey(modulusString, publicExponentString);

			System.out.println(privateString);
			// privateExponentString = string2HexString(D);
			// System.out.println(privateExponentString);
			// 由n和d获取私钥
			PrivateKey privateKey = getPrivateKey(modulusString, privateString);

			// 公钥加密
			byte[] encryptedBytes = encrypt(data.getBytes(), publicKey);
			String str = bytes2HexString(encryptedBytes);
			System.out.println("加密后：" + str);
			System.out.println("加密后："
					+ "9DCB63911950FA6D5CBCFB7599585250DD1CDD7C797F87454D073B42830C78DC9EFAD73E9F47CA6B0473E866114F140E755F40C1B331E6DC19EF2DAE8E714179");
			for (byte b : encryptedBytes) {
				System.out.print(b + "|");
			}
			System.out.println();
			// System.out.println("加密后：" + bytes2HexString(encryptedBytes));

			// String s =
			// "5BA8E20975BD11CC7DAC2D100870E9581EE2AABD360D67B7B1A10C7FB309BDF5D75FB78F2FD227B73B7F81D5335A829F9F62B5014976152EF8ABF8138D4A1354"
			// +
			// "680BDFF57B0152A60A201F5A164F955930DB8F2EEDCCEE451CE18B7740CFEC70EA7684859C9E38BB6B4C84F94A4AC66ABFA6E2BF05FC54EB67897B7817E92D77"
			// +
			// "5735A82D7A3B53CD3E9380B74D75C65DE5AB7DE0495AB034B2B6A64DA0A6748A85A52D5B174CAE9737371DEB420A0CF477685D5B8FEF2533E0D069105FBCF5FE"
			// +
			// "52C777D990DAEBB2E9D607FF7D6BB5F088955325AD3226C50ACB39AD80603EB570E6E216B019141AD587356AA2897A011FF8B62E0DF8DC75BFA6158A94937024"
			// +
			// "4AE28CDE8063130649BC53527E13D3A483177086A4A026A738723E87239F1DB3788E18D17F9F53BFB9A6BA6D5EF2C77033E5678197B83979750701AEF82DF62A"
			// +
			// "3CDE02E7E3B2155490AF766C30F70C08C8435E17A9F944B9A8D225D4971425DE18EA189614CF0A420CBAB9A3A84EAB5CF7C215906F182DF5964B8F840D933D89";
			// String s =
			// "5BA8E20975BD11CC7DAC2D100870E9581EE2AABD360D67B7B1A10C7FB309BDF5D75FB78F2FD227B73B7F81D5335A829F9F62B5014976152EF8ABF8138D4A1354";
			// byte[] encryptedBytes2 = hexString2Bytes(s);
			// str =
			// "9DCB63911950FA6D5CBCFB7599585250DD1CDD7C797F87454D073B42830C78DC9EFAD73E9F47CA6B0473E866114F140E755F40C1B331E6DC19EF2DAE8E714179";
			//str = "08083A11452EE7F39D2F3EF88FA93EC0C4DCD7A7081C01E6400F553A675B41C2E4AE4192C9FFBFC4B2F6283DE3C2844C68D5649956B52DF424AF171D0742D18A";
			byte[] encryptedByte = hexString2Bytes(str);
			// 私钥解密
			byte[] decryptedBytes = decrypt(encryptedByte, privateKey);
			System.out.println("解密后：" + new String(decryptedBytes));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @Title:bytes2HexString @Description:字节数组转16进制字符串 @param b 字节数组 @return
	 *                        16进制字符串 @throws
	 */
	public static String bytes2HexString(byte[] b) {
		StringBuffer result = new StringBuffer();
		String hex;
		for (int i = 0; i < b.length; i++) {
			hex = Integer.toHexString(b[i] & 0xFF);
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
	public static byte[] hexString2Bytes(String src) {
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
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
		for (int i = 0; i < strPart.length(); i++) {
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
	public static String hexString2String(String src) {
		String temp = "";
		for (int i = 0; i < src.length() / 2; i++) {
			temp = temp + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
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

}
