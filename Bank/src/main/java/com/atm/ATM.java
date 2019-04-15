package com.atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * An Automated Teller Machine
 */
public class ATM {

    /**
     * The user currently interacting with the ATM
     */
    private User currentUser;

    /**
     * Cash manager for this ATM
     */
    private final CashManager cash;

    /**
     * This ATM's current time
     */
    private static LocalDateTime time;

    /**
     * The accounts manager for this ATM
     */
    private final BankAccountsManager accounts;

    /**
     * The transactions manager for this ATM
     */
    private final TransactionsManager transactions;

    /**
     * The user account manager for this ATM.
     */
    private final UserManager users;

    /**
     * Creates an ATM and instantiates all of its managers so it has access to users and accounts
     */
    public ATM(Connection conn){
        //sets ATM time
        time = LocalDateTime.now();

        //adds appropriate managers
        users = new UserManager(conn);
        cash = new CashManager();
		accounts = new BankAccountsManager(conn, this);
		transactions = new TransactionsManager(conn);
        //if first day of the month apply interest to savings accounts.
        checkForFirstOfMonth();

        //add an admin to the ATM so it can always be accessed even if files are missing/damaged.
        addManager();
    }

    /**
     * Set the time for the ATM
     * @param time String
     * @return boolean
     */
    public static boolean setATMTime(String time) {
        String[] date = time.split(" ");
        try {
            int year = Integer.valueOf(date[0]);
            int month = Integer.valueOf(date[1]);
            int day = Integer.valueOf(date[2]);
            int hour = Integer.valueOf(date[3]);
            int minute = Integer.valueOf(date[4]);
            int second = Integer.valueOf(date[5]);
            ATM.time = LocalDateTime.of(year, month, day, hour, minute, second, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * return this ATM's current time
     *
     * @return LocalDateTime
     */
    public static LocalDateTime getTime() {
        return time;
    }


    /**
     * Set the current user of the ATM
     * @param user User
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Checks if its the first of the month and compounds savings account if it is.
     */
    public void checkForFirstOfMonth() {
        if (getTime().getDayOfMonth() == 1) {
            getAccounts().compoundInterest();
            getUsers().monthlyBankFee();
        }
    }

    /**
     * return this ATM's current user
     * @return User
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Return this ATM's user account manager.
     */
    public UserManager getUsers() {
        return users;
    }

    /**
     * Return this ATM's cash manager.
     *
     * @return CashManager
     */
    public CashManager getCashManager() {
        return cash;
    }

    /**
     * Return this ATM's accounts manager.
     *
     * @return AccountsManager
     */
    public BankAccountsManager getAccounts() {
        return accounts;
    }

    /**
     * Returns this ATM's transaction manager.
     *
     * @return TransactionsManager
     */
    public TransactionsManager getTransactions() {
        return transactions;
    }

    /**
     * Adds a default bank manager to the ATM.
     */
    private void addManager() {
        if(getUsers().usernameIsAvailable("admin")) {
            BankManagerUser manager = new BankManagerUser("admin", "admin", "admin", "admin");
            getUsers().addAdminAccount(manager);
        }
    }

    /**
     * View the alerts for the cash manager to see such as cash being low.
     */
    public ArrayList<String> viewAlerts() {
        ArrayList<String> alerts = new ArrayList<>();
        if (new File("./alerts.txt").exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("./alerts.txt"));
                String line = reader.readLine();
                while (line != null) {
                    alerts.add(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        return alerts;
    }
}

