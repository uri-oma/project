package com.bank.model.enums;

public enum AccountType {
	CHECKING(0), SAVINGS(1);
	
	private final int value;
	private AccountType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
