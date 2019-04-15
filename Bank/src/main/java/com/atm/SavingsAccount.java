package com.atm;

/**
 * A savings account.
 */
public class SavingsAccount extends AssetAccounts {

	/**
	 * Constructs a savings account.
	 *
	 * @param user The customer to connect this savings account to.
	 */
	SavingsAccount(CustomerUser user) {
		super(user);
	}

	/**
	 * Method that returns true if the user can take out the amount requested and false otherwise.
	 *
	 * @param amount Amount to check if this account can be withdrawn from.
	 * @return If the funds can be taken out of this account.
	 */
	public boolean canTakeOut(int amount) {
		if (getBalance() >= 0) {
			return ((getBalance() - amount) >= 0);
		} else {
			return false;
		}
	}

	/**
	 * Returns what type of account this is
	 * @return String
	 */
	public String getType(){
		return AccountsFactory.SAVINGS;
	}

	/**
	 * ToString method.
	 * @return String
	 */
	@Override
	public String toString() {
		return getType() + ": " + super.toString();
	}
}
