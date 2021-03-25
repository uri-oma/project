package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dao.UserDAO;
import com.bank.dbutil.PostgresConnection;
import com.bank.exceptions.SetInputException;
import com.bank.model.Customer;
import com.bank.model.Employee;
import com.bank.model.User;
import com.bank.util.Menu;

public class UserDAOImpl implements UserDAO {
	@Override
	public String getPassFromId(int id) {
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select password from bank.users where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("password");
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return null;
	}
	@Override
	public User getUserFromId(int id) {
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select user_type from bank.users where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt("user_type") == 0) {
					sql = "select users.id, first_name, middle_name, last_name, password, user_type, street_address, city, state, zip, phone "
						+ "from bank.users join bank.customers on users.id = customers.id "
						+ "where users.id = ?";
					ps = connection.prepareStatement(sql);
					ps.setInt(1, id);
					rs = ps.executeQuery();
					if (rs.next()) {
						Customer user = new Customer();
						try {
							user.setId(id);
							user.setFirst(rs.getString("first_name"));
							user.setMiddle(rs.getString("middle_name"));
							user.setLast(rs.getString("last_name"));
							user.setPassword(rs.getString("password"));
							user.setUt(rs.getInt("user_type"));
							user.setStreet(rs.getString("street_address"));
							user.setCity(rs.getString("city"));
							user.setState(rs.getString("state"));
							user.setZip(rs.getString("zip"));
							user.setPhone(rs.getString("phone"));
						} catch (SetInputException e) {
							Menu.errorln(e.getMessage());
						}
						return user;
					}
				} 
				if (rs.getInt("user_type") == 1) {
					sql = "select users.id, first_name, middle_name, last_name, password, user_type, num_approved "
						+ "from bank.users join bank.employees on users.id = employees.id "
						+ "where users.id = ?";
					ps = connection.prepareStatement(sql);
					ps.setInt(1, id);
					rs = ps.executeQuery();
					if (rs.next()) {
						Employee user = new Employee();
						try {
							user.setId(id);
							user.setFirst(rs.getString("first_name"));
							user.setMiddle(rs.getString("middle_name"));
							user.setLast(rs.getString("last_name"));
							user.setPassword("password");
							user.setUt(rs.getInt("user_type"));
							user.setNum_approved(rs.getInt("num_approved"));
						} catch (SetInputException e) {
							Menu.errorln(e.getMessage());
						}
						return user;
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return null;
	}
	@Override
	public int addCustomer(Customer c) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "with ins as (insert into bank.users (first_name, middle_name, last_name, password, user_type) values (?, ?, ?, ?, 0) returning id) "
					   + "insert into bank.customers (id, street_address, city, state, zip, phone) "
					   + "select id, ?, ?, ?, ?, ? "
					   + "from ins returning id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, c.getFirst());
			ps.setString(2, c.getMiddle());
			ps.setString(3, c.getLast());
			ps.setString(4, c.getPassword());
			ps.setString(5, c.getStreet());
			ps.setString(6, c.getCity());
			ps.setString(7, c.getState());
			ps.setString(8, c.getZip());
			ps.setString(9, c.getPhone());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				i = rs.getInt("id");
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return i;
	}
	@Override
	public int incrementApproved(int empId) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "update bank.employees set num_approved = num_approved + 1 where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, empId);
			i = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return i;
	}
	@Override
	public List<Integer> getAllCustomerIds() {
		List<Integer> li = new ArrayList<Integer>();
		
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id from bank.users where user_type = 0";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				li.add(rs.getInt("id"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return li;
	}
	@Override
	public String getNameFromId(int id) {
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select first_name, last_name from bank.users where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("first_name") + " " + rs.getString("last_name");
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return null;
	}
	@Override
	public String getNameFromAccountId(int id) {
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select first_name, last_name from bank.users join bank.accounts on bank.users.id = bank.accounts.cust_id where bank.accounts.id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("first_name") + " " + rs.getString("last_name");
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return null;
	}
}