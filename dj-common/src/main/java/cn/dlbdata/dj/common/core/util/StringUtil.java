package cn.dlbdata.dj.common.core.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {
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
			return sex;
		}

		String sexStr = cardId.substring(16, 17);
		int sexFlag = Integer.parseInt(sexStr);
		if (sexFlag % 2 == 0) {
			sex = "女";
		} else {
			sex = "男";
		}
		return sex;
	}
}
