package com.atm;

/**
 * A factory class for user accounts.
 */
public class UserFactory {

    public static final String CUSTOMER_USER = "CUSTOMER_USER";
    public static final String BANK_TELLER = "BANK_TELLER";
    public static final String BANK_MANAGER = "BANK_MANAGER";

    /**
     * Creates and returns a user.
     * @param username String
     * @param password String
     * @param question String
     * @param answer String
     * @return CustomerUser
     */
    public User createUser(String type, String username, String password, String question, String answer, boolean isStudent){
        AccountsFactory factory = new AccountsFactory();
        if(type.equalsIgnoreCase(UserFactory.CUSTOMER_USER)){
            CustomerUser tempUser = new CustomerUser(username, password, question, answer, isStudent);
            tempUser.setPrimaryAccount(factory.createAccount(AccountsFactory.CHEQUING, tempUser, null));
            return tempUser;
        } else if(type.equalsIgnoreCase(UserFactory.BANK_TELLER)){
            BankTellerUser tempUser = new BankTellerUser(username, password, question, answer);
            tempUser.setPrimaryAccount(factory.createAccount(AccountsFactory.CHEQUING, tempUser, null));
            return tempUser;
        } else if(type.equalsIgnoreCase(UserFactory.BANK_MANAGER)){
            return new BankManagerUser(username, password, question, answer);
        } else {
            return null;
        }
    }
}
