package com.bank.model;

import com.bank.exceptions.SetInputException;
import com.bank.model.enums.UserType;

public class Customer extends User {
	private String street, city, state, zip, phone;
	
	// CONSTRUCTOR
	
	public Customer() { }
	public Customer(int id, String first, String middle, String last, String password,
	String street, String city, String state, String zip, String phone) {
		super(id, first, middle, last, password, UserType.CUSTOMER);
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
	}
	
	// GET+SET
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) throws SetInputException {
		if (street != null && !street.isBlank()) {
			this.street = street;
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) throws SetInputException {
		if (city != null && !city.isBlank()) {
			this.city = city;
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	public String getState() {
		return state;
	}
	public void setState(String state) throws SetInputException {
		if (state != null && !state.isBlank()) {
			this.state = state.toUpperCase();
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) throws SetInputException {
		if (zip != null && !zip.isBlank()) {
			this.zip = zip;
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) throws SetInputException {
		if (phone != null && !phone.isBlank()) {
			this.phone = phone;
		} else {
			throw new SetInputException("this field can't be empty");
		}
	}
	
	// OVERRIDE
	
	@Override
	public String toString() {
		return super.toString() + " Customer [street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip + ", phone="
				+ phone + "]";
	}
}