

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextHelper implements ApplicationContextAware {

	private static ApplicationContext ctx;

	public static <T> T getBean(Class<T> clazz) {
		return ctx.getBean(clazz);
	}

	public static Object getBean(String className) {
		return ctx.getBean(className);
	}

	public static ApplicationContext getContext() {
		return ctx;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ctx = context;
	}

	public static void init(String[] configLocation) {
		if (null == ctx) {
			ctx = new ClassPathXmlApplicationContext(configLocation);
		}
	}
}
