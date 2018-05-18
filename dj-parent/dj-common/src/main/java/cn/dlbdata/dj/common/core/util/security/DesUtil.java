package cn.dlbdata.dj.common.core.util.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

public class DesUtil
{
	private static Logger logger = Logger.getLogger(DesUtil.class);
	// 密钥
	private static String secretKey = "b0326c4f1e0e2c2970584b14a5a36d1886b4b115";
	// 向量
	private static String iv = "abcdefgh";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	private DesUtil()
	{

	}

	private DesUtil(String secretKey, String iv)
	{
		if (secretKey != null)
		{
			DesUtil.secretKey = secretKey;
		}
		if (iv != null)
		{
			DesUtil.iv = iv;
		}
	}

	static DesUtil desUtil = null;

	public static DesUtil getInstance()
	{
		if (desUtil == null)
		{
			desUtil = new DesUtil();
		}

		return desUtil;
	}

	public static DesUtil getInstance(String secretKey, String iv)
	{
		if (desUtil == null)
		{
			desUtil = new DesUtil(secretKey, iv);
		}
		else
		{
			if (secretKey != null)
			{
				DesUtil.secretKey = secretKey;
			}
			if (iv != null)
			{
				DesUtil.iv = iv;
			}
		}

		return desUtil;
	}

	public static void setSecretKey(String secretKey, String iv)
	{
		if (secretKey != null)
		{
			DesUtil.secretKey = secretKey;
		}
		if (iv != null)
		{
			DesUtil.iv = iv;
		}
	}

	/**
	 * 3DES加密
	 * 
	 * @param plainText
	 *            普通文本
	 * @return
	 * @throws Exception
	 */
	public String encode(String plainText)
	{
		if (plainText == null || plainText.trim().length() == 0)
		{
			return null;
		}
		String result = null;
		try
		{
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			Key deskey = keyfactory.generateSecret(spec);
			if (deskey == null)
			{
				return null;
			}
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			result = Base64Util.encode(encryptData);
			if (result == null)
			{
				return null;
			}
			// TODO 将特殊符号进行替换
			result = result.replaceAll("\\+", ".").replaceAll("/", "_");
		}
		catch (Exception e)
		{
			logger.error("encode error", e);
			return null;
		}

		return result;
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public String decode(String encryptText)
	{
		if (encryptText == null || encryptText.trim().length() == 0)
		{
			return null;
		}
		// TODO 将./还原为+/
		encryptText = encryptText.replaceAll("\\.", "+").replaceAll("_", "/");
		String result = null;
		try
		{
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			Key deskey = keyfactory.generateSecret(spec);
			if (deskey == null)
			{
				return null;
			}
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));
			result = new String(decryptData, encoding);
		}
		catch (Exception e)
		{
			logger.error("decode error:" + encryptText);
			return encryptText;
		}

		return result;
	}

	public static void main(String[] args)
	{
		String encryptText = "xpS.3vVxkuA=";
		System.out.println(DesUtil.getInstance().decode(encryptText));
	}
}
