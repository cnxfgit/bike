package bike.test;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class ClassDemo {
	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();

		ClassLoader classLoader  = ClassDemo.class.getClassLoader();
		InputStream iStream = classLoader.getResourceAsStream("pro.properties");
		
		properties.load(iStream);
		
		String className = properties.getProperty("className");
		String methodName = properties.getProperty("methodName");
		
		Class cls = Class.forName(className);
		@SuppressWarnings("deprecation")
		Object object = cls.newInstance();

		Method method = cls.getMethod(methodName); 
		method.invoke(object);
	}
}
