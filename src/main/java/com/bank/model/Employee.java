package com.bank.model;

import com.bank.model.enums.UserType;

public class Employee extends User {
	private int num_approved;

	// CONSTRUCTOR
	
	public Employee() { }
	public Employee(int id, String first, String middle, String last, String password,
	int num_approved) {
		super(id, first, middle, last, password, UserType.EMPLOYEE);
		this.num_approved = num_approved;
	}
	
	// GET+SET
	
	public int getNum_approved() {
		return num_approved;
	}
	public void setNum_approved(int num_approved) {
		this.num_approved = num_approved;
	}
	
	// OVERRIDE
	
	@Override
	public String toString() {
		return super.toString() + " Employee [num_approved=" + num_approved + "]";
	}
}