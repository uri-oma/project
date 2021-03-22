package com.bank.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dbutil.PostgresConnection;
import com.bank.model.Transaction;
import com.bank.util.Menu;

public class TransactionDAOImpl implements TransactionDAO {
	@Override
	public int addWithdrawalOrDeposit(Transaction t) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into bank.transactions (from_id, to_id, amount, transaction_type, transaction_date) "
					   + "values (?, ?, ?, ?, ?) returning id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, t.getFrom_id());
			ps.setNull(2, java.sql.Types.INTEGER);
			ps.setBigDecimal(3, t.getAmount());
			ps.setInt(4, t.getType());
			ps.setTimestamp(5, new Timestamp(t.getDate().getTime()));
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
	public int addTransfer(Transaction t, int toId) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into bank.transactions (from_id, to_id, amount, transaction_type, transaction_date) "
					   + "values (?, ?, ?, ?, ?) returning id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, t.getFrom_id());
			ps.setInt(2, toId);
			ps.setBigDecimal(3, t.getAmount());
			ps.setInt(4, t.getType());
			ps.setTimestamp(5, new Timestamp(t.getDate().getTime()));
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
	public List<Transaction> getPendingTransfers(int userId) {
		AccountDAO dao = new AccountDAOImpl();
		List<Integer> li = dao.getActivatedAccountIdsByCustId(userId);
		List<Transaction> tli = new ArrayList<Transaction>();
		
		try (Connection connection = PostgresConnection.getConnection()) {
			for (Integer i : li) {
				String sql = "select id, from_id, to_id, amount, transaction_type, transaction_date from bank.transactions where to_id = ? and transaction_type = 2";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, i);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Transaction tr = new Transaction();
					tr.setId(rs.getInt("id"));
					tr.setFrom_id(rs.getInt("from_id"));
					tr.setTo_id(rs.getInt("to_id"));
					tr.setAmount(new BigDecimal(rs.getString("amount").replaceAll("[$,]", "")));
					tr.setType(rs.getInt("transaction_type"));
					tr.setDate(rs.getTimestamp("transaction_date"));
					tli.add(tr);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return tli;
	}
	@Override
	public Transaction getTransactionFromId(int id) {
		Transaction tr = new Transaction();
		
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id, from_id, to_id, amount, transaction_type, transaction_date from bank.transactions where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tr.setId(rs.getInt("id"));
				tr.setFrom_id(rs.getInt("from_id"));
				tr.setTo_id(rs.getInt("to_id"));
				tr.setAmount(new BigDecimal(rs.getString("amount").replaceAll("[$,]", "")));
				tr.setType(rs.getInt("transaction_type"));
				tr.setDate(rs.getTimestamp("transaction_date"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return tr;
	}
	public int approveTransfer(Transaction t) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "update bank.transactions set transaction_type = 3 where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, t.getId());
			i = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return i;
	}
	public int rejectTransfer(Transaction t) {
		int i = 0;

		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "delete from bank.transactions where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, t.getId());
			i = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return i;
	}
	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> li = new ArrayList<Transaction>();
		
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select id, from_id, to_id, amount, transaction_type, transaction_date from bank.transactions";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Transaction tr = new Transaction();
				tr.setId(rs.getInt("id"));
				tr.setFrom_id(rs.getInt("from_id"));
				tr.setTo_id(rs.getInt("to_id"));
				tr.setAmount(new BigDecimal(rs.getString("amount").replaceAll("[$,]", "")));
				tr.setType(rs.getInt("transaction_type"));
				tr.setDate(rs.getTimestamp("transaction_date"));
				li.add(tr);
			}
		} catch (ClassNotFoundException | SQLException e) {
			Menu.errorln(e.getMessage());
		}
		return li;
	}
}
