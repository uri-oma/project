package com.bank.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.bank.dao.UserDAO;
import com.bank.dao.impl.UserDAOImpl;

@RunWith(Parameterized.class)
public class GetPassFromIdTest {
	private UserDAO dao = new UserDAOImpl();
	
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"rock", 1001},
			{"wheel", 1002},
			{"quest", 1003},
			{"loss", 1004},
			{"store", 1005},
			{"real", 1006},
			{"flash", 1007},
			{"branch", 1008}
		});
	}
	
	private final String pass;
	private final int id;
	
	public GetPassFromIdTest(String pass, int id) {
		this.pass = pass;
		this.id = id;
	}
	
	@Test
	public void testGetPassFromId() {
		assertEquals(pass, dao.getPassFromId(id));
	}
}