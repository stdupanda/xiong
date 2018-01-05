package jdk8新;

import java.util.Optional;

public class 测试Optional {
	public static void main(String[] args) {
		String str = new String();
		Optional<String> optional = Optional.ofNullable(str);
		String optional2 = Optional.ofNullable(str).orElse("2");
		System.out.println(optional.get());
		System.out.println(optional2);
		System.out.println(".....");
	}
}
