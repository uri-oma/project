package com.bank.service;

import com.bank.exceptions.BusinessException;
import com.bank.model.Customer;
import com.bank.model.User;

public interface UserService {
	User login(int id, String password) throws BusinessException;
	int register(Customer c) throws BusinessException;
}