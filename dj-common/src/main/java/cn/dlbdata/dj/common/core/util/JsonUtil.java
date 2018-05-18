package cn.dlbdata.dj.common.core.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	private static Gson gson = new Gson();

	/**
	 * @MethodName : toJson
	 * @Description : 将对象转为JSON串，此方法能够满足大部分需求
	 * @param src
	 *            :将要被转化的对象
	 * @return :转化后的JSON串
	 */
	public static String toJsonString(Object src) {
		if (src == null) {
			return gson.toJson(JsonNull.INSTANCE);
		}
		return gson.toJson(src);
	}

	public static <T> T toObject(String jsonStr, Class<T> cls) {
		if (StringUtils.isEmpty(jsonStr)) {
			return null;
		}
		return gson.fromJson(jsonStr, cls);
	}

	public static Map<String, Object> toMapObject(String jsonStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(jsonStr)) {
			return result;
		}
		result = (Map<String, Object>) JSONObject.parse(jsonStr);

		return result;
	}

	public static <T> T[] toArray(String jsonStr, Class<T[]> cls) {
		if (StringUtils.isEmpty(jsonStr)) {
			return null;
		}
		T[] array = gson.fromJson(jsonStr, cls);
		return array;
	}

	/**
	 * @param json
	 * @param clazz
	 * @return
	 */
	// public static <T> List<T> jsonToList(String jsonStr, Class<T[]> clazz) {
	// Gson gson = new Gson();
	// T[] array = gson.fromJson(jsonStr, clazz);
	// return Arrays.asList(array);
	// }

	/**
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T> clazz) {
		Type type = new TypeToken<List<JsonObject>>() {
		}.getType();
		List<JsonObject> jsonObjects = new Gson().fromJson(json, type);

		List<T> arrayList = new ArrayList<>();
		for (JsonObject jsonObject : jsonObjects) {
			arrayList.add(new Gson().fromJson(jsonObject, clazz));
		}
		return arrayList;
	}

	public static void main(String[] args) {
		String str = "{\"applyStatus\":\"1\",\"deptId\":7,\"roleId\":[2,4],\"orderBy\":\"create_Time desc\"}";
		Map<String, Object> map = toMapObject(str);
		System.out.println(map.get("roleId").getClass().getName());
		for (Map.Entry<String, Object> m : map.entrySet()) {
			String key = m.getKey();
			Object value = m.getValue();
			if (value instanceof com.alibaba.fastjson.JSONArray) {
				List<Object> list = (List<Object>) com.alibaba.fastjson.JSONArray.parse(map.get(key).toString());
				
				System.out.println(list.size());
				map.put(key, list);
				System.out.println(map.get("roleId").getClass().getName());
			}
		}
		System.out.println(map.toString());
	}
}
