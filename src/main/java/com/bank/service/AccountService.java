package com.bank.service;

import java.math.BigDecimal;

import com.bank.exceptions.BusinessException;
import com.bank.model.Account;

public interface AccountService {
	int apply(Account acc) throws BusinessException;
	int modifyBalance(int id, BigDecimal balance) throws BusinessException;
}