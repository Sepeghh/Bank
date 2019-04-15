package com.atm;

/**
 * Debt Accounts that includes CreditAccounts and LineOfCreditAccounts.
 */
public abstract class DebtAccounts extends Account {
	/**
	 * Balance of this account in terms of pennies
	 */
	private int balance;

	/**
	 * Constructor a debt account.
	 *
	 * @param user The customer to associate this account with.
	 */
	DebtAccounts(CustomerUser user) {
		super(user);
	}

	/**
	 * getter for account balance.
	 * if user owes money, balance is positive, if overpays, balance is negative.
	 *
	 * @return The amount of the balance for this account.
	 */
	@Override
	public int getBalance() {
		return (-balance);
	}

	/**
	 * method to increase balance from transfers.
	 *
	 * @param amount Adds funds to the account.
	 */
	@Override
	public void moneyIn(int amount) {
		balance += amount;
	}

	/**
	 * Returns whether an amount can be taken out, always false for debt accounts.
	 *
	 * @param amount Amount to withdraw from the account (with limit of 2000 dollars).
	 * @return If this account can withdraw this amount.
	 */
	@Override
	public boolean canTakeOut(int amount) {
		return ((balance - amount) >= -200000);
	}

	/**
	 * Take money out of this account.
	 *
	 * @param amount Amount to remove from the account.
	 */
	@Override
	public void moneyOut(int amount) {
		balance -= amount;
	}

	/**
	 * setter (helper) for when bank needs to compound interest at beginning of month.
	 *
	 * @param amount Amount to set this accounts balance to.
	 */
	@Override
	public void setBalance(int amount) {
		balance = amount;
	}
}
