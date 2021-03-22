package com.bank.model.enums;

public enum TransactionType {
	WITHDRAWAL(0), DEPOSIT(1), TRANSFER_PENDING(2), TRANSFER_APPROVED(3);
	
	private final int value;
	private TransactionType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
