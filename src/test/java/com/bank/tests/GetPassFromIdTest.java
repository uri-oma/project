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
			{"apples", 1001},
			{"umbrella", 1002},
			{"never123", 1003},
			{"tenten202", 1004},
			{"234redblue", 1005},
			{"polarbears", 1006},
			{"addsub1278", 1007},
			{"11489222", 1008}
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