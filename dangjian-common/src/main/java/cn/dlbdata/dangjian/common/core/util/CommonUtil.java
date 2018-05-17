package cn.dlbdata.dangjian.common.core.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	/**
	 * 生成long类型ID
	 * 
	 * @return
	 */
	public static long generatorLongId() {
		return IdWorker.getInstance().nextId();
	}

	/**
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String generatorUuid() {
		return UUID.randomUUID().toString();
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

	public static void main(String[] args) {
		// System.out.println(getSexByCardId("310102197810150020"));
		for (int i = 0; i < 10000; i++) {
			System.out.println(generatorLongId());
		}
	}
}
