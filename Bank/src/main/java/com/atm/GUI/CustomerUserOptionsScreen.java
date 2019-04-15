package com.atm.GUI;

import com.atm.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Options screen for a customer User
 */
public class CustomerUserOptionsScreen extends OptionsScreen {

    private final JButton changePassword = new JButton("Change Password");
    private final JButton viewAccountSummary = new JButton("View And Edit Accounts");
    private final JButton transactions = new JButton("Transactions");
    private final JButton requestAnAccount = new JButton("Request An Account");
    private final JButton payBill = new JButton("Pay A Bill");
    private final JButton withdraw = new JButton("Withdraw");
    private final JButton deposit = new JButton("Deposit");
    private final JButton logout = new JButton("Logout");

    /**
     * Creates an option screen for a user
     *
     * @param user User currently interacting with the ATM
     * @param atm  ATM currently being interacted with
     */
    public CustomerUserOptionsScreen(User user, ATM atm) {
        super(user, atm);
    }

    /**
     * Shows the initial menu of options for the user
     */
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
            changePasswordScreen();
            screen.setVisible(false);
            screen.dispose();
        });

        //User wants to view their account summary and edit
        constraints.gridx = 1;
        viewAccountSummary.setPreferredSize(new Dimension(200, 40));
        panel.add(viewAccountSummary, constraints);
        viewAccountSummary.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            viewAccounts((CustomerUser) user);
        });

        //User wants to do/view a transaction
        constraints.gridx = 0;
        constraints.gridy = 1;
        transactions.setPreferredSize(new Dimension(200, 40));
        transactions.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            transactionOptions((CustomerUser) user);
        });
        panel.add(transactions, constraints);

        //User wants to request an account
        constraints.gridx = 1;
        requestAnAccount.setPreferredSize(new Dimension(200, 40));
        requestAnAccount.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            requestScreen((CustomerUser) user);
        });
        panel.add(requestAnAccount, constraints);

        //User wants to pay a bill
        constraints.gridx = 0;
        constraints.gridy = 2;
        payBill.setPreferredSize(new Dimension(200, 40));
        payBill.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            billScreen((CustomerUser) (user));
        });
        panel.add(payBill, constraints);

        //User wants to withdraw from ATM
        constraints.gridx = 1;
        withdraw.setPreferredSize(new Dimension(200, 40));
        withdraw.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            withdrawScreen((CustomerUser) user);
        });
        panel.add(withdraw, constraints);

        //User wants to deposit into their accounts
        constraints.gridx = 0;
        constraints.gridy = 3;
        deposit.setPreferredSize(new Dimension(200, 40));
        deposit.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            depositScreen((CustomerUser) user);
        });
        panel.add(deposit, constraints);

        //User wants to logout, calls the ATM's login screen
        constraints.gridy = 3;
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
        screen.setVisible(true);
        screen.setLocationRelativeTo(null);
    }

    /**
     * User wants to deposit
     *
     * @param tempUser User
     */
    void depositScreen(CustomerUser tempUser) {
        JFrame stockFrame = new JFrame("Bank of 207");
        JPanel stockPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        stockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel five = new JLabel("Number of fives: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        stockPanel.add(five, constraints);

        JTextField fiveText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(fiveText, constraints);

        JLabel ten = new JLabel("Number of tens: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        stockPanel.add(ten, constraints);

        JTextField tenText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(tenText, constraints);

        JLabel twenty = new JLabel("Number of twenty's: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        stockPanel.add(twenty, constraints);

        JTextField twentyText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(twentyText, constraints);

        JLabel fifty = new JLabel("Number of fifty's: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        stockPanel.add(fifty, constraints);

        JTextField fiftyText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(fiftyText, constraints);

        JLabel prompt = new JLabel("Please select account from below:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        stockPanel.add(prompt, constraints);

        ArrayList<Account> temp = atm.getAccounts().getAccountsForUser(tempUser);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : temp) {
            listModel.addElement(acc.toString());
        }
        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(800, 200));
        constraints.gridx = 0;
        constraints.gridy = 5;
        stockPanel.add(listScroller, constraints);

        JLabel chequeInstructions = new JLabel("Please enter cheque amount: ");
        constraints.gridy = 6;
        stockPanel.add(chequeInstructions, constraints);
        JTextField cheque = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(cheque, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            try {
                if (list.getSelectedIndex() != -1) {
                    int tempFive = 0;
                    if(!fiveText.getText().isEmpty()){
                        tempFive = Integer.parseInt(fiveText.getText());
                    }
                    int tempTen = 0;
                    if(!tenText.getText().isEmpty()) {
                        tempTen = Integer.parseInt(tenText.getText());
                    }
                    int tempTwenty = 0;
                    if(!twentyText.getText().isEmpty()) {
                        tempTwenty = Integer.parseInt(twentyText.getText());
                    }
                    int tempFifty = 0;
                    if(!fiftyText.getText().isEmpty()) {
                        tempFifty = Integer.parseInt(fiftyText.getText());
                    }
                    if (!(0<= tempFifty && 0<= tempTwenty && 0<=tempTen && 0<= tempFive)) {
                        throw new Exception( "Wrong Format" );
                    }
                    String[] name = list.getSelectedValue().split(":");
                    atm.getAccounts().deposit(atm.getAccounts().findAccount(name[1].trim()), tempFive, tempTen, tempTwenty, tempFifty);
                    if (!cheque.getText().isEmpty()) {
                        atm.getAccounts().deposit(atm.getAccounts().findAccount(name[1].trim()), (int) (Double.parseDouble(cheque.getText()) * 100));
                    }
                    stockFrame.setVisible( false );
                    stockFrame.dispose();
                    depositScreen( tempUser );
                    showMessage( "Cash Successfully Added" );
                }
                else {
                    showMessage( "Please Choose an Account to deposit into" );
                }
            } catch (Exception e1) {
                showMessage( "Invalid Input Please Try Again!" );
                stockFrame.repaint();

            }
        });
        constraints.gridx = 0;
        constraints.gridy = 7;
        stockPanel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            stockFrame.setVisible(false);
            stockFrame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 7;
        stockPanel.add(exit, constraints);

        stockFrame.add(stockPanel);
        stockFrame.pack();
        stockFrame.setVisible(true);
        stockFrame.setLocationRelativeTo(null);

    }

    /**
     * User wants to withdraw money out of the Bank/ATM
     * @param tempUser the user who intend to withdraw
     */
    void withdrawScreen(CustomerUser tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel withdraw = new JLabel("Please select account to withdraw from and enter amount");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(withdraw, constraints);

        ArrayList<Account> temp = atm.getAccounts().getAccountsForUser(tempUser);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : temp) {
            if (!(acc instanceof CreditAccount) && !(acc instanceof LineOfCreditAccount) && !(acc instanceof USAccount)) {
                listModel.addElement(acc.toString());
            }
        }
        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(800, 200));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(listScroller, constraints);

        JLabel amount = new JLabel("Amount must me multiples of five: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(amount, constraints);
        JTextField numAmount = new JTextField(5);
        constraints.gridx = 1;
        panel.add(numAmount, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if ((list.getSelectedIndex() != -1)) {
                try{
                    int withdrawAmount = Integer.parseInt( numAmount.getText() );
                    String[] name = list.getSelectedValue().split(":");
                    if (!atm.getAccounts().withdraw( withdrawAmount*100,atm.getAccounts().findAccount( name[1].trim() ) )){
                        throw new IllegalArgumentException("Wrong Format") ;
                    }
                    frame.setVisible( false );
                    frame.dispose();
                    withdrawScreen( tempUser );
                    showMessage( "Withdraw Successful" );
                }
                catch (Exception ee){
                    showMessage( "Insufficient cash" );
                    frame.repaint();
                }
            }else {
                showMessage( "Please Select an Account" );
                frame.repaint(  );
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * User wants to Pay the Bill
     * @param tempUser the User who wants to pay bills
     */
    void billScreen(CustomerUser tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel instructions = new JLabel("Please select an account to pay from, enter destination, amount, and its currency");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(instructions, constraints);

        ArrayList<Account> temp = atm.getAccounts().getAccountsForUser(tempUser);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : temp) {
            listModel.addElement(acc.toString());
        }
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(600, 100));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(listScroller, constraints);

        JLabel destination = new JLabel("Destination: ");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(destination, constraints);
        JTextField destInput = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(destInput, constraints);

        JLabel amount = new JLabel("Amount: ");
        constraints.gridy = 5;
        constraints.gridx = 0;
        panel.add(amount, constraints);
        JTextField amountInput = new JTextField(10);
        constraints.gridx = 1;
        panel.add(amountInput, constraints);

        JRadioButton rb1 = new JRadioButton("CAD");
        //rb1.setBounds(100,50,100,30);
        JRadioButton rb2 = new JRadioButton("USD");
        //rb2.setBounds(100,100,100,30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);
        constraints.gridx = 0;
        constraints.gridy = 2;
        rb1.setBounds(100, 50, 100, 30);
        rb2.setBounds(100, 100, 100, 30);
        panel.add(rb1, constraints);
        constraints.gridy = 3;
        panel.add(rb2, constraints);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                String[] temp1 = list.getSelectedValue().split(":");
                if (rb1.isSelected()) {
                    if(atm.getAccounts().payBill((int)(Double.parseDouble(amountInput.getText()) * 100), destInput.getText(), atm.getAccounts().findAccount(temp1[1].trim()), "CAD")){
                        frame.setVisible( false );
                        frame.dispose();
                        billScreen( tempUser );
                        showMessage("Payed bill");
                    } else {
                        showMessage("Bill not payed, invalid input please try again");
                    }
                }
                if (rb2.isSelected()) {
                    if(atm.getAccounts().payBill((int)(Double.parseDouble(amountInput.getText()) * 100), destInput.getText(), atm.getAccounts().findAccount(temp1[1].trim()), "USD")){
                        frame.setVisible( false );
                        frame.dispose();
                        billScreen( tempUser );
                        showMessage("Payed bill");
                    } else {
                        showMessage("Bill not payed, invalid input please try again");
                    }
                }
            } else {
                showMessage( "Please Select an Account" );
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(confirm, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * User want's to request new account
     * @param tempUser user who wants new account
     */
    void requestScreen(CustomerUser tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel request = new JLabel("Enter customer username if you would like this to be a joint account, then select an account type");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(request, constraints);

        JLabel joint = new JLabel("Enter other persons username if this is a joint account: ");
        constraints.gridy = 1;
        panel.add(joint, constraints);
        JTextField jointUsername = new JTextField(20);
        constraints.gridx = 1;
        panel.add(jointUsername, constraints);

        JButton credit = new JButton("Credit Card");
        credit.setPreferredSize(new Dimension(200, 30));
        credit.addActionListener(e -> {
            CustomerUser jointUser = (CustomerUser) atm.getUsers().getUserAccount(jointUsername.getText());
            atm.getAccounts().requestAccount(tempUser, jointUser, AccountsFactory.CREDIT_CARD);
            showMessage( "Credit account requested, please check back later" );
            frame.repaint();
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(credit, constraints);

        JButton cheque = new JButton("Chequing Account");
        cheque.setPreferredSize(new Dimension(200, 30));
        cheque.addActionListener(e -> {
            CustomerUser jointUser = (CustomerUser) atm.getUsers().getUserAccount(jointUsername.getText());
            atm.getAccounts().requestAccount(tempUser, jointUser, AccountsFactory.CHEQUING);
            showMessage( "Chequing account requested, please check back later" );
            frame.repaint();
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(cheque, constraints);

        JButton saving = new JButton("Savings Account");
        saving.setPreferredSize(new Dimension(200, 30));
        saving.addActionListener(e -> {
            CustomerUser jointUser = (CustomerUser) atm.getUsers().getUserAccount(jointUsername.getText());
            atm.getAccounts().requestAccount(tempUser, jointUser, AccountsFactory.SAVINGS);
            showMessage( "Savings account requested, please check back later" );
            frame.repaint();
        });
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(saving, constraints);

        JButton lineCredit = new JButton("Line of Credit Account");
        lineCredit.setPreferredSize(new Dimension(200, 30));
        lineCredit.addActionListener(e -> {
            CustomerUser jointUser = (CustomerUser) atm.getUsers().getUserAccount(jointUsername.getText());
            atm.getAccounts().requestAccount(tempUser, jointUser, AccountsFactory.LINE_OF_CREDIT);
            showMessage( "Line of Credit account requested, please check back later" );
            frame.repaint();
        });
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(lineCredit, constraints);

        JButton us = new JButton("US Bank Account");
        us.setPreferredSize(new Dimension(200, 30));
        us.addActionListener(e -> {
            CustomerUser jointUser = (CustomerUser) atm.getUsers().getUserAccount(jointUsername.getText());
            atm.getAccounts().requestAccount(tempUser, jointUser, AccountsFactory.US);
            showMessage( "US Bank account requested, please check back later" );
            frame.repaint();
        });
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(us, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for viewing customers personal accounts and can change their primary account
     * @param tempUser CustomerUser
     */
    void viewAccounts(CustomerUser tempUser) {
        JFrame accountsFrame = new JFrame("Bank of 207");
        JPanel accountPanel = new JPanel(new GridBagLayout());
        //JScrollPane scrPane = new JScrollPane(accountPanel);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel setPrimaryText = new JLabel("Select an account then click Set Primary to change primary account");
        constraints.gridx = 0;
        constraints.gridy = 0;
        accountPanel.add(setPrimaryText, constraints);

        ArrayList<Account> temp = atm.getAccounts().getAccountsForUser(tempUser);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : temp) {
            if (acc.getName().equals(tempUser.getPrimaryAccount().getName())){
                listModel.addElement("Primary Account: " + acc.toString());
            } else {
                listModel.addElement(acc.toString());
            }
        }
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(800, 200));
        constraints.gridx = 0;
        constraints.gridy = 1;
        accountPanel.add(listScroller, constraints);

        JLabel viewNetTotal = new JLabel("Net Total Of Accounts: $" + atm.getAccounts().getNetTotal(tempUser) / 100 + "." + atm.getAccounts().getNetTotal((CustomerUser) user) % 100);
        constraints.gridy = 2;
        accountPanel.add(viewNetTotal, constraints);

        JButton setPrimary = new JButton("Set Primary Account");
        setPrimary.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                String[] accountList = list.getSelectedValue().split(":");
                String acc = accountList[1].trim();
                if(!accountList[0].contains("Primary")) {
                    if (atm.getAccounts().findAccount(acc).getType().equals(AccountsFactory.CHEQUING)) {
                        tempUser.setPrimaryAccount(atm.getAccounts().findAccount(acc));
                        accountsFrame.setVisible(false);
                        accountsFrame.dispose();
                        viewAccounts(tempUser);
                    } else {
                        showMessage("Only chequing account can be primary accounts");
                    }
                } else {
                    showMessage("Already primary");
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        accountPanel.add(setPrimary, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            this.user.getOptionsScreen(atm);
            accountsFrame.setVisible(false);
            accountsFrame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        accountPanel.add(exit, constraints);

        accountsFrame.add(accountPanel);
        accountsFrame.pack();
        accountsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        accountsFrame.setVisible(true);
        accountsFrame.setLocationRelativeTo(null);
    }

    /**
     * Screen that displays the users transaction options, then calls the following screen depending on what they want to do
     * @param tempUser CustomerUser
     */
    void transactionOptions(CustomerUser tempUser) {
        JFrame transactionFrame = new JFrame("Bank of 207");
        JPanel transactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton transferToAccount = new JButton("Transfer To Another Bank Account");
        transferToAccount.addActionListener(e -> {
            transferToBankAccount(tempUser);
            transactionFrame.setVisible(false);
            transactionFrame.dispose();
        });

        JButton transferToUser = new JButton("Transfer To Another User");
        transferToUser.addActionListener(e -> {
            transferToUser(tempUser);
            transactionFrame.setVisible(false);
            transactionFrame.dispose();
        });

        JButton viewTransfers = new JButton("View transaction history");
        viewTransfers.addActionListener(e -> {
            viewTransferHistory(tempUser);
            transactionFrame.setVisible(false);
            transactionFrame.dispose();
        });

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            this.user.getOptionsScreen(atm);
            transactionFrame.setVisible(false);
            transactionFrame.dispose();
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        transactionPanel.add(exit, constraints);

        constraints.gridx = 0;
        constraints.gridy = 0;
        transactionPanel.add(transferToAccount, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        transactionPanel.add(transferToUser, constraints);

        constraints.gridy = 0;
        constraints.gridy = 2;
        transactionPanel.add(viewTransfers, constraints);

        transactionFrame.add(transactionPanel);
        transactionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transactionFrame.pack();
        transactionFrame.setVisible(true);
        transactionFrame.setLocationRelativeTo(null);
    }

    /**
     * View tempUsers transaction history
     * @param tempUser CustomerUser
     */
    private void viewTransferHistory(CustomerUser tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel recentUser = new JLabel("Most recent transaction for user: " + atm.getTransactions().getLastTransaction(tempUser));
        constraints.gridy = 0;
        constraints.gridx = 0;
        panel.add(recentUser);

        JTextField history  = new JTextField();
        constraints.gridy = 3;
        panel.add(history, constraints);

        ArrayList<Account> temp = atm.getAccounts().getAccountsForUser(tempUser);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : temp) {
            listModel.addElement(acc.toString());
        }
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(800, 200));
        constraints.gridy = 2;
        panel.add(listScroller, constraints);
        list.addListSelectionListener(evt -> {
            if (list.getSelectedIndex() != -1) {
                String[] accountList = list.getSelectedValue().split(":");
                String fromAccount = accountList[1].trim();
                Transaction trans = atm.getTransactions().getLastTransaction(atm.getAccounts().findAccount(fromAccount));
                history.setText(trans.getId() + ": " + trans.toString());
                frame.pack();
                frame.repaint();
            }
        });
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for transferring to another user
     * @param tempUser CustomerUser
     */
    private void transferToUser(CustomerUser tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel enterUser = new JLabel("Please enter username of customer to transfer to: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(enterUser, constraints);

        JTextField userIn = new JTextField(10);
        constraints.gridx = 1;
        panel.add(userIn, constraints);

        ArrayList<Account> temp = atm.getAccounts().getAccountsForUser(tempUser);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : temp) {
            if (!(acc instanceof CreditAccount)) {
                listModel.addElement(acc.toString());
            }
        }
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(800, 200));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(listScroller, constraints);

        JLabel amount = new JLabel("Please enter amount you wish to transfer: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(amount, constraints);

        JTextField amountInput = new JTextField(10);
        constraints.gridx = 1;
        panel.add(amountInput, constraints);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                String[] accountList = list.getSelectedValue().split(":");
                String fromAccount = accountList[1].trim();
                User toUser = atm.getUsers().getUserAccount(userIn.getText());
                if(atm.getAccounts().transfer(atm.getAccounts().findAccount(fromAccount), (CustomerUser) toUser, Integer.parseInt(amountInput.getText()) * 100)) {
                    frame.setVisible(false);
                    frame.dispose();
                    transferToUser(tempUser);
                    showMessage("Transfer Completed");
                } else {
                    frame.setVisible(false);
                    frame.dispose();
                    transferToUser(tempUser);
                    showMessage("Transfer failed, please try again");
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(confirm, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Screen for transferring to another one of temp users accounts
     * @param tempUser CustomerUser
     */
    private void transferToBankAccount(CustomerUser tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel firstSelect = new JLabel("Please select an account to transfer from: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(firstSelect);

        ArrayList<Account> temp = atm.getAccounts().getAccountsForUser(tempUser);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : temp) {
            if (!(acc instanceof CreditAccount)) {
                listModel.addElement(acc.toString());
            }
        }
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(800, 200));
        constraints.gridy = 1;
        panel.add(listScroller, constraints);
        list.addListSelectionListener(evt -> {
            DefaultListModel<String> listModel2 = new DefaultListModel<>();
            if (list.getSelectedIndex() != -1) {
                String[] accountList = list.getSelectedValue().split(":");
                String fromAccount = accountList[1].trim();

                for (Account acc : temp) {
                    if (!acc.getName().equals(fromAccount))
                        listModel2.addElement(acc.toString());
                }
                JList<String> list2 = new JList<>(listModel2); //data has type Object[]
                list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                list2.setVisibleRowCount(-1);

                JLabel secondSelect = new JLabel("Please select an account to transfer to: ");
                constraints.gridx = 0;
                constraints.gridy = 2;
                panel.add(secondSelect, constraints);

                JScrollPane listScroller2 = new JScrollPane(list2);
                listScroller2.setPreferredSize(new Dimension(800, 200));
                constraints.gridx = 0;
                constraints.gridy = 3;
                panel.add(listScroller2, constraints);
                frame.pack();
                frame.repaint();
                list2.addListSelectionListener(evt1 -> {
                    if (list2.getSelectedIndex() != -1) {
                        String[] accountList1 = list2.getSelectedValue().split(":");
                        String toAccount = accountList1[1].trim();
                        JLabel enterAmount = new JLabel("Please enter amount you would like to transfer");
                        constraints.gridx = 0;
                        constraints.gridy = 4;
                        panel.add(enterAmount, constraints);
                        JTextField amount = new JTextField(5);
                        constraints.gridx = 1;
                        panel.add(amount, constraints);
                        JButton confirm = new JButton("Confirm");
                        confirm.addActionListener(e -> {
                            if(!amount.getText().isEmpty()) {
                                atm.getAccounts().transfer(atm.getAccounts().findAccount(fromAccount), atm.getAccounts().findAccount(toAccount), (int) Double.parseDouble(amount.getText()) * 100);
                                transferToBankAccount(tempUser);
                                frame.setVisible(false);
                                frame.dispose();
                                showMessage("Transfer Successful");
                            }
                        });
                        constraints.gridx = 1;
                        constraints.gridy = 5;
                        panel.add(confirm, constraints);
                        frame.pack();
                        frame.repaint();
                    }
                });
            }
        });

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            user.getOptionsScreen(atm);
        });
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
