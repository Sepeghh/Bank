package com.atm;

/**
 * A bank account stored in US dollars
 */
public class USAccount extends Account {

    /**
     * Balance of this account in pennies
     */
    private int balance;

    /**
     * Creates a US bank account
     * @param user User this account belongs to
     */
    USAccount(CustomerUser user) {
        super(user);
    }

    /**
     * Returns that a user cannot withdraw US cash because there is no US dollars in the ATM
     * @param amount int
     * @return boolean
     */
    @Override
    public boolean canTakeOut(int amount) {
        return false;
    }

    /**
     * Takes money out of this account
     * @param amount int
     */
    @Override
    public void moneyOut(int amount) {
        balance -= amount;
    }

    /**
     * Return the balance of this account in pennies
     * @return int
     */
    @Override
    public int getBalance() {
        return balance;
    }

    /**
     * puts money in pennies in this account
     * @param amount int
     */
    @Override
    public void moneyIn(int amount) {
        balance += amount;
    }

    /**
     * Sets the balance of this account in pennies
     * @param balance int
     */
    @Override
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Gets this accounts type
     * @return String
     */
    public String getType() {
        return AccountsFactory.US;
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
