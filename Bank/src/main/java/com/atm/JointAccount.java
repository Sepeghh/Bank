package com.atm;

/**
 * A joint account shared by two users
 */
public class JointAccount extends Account{

    /**
     * User this joint account is shared with
     */
    private final CustomerUser user2;

    /**
     * Balance of this joint account in pennies
     */
    private int balance;

    /**
     * Original account that is now joint
     */
    private final Account acc;

    /**
     * Create a joint account given an account and user to share it with
     * @param acc Account
     * @param user CustomerUser
     */
    JointAccount(Account acc, CustomerUser user) {
        super(acc.getUser());
        user2 = user;
        this.acc = acc;
        this.balance = acc.getBalance();
    }

    /**
     * Gets the balance for this account in pennies
     * @return int
     */
    @Override
    public int getBalance() {
        return balance;
    }

    /**
     * Returns whether given amount in pennies can be taken out of the account
     * @param amount int
     * @return boolean
     */
    @Override
    public boolean canTakeOut(int amount) {
        return acc.canTakeOut(amount);
    }

    /**
     * Takes a given amount in pennies out of this account
     * @param amount int
     */
    @Override
    public void moneyOut(int amount) {
        balance -= amount;
    }

    /**
     * Puts a given amount in pennies into this account
     * @param amount int
     */
    @Override
    public void moneyIn(int amount) {
        balance += amount;
    }

    /**
     * Sets the balance of this account in pennies
     * @param amount int
     */
    public void setBalance(int amount) {
        balance += amount;
    }

    /**
     * Gets the second owner of this joint account
     * @return CustomerUser
     */
    private CustomerUser getUser2() {
        return user2;
    }

    /**
     * Returns what type of account this is.
     * @return String
     */
    public String getType(){
        return acc.getType();
    }

    /**
     * ToString method.
     * @return String
     */
    @Override
    public String toString() {
        return "Joint: " + acc.getType() + ": " + super.toString() + " owned by " + getUser() + " and " + getUser2();
    }
}
