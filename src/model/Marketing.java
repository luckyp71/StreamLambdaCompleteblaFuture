package model;

public class Marketing extends Employee {
	
	private int bonus;
	
	public Marketing() {}
	
	public Marketing(String name, int age, int salary, int bonus, String address) {
		super(name, age, salary, address);
		this.bonus = bonus;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
}
