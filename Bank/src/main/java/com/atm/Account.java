package com.atm;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Abstract class Account for all the accounts.
 */
public abstract class Account {

	/**
	 * A unique sequence for every account.
	 */
	private UUID accountNumber = UUID.randomUUID();

	/**
	 * Time of account creation.
	 */
	private final LocalDateTime dateOfCreation;

	/**
	 * User who owns this account.
	 */
	private final CustomerUser user;

	/**
	 * Creates a bank account with account number and date of creation for a single user.
	 *
	 * @param user User who owns this account.
	 */
	Account(CustomerUser user) {
		this.user = user;
		dateOfCreation = ATM.getTime();
	}

	/**
	 * Return account number for this account.
	 *
	 * @return int
	 */
	public String getName() {
		return accountNumber.toString();
	}

	/**
	 * abstract method for getting account balance.
	 */
	public abstract int getBalance();

	/**
	 * Return date of creation of this account.
	 */
	private LocalDateTime getCreationDate() {
		return this.dateOfCreation;
	}

	/**
	 * Set this account number.
	 *
	 * @param sequence String
	 */
	void setAccountNumber(String sequence) {
		this.accountNumber = UUID.fromString(sequence);
	}

	/**
	 * Get the user this account belongs to.
	 *
	 * @return CustomerUser
	 */
	public CustomerUser getUser() {
		return user;
	}

	/**
	 * Abstract method returns if this account can remove a specified amount.
	 *
	 * @param amount int
	 * @return boolean
	 */
	public abstract boolean canTakeOut(int amount);

	/**
	 * Abstract method that takes a specified amount out of this account.
	 *
	 * @param amount int
	 */
	public abstract void moneyOut(int amount);

	/**
	 * Abstract method that puts a specified amount of money in this account.
	 *
	 * @param amount int
	 */
	public abstract void moneyIn(int amount);

	/**
	 * setter (helper) for when bank needs to compound interest at beginning of month.
	 *
	 * @param amount int
	 */
	public abstract void setBalance(int amount);

	/**
	 * Returns a string stating the account type
	 * @return String
	 */
	public abstract String getType();

	/**
	 * Return a string representing this account
	 *
	 * @return String
	 */
	public String toString() {
		return this.getName() + ": Balance: $" + this.getBalance() / 100 + "." + this.getBalance() % 100 + " Created on: " + this.getCreationDate().toString();
	}
}


