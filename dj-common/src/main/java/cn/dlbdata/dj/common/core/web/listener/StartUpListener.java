package cn.dlbdata.dj.common.core.web.listener;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.hook.CoreStart;


public class StartUpListener implements ServletContextListener {
	/**
	 * 日志器
	 */
	protected Logger log = Logger.getLogger(StartUpListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent context) {

	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		String path = context.getServletContext().getRealPath("");
		File file = new File(path);
		String realPath = path;
		try {
			realPath = file.getCanonicalPath();
		} catch (IOException e) {
			log.error(e);
		}
		CoreConst.WEB_PROJECT_PATH = realPath;

		String tmpPath = System.getProperty("user.dir");
		tmpPath = tmpPath.substring(0, tmpPath.lastIndexOf(File.separator));
		CoreConst.WEB_PHYSICAL_PATH = realPath;

		CoreConst.servletContext = context.getServletContext();

		ApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context
				.getServletContext());
		CoreConst.springContext = springContext;
		CoreStart appStart = (CoreStart) CoreConst.springContext.getBean("coreStart");

		try {
			appStart.startUp();
		} catch (Exception e) {
			log.error("AppStart", e);
		}
	}
}
