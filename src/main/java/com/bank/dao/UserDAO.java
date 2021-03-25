package com.bank.dao;

import java.util.List;

import com.bank.model.Customer;
import com.bank.model.User;

public interface UserDAO {
	String getPassFromId(int id);
	User getUserFromId(int id);
	int addCustomer(Customer c);
	int incrementApproved(int empId);
	List<Integer> getAllCustomerIds();
	String getNameFromId(int id);
	String getNameFromAccountId(int id);
}