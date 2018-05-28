package cn.dlbdata.dj.web.base;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;

import com.google.gson.Gson;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.JsonUtil;
import cn.dlbdata.dj.common.core.util.JwtTokenUtil;
import cn.dlbdata.dj.common.core.util.cache.CacheManager;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.vo.UserVo;

@Controller
public class BaseController {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	private IUserService userService;

	/**
	 * 获取当前用户的信息
	 * 
	 * @return
	 */
	protected UserVo getCurrentUserFromCache() {
		// 从header中获取token
		String token = getHeader("token");

		// 从缓存中获取当前用户的信息
		UserVo currUser = getCacheUserByToken(token);

		return currUser;
	}

	/**
	 * 根据ID从缓存中获取用户信息
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	protected UserVo getCacheUserById(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		UserVo currUser = (UserVo) CacheManager.getInstance().get(id);
		return currUser;
	}

	/**
	 * 根据token获取当前用户信息
	 * 
	 * @param token
	 * @return
	 */
	protected UserVo getCacheUserByToken(String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		Map<String, String> tokenMap = JwtTokenUtil.getTokenInfo(token);
		if (tokenMap == null) {
			return null;
		}
		String userId = tokenMap.get(JwtTokenUtil.KEY_UID);
		UserVo currUser = (UserVo) CacheManager.getInstance().get(userId);
		// 如果缓存中获取失败，从数据库中查询
		if (currUser == null) {
			currUser = userService.getUserDetailById(DigitUtil.parseToLong(userId));
			CacheManager.getInstance().put(userId, currUser);
		}
		return currUser;
	}

	/**
	 * 根据key从header中获取值
	 * 
	 * @param key
	 * @return
	 */
	protected String getHeader(String key) {
		String header = request.getHeader(key);
		logger.info("getHeader=>" + key + "->" + header);
		return header;
	}

	/**
	 * 获取当前请求的根URL
	 * 
	 * @return
	 */
	protected String getRootUrl() {
		String contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		return contextpath;
	}

	/**
	 * 获取实际的地址
	 * 
	 * @return
	 */
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

	/**
	 * 打印request里面的参数及值
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	protected void printFormDatas(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		if (parameters != null) {
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				System.out.println(entry.getKey() + ":" + JsonUtil.toJsonString(entry.getValue()));
			}
		}
	}

}
