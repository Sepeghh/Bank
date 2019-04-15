package com.atm;

/**
 * A line of credits account.
 */
public class LineOfCreditAccount extends DebtAccounts {

	/**
	 * Constructs a line of credit account.
	 *
	 * @param user Customer account to associate this credit account to.
	 */
	LineOfCreditAccount(CustomerUser user) {
		super(user);
	}

	/**
	 * Returns what type of account this is
	 * @return String
	 */
	public String getType(){
		return AccountsFactory.LINE_OF_CREDIT;
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
