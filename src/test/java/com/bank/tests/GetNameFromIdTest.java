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
public class GetNameFromIdTest {
	private UserDAO dao = new UserDAOImpl();
	
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{"Truman Ellington", 1001},
			{"Katheryn Minett", 1002},
			{"Oliver Herbert", 1003},
			{"Delia Ericson", 1004},
			{"Andrew Powers", 1005},
			{"Hudson Brester", 1006},
			{"Rosemarie Martel", 1007},
			{"Royal Nye", 1008}
		});
	}
	
	private final String pass;
	private final int id;
	
	public GetNameFromIdTest(String pass, int id) {
		this.pass = pass;
		this.id = id;
	}
	
	@Test
	public void testGetNameFromId() {
		assertEquals(pass, dao.getNameFromId(id));
	}
}