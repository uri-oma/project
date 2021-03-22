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
public class ValidatePhoneTest {
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"1234567890", false},
			{"310-798-4567", true},
			{"111-222-3333", true},
			{"12-456-789", false},
			{"258-456-7913", true},
			{"1111-111-111", false},
			{"979-9x9-9999", false},
			{"154-236-7895", true}
		});
	}
	
	private final String test;
	private final boolean isValid;
	
	public ValidatePhoneTest(String test, boolean isValid) {
		this.test = test;
		this.isValid = isValid;
	}
	
	@Test
	public void testValidatePhone() {
		try {
			assertEquals(isValid, InputValidation.validatePhone(test));
		} catch (BusinessException e) { }
	}
}
