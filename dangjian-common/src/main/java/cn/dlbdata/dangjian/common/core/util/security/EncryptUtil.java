package cn.dlbdata.dangjian.common.core.util.security;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
	static byte[] PRESHARED_KEY_REQUEST = "PRESHARED_KEY_REQUEST".getBytes();
	static byte[] PRESHARED_KEY_RESPONSE = "PRESHARED_KEY_RESPONSE".getBytes();

	/**
	 * 加密byte数组
	 * 
	 * @param data
	 *            返回加密后的byte数组（包含iv)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(PRESHARED_KEY_RESPONSE, "AES"));
		// new SecretKeySpec(Base64.decode(PRESHARED_KEY_RESPONSE, Base64.DEFAULT),
		// "AES"));
		byte[] encrypted = cipher.doFinal(data);
		return byteMerger(cipher.getIV(), encrypted);
	}

	/**
	 * 解密byte数组
	 * 
	 * @param encrypted
	 *            需要解密的byte数组
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(PRESHARED_KEY_REQUEST, "AES"));
		// new SecretKeySpec(Base64.decode(PRESHARED_KEY_REQUEST, Base64.DEFAULT),
		// "AES"));
		byte[] decrypted = cipher.doFinal(encrypted);
		return Arrays.copyOfRange(decrypted, cipher.getBlockSize(), decrypted.length);
	}

	/**
	 * 将2个byte数组合并
	 * 
	 * @param byte1
	 * @param byte2
	 * @return
	 */
	public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
		byte[] result = new byte[byte1.length + byte2.length];
		System.arraycopy(byte1, 0, result, 0, byte1.length);
		System.arraycopy(byte2, 0, result, byte1.length, byte2.length);
		return result;
	}

}
