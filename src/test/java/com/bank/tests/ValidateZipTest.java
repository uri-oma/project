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
public class ValidateZipTest {
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"79936", true},
			{"3526", false},
			{"x2489", false},
			{"63829", true},
			{"239593", false},
			{"00001", true},
			{"29537", true},
			{"berry", false}
		});
	}
	
	private final String test;
	private final boolean isValid;
	
	public ValidateZipTest(String test, boolean isValid) {
		this.test = test;
		this.isValid = isValid;
	}
	
	@Test
	public void testValidateZip() {
		try {
			assertEquals(isValid, InputValidation.validateZip(test));
		} catch (BusinessException e) { }
	}
}