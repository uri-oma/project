package com.bank.model;

import com.bank.exceptions.SetInputException;
import com.bank.model.enums.UserType;

public abstract class User {
	private int id;
	private String first, middle, last;
	private String password;
	private UserType ut;
	
	// CONSTRUCTOR
	
	public User() { }
	public User(int id, String first, String middle, String last, String password, UserType ut) {
		super();
		this.id = id;
		this.first = first;
		this.middle = middle;
		this.last = last;
		this.password = password;
		this.ut = ut;
	}
	
	// GET+SET
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) throws SetInputException {
		if (first != null && !first.isBlank()) {
			this.first = first;
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		if (middle != null && !middle.isBlank()) {
			this.middle = middle;
		} else {
			this.middle = null;
		}
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) throws SetInputException {
		if (last != null && !last.isBlank()) {
			this.last = last;
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws SetInputException {
		if (password != null && !password.isBlank()) {
			this.password = password;
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	public int getUt() {
		return this.ut.getValue();
	}
	public void setUt(int ut) {
		this.ut = UserType.values()[ut];
	}
	public String getFirstLast() {
		return first + " " + last;
	}
	
	// OVERRIDE
	
	@Override
	public String toString() {
		return "User [id=" + id + ", first=" + first + ", middle=" + middle + ", last=" + last + ", password="
				+ password + ", ut=" + ut + "]";
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		User u = (User) o;

		return this.id == u.id;
	}
}