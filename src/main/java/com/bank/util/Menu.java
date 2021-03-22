package com.bank.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.UserDAO;
import com.bank.dao.impl.AccountDAOImpl;
import com.bank.dao.impl.TransactionDAOImpl;
import com.bank.dao.impl.UserDAOImpl;
import com.bank.exceptions.BusinessException;
import com.bank.exceptions.SetInputException;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import com.bank.service.impl.AccountServiceImpl;
import com.bank.service.impl.UserServiceImpl;

public class Menu {
	private static Logger log = Logger.getLogger(Menu.class);
	private static Scanner sc = new Scanner(System.in);
	private static UserService us = new UserServiceImpl();
	private static AccountService as = new AccountServiceImpl();
	private static UserDAO udao = new UserDAOImpl();
	private static AccountDAO adao = new AccountDAOImpl();
	private static TransactionDAO tdao = new TransactionDAOImpl();
	
	private static final int LENGTH = 64; // display width
	
	public static void displaySign(String s) { // displays sign
		oln(displayBorder('$'));
		oln(centerString(s));
		oln(displayBorder('$'));
	}
	public static int displayMain() { // displays main menu and returns user choice
		String s;
		int ch = -1;
		
		oln("what would you like to do?");
		o("1 - log in");
		o("2 - register for customer account");
		oln("==== 0 - exit ====");
		s = sc.nextLine();
		b();
		try {
			if (InputValidation.validateStringToInt(s, 0, 2)) {
				ch = Integer.parseInt(s);
			}
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
		
		return ch;
	}
	public static User displayLogin() { // displays login menu
		String s;
		String password;
		int id = 0;
		
		o("enter user id:");
		s = sc.nextLine();
		try {
			if (InputValidation.validateStringToInt(s)) {
				id = Integer.parseInt(s);
			}
			o("enter user password:");
			password = sc.nextLine();
			b();
			User activeUser = us.login(id, password);
			oln("W E L C O M E , " + activeUser.getFirstLast() + "!");
			return activeUser;
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
		return null;
	}
	public static void displayRegister() { // displays register menu
		try {
			Customer c = new Customer();
			String s;
			
			o("enter first name:");
			c.setFirst(sc.nextLine());
			o("enter middle name. if not applicable, press enter:");
			c.setMiddle(sc.nextLine());
			o("enter last name:");
			c.setLast(sc.nextLine());
			o("set your password:");
			c.setPassword(sc.nextLine());
			o("enter street address:");
			c.setStreet(sc.nextLine());
			o("enter city:");
			c.setCity(sc.nextLine());
			o("enter 2-letter state abbreviation:");
			s = sc.nextLine();
			if (InputValidation.validateStateAbbreviation(s)) {
				c.setState(s);
			}
			o("enter 5-digit zip code:");
			s = sc.nextLine();
			if (InputValidation.validateZip(s)) {
				c.setZip(s);
			}
			o("enter phone number in ###-###-#### format:");
			s = sc.nextLine();
			if (InputValidation.validatePhone(s)) {
				c.setPhone(s);
			}
			b();
			
			int customerId = us.register(c);
			
			if (customerId != 0) {
				oln("registration successful! your customer id is: " + customerId);
			}
		} catch (SetInputException e) {
			errorln(e.getMessage());
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
	}
	public static int displayCustomerMenu() { // displays customer menu
		String s;
		int ch = -1;
		
		oln("what would you like to do?");
		o("1 - apply for new bank account");
		o("2 - view account balances");
		o("3 - withdraw or deposit");
		o("4 - manage transfers");
		oln("==== 0 - exit ====");
		s = sc.nextLine();
		b();
		try {
			if (InputValidation.validateStringToInt(s, 0, 4)) {
				ch = Integer.parseInt(s);
			}
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
		
		return ch;
	}
	public static int displayEmployeeMenu() { // displays employee menu
		String s;
		int ch = -1;
		
		oln("what would you like to do?");
		o("1 - view pending customer accounts");
		o("2 - view customer's account");
		o("3 - view transaction logs");
		oln("==== 0 - exit ====");
		s = sc.nextLine();
		b();
		try {
			if (InputValidation.validateStringToInt(s, 0, 3)) {
				ch = Integer.parseInt(s);
			}
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
		
		return ch;
	}
	public static void displayApplyAccount(int id) { // displays menu to apply for a new bank account
		String s;
		Account acc = new Account(id, false);
		
		oln("what kind of account would you like to apply for?");
		o("1 - checking");
		oln("2 - savings");
		s = sc.nextLine();
		b();
		try {
			if (InputValidation.validateStringToInt(s, 1, 2)) {
				acc.setAT(Integer.parseInt(s) - 1);
			}
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
		o("would you like to make an initial deposit? (y/n)");
		try {
			s = sc.nextLine();
			if (InputValidation.validateYN(s)) {
				if (s.equalsIgnoreCase("y")) {
					o("how much would you like to deposit?");
					s = sc.nextLine();
					if (InputValidation.validateMoney(s)) { acc.setBalance(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", "")))); }
				} else {
					acc.setBalance(BigDecimal.ZERO);
				}
			}
			int accountId =  as.apply(acc);
			if (accountId != 0) {
				b();
				o("application successful! your account id is: " + accountId);
				oln("your bank account is pending review by an employee");
			}
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
	}
	public static void displayAccountBalances(int id) { // displays customer's account balances
		String s;
		List<Integer> li = adao.getAccountIdsByCustId(id);
		
		if (li.size() != 0) {
			oln("which account balance would you like to view?");
			for (Integer i : li) {
				o(i + "");
			}
			b();
			s = sc.nextLine();
			b();
			try {
				if (InputValidation.validateId(s, li)) {
					Account activeAccount = adao.getAccountById(Integer.parseInt(s));
					if (activeAccount.getIsActivated()) {
						oln(activeAccount.toString());
					} else {
						oln(activeAccount.toString());
					}
				}
			} catch (BusinessException e) {
				errorln(e.getMessage());
			}
		} else {
			oln ("you have no active accounts");
		}
	}
	public static int displayTransactionMenu() { // displays withdraw/deposit menu
		String s;
		int ch = -1;
		
		oln("what kind of transaction would you like to make?");
		o("1 - withdrawal");
		oln("2 - deposit");
		s = sc.nextLine();
		b();
			try {
				if (InputValidation.validateStringToInt(s, 1, 2)) {
					ch = Integer.parseInt(s);
				}
			} catch (BusinessException e) {
				errorln(e.getMessage());
			}
		return ch;
	}
	public static void displayWithdraw(int id) { // displays withdraw prompt
		String s;
		List<Integer> li = as.getActivatedCustAccountIds(id);
		
		if (li.size() != 0) {
			oln("which account would you like to make a withdrawal?");
			for (Integer i : li) {
				o(i + "");
			}
			b();
			s = sc.nextLine();
			b();
			try {
				if (InputValidation.validateId(s, li)) {
					Account activeAccount = adao.getAccountById(Integer.parseInt(s));
					oln(activeAccount.toString());
					oln("how much would you like to withdraw?");
					s = sc.nextLine();
					b();
					if (InputValidation.validateMoney(s) && BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", ""))).compareTo(activeAccount.getBalance()) <= 0) {
						as.modifyBalance(activeAccount.getId(), activeAccount.getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", "")))));
						activeAccount.setBalance(activeAccount.getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", "")))));
						oln("new balance for " + activeAccount.toString());
						Transaction tr = new Transaction();
						tr.setFrom_id(activeAccount.getId());
						tr.setAmount(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", ""))));
						tr.setType(0);
						tr.setDate(new Date());
						oln(tr.toString());
						tdao.addWithdrawalOrDeposit(tr);
					} else {
						oln("can't withdraw more than $" + activeAccount.getBalance());
					}
				}
			} catch (BusinessException e) {
				oln(e.getMessage());
			}
		} else {
			oln ("you have no active accounts");
		}
	}
	public static void displayDeposit(int id) { // displays deposit prompt
		String s;
		List<Integer> li = as.getActivatedCustAccountIds(id);
		
		if (li.size() != 0) {
			oln("which account would you like to make a deposit?");
			for (Integer i : li) {
				o(i + "");
			}
			b();
			s = sc.nextLine();
			b();
			try {
				if (InputValidation.validateId(s, li)) {
					Account activeAccount = adao.getAccountById(Integer.parseInt(s));
					oln(activeAccount.toString());
					oln("how much would you like to deposit?");
					s = sc.nextLine();
					b();
					if (InputValidation.validateMoney(s)) {
						as.modifyBalance(activeAccount.getId(), activeAccount.getBalance().add(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", "")))));
						activeAccount.setBalance(activeAccount.getBalance().add(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", "")))));
						oln("new balance for " + activeAccount.toString());
						Transaction tr = new Transaction();
						tr.setFrom_id(activeAccount.getId());
						tr.setAmount(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", ""))));
						tr.setType(1);
						tr.setDate(new Date());
						oln(tr.toString());
						tdao.addWithdrawalOrDeposit(tr);
					}
				}
			} catch (BusinessException e) {
				oln(e.getMessage());
			}
		} else {
			oln ("you have no active accounts");
		}
	}
	public static int displayTransfer() { // displays post/approve transfer menu
		String s;
		int ch = -1;
		
		oln("would you like to post or approve a transfer?");
		o("1 - post");
		oln("2 - approve");
		s = sc.nextLine();
		b();
			try {
				if (InputValidation.validateStringToInt(s, 1, 2)) {
					ch = Integer.parseInt(s);
				}
			} catch (BusinessException e) {
				errorln(e.getMessage());
			}
		return ch;
	}
	public static void displayPostTransfer(int id) { // displays post transfer prompt
		String s;
		List<Integer> li = as.getActivatedCustAccountIds(id);
		
		if (li.size() != 0) {
			oln("which account would you like to transfer from?");
			for (Integer i : li) {
				o(i + "");
			}
			b();
			s = sc.nextLine();
			b();
			try {
				if (InputValidation.validateId(s, as.getActivatedCustAccountIds(id))) {
					Account activeAccount = adao.getAccountById(Integer.parseInt(s));
					oln(activeAccount.toString());
					o("enter the account number you would like to transfer to:");
					s = sc.nextLine();
					if (InputValidation.validateId(s, adao.getAllAccountIds()) && Integer.parseInt(s) != activeAccount.getId()) {
						int toId = Integer.parseInt(s);
						o("how much would you like to transfer?");
						s = sc.nextLine();
						b();
						if (InputValidation.validateMoney(s) && BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", ""))).compareTo(activeAccount.getBalance()) <= 0) {
							as.modifyBalance(activeAccount.getId(), activeAccount.getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", "")))));
							activeAccount.setBalance(activeAccount.getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", "")))));
							oln("new balance for " + activeAccount.toString());
							Transaction tr = new Transaction();
							tr.setFrom_id(activeAccount.getId());
							tr.setTo_id(toId);
							tr.setAmount(BigDecimal.valueOf(Double.parseDouble(s.replaceAll("[$]", ""))));
							tr.setType(2);
							tr.setDate(new Date());
							oln(tr.toString());
							tdao.addTransfer(tr, toId);
						} else {
							oln("can't transfer more than $" + activeAccount.getBalance());
						}
					} else {
						oln("can't post transfer to the same account");
					}
				}
			} catch (BusinessException e) {
				oln(e.getMessage());
			}
		} else {
			oln ("you have no active accounts");
		}
	}
	public static void displayApproveTransfer(int id) { // displays approve transfer prompt
		String s;
		List<Transaction> transfers = tdao.getPendingTransfers(id);
		Transaction activeTransaction;
		
		if (transfers.size() != 0) {
			oln("here are your pending transfers:");
			for (Transaction t : transfers) {
				o(t.toString());
			}
			b();
			o("enter transfer id to approve or reject:");
			s = sc.nextLine();
			try {
				if (InputValidation.validateTransferId(s, transfers)) {
					activeTransaction = tdao.getTransactionFromId(Integer.parseInt(s));
					oln(activeTransaction.toString());
					oln("would you like to approve this transfer? (y/n)");
					try {
						s = sc.nextLine();
						b();
						if (InputValidation.validateYN(s)) {
							if (s.equalsIgnoreCase("y")) {
								as.modifyBalance(activeTransaction.getTo_id(), adao.getAccountById(activeTransaction.getTo_id()).getBalance().add(activeTransaction.getAmount()));
								oln(adao.getAccountById(activeTransaction.getTo_id()).toString());
								tdao.approveTransfer(activeTransaction);
							} else {
								as.modifyBalance(activeTransaction.getFrom_id(), adao.getAccountById(activeTransaction.getFrom_id()).getBalance().add(activeTransaction.getAmount()));
								oln(adao.getAccountById(activeTransaction.getFrom_id()).toString());
								tdao.rejectTransfer(activeTransaction);
							}
						}
					} catch (BusinessException e) {
						errorln(e.getMessage());
					}
				}
			} catch (BusinessException e) {
				errorln(e.getMessage());
			}
		} else {
			oln("you have no pending transfers");
		}
	}
	public static void displayApproveCustomer(int id) {
		String s;
		List<Account> pending = adao.getPendingAccounts();
		List<Integer> pendingId = new ArrayList<Integer>();
		for (Account a : pending) {
			pendingId.add(a.getId());
		}
		if (pending.size() != 0) {
			oln("here are the pending accounts:");
			for (Account a : pending) {
				o(a.toString());
			}
			b();
			o("enter account id to approve or reject:");
			s = sc.nextLine();
			try {
				if (InputValidation.validateId(s, pendingId)) {
					Account activeAccount = adao.getAccountById(Integer.parseInt(s));
					oln(activeAccount.toString());
					oln("would you like to approve this account?");
					try {
						s = sc.nextLine();
						b();
						if (InputValidation.validateYN(s)) {
							if (s.equalsIgnoreCase("y")) {
								adao.activate(activeAccount.getId(), id);
								oln(adao.getAccountById(activeAccount.getId()).toString() + " has been approved");
							} else {
								adao.deleteAccount(activeAccount.getId());
								oln("account rejected");
							}
						}
					} catch (BusinessException e) {
						errorln(e.getMessage());
					}
				}
			} catch (BusinessException e) {
				errorln(e.getMessage());
			}
		} else {
			oln("there are no pending accounts");
		}
	}
	public static void displayViewCustomer() {
		String s;
		List<Integer> custIds = udao.getAllCustomerIds();
		List<Integer> accountIds;
		
		oln("enter customer id:");
		s = sc.nextLine();
		b();
		try {
			if (InputValidation.validateId(s, custIds)) {
				accountIds = adao.getAccountIdsByCustId(Integer.parseInt(s));
				for (Integer i : accountIds) {
					o(i + "");
				}
				b();
				oln("enter account id:");
				s = sc.nextLine();
				b();
				try {
					if (InputValidation.validateId(s, accountIds)) {
						oln(adao.getAccountById(Integer.parseInt(s)).toString());
					}
				} catch (BusinessException e) {
					errorln(e.getMessage());
				}
			}
		} catch (BusinessException e) {
			errorln(e.getMessage());
		}
	}
	public static void displayLogs() {
		oln("full transaction log:");
		for (Transaction t : tdao.getAllTransactions()) {
			o(t.toString());
		}
		b();
	}
	public static void displayBye() { // displays bye message
		oln(displayBorder('-'));
		oln("thank you for banking with BIG BUCK$ BANK!!");
	}
	// UTILITY
	private static String centerString(String s) { // center string 's' within the display
		if (s.length() >= LENGTH) {
			return s;
		} else {
			StringBuilder sb = new StringBuilder();
			
			for (int a = 1; a <= (LENGTH - s.length()) / 2; a++) {
				sb.append(' ');
			}
			return sb.append(s).toString();
		}
	}	
	private static String displayBorder(char c) { // displays a border of char 'c' within the display
		StringBuilder sb = new StringBuilder();
		
		for (int a = 1; a <= LENGTH; a++) {
			sb.append(c);
		}
		return sb.toString();
	}
	// OUTPUT
	public static void b() { // outputs new line
		log.info("");
	}
	public static void oln(String s) { // outputs string 's' with new line
		log.info(s);
		log.info("");
	}
	public static void o(String s) { // outputsstring 's' without new line
		log.info(s);
	}
	public static void errorln(String s) { // error outputs string 's'
		log.error(s);
		log.info("");
	}
}