package contatti;

public class Person {
	private String name;
	private String surname;
	private String address;
	private String phone_no;
	private int id;
	private int age;
	
	public Person(String name, String surname, String address, String phone_no, int age, int id) {
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.phone_no = phone_no;
		this.age = age;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public int getAge() {
		return age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
