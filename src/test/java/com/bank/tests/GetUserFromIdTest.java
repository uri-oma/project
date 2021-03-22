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
import com.bank.model.Customer;
import com.bank.model.Employee;
import com.bank.model.User;

@RunWith(Parameterized.class)
public class GetUserFromIdTest {
	private UserDAO dao = new UserDAOImpl();
	
	@Parameters
	public static Collection<Object[]> cases() {
		return Arrays.asList(new Object[][] {
			{new Customer(1001, "Jason", "Nelson", "Smith", "apples", 
						  "111 Street", "Allen", "TX", "12345", "123-456-7897"), 1001},
			{new Customer(1002, "Katey", null, "Capps", "umbrella",
					  	  "246 Road", "Albany", "NY", "22558", "023-156-4897"), 1002},
			{new Employee(1003, "Nora", "Misty", "Doe", "never123", 48), 1003},
			{new Customer(1004, "Wanda", null, "Graves", "tenten202",
						  "369 Park", "San Francisco", "CA", "15973", "114-885-9966"), 1004}
		});
	}
	
	private final User user;
	private final int id;
	
	public GetUserFromIdTest(User user, int id) {
		this.user = user;
		this.id = id;
	}
	
	@Test
	public void testGetUserFromId() {
		assertEquals(user, dao.getUserFromId(id));
	}
}