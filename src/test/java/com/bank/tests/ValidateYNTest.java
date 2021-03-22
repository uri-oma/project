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
public class ValidateYNTest {
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"y", true},
			{"x", false},
			{"aa", false},
			{"N", true},
			{"00", false},
			{"n", true},
			{"Y", true},
			{"ball", false}
		});
	}
	
	private final String test;
	private final boolean isValid;
	
	public ValidateYNTest(String test, boolean isValid) {
		this.test = test;
		this.isValid = isValid;
	}
	
	@Test
	public void testValidateYN() {
		try {
			assertEquals(isValid, InputValidation.validateYN(test));
		} catch (BusinessException e) { }
	}
}