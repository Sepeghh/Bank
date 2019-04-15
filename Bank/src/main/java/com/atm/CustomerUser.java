package com.atm;

import com.atm.GUI.*;


/**
 * A customer of the banks user account.
 */
public class CustomerUser extends User {

	/**
	 * This users primary bank account for deposits.
	 */
	private Account primaryAccount;

	/**
	 * Whether this user is a student and does not pay account fees
	 */
	private boolean isStudent;

	/**
	 * Creates a customer user account
	 *
	 * @param username The username of the user.
	 * @param password The password of the user.
	 */
	public CustomerUser(String username, String password, String securityQuestion, String securityAnswer, boolean isStudent) {
		super(username, password,securityQuestion,securityAnswer);
		this.isStudent = isStudent;
	}

	/**
	 * Sets whether this user is a student
	 * @param student boolean
	 */
	public void setStudent(boolean student) {
		isStudent = student;
	}

	/**
	 * Return whether this user is a student
	 * @return boolean
	 */
	public boolean isStudent(){
		return isStudent;
	}

	/**
	 * Return the primary account for this user.
	 *
	 * @return The primary account for this user.
	 */
	public Account getPrimaryAccount() {
		return this.primaryAccount;
	}

	/**
	 * Set the primary account for this user
	 *
	 * @param acc Account name of account to be made primary
	 */
	public void setPrimaryAccount(Account acc) {
		this.primaryAccount = acc;
	}

	/**
	 * Return the string representing this user and their accounts
	 *
	 * @return String representation of the CustomerUser
	 */
	public String toString() {
		return getUsername();
	}

	/**
	 * Calls this users options screen on login
	 * @param atm atm being run
	 */
	public void getOptionsScreen(ATM atm){
		CustomerUserOptionsScreen optionScreen = new CustomerUserOptionsScreen(this, atm);
		optionScreen.initialOptionScreen();
	}
}