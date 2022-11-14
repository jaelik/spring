package vo;

public class PersonVO {
	private String name;
	private int age;
	private String tel;
	
	public PersonVO() {
		//기본 생성자 
		System.out.println("--PersonVO의 생성자--");
	}
	
	public PersonVO(String name, int age, String tel) {
		this.name =name;
		this.age = age;
		this.tel = tel ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	

}
