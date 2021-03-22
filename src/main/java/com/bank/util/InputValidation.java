package com.bank.util;

import java.util.List;

import com.bank.exceptions.BusinessException;
import com.bank.model.Transaction;
import com.bank.model.enums.States;

public class InputValidation {
	public static boolean validateStringToInt(String s) throws BusinessException { // validates string s to int by regex
		if (s.matches("^([0]|[1-9][0-9]*)$")) {
			return true;
		} else {
			throw new BusinessException("input is invalid");
		}
	}
	public static boolean validateStringToInt(String s, int lb, int ub) throws BusinessException { // validates string s to int by regex, between lb and ub
		if (validateStringToInt(s)) {
			int i = Integer.parseInt(s);
			if (i >= lb && i <= ub) {
				return true;
			} else {
				throw new BusinessException("input is not in valid range of " + lb + " and " + ub);
			}
		} else {
			throw new BusinessException("input is invalid");
		}
	}
	public static boolean validateStateAbbreviation(String s) throws BusinessException { // validates state string s by regex
		boolean matchFound = false;
		int i = 0;
		
		while (!matchFound && i < States.values().length) {
			if (s.toUpperCase().matches(States.values()[i].name())) {
				return true;
			} else {
				i++;
			}
		}
		
		throw new BusinessException("state input is invalid");
	}
	public static boolean validateZip(String s) throws BusinessException { // validates zip code s
		if (s.matches("[0-9]{5}")) {
			return true;
		} else {
			throw new BusinessException("zip code is invalid");
		}
	}
	public static boolean validatePhone(String s) throws BusinessException { // validates phone number s (###-###-####)
		if (s.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}")) {
			return true;
		} else {
			throw new BusinessException("phone number is invalid");
		}
	}
	public static boolean validateYN(String s) throws BusinessException {
		if (s.matches("[ynYN]")) {
			return true;
		} else {
			throw new BusinessException("input is not 'y' or 'n'");
		}
	}
	public static boolean validateMoney(String s) throws BusinessException { // validates dollar amount
		if (!s.isBlank() && s.matches("^\\$?(([1-9][0-9]+)|([0-9]?))(\\.[0-9]{2})?$")) {
			return true;
		} else {
			throw new BusinessException("input is not valid dollar amount");
		}
	}
	public static boolean validateId(String s, List<Integer> acc) throws BusinessException {
		for (Integer a : acc) {
			if (s.equals(a + "")) {
				return true;
			}
		}
		throw new BusinessException("id does not match any accounts");
	}
	public static boolean validateTransferId(String s, List<Transaction> tr) throws BusinessException {
		for (Transaction t : tr) {
			if (s.equals(t.getId() + "")) {
				return true;
			}
		}
		throw new BusinessException("id does not match any transfers");
	}
}