package com.bank.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.bank.dao.AccountDAO;
import com.bank.dao.impl.AccountDAOImpl;
import com.bank.exceptions.BusinessException;
import com.bank.model.Account;
import com.bank.service.AccountService;

public class AccountServiceImpl implements AccountService {
	AccountDAO dao = new AccountDAOImpl();
	
	@Override
	public int apply(Account acc) throws BusinessException {
		int i = dao.addAccount(acc);
		
		if (i == 0) {
			throw new BusinessException("account application was unsuccessful");
		} else {
			return i;
		}
	}
	@Override
	public List<Integer> getActivatedCustAccountIds(int id) {
		return dao.getActivatedAccountIdsByCustId(id);
	}
	@Override
	public int modifyBalance(int id, BigDecimal balance) throws BusinessException {
		int i = dao.modifyBalance(id, balance);
		
		if (i == 0) {
			throw new BusinessException("balance update was unsuccessful");
		} else {
			return i;
		}
	}
}
