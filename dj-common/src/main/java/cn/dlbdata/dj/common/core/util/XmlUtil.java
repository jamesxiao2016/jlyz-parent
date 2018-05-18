package cn.dlbdata.dj.common.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 处理xml的工具类
 */
public class XmlUtil {
	// 日志器
	protected static final Logger logger = Logger.getLogger(XmlUtil.class);

	/**
	 * 将微信服务器发送的Request请求中Body的XML解析为Map
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseRequestXmlToMap(HttpServletRequest request) throws Exception {
		// 解析结果存储在HashMap中
		Map<String, String> resultMap = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		resultMap = parseInputStreamToMap(inputStream);
		return resultMap;
	}

	/**
	 * 将String类型的XML解析为Map
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXmlStringToMap(String str) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		resultMap = parseInputStreamToMap(inputStream);
		return resultMap;
	}

	/**
	 * 将输入流中的XML解析为Map
	 * 
	 * @param inputStream
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Map<String, String> parseInputStreamToMap(InputStream inputStream) throws DocumentException,
			IOException {
		// 解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		// 释放资源
		inputStream.close();
		return map;
	}

	/**
	 * 将XML格式数据转换成对象(集合)
	 * 
	 * @param xmlStr
	 *            XML数据
	 * @param obj
	 *            转换成的对象类型
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> fromXMLToList(String xmlStr, Object obj) {
		XStream xstream = new XStream(new DomDriver());

		xstream.alias(obj.getClass().getSimpleName(), obj.getClass());

		List<Object> list = (List<Object>) xstream.fromXML(xmlStr);

		return list;
	}

	/**
	 * 将XML格式数据转换成对象(集合)
	 * 
	 * @param xmlStr
	 *            XML数据
	 * @param cls
	 *            转换成的对象类型
	 * @return
	 */
	public static Object fromXMLToObject(String xmlStr, Object cls) {
		XStream xstream = new XStream(new DomDriver());

		xstream.alias(cls.getClass().getSimpleName(), cls.getClass());
		xstream.autodetectAnnotations(true);

		Object objRs = xstream.fromXML(xmlStr);

		return objRs;
	}

	/**
	 * 将对象转换成XML格式
	 * 
	 * @param obj
	 *            需要转换到数据对象
	 * @param cls
	 *            数据对象到类型
	 * @return
	 */
	public static String toXML(Object obj, Class<?>... classes) {
		XStream xstream = new XStream(new XppDriver());

		if (classes != null) {
			for (Class<?> cls : classes) {
				xstream.alias(cls.getSimpleName(), cls);
			}
		}

		String xmlStr = xstream.toXML(obj);

		return xmlStr;
	}

	/**
	 * 对象转Map
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map<Object, Object> convertToMap(Object bean) throws Exception {
		Class<?> type = bean.getClass();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object value = readMethod.invoke(bean, new Object[0]);
				Object key = "";// MapConstant.fieldMap.get(propertyName.toString());

				if (key != null) {
					if (value != null) {
						returnMap.put(key, value);
					} else {
						returnMap.put(key, "");
					}
				}

			}
		}
		return returnMap;
	}
}
