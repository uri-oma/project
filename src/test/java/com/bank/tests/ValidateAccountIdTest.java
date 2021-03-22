package com.bank.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bank.dao.AccountDAO;
import com.bank.dao.impl.AccountDAOImpl;
import com.bank.exceptions.BusinessException;
import com.bank.util.InputValidation;

public class ValidateAccountIdTest {
	static AccountDAO dao = new AccountDAOImpl();
	static List<Integer> li = new ArrayList<Integer>();
	
	@BeforeClass
	public static void setup() {
		li = dao.getAccountIdsByCustId(0);
	}
	@Test
	public void testValidateAccountId1() {
		try {
			assertTrue(InputValidation.validateId("100001", li));
		} catch (BusinessException e) { }
	}
	@Test
	public void testValidateAccountIdOutOfRange1() {
		try {
			assertFalse(InputValidation.validateId("100005", li));
		} catch (BusinessException e) { }
	}
	@Test
	public void testValidateAccountIdOutOfRange2() {
		try {
			assertFalse(InputValidation.validateId("100006", li));
		} catch (BusinessException e) { }
	}
	@Test
	public void testValidateAccountId2() {
		try {
			assertTrue(InputValidation.validateId("100003", li));
		} catch (BusinessException e) { }
	}
	@Test
	public void testValidateAccountIdNotIntString() {
		try {
			assertFalse(InputValidation.validateId("ice", li));
		} catch (BusinessException e) { }
	}
}