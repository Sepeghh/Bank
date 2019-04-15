package com.atm;

import com.atm.GUI.*;

/**
 * A bank teller with limited access.
 * this user can also have their own bank accounts
 */
public class BankTellerUser extends CustomerUser {

    /**
     * Creates a bank teller
     * @param username String
     * @param password String
     */
    BankTellerUser(String username, String password,String securityQuestion, String securityAnswer){
        super(username, password,securityQuestion,securityAnswer, false);
    }

    /**
     * Calls this users option screen on login
     * @param atm ATM
     */
    @Override
    public void getOptionsScreen(ATM atm){
        //Create the Screen
        BankTellerOptionScreen optionScreen = new BankTellerOptionScreen(this, atm);
        //Makes options to display
        optionScreen.initialOptionScreen();
    }
}

