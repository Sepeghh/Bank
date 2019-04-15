package com.atm;

/**
 * class for accounts factory to generate accounts.
 */

public class AccountsFactory {

    public static final String CHEQUING = "CHEQUING";
    public static final String SAVINGS = "SAVINGS";
    public static final String CREDIT_CARD = "CREDIT_CARD";
    public static final String LINE_OF_CREDIT = "LINE_OF_CREDIT";
    public static final String US = "US";

    /**
     * Create an account for a specified user.
     *
     * @param accountType String The type of account to create.
     * @param user CustomerUser The Customer to create the account for.
     * @return Account
     */
    public Account createAccount(String accountType, CustomerUser user, CustomerUser user1) {
        Account newAccount;
        if (accountType.equalsIgnoreCase(AccountsFactory.CREDIT_CARD)) {
            newAccount = new CreditAccount(user);
        } else if (accountType.equalsIgnoreCase(AccountsFactory.LINE_OF_CREDIT)) {
            newAccount = new LineOfCreditAccount(user);
        } else if (accountType.equalsIgnoreCase(AccountsFactory.CHEQUING)) {
            newAccount = new ChequingAccount(user);
        } else if (accountType.equalsIgnoreCase(AccountsFactory.SAVINGS)) {
            newAccount = new SavingsAccount(user);
        } else // if (accountType.equalsIgnoreCase("US")){
        {
            newAccount = new USAccount(user);
        }
        if (user1 != null) {
            //Add joint student account
            return new JointAccount(newAccount, user1);
        } else {
            //Add a simple account
            return newAccount;
        }
    }
}
