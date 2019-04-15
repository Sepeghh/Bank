package com.atm;

import com.atm.GUI.*;

/**
 * A Bank Manager
 */
public class BankManagerUser extends User {

	/**
	 * Construct a new BankManager.
	 *
	 * @param username this BankManager username.
	 * @param password this BankManager password.
	 */
	public BankManagerUser(String username, String password,String securityQuestion, String securityAnswer) {
		super(username, password,securityQuestion,securityAnswer);
	}

	/**
	 * Calls this users options screen on login
	 * @param atm atm being run
	 */
	public void getOptionsScreen(ATM atm){
		BankManagerUserOptionsScreen optionsScreen = new BankManagerUserOptionsScreen(this, atm);
		optionsScreen.initialOptionScreen();
	}
}









