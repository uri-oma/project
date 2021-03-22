package com.bank.dao;

import java.util.List;

import com.bank.model.Transaction;

public interface TransactionDAO {
	int addWithdrawalOrDeposit(Transaction t);
	int addTransfer(Transaction t, int toId);
	List<Transaction> getPendingTransfers(int userId);
	Transaction getTransactionFromId(int id);
	int approveTransfer(Transaction t);
	int rejectTransfer(Transaction t);
	List<Transaction> getAllTransactions();
}
