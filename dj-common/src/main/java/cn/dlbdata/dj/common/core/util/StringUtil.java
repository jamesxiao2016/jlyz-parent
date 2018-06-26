package cn.dlbdata.dj.common.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class StringUtil extends StringUtils {
	static final Logger logger = Logger.getLogger(ConfigUtil.class);

	/**
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String generatorUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成32位的uuid（不包含-）
	 * 
	 * @return
	 */
	public static String generatorShortUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 根据身份证号判断性别
	 * 
	 * @param cardId
	 * @return
	 */
	public static String getSexByCardId(String cardId) {
		String sex = "";
		if (StringUtils.isEmpty(cardId) || cardId.length() != 18) {
			logger.error("身份证格式不正确" + cardId);
			return sex;
		}

		String sexStr = cardId.substring(16, 17);
		int sexFlag = Integer.parseInt(sexStr);
		if (sexFlag % 2 == 0) {
			sex = "0";
		} else {
			sex = "1";
		}
		return sex;
	}

	/**
	 * 根据身份证号判断生日
	 * 
	 * @param cardId
	 * @return
	 * @throws ParseException
	 */
	public static Date getBirthdayByCardId(String cardId) {
		Date birthday = null;
		if (StringUtils.isEmpty(cardId) || cardId.length() != 18) {
			logger.error("身份证格式不正确->" + cardId);
			return birthday;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String birthStr = cardId.substring(6, 14);
		try {
			birthday = formatter.parse(birthStr);
		} catch (ParseException e) {
			logger.error("生日转换失败->" + birthStr);
			return birthday;
		}
		return birthday;
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @param max
	 *            最大长度
	 * @return
	 */
	public static String subString(String str, int max) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}

		int length = str.length();
		if (length > max) {
			return str.substring(0, max) + "...";
		}

		return str;
	}

}
