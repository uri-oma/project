package com.bank.service;

import java.math.BigDecimal;
import java.util.List;

import com.bank.exceptions.BusinessException;
import com.bank.model.Account;

public interface AccountService {
	int apply(Account acc) throws BusinessException;
	List<Integer> getActivatedCustAccountIds(int id);
	int modifyBalance(int id, BigDecimal balance) throws BusinessException;
}