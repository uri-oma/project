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
public class ValidateStringToIntTest {
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"9", true},
			{"+9", false},
			{"ash9", false},
			{"200", true},
			{"00", false},
			{"101", true},
			{"1", true},
			{"01", false}
		});
	}
	
	private final String test;
	private final boolean isValid;
	
	public ValidateStringToIntTest(String test, boolean isValid) {
		this.test = test;
		this.isValid = isValid;
	}
	
	@Test
	public void testValidateStringToInt() {
		try {
			assertEquals(isValid, InputValidation.validateStringToInt(test));
		} catch (BusinessException e) { }
	}
}