package com.atm;

/**
 * Abstract account for asset accounts such as chequing and savings.
 */
public abstract class AssetAccounts extends Account {

	/**
	 * Balance of this account in terms of pennies.
	 */
	private int balance;

	/**
	 * Creates an asset account.
	 *
	 * @param user CustomerUser
	 */
	AssetAccounts(CustomerUser user) {
		super(user);
	}

	/**
	 * Return the balance of this asset account in terms of pennies.
	 *
	 * @return int
	 */
	@Override
	public int getBalance() {
		return balance;
	}

	/**
	 * Puts money into this account in terms of pennies.
	 *
	 * @param amount int
	 */
	@Override
	public void moneyIn(int amount) {
		balance += amount;
	}

	/**
	 * Take money out of this account in terms of pennies.
	 *
	 * @param amount int
	 */
	@Override
	public void moneyOut(int amount) {
		balance -= amount;
	}

	/**
	 * Abstract method of whether a certain amount can be withdrawn from this account
	 *
	 * @param amount int
	 * @return boolean
	 */
	@Override
	public abstract boolean canTakeOut(int amount);

	/**
	 * setter (helper) for when bank needs to compound interest at beginning of month.
	 *
	 * @param amount int
	 */
	@Override
	public void setBalance(int amount) {
		balance = amount;
	}
}
