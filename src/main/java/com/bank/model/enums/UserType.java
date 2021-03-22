package com.bank.model.enums;

public enum UserType {
	CUSTOMER(0), EMPLOYEE(1);
	
	private final int value;
	private UserType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
