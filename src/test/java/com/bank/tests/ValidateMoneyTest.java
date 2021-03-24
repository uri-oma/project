package com.bank.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.bank.exceptions.BusinessException;
import com.bank.util.InputValidation;

@RunWith(Parameterized.class)
public class ValidateMoneyTest {
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"$9.00", true},
			{"18.9", true},
			{"$18.93", true},
			{"08.9", false},
			{"$900", true},
			{"$1.0", false},
			{"900.", false},
			{"$24.99", true},
			{"44.91", true},
			{"-$44.92", false},
			{"$293.1", false},
			{"25", true},
			{"325.24", true},
			{"10.00", true},
			{"99.80", true},
			{"025", false},
			{"$009", false},
			{".39", true},
			{"$.93", true},
			{"08.9$", false},
			{"$0.82", true},
			{"$00", false},
			{"-28", false},
			{"$.99", true},
			{"44.91", true},
			{"$.", false},
			{"$2.1", false},
			{"250", true},
			{"325.4", false},
			{"90.00", true},
			{".80", true},
			{"", false}
		});
	}
	
	private final String test;
	private final boolean isValid;
	
	public ValidateMoneyTest(String test, boolean isValid) {
		this.test = test;
		this.isValid = isValid;
	}
	
	@Test
	public void testValidateMoney() {
		try {
			assertEquals(isValid, InputValidation.validateMoney(test));
		} catch (BusinessException e) { }
	}
}
