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
public class ValidateStringToIntWithBoundsTest {
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"6", true},
			{"+9", false},
			{"ash9", false},
			{"7", true},
			{"12", false},
			{"8", true},
			{"00", false},
			{"1", false}
		});
	}
	
	private final String test;
	private final boolean isValid;
	
	public ValidateStringToIntWithBoundsTest(String test, boolean isValid) {
		this.test = test;
		this.isValid = isValid;
	}
	
	@Test
	public void testValidateStringToIntWithBounds() {
		try {
			assertEquals(isValid, InputValidation.validateStringToInt(test, 5, 10));
		} catch (BusinessException e) { }
	}
}