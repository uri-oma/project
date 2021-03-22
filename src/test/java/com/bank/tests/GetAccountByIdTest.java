package com.bank.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.bank.dao.AccountDAO;
import com.bank.dao.impl.AccountDAOImpl;
import com.bank.model.Account;
import com.bank.model.enums.AccountType;

@RunWith(Parameterized.class)
public class GetAccountByIdTest {
	private AccountDAO dao = new AccountDAOImpl();
	
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{new Account(100003, 1001, 0, BigDecimal.valueOf(0), AccountType.values()[1], false), 100003},
			{new Account(100004, 1001, 0, BigDecimal.valueOf(13.35), AccountType.values()[1], false), 100004},
			{new Account(100006, 1002, 0, BigDecimal.valueOf(45), AccountType.values()[1], false), 100006},
			{new Account(100005, 1002, 0, BigDecimal.valueOf(0), AccountType.values()[0], true), 100005},
		});
	}
	
	private final Account acc;
	private final int id;
	
	public GetAccountByIdTest(Account acc, int id) {
		this.acc = acc;
		this.id = id;
	}
	
	@Test
	public void testGetAccountById() {
		assertEquals(acc, dao.getAccountById(id));
	}
}