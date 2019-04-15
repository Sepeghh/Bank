package com.atm;

/**
 * A credit account.
 */

public class CreditAccount extends DebtAccounts {

	/**
	 * Constructs a credit account.
	 *
	 * @param user The user to associate this account with.
	 */
	CreditAccount(CustomerUser user) {
		super(user);
	}

	/**
	 * Takes money out of an account however because this is a credit account nothing can be taken out.
	 *
	 * @param amount Amount to withdraw from this account.
	 */
	public void moneyOut(int amount) {
	}

	/**
	 * Returns what type of account this is
	 * @return String
	 */
	public String getType(){
		return AccountsFactory.CREDIT_CARD;
	}

	/**
	 * ToString method
	 * @return String
	 */
	@Override
	public String toString() {
		return getType() + ": " + super.toString();
	}
}