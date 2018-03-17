package model;

public class Employee {

	private String name;
	private int age;
	private int salary;
	private String address;
	
	public Employee(){}
	
	public Employee(String name, int age, int salary, String address) {
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.address = address;
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

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
