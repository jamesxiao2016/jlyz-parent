package cn.dlbdata.dangjian.web.controller.base;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.google.gson.Gson;

import cn.dlbdata.dangjian.common.core.util.JsonUtil;

public class BaseController {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;

//	protected AccountVo getCurrentUser() {
//		String token = getHeader("token");
//		AccountVo currUser = getCacheUserByToken(token);
//
//		return currUser;
//	}
//
//	protected AccountVo getCacheUserById(String id) {
//		if (StringUtils.isEmpty(id)) {
//			return null;
//		}
//		AccountVo currUser = (AccountVo) CacheUtil.get(id);
//		return currUser;
//	}
//
//	protected AccountVo getCacheUserByToken(String token) {
//		if (StringUtils.isEmpty(token)) {
//			return null;
//		}
//		AccountVo vo = JwtTokenUtil.getTokenInfo(token);
//		if (vo == null) {
//			return null;
//		}
//		AccountVo currUser = (AccountVo) CacheUtil.get(vo.getId() + "");
//		return currUser;
//	}

	/**
	 * 客户端返回JSON字符串
	 * 
	 * @param response
	 * @param object
	 * @return
	 */
	protected String getClientUuid() {
		return getHeader("client_uuid");
	}

	protected String getHeader(String key) {
		String header = request.getHeader(key);
		logger.info("getHeader=>" + key + "->" + header);
		return header;
	}

	protected String getRootUrl() {
		String contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		return contextpath;
	}

	protected String getRealDirPath() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
	}

	/**
	 * 客户端返回JSON字符串
	 * 
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, new Gson().toJson(object), "application/json");
	}

	/**
	 * 客户端返回字符串
	 * 
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			// 解决跨域问题
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected void printFormDatas(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		if (parameters != null) {
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				System.out.println(entry.getKey() + ":" + JsonUtil.toJsonString(entry.getValue()));
			}
		}
	}

	/**
	 * 设置本地语言
	 * 
	 * @param lang
	 */
	protected void setLocal(String lang) {
		if (StringUtils.isEmpty(lang)) {
			Locale locale = (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
			if (locale != null) {
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			} else {
				locale = new Locale("zh", "CN");
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			}
		} else {
			if ("en".equals(lang)) {
				Locale locale = new Locale("en", "US");
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			} else {
				Locale locale = new Locale("zh", "CN");
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			}
		}
	}
}
