package cn.dlbdata.dangjian.common.core.util.security;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtils {
	public static byte[] EMPTY_BYTE = new byte[0];
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/** */
	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";
	/** */
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 64;
	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            要解密的数据
	 * @param key
	 *            私钥Key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
		if (data == null || data.length == 0 || privateKey == null) {
			return EMPTY_BYTE;
		}
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return partition(data, cipher);
	}

	/**
	 * 分割进行加密
	 * 
	 * @param data
	 * @param cipher
	 * @return
	 * @throws Exception
	 */
	private static byte[] partition(byte[] data, Cipher cipher) throws Exception {
		if (data == null || data.length == 0) {
			return EMPTY_BYTE;
		}
		int inputLen = data.length;
		int offSet = 0;
		byte[] cache;
		int count = 0;
		ByteArrayOutputStream out = null;
		byte[] encryptedData = EMPTY_BYTE;
		try {
			out = new ByteArrayOutputStream();
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				count++;
				offSet = count * MAX_ENCRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
		} finally {
			if (out != null) {
				out.close();
			}
		}

		return encryptedData;
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
		if (data == null || data.length == 0 || publicKey == null) {
			return EMPTY_BYTE;
		}
		// 对数据解密
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return partition(data, cipher);

	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            要加密的数据
	 * @param publicKey
	 *            公钥Key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
		if (data == null || data.length == 0 || publicKey == null) {
			return EMPTY_BYTE;
		}
		// 对数据加密
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return partition(data, cipher);
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            要加密的数据
	 * @param PrivateKey
	 *            私钥Key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
		if (data == null || data.length == 0 || privateKey == null) {
			return EMPTY_BYTE;
		}
		// 对数据加密
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return partition(data, cipher);
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param key
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64.decode(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// RSAPrivateCrtKey prk = (RSAPrivateCrtKey)privateKey;

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return partition(data, cipher);
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64.decode(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return partition(data, cipher);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 *            要加密的数据
	 * @param key
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = Base64.decode(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return partition(data, cipher);
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            要解密的数据
	 * @param key
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64.decode(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return partition(data, cipher);

	}

	/**
	 * <p>
	 * 数字签名
	 * </p>
	 * 
	 * @param content
	 *            已加密数据
	 * @param pprivateKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes());
			byte[] signed = signature.sign();
			return Base64.encode(signed); // 返回结果base64
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** */
	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean doCheck(String content, String sign, String publicKey) {
		String charset = "utf-8";
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(charset));
			boolean bverify = signature.verify(Base64.decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// *************************************为测试加入函数**********************************************************/

	/** */
	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/** */
	/**
	 * 
	 * <p>
	 * 获取私钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64.encode(key.getEncoded());
	}

	/** */
	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64.encode(key.getEncoded());
	}

	/**
	 * 转换PublicKey实例
	 * 
	 * @param modulusStr
	 * @param exponentStr
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String modulusStr, String exponentStr) throws Exception {
		BigInteger modulus = new BigInteger(modulusStr);
		BigInteger exponent = new BigInteger(exponentStr);
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(publicKeySpec);
	}

	/**
	 * 转换PrivateKey实例
	 * 
	 * @param modulusStr
	 * @param exponentStr
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String modulusStr, String exponentStr) throws Exception {

		BigInteger modulus = new BigInteger(modulusStr);
		BigInteger exponent = new BigInteger(exponentStr);
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(privateKeySpec);
	}

	// btye转换hex函数
	public static String ByteToHex(byte[] byteArray) {
		StringBuffer StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return StrBuff.toString();
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String publicExponentString = "65537";
	public static String N = "A4CA0B245186B6B89FB1DE0B1B5024904A1CB582FA7B442F24328154578CA135CA152CC5FAAB26333899F69F4A4F8F36C8023EDE40CAB298D2653093F46D0959";
	public static String D = "8711882263A46C9E555EBFE610A416E421AAF867FC110F29289D30FE45A861F557AE6027853AEFCD1D1782B48CBF7FC3537BCC9AB929C02597A39BDA6C3E1011";
	// D
	public static String privateString = "7074110484728605503039318450321474693815084435144517773806267317881222456683547229305982607580883291764454976307314933459037413631595416392372761502027793";
	// N = P*Q
	public static String modulusString = "8630712415765188280296513455306857922232425277399450105961638609974154851232936628738656365674332883922600323312414038878959307949013570791049563978139993";

	
	
	public static void main(String[] args) {

		try {
			PublicKey publicKey = getPublicKey(modulusString, publicExponentString);
			// System.out.println(privateString);
			// privateExponentString = string2HexString(D);
			// System.out.println(privateExponentString);
			// 由n和d获取私钥
			PrivateKey privateKey = getPrivateKey(modulusString, privateString);

			String txt = "12345678901234,12345678902222";

			System.out.println("明文 ：\r\n" + txt);
			byte[] data = txt.getBytes();
			byte[] encodedData = encryptByPublicKey(data, publicKey);
			System.out.println("加密后：\r\n" + ByteToHex(encodedData));
			//String str = "A43AA20E77C52478CEE5EDEBE3D98DDB098B93293927E499FD4FA63537F359A7563ABAFFC6609DC300D8FA23748B69A49F9A6B2E117326E266CC185C2A6C04E4";
			//str = "379F5DBF91ADAEF4A5EE2AC52571EA3417F01FC631BE9820C880690452D3A54FCBAFC17B198C7032FF96E8509A5556A1D4C7DD6A4A96ACCDDF78AD9D47990D42";
			//encodedData = hexStringToBytes(str);
			byte[] decodedData = decryptByPrivateKey(encodedData, privateKey);
			System.out.println("解密后: \r\n" + bytesToHexString(decodedData));
			System.out.println("解密后: \r\n" + new String(decodedData).trim());
			System.out.println(data.length + ":" + encodedData.length + ":" + decodedData.length);
			System.out.println("------------decryptByPublicKey-----------------");
			decodedData = decryptByPublicKey(encodedData, publicKey);
			System.out.println("解密后: \r\n" + bytesToHexString(decodedData));
			System.out.println("解密后: \r\n" + new String(decodedData).trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
