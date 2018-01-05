package 修改加载路径;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 将 web.xml 中的
 * context-param 、param-name 
 * hcecloud.env 写入 System 的 Property 中。
 */
public class WebListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(WebListener.class);
	private static final String PAK_CONFIG_PATH_KEY = "hcecloud.env";// web.xml 中配置的打包路径 key

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		LOGGER.info("shutting down service...");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String initParameter = event.getServletContext().getInitParameter(PAK_CONFIG_PATH_KEY);
		if (StringUtils.isBlank(initParameter)) {
			LOGGER.error("获取打包配置失败！请检查 web.xml -> <context-param> " + PAK_CONFIG_PATH_KEY + " 配置！");
			System.exit(0);
		}

		LOGGER.info("获取到打包配置: " + initParameter);
		System.setProperty(PAK_CONFIG_PATH_KEY, initParameter);
	}
}
