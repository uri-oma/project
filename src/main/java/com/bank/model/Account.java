package com.bank.model;

import java.math.BigDecimal;

import com.bank.model.enums.AccountType;

public class Account {
	private int id, cust_id, emp_id;
	private BigDecimal balance;
	private AccountType at;
	private boolean isActivated;
	
	// CONSTRUCTOR
	
	public Account() { }
	public Account(int cust_id, boolean isActivated) {
		super();
		this.cust_id = cust_id;
		this.isActivated = isActivated;
	}
	public Account(int id, int cust_id, int emp_id, BigDecimal balance, AccountType at, boolean isActivated) {
		super();
		this.id = id;
		this.cust_id = cust_id;
		this.emp_id = emp_id;
		this.balance = balance;
		this.at = at;
		this.isActivated = isActivated;
	}

	// GET+SET
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public int getAT() {
		return this.at.getValue();
	}
	public void setAT(int at) {
		this.at = AccountType.values()[at];
	}
	public boolean getIsActivated() {
		return this.isActivated;
	}
	public void setIsActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	@Override
	public String toString() {
		if (isActivated) {
			return at.name() + " " + id + ": " + "$" + balance;
		} else
			return at.name() + " " + id + " (initial balance $" + balance + ") is pending review";
 	}
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Account)) {
			return false;
		}
		Account a = (Account) o;

		return this.id == a.id;
	}
}
