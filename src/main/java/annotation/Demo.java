package annotation;

@MyAnnotation(name = "sss")
public class Demo {

	@MyAnnotation
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	public static void main(String[] args) {
		if (Demo.class.isAnnotationPresent(MyAnnotation.class)) {
			MyAnnotation m = Demo.class.getAnnotation(MyAnnotation.class);
			System.out.println(m);
			System.out.println(m.name());
		}
	}
}
