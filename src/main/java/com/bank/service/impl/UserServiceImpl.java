package com.bank.service.impl;

import com.bank.dao.UserDAO;
import com.bank.dao.impl.UserDAOImpl;
import com.bank.exceptions.BusinessException;
import com.bank.model.Customer;
import com.bank.model.User;
import com.bank.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDAO dao = new UserDAOImpl();
	
	@Override
	public User login(int id, String password) throws BusinessException {
		if (password.equals(dao.getPassFromId(id))) {
			return dao.getUserFromId(id);
		} else {
			throw new BusinessException("id/password not found");
		}
	}
	@Override
	public int register(Customer c) throws BusinessException {
		int i = dao.addCustomer(c);
		
		if (i == 0) {
			throw new BusinessException("registration was unsuccessful");
		} else {
			return i;
		}
	}
}