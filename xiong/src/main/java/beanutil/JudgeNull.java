package beanutil;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

public class JudgeNull {

	@Test
	public void copyProperties() throws IllegalAccessException, InvocationTargetException{
		Dog dog = new Dog("jim", 10, "my dog");
		System.out.println(dog);
		Object newDog = new Dog();
		BeanUtils.copyProperties(newDog, dog);
		System.out.println(newDog);
	}
	
	@Test
	public void c1opyProperties() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Dog dog = new Dog("jim", 10, "my dog");
		System.out.println(dog);
//		String[] arrayProperty = BeanUtils.getArrayProperty(dog, "name");
//		System.out.println(Arrays.toString(arrayProperty));
		Map<String, Object> map1 = PropertyUtils.describe(dog);
		System.out.println(map1);
		Set<String> keySet = map1.keySet();
		for (String string : keySet) {
			Object object = map1.get(string);
			System.out.println(object);
		}
		
		Map<String, String> map2 = BeanUtils.describe(dog);
		System.out.println(map2);
	}
}
