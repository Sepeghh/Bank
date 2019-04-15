package com.atm.GUI;

import com.atm.ATM;
import com.atm.CurrencyExchange;
import com.atm.CustomerUser;
import com.atm.User;
import javax.swing.*;
import java.awt.*;

/**
 * Bank tellers interface with the ATM
 */
public class BankTellerOptionScreen extends CustomerUserOptionsScreen {

    private final JButton changePassword = new JButton("Change Password");
    private final JButton viewUserAccountSummary = new JButton("View And Edit Accounts");
    private final JButton transactions = new JButton("Transactions");
    private final JButton requestAnAccount = new JButton("Request An Account");
    private final JButton payBill = new JButton("Pay A Bill");
    private final JButton withdraw = new JButton("Withdraw");
    private final JButton deposit = new JButton("Deposit");
    private final JButton recoverCustomerPassword = new JButton("Reset User Password");
    private final JButton currencyConverter = new JButton("Currency Converter");
    private final JButton logout = new JButton("Logout");

    /**
     * Object for converting currency
     */
    private final CurrencyExchange currencyExchange = new CurrencyExchange();

    /**
     * Creates a bank tellers option screen
     * @param user User interacting currently with the ATM
     * @param atm current ATM system being interacted with
     */
    public BankTellerOptionScreen(User user, ATM atm) {
        super(user, atm);
    }

    /**
     * Bank tellers initial options screen
     */
    @Override
    public void initialOptionScreen() {
        JFrame screen = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //User wants to change their password
        constraints.gridx = 0;
        constraints.gridy = 0;
        changePassword.setPreferredSize(new Dimension(200, 40));
        panel.add(changePassword, constraints);
        changePassword.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            changePasswordScreen();
        });

        //User wants to view account summary for a user
        constraints.gridx = 1;
        viewUserAccountSummary.setPreferredSize(new Dimension(200, 40));
        panel.add(viewUserAccountSummary, constraints);
        viewUserAccountSummary.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            doAction("VIEW_ACCOUNT");
        });

        //User wants to view the transactions option screen
        constraints.gridx = 0;
        constraints.gridy = 1;
        transactions.setPreferredSize(new Dimension(200, 40));
        transactions.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            doAction( "TRANSFER" );
        });
        panel.add(transactions, constraints);

        //shows users request account screen
        constraints.gridx = 1;
        requestAnAccount.setPreferredSize(new Dimension(200, 40));
        requestAnAccount.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            doAction( "REQUEST_ACCOUNT" );
        });
        panel.add(requestAnAccount, constraints);

        //Shows users pay bill screen
        constraints.gridx = 0;
        constraints.gridy = 2;
        payBill.setPreferredSize(new Dimension(200, 40));
        payBill.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            doAction( "PAY_BILL" );
        });
        panel.add(payBill, constraints);

        //User wants to withdraw
        constraints.gridx = 1;
        withdraw.setPreferredSize(new Dimension(200, 40));
        withdraw.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            doAction( "WITHDRAW"  );
        });
        panel.add(withdraw, constraints);

        //User wants to deposit
        constraints.gridx = 0;
        constraints.gridy = 3;
        deposit.setPreferredSize(new Dimension(200, 40));
        deposit.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            doAction("DEPOSIT" );
        });
        panel.add(deposit, constraints);

        //User wants to recover someones password
        constraints.gridx = 1;
        recoverCustomerPassword.setPreferredSize(new Dimension(200, 40));
        recoverCustomerPassword.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            recoverCustomerPassword();
        });
        panel.add(recoverCustomerPassword, constraints);

        //User wants to view currency conversion
        constraints.gridy = 4;
        constraints.gridx = 0;
        currencyConverter.setPreferredSize(new Dimension(200, 40));
        currencyConverter.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            currencyConverter();
        });
        panel.add(currencyConverter, constraints);

        //User wants to log out, calls login screen
        constraints.gridx = 1;
        logout.setPreferredSize(new Dimension(200, 40));
        logout.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            GUI.logout();
        });
        panel.add(logout, constraints);

        screen.add(panel);
        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.pack();
        //Display this frame
        screen.setVisible(true);
        screen.setLocationRelativeTo(null);
    }

    /**
     * Screen for resetting a users password
     */
    private void recoverCustomerPassword() {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //Ask for the Username of the Customer who wants to recover their Account
        JLabel getUserName = new JLabel("Please enter the Customer UserName: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(getUserName, constraints);
        //get the Username of the Customer who wants to recover their Account
        JTextField username = new JTextField(10);
        constraints.gridx = 1;
        panel.add(username, constraints);

        JButton submit = new JButton("Submit");

        submit.addActionListener(e -> {
            User tempUser = atm.getUsers().getUserAccount(username.getText());
            if (tempUser == null) {
                showMessage( "Can't find the User" );
                panel.repaint();
            } else {
                //if User with such Username exist
                //check the Security Questions
                checkSQ(tempUser);
                //close this frame
                frame.setVisible(false);
                frame.dispose();
            }

        });
        constraints.gridy = 3;
        constraints.gridx = 0;
        submit.setPreferredSize(new Dimension(150, 30));
        panel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            this.user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 1;
        exit.setPreferredSize(new Dimension(150, 30));
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        //Display this frame
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Helper method for resetting password, checks the security questions
     * @param tempUser Checks users security questions
     */
    private void checkSQ(User tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //Shows the Security Question
        JLabel securityQuestion = new JLabel("Security Question: " + tempUser.getSecurityQuestion());
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(securityQuestion, constraints);

        //Ask for the answer of the Security Question
        JLabel enterSQ = new JLabel("Please Enter the Answer to the Security Question:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(enterSQ, constraints);
        //Get the answer for the Security Question
        JTextField securityAnswer = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(securityAnswer, constraints);

        JButton submit = new JButton("Submit");

        submit.addActionListener(e1 -> {
            if (securityAnswer.getText().trim().equalsIgnoreCase(tempUser.getSecurityAnswer().trim())) {
                //if the answer to the Security Question is correct
                setPassword(tempUser);
                frame.setVisible(false);
                frame.dispose();
            } else {
                showMessage( "Incorrect Answer, please try Again" );
                panel.repaint();
            }

        });
        constraints.gridy = 3;
        constraints.gridx = 0;
        submit.setPreferredSize(new Dimension(150, 30));
        panel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 1;
        exit.setPreferredSize(new Dimension(150, 30));
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        //display the frame
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for changing the users password
     * @param tempUser User whose password is being changed
     */
    private void setPassword(User tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //Ask for the new password
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel enterNew = new JLabel("Please enter new password");
        panel.add(enterNew, constraints);

        //get the new password
        constraints.gridx = 1;
        JPasswordField newPass = new JPasswordField(20);
        panel.add(newPass, constraints);

        //Ask to confirm the new Password
        JLabel enterNew2 = new JLabel("Please confirm your new password");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(enterNew2, constraints);
        //get the password again
        JPasswordField newPass2 = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(newPass2, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e1 -> {
            if (newPass.getText().equals(newPass2.getText())) {
                //if the entered passwords match each other
                //change the password for the user
                atm.getUsers().changePassword(tempUser, newPass.getText(), tempUser.getPassword());

                showMessage( "Password Successfully Changes" );
                panel.repaint();
            } else {
                showMessage( "New Passwords did not match, please try again" );
                panel.repaint();
            }

        });
        constraints.gridy = 3;
        constraints.gridx = 0;
        submit.setPreferredSize(new Dimension(150, 30));
        panel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            //Call the main menu Again
            this.user.getOptionsScreen(atm);

            //Close this frame
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 1;
        exit.setPreferredSize(new Dimension(150, 30));
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        //Display the frame
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen that shows current currency exchange, updated in real time
     */
    private void currencyConverter() {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel enterCAD = new JLabel("Amount of Money in CAD: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(enterCAD, constraints);

        JTextField amountNCAD = new JTextField(10);
        constraints.gridx = 1;
        panel.add(amountNCAD, constraints);

        JButton cadToUSD = new JButton("CAD to USD");
        cadToUSD.addActionListener(e -> {
            // if they want to convert the CAD to USD
            try {
                double amount = Double.parseDouble(amountNCAD.getText());
                //close the frame
                frame.setVisible(false);
                frame.dispose();
                cadToUSD(amount);

            } catch (Exception WrongFormat) {
                showMessage( "Invalid Input" );
                panel.repaint();
            }
        });

        constraints.gridx = 2;
        cadToUSD.setPreferredSize(new Dimension(150, 30));
        panel.add(cadToUSD, constraints);

        JLabel enterUS = new JLabel("Amount of Money in USD: ");
        constraints.gridy = 1;
        constraints.gridx = 0;
        panel.add(enterUS, constraints);

        JTextField amountNUSD = new JTextField(10);
        constraints.gridx = 1;
        panel.add(amountNUSD, constraints);

        JButton usdToCAD = new JButton("USD to CAD");
        usdToCAD.addActionListener(e -> {
            try {
                // if they want to convert the USD to CAD
                double amount = Double.parseDouble(amountNUSD.getText());
                //close the frame
                frame.setVisible(false);
                frame.dispose();
                usdToCAD(amount);
            } catch (Exception WrongFormat) {
                showMessage( "Invalid Input" );
                panel.repaint();
            }
        });

        constraints.gridx = 2;
        usdToCAD.setPreferredSize(new Dimension(150, 30));
        panel.add(usdToCAD, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            //Open the main menu again
            user.getOptionsScreen(atm);
            //close this frame
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridy = 3;
        constraints.gridx = 1;
        exit.setPreferredSize(new Dimension(150, 30));
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        //Display this frame
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for converting USD to CAD
     * @param amount int
     */
    private void usdToCAD(double amount) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel labelToCad = new JLabel("Amount of Money in CAD: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelToCad, constraints);

        int temp = currencyExchange.usdToCad((int)amount * 100);
        JLabel amountNCAD = new JLabel("$" + temp / 100 + "." + temp % 100);
        constraints.gridx = 1;
        panel.add(amountNCAD, constraints);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(e -> {
            currencyConverter();
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 2;
        returnButton.setPreferredSize(new Dimension(150, 30));
        panel.add(returnButton, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for converting CAD to USD
     * @param amount double
     */
    private void cadToUSD(double amount) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel labelToUSD = new JLabel("Amount of Money in USD: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelToUSD, constraints);

        int temp = currencyExchange.cadToUsd((int)amount * 100);
        JLabel amountNUSD = new JLabel("$" + temp / 100 + "." + temp % 100);
        constraints.gridx = 1;
        panel.add(amountNUSD, constraints);

        JButton returnButton = new JButton("Return ");
        returnButton.addActionListener(e -> {
            currencyConverter();
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 2;
        returnButton.setPreferredSize(new Dimension(150, 30));
        panel.add(returnButton, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for viewing a users accounts using their login info
     * @param action The Action which will be operated
     */
    private void doAction(String action) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //Ask for Username
        JLabel labelUserName = new JLabel("Enter username: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelUserName, constraints);

        //Get the Username
        JTextField username = new JTextField(20);
        constraints.gridx = 1;
        panel.add(username, constraints);

        //Ask for Password
        JLabel labelPassword = new JLabel("Enter password: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelPassword, constraints);
        //get the password
        JPasswordField password = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(password, constraints);

        JButton confirm = new JButton("Confirm");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(confirm, constraints);

        confirm.addActionListener( e -> {
            if ( atm.getUsers( ).checkLogin( atm.getUsers( ).getUserAccount( username.getText( ) ), password.getText( ) ) ) {
                //if the username and password are correct
                //close this screen
                frame.setVisible( false );
                frame.dispose( );
                //find the user related to inserted username, password to the action for them
                CustomerUser tempUser = (CustomerUser) atm.getUsers().getUserAccount( username.getText() );
                switch (action) {
                    case "VIEW_ACCOUNT":
                        viewAccounts(tempUser);
                        break;
                    case "DEPOSIT":
                        depositScreen(tempUser);
                        break;
                    case "TRANSFER":
                        transactionOptions(tempUser);
                        break;
                    case "PAY_BILL":
                        billScreen(tempUser);
                        break;
                    case "REQUEST_ACCOUNT":
                        requestScreen(tempUser);
                        break;
                    case "WITHDRAW":
                        withdrawScreen(tempUser);
                        break;
                        default: break;
                }
            } else {
                showMessage( "Invalid username/password. Please try again." );
                frame.repaint();
            }
        });

        //Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            this.user.getOptionsScreen( atm );
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 3;
        exitButton.setPreferredSize(new Dimension(100, 30));
        panel.add(exitButton, constraints);
        //add the panel to the frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        //display the frame
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}

