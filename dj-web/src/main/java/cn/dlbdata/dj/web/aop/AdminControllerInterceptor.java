package cn.dlbdata.dj.web.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.dlbdata.dj.common.core.bean.JqGridBean;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.JwtTokenUtil;
import cn.dlbdata.dj.common.core.util.cache.CacheManager;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.util.security.MD5Util;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.vo.TokenVo;

@Component
@Aspect
public class AdminControllerInterceptor {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("execution(public * cn.dlbdata.dj.web.controller.admin.*.*(..)) || execution(public * cn.dlbdata.dj.web.base.ComponentController.query*(..))")
	public void checkToken() {
	}

	@Before("checkToken()")
	public void beforeCheckToken() {
	}

	@AfterReturning("checkToken()")
	public void afterCheckToken() {
	}

	// 抛出异常时才调用
	@AfterThrowing("checkToken()")
	public void afterThrowing() {
	}

	@Around("checkToken()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {

		// 日志实体对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Object result = pjp.proceed();
		String requestUrl = request.getRequestURI();
		logger.info("requestUrl->" + requestUrl);

		if (result instanceof ResultVo) {
			// 获取当前登陆用户信息
			UserVo currUser = getCacheUserByToken(request);
			if (currUser == null) {
				ResultVo<?> rs = (ResultVo<?>) result;
				rs.setCode(ResultCode.NOT_LOGIN.getCode());
				rs.setMsg("用户未登录或已过期，请重新登录");
				return rs;
			}
		} else if (result instanceof JqGridBean) {
			// 获取当前登陆用户信息
			UserVo currUser = getCacheUserByToken(request);
			if (currUser == null) {
				return new JqGridBean<>(ResultCode.NOT_LOGIN.getCode() + "", "用户未登录或已过期，请重新登录");
			}
		}

		// // 拦截的实体类，就是当前正在执行的controller
		// Object target = pjp.getTarget();
		// // 拦截的方法名称。当前正在执行的方法
		// String methodName = pjp.getSignature().getName();
		// // 拦截的方法参数
		// Object[] args = pjp.getArgs();
		// // 拦截的放参数类型
		// Signature sig = pjp.getSignature();
		// MethodSignature msig = null;
		// if (!(sig instanceof MethodSignature)) {
		// throw new IllegalArgumentException("该注解只能用于方法");
		// }
		// msig = (MethodSignature) sig;
		// Class[] parameterTypes = msig.getMethod().getParameterTypes();
		//
		// Object object = null;
		//
		// Method method = null;
		// try {
		// method = target.getClass().getMethod(methodName, parameterTypes);
		// } catch (NoSuchMethodException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (SecurityException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// if (null != method) {
		// // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
		// if (method.isAnnotationPresent(SystemLog.class)) {
		// SystemLog systemlog = method.getAnnotation(SystemLog.class);
		// log.setModule(systemlog.module());
		// log.setMethod(systemlog.methods());
		// log.setLoginIp(getIp(request));
		// log.setActionUrl(request.getRequestURI());
		//
		// try {
		// object = pjp.proceed();
		// log.setDescription("执行成功");
		// log.setState((short) 1);
		// } catch (Throwable e) {
		// // TODO Auto-generated catch block
		// log.setDescription("执行失败");
		// log.setState((short)-1);
		// }
		// } else {//没有包含注解
		// object = pjp.proceed();
		// log.setDescription("此操作不包含注解");
		// log.setState((short)0);
		// }
		// } else { //不需要拦截直接执行
		// object = pjp.proceed();
		// log.setDescription("不需要拦截直接执行");
		// log.setState((short)0);
		// }

		return result;
	}

	private TokenVo getTokenUserInfo(HttpServletRequest request) {
		// 从header中获取token
		String token = request.getHeader("atoken");
		Map<String, String> tokenMap = JwtTokenUtil.getTokenInfo(token);
		if (tokenMap == null || tokenMap.isEmpty()) {
			return null;
		}
		String tokenMd5 = MD5Util.encode(token);
		// 检查token是否有效
		String tokenCache = JwtTokenUtil.USER_TOKEN_CACHE.getIfPresent(tokenMd5);
		if (!token.equals(tokenCache)) {
			return null;
		}
		TokenVo vo = new TokenVo();
		vo.setUserId(DigitUtil.parseToLong(tokenMap.get(JwtTokenUtil.KEY_UID)));
		vo.setUserName(tokenMap.get(JwtTokenUtil.KEY_UNAME));
		vo.setDeptId(DigitUtil.parseToLong(tokenMap.get(JwtTokenUtil.KEY_CID)));
		vo.setRoleId(DigitUtil.parseToLong(tokenMap.get(JwtTokenUtil.KEY_UTYPE)));
		return vo;
	}

	protected UserVo getCacheUserByToken(HttpServletRequest request) {
		// 从header中获取token
		String token = request.getHeader("atoken");
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		Map<String, String> tokenMap = JwtTokenUtil.getTokenInfo(token);
		if (tokenMap == null || tokenMap.isEmpty()) {
			return null;
		}

		// String tokenMd5 = MD5Util.encode(token);
		// // 检查token是否有效
		// String tokenCache = JwtTokenUtil.USER_TOKEN_CACHE.getIfPresent(tokenMd5);
		// if (!token.equals(tokenCache)) {
		// return null;
		// }
		String userId = tokenMap.get(JwtTokenUtil.KEY_UID);
		UserVo currUser = (UserVo) CacheManager.getInstance().get(userId);

		return currUser;
	}
}
