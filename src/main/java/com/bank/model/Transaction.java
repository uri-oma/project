package com.bank.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import com.bank.model.enums.TransactionType;

public class Transaction {
	private int id, from_id, to_id;
	private BigDecimal amount;
	private TransactionType type;
	private Date date;
	
	// CONSTRUCTOR
	
	public Transaction() { }
	public Transaction(int id, int from_id, int to_id, BigDecimal amount, TransactionType type, Date date) {
		super();
		this.id = id;
		this.from_id = from_id;
		this.to_id = to_id;
		this.amount = amount;
		this.type = type;
		this.date = date;
	}
	
	// GET+SET
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getType() {
		return this.type.getValue();
	}
	public void setType(int type) {
		this.type = TransactionType.values()[type];
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
//	@Override
//	public String toString() {
//		return "Transaction [id=" + id + ", from_id=" + from_id + ", to_id=" + to_id + ", amount=" + amount + ", type="
//				+ type + ", date=" + date + "]";
//	}
	@Override
	public String toString() {
		String s = "[" + date + "] ";
		
		switch (type) {
		case WITHDRAWAL:
			s += "withdrawal " + id + " from acc " + from_id + " of " + currencyFormat(amount);
			break;
		case DEPOSIT:
			s += "deposit " + id + " to acc " + from_id + " of " + currencyFormat(amount);
			break;
		case TRANSFER_APPROVED:
			s += "approved transfer " + id + " from acc " + from_id + " to acc " + to_id + " of " + currencyFormat(amount);
			break;
		case TRANSFER_PENDING:
			s += "pending transfer " + id + " from acc " + from_id + " to acc " + to_id + " of " + currencyFormat(amount);
			break;
		}

		return s;
	}
	public String toFormattedString() {
		String s = String.format("%-26s", "[" + date + "] ");
		
		switch (type) {
		case WITHDRAWAL:
			s += String.format("%-20s", "withdrawal") + id + " from acc " + from_id + " of " + currencyFormat(amount);
			break;
		case DEPOSIT:
			s += String.format("%-20s", "deposit") + id + " to   acc " + from_id + " of " + currencyFormat(amount);
			break;
		case TRANSFER_APPROVED:
			s += String.format("%-20s", "approved transfer") + id + " from acc " + from_id + " to acc " + to_id + " of " + currencyFormat(amount);
			break;
		case TRANSFER_PENDING:
			s += String.format("%-20s", "pending transfer") + id + " from acc " + from_id + " to acc " + to_id + " of " + currencyFormat(amount);
			break;
		}

		return s;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Transaction)) {
			return false;
		}
		Transaction t = (Transaction) o;

		return this.id == t.id;
	}
	public static String currencyFormat(BigDecimal bd) {
		return NumberFormat.getCurrencyInstance().format(bd);
	}
}
