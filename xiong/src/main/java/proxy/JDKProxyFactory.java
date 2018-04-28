package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxyFactory implements InvocationHandler {
	private Object obj;
	
	public JDKProxyFactory() {
		// do nothing
	}

	public JDKProxyFactory(Object obj) {
		this.obj = obj;
	}

	public Object createProxy() {
		ClassLoader classLoader = this.obj.getClass().getClassLoader();
		Class<?>[] interfaces = this.obj.getClass().getInterfaces();
		return Proxy.newProxyInstance(classLoader, interfaces, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method + " <> " + Arrays.asList(args));
		Object ret = method.invoke(this.obj, args);
		return ret;
	}
	
	public static void main(String[] args) {
		UserService service = new UserServiceImp();
		JDKProxyFactory factory = new JDKProxyFactory(service);
		// target object should be interface, not the implements.
//		UserServiceImp ret = (UserServiceImp) factory.creatProxyInstance(service);// this is error!
		UserService ret = (UserService) factory.createProxy();// this is right.
		ret.getAgeById(1);
		ret.getNameById(2);
	}
}
