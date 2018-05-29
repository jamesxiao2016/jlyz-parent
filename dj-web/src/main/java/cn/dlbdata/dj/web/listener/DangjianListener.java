package cn.dlbdata.dj.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.dlbdata.dj.common.core.util.cache.CacheManager;

public class DangjianListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		CacheManager.getInstance();
	}

}
