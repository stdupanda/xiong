package beanutil;

public class Dog {
	private String name;
	private Integer age;
	private String desc;
	
	public Dog() {
	}
	
	public Dog(String name, Integer age, String desc) {
		this.name = name;
		this.age = age;
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return hashCode() + "Dog [name=" + name + ", age=" + age + ", desc=" + desc + "]";
	}

	//get set
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
