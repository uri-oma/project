package com.bank.dao;

import java.math.BigDecimal;
import java.util.List;

import com.bank.model.Account;

public interface AccountDAO {
	int addAccount(Account acc);
	List<Integer> getAccountIdsByCustId(int id);
	Account getAccountById(int id);
	List<Integer> getActivatedAccountIdsByCustId(int id);
	int modifyBalance(int id, BigDecimal balance);
	List<Integer> getAllAccountIds();
	List<Account> getPendingAccounts();
	int activate(int accountId, int empId);
	int deleteAccount(int id);
}