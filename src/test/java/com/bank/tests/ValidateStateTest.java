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
public class ValidateStateTest {
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"AW", false},
			{"WV", true},
			{"TX", true},
			{"35", false},
			{"NM", true},
			{"state", false},
			{"o", false},
			{"MN", true}
		});
	}
	
	private final String test;
	private final boolean isValid;
	
	public ValidateStateTest(String test, boolean isValid) {
		this.test = test;
		this.isValid = isValid;
	}
	
	@Test
	public void testValidateState() {
		try {
			assertEquals(isValid, InputValidation.validateStateAbbreviation(test));
		} catch (BusinessException e) { }
	}
}
