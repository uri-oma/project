package com.bank.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dao.AccountDAO;
import com.bank.dao.UserDAO;
import com.bank.dbutil.PostgresConnection;
import com.bank.model.Account;
import com.bank.util.Menu;

public class AccountDAOImpl implements AccountDAO {
	@Override
	public int addAccount(Account acc) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into bank.accounts (cust_id, emp_id, balance, account_type, is_activated) "
					   + "values (?, ?, ?, ?, ?) returning id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, acc.getCust_id());
			ps.setNull(2, java.sql.Types.INTEGER);
			ps.setBigDecimal(3, acc.getBalance());
			ps.setInt(4, acc.getAT());
			ps.setBoolean(5, acc.getIsActivated());
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
	public List<Integer> getAccountIdsByCustId(int id) {
		List<Integer> li = new ArrayList<Integer>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id from bank.accounts where cust_id = ? order by id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
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
	public Account getAccountById(int id) {
		Account acc = new Account();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id, cust_id, emp_id, balance, account_type, is_activated from bank.accounts where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				acc.setId(rs.getInt("id"));
				acc.setCust_id(rs.getInt("cust_id"));
				acc.setEmp_id(rs.getInt("emp_id"));
				acc.setBalance(new BigDecimal(rs.getString("balance").replaceAll("[$,]", "")));
				acc.setAT(rs.getInt("account_type"));
				acc.setIsActivated(rs.getBoolean("is_activated"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return acc;
	}
	@Override
	public List<Integer> getActivatedAccountIdsByCustId(int id) {
		List<Integer> li = new ArrayList<Integer>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id from bank.accounts where cust_id = ? and is_activated = true order by id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
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
	public int modifyBalance(int id, BigDecimal amount) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "update bank.accounts set balance = ? where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setBigDecimal(1, amount);
			ps.setInt(2, id);
			i = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return i;
	}
	@Override
	public List<Integer> getAllActivatedAccountIds() {
		List<Integer> li = new ArrayList<Integer>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id from bank.accounts where is_activated = true order by id";
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
	public List<Account> getPendingAccounts() {
		List<Account> li = new ArrayList<Account>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id, cust_id, balance, account_type from bank.accounts where is_activated = false order by id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account acc = new Account();
				acc.setId(rs.getInt("id"));
				acc.setCust_id(rs.getInt("cust_id"));
				acc.setBalance(new BigDecimal(rs.getString("balance").replaceAll("[$,]", "")));
				acc.setAT(rs.getInt("account_type"));
				li.add(acc);
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return li;
	}
	@Override
	public int activate(int accountId, int empId) {
		UserDAO dao = new UserDAOImpl();
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "update bank.accounts set is_activated = true, emp_id = ? where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, empId);
			ps.setInt(2, accountId);
			i = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		dao.incrementApproved(empId);
		return i;
	}
	@Override
	public int deleteAccount(int id) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "delete from bank.accounts where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			i = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return i;
	}
	
}
