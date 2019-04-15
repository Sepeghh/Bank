package com.atm;

/**
 * A chequing Account.
 */
public class ChequingAccount extends AssetAccounts {

	/**
	 * Creates a new chequing account.
	 *
	 * @param user Customer to associate this account with.
	 */
	public ChequingAccount(CustomerUser user) {
		super(user);
	}

	/**
	 * Method that returns true if the user can take out the amount requested and false otherwise.
	 *
	 * @param amount Amount to check if this account can withdraw.
	 * @return If this account has enough to withdraw this amount.
	 */
	public boolean canTakeOut(int amount) {
		if (getBalance() >= 0) {
			return ((getBalance() - amount) >= -10000);
		} else {
			return false;
		}
	}

	/**
	 * Returns what type of account this is
	 * @return String
	 */
	public String getType(){
		return AccountsFactory.CHEQUING;
	}

	/**
	 *ToString method
	 * @return String
	 */
	@Override
	public String toString() {
		return getType() + ": " + super.toString();
	}
}
