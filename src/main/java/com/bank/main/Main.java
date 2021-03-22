package com.bank.main;

import com.bank.model.User;
import com.bank.model.enums.TransactionType;
import com.bank.model.enums.UserType;
import com.bank.util.Menu;

public class Main {
	public static void main(String[] args) {
		int ch;
		User activeUser;
		
		do {
			Menu.displaySign("BIG BUCK$ BANK");
			ch = Menu.displayMain();
			switch (ch) {
			case 1:
				activeUser = Menu.displayLogin();
				if (activeUser != null) {
					do {
						switch (UserType.values()[activeUser.getUt()]) {
						case CUSTOMER:
							Menu.displaySign("BBB Customer Menu");
							ch = Menu.displayCustomerMenu();
							switch (ch) {
							case 1: 
								Menu.displayApplyAccount(activeUser.getId());
								break;
							case 2:
								Menu.displayAccountBalances(activeUser.getId());
								break;
							case 3:
								ch = Menu.displayTransactionMenu();
								if (ch >= 1 && ch <= 2) {
									switch(TransactionType.values()[ch - 1]) {
									case WITHDRAWAL: 
										Menu.displayWithdraw(activeUser.getId());
										break;
									case DEPOSIT:
										Menu.displayDeposit(activeUser.getId());
										break;
									default: break;
									}
								}
								break;
							case 4:
								ch = Menu.displayTransfer();
								switch(ch) {
								case 1: 
									Menu.displayPostTransfer(activeUser.getId());
									break;
								case 2:
									Menu.displayApproveTransfer(activeUser.getId());
									break;
								}
								break;
							}
							break;
						case EMPLOYEE:
							Menu.displaySign("BBB Employee Menu");
							ch = Menu.displayEmployeeMenu();
							switch (ch) {
							case 1: 
								Menu.displayApproveCustomer(activeUser.getId());
								break;
							case 2: 
								Menu.displayViewCustomer();
								break;
							case 3: 
								Menu.displayLogs();
								break;
							}
							break;
						}
					} while (ch != 0);
				}
				ch = -1;
				break;
			case 2:
				Menu.displayRegister();
				break;
			case 0:
				Menu.displayBye();
				break;
			}
		} while (ch != 0);
	}
}