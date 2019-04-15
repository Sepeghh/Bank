package com.atm.GUI;

import com.atm.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Interface for a bank manager
 */
public class BankManagerUserOptionsScreen extends OptionsScreen {

    /**
     * Creates a bank Manager option screen
     * @param user User interacting currently with the ATM
     * @param atm current ATM system being interacted with
     */
    public BankManagerUserOptionsScreen(BankManagerUser user, ATM atm) {
        super(user, atm);
    }

    /**
     * Bank Manager initial options screen
     */
    public void initialOptionScreen() {
        JFrame screen = new JFrame("Bank of 207");

        //User wants to change their password
        JButton changePassword = new JButton("Change Password");
        changePassword.setPreferredSize(new Dimension(200, 40));
        changePassword.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            changePasswordScreen();
        });

        //User wants to create a new user
        JButton createUser = new JButton("Create New User");
        createUser.setPreferredSize(new Dimension(200, 40));
        createUser.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            createNewUserScreen();
        });

        //User wants to set ATM time
        JButton setTime = new JButton("Set ATM Time");
        setTime.setPreferredSize(new Dimension(200, 40));
        setTime.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            setTimeScreen();
        });

        //User wants to view/undo transactions
        JButton transactions = new JButton("View/Undo Transactions");
        transactions.setPreferredSize(new Dimension(200, 40));
        transactions.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            transactionsOptions();
        });

        //User wants to shutdown the ATM
        JButton shutdown = new JButton("Shutdown ATM");
        shutdown.setPreferredSize(new Dimension(200, 40));
        shutdown.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
        });

        //User wants to view and edit account requests, can approve or deny them
        //TODO reject account requests
        JButton requests = new JButton("Account Requests");
        requests.setPreferredSize(new Dimension(200, 40));
        requests.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            accountRequests();
        });

        //User wants to view ATM alerts
        JButton alerts = new JButton("View ATM alerts");
        alerts.setPreferredSize(new Dimension(200, 40));
        alerts.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            viewAlerts();
        });

        //User wants to restock the ATM
        JButton restock = new JButton("Restock ATM");
        restock.setPreferredSize(new Dimension(200, 40));
        restock.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            restockScreen();
        });

        //User wants to edit account type
        JButton editAccountType = new JButton("Edit User accounts");
        editAccountType.setPreferredSize(new Dimension(200, 40));
        editAccountType.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            editUserScreen();
        });

        //User wants to log out, returns ATM to login screen
        JButton logout = new JButton("Log out");
        logout.setPreferredSize(new Dimension(200, 40));
        logout.addActionListener(e -> {
            screen.setVisible(false);
            screen.dispose();
            GUI.logout();
        });

        //create the new panel with grid layout
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //add parts to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(changePassword, constraints);

        //add parts to panel
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(createUser, constraints);

        //add parts to panel
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(setTime, constraints);

        //add parts to panel
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(transactions, constraints);

        //add parts to panel
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(shutdown, constraints);

        //add parts to panel
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(requests, constraints);

        //add parts to panel
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(alerts, constraints);

        //add parts to panel
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(restock, constraints);

        //add parts to panel
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(editAccountType, constraints);

        //add parts to panel
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(logout, constraints);

        screen.add(panel);
        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.pack();
        screen.setVisible(true);
        screen.setLocationRelativeTo(null);
    }

    /**
     * User wants to edit user accounts
     */
    private void editUserScreen() {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Ask for the username
        JLabel username = new JLabel("Please enter username of customer: ");
        constraints.gridy = 0;
        constraints.gridx = 0;
        panel.add(username, constraints);

        JTextField userInput = new JTextField(20);
        constraints.gridx = 1;
        panel.add(userInput, constraints);

        JButton findUser = new JButton("Find User Account");
        findUser.addActionListener(e -> {
            if (userInput.getText().isEmpty() || atm.getUsers().getUserAccount(userInput.getText()) == null) {
                showMessage("Username invalid");
            } else {
                frame.setVisible(false);
                frame.dispose();
                editOptionsScreen((CustomerUser) atm.getUsers().getUserAccount(userInput.getText()));
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add( findUser );

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            frame.setVisible(false);
            frame.dispose();
        });
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for editing a users account
     */
    private void editOptionsScreen(CustomerUser tempUser) {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (tempUser.isStudent()) {
            JButton notStudent = new JButton("Make not student");
            notStudent.addActionListener(e -> {
                tempUser.setStudent(false);
                atm.getUsers().setStudent(false, tempUser);
                frame.setVisible(false);
                frame.dispose();
                editUserScreen();
                showMessage("This user is no longer a student account");
            });
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(notStudent, constraints);
        } else {
            JButton makeStudent = new JButton("Make student");
            makeStudent.addActionListener(e -> {
                tempUser.setStudent(false);
                atm.getUsers().setStudent(true, tempUser);
                frame.setVisible(false);
                frame.dispose();
                editUserScreen();
                showMessage("This user is now a student account");
            });
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(makeStudent, constraints);
        }
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            user.getOptionsScreen(atm);
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Creates a screen that shows the ATM alerts
     */
    private void viewAlerts() {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArrayList<String> temp = atm.viewAlerts();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String alert : temp) {
            listModel.addElement(alert);
        }
        JList<String> list = new JList<>(listModel);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(400, 150));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(listScroller, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            user.getOptionsScreen(atm);
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * User wants to view account requests and can reject or accept them
     */
    private void accountRequests() {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel instructions = new JLabel("Please select an account from the list and click create account");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(instructions, constraints);

        ArrayList<String[]> temp = atm.getAccounts().getAccountRequest();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String[] acc : temp) {
            if (acc[1] == null) {
                listModel.addElement("User " + acc[0] + " has requested a " + acc[2]);
            } else {
                listModel.addElement("User " + acc[0] + " has requested a joint " + acc[2] + " with user " + acc[1]);
            }
        }
        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(400, 150));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(listScroller, constraints);
        JButton submit = new JButton("Create Account");
        submit.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                String[] account = list.getSelectedValue().split(" ");
                if (account.length == 6) {
                    CustomerUser tempUser = (CustomerUser) atm.getUsers().getUserAccount(account[1]);
                    atm.getAccounts().createAccount(account[5], tempUser, null);
                } else {
                    CustomerUser user1 = (CustomerUser) atm.getUsers().getUserAccount(account[1]);
                    CustomerUser user2 = (CustomerUser) atm.getUsers().getUserAccount(account[9]);
                    atm.getAccounts().createAccount(account[6], user1, user2);
                }
                String[] data = temp.get(list.getSelectedIndex());
                atm.getAccounts().deleteAccountRequest(data);
                frame.setVisible(false);
                frame.dispose();
                accountRequests();
                showMessage("Account Created");
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(submit, constraints);

        JButton reject = new JButton("Reject request");
        reject.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                String[] data = temp.get(list.getSelectedIndex());
                atm.getAccounts().deleteAccountRequest(data);
                frame.setVisible(false);
                frame.dispose();
                accountRequests();
                showMessage("Request Rejected");
            }
        });
        constraints.gridx = 1;
        panel.add(reject, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            user.getOptionsScreen(atm);
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * User wants to add money to the ATM
     */
    private void restockScreen() {
        JFrame stockFrame = new JFrame("Bank of 207");
        JPanel stockPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        stockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel prompt = new JLabel("Please enter amount for each type of bill");
        constraints.gridx = 0;
        constraints.gridy = 0;
        stockPanel.add(prompt, constraints);

        JLabel five = new JLabel("Number of fives: ");
        constraints.gridy = 1;
        stockPanel.add(five, constraints);

        JTextField fiveText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(fiveText, constraints);

        JLabel ten = new JLabel("Number of tens: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        stockPanel.add(ten, constraints);

        JTextField tenText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(tenText, constraints);

        JLabel twenty = new JLabel("Number of twenty's: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        stockPanel.add(twenty, constraints);

        JTextField twentyText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(twentyText, constraints);

        JLabel fifty = new JLabel("Number of fifty's: ");
        constraints.gridx = 0;
        constraints.gridy = 4;
        stockPanel.add(fifty, constraints);

        JTextField fiftyText = new JTextField(5);
        constraints.gridx = 1;
        stockPanel.add(fiftyText, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            try {
                int tempFive = 0;
                if (!fiveText.getText().isEmpty()) {
                    tempFive = Integer.parseInt(fiveText.getText());
                }
                int tempTen = 0;
                if (!tenText.getText().isEmpty()) {
                    tempTen = Integer.parseInt(tenText.getText());
                }
                int tempTwenty = 0;
                if (!twentyText.getText().isEmpty()) {
                    tempTwenty = Integer.parseInt(twentyText.getText());
                }
                int tempFifty = 0;
                if (!fiftyText.getText().isEmpty()) {
                    tempFifty = Integer.parseInt(fiftyText.getText());
                }
                if (!(0 <= tempFifty && 0 <= tempTwenty && 0 <= tempTen && 0 <= tempFive)) {
                    throw new IllegalArgumentException("Wrong Format");
                }
                atm.getCashManager().addFunds(tempFive, tempTen, tempTwenty, tempFifty);
                stockFrame.setVisible(false);
                stockFrame.dispose();
                restockScreen();
                showMessage("Money Successfully added");
            } catch (Exception e1) {
                stockFrame.setVisible(false);
                stockFrame.dispose();
                restockScreen();
                showMessage("Invalid input please try again");
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 5;
        stockPanel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            stockFrame.setVisible(false);
            stockFrame.dispose();
            user.getOptionsScreen(atm);
        });
        constraints.gridx = 1;
        constraints.gridy = 5;
        stockPanel.add(exit, constraints);

        stockFrame.add(stockPanel);
        stockFrame.pack();
        stockFrame.setVisible(true);
        stockFrame.setLocationRelativeTo(null);
    }

    /**
     * Displays screen for creating a new user
     */
    private void createNewUserScreen() {
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton newCustomerUser = new JButton("Create new Customer User");
        newCustomerUser.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            newCustomerUserScreen();
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        newCustomerUser.setPreferredSize(new Dimension(200, 40));
        panel.add(newCustomerUser, constraints);

        JButton newBankTeller = new JButton("Create new Bank Teller");
        newBankTeller.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            newBankTellerUserScreen();
        });
        constraints.gridy = 1;
        newBankTeller.setPreferredSize(new Dimension(200, 40));
        panel.add(newBankTeller, constraints);

        JButton newBankManager = new JButton("Create new Bank Manager");
        newBankManager.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            newBankManagerUserScreen();
        });
        constraints.gridy = 2;
        newBankManager.setPreferredSize(new Dimension(200, 40));
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(newBankManager, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Creates screen for creating a new bank manager
     */
    private void newBankManagerUserScreen() {
        JFrame newUserFrame = new JFrame("Bank of 207");
        JPanel newUserPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel enterUsername = new JLabel("Please enter username of new manager: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        newUserPanel.add(enterUsername, constraints);

        JTextField username = new JTextField(20);
        constraints.gridx = 1;
        newUserPanel.add(username, constraints);

        JLabel enterPassword = new JLabel("Please enter password for new manager");
        constraints.gridx = 0;
        constraints.gridy = 1;
        newUserPanel.add(enterPassword, constraints);

        JPasswordField password = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        newUserPanel.add(password, constraints);

        JLabel question = new JLabel("Please select a security question from the list below");
        constraints.gridx = 0;
        constraints.gridy = 2;
        newUserPanel.add(question, constraints);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Name of childhood pet");
        listModel.addElement("Mothers maiden name");
        listModel.addElement("Name of elementary school");
        listModel.addElement("Where did you meet your significant other");
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(300, 150));
        constraints.gridx = 0;
        constraints.gridy = 3;
        newUserPanel.add(listScroller, constraints);

        JLabel ans = new JLabel("Please enter security question answer: ");
        constraints.gridy = 4;
        newUserPanel.add(ans, constraints);
        JTextField answer = new JTextField(20);
        constraints.gridx = 1;
        newUserPanel.add(answer, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if (!(username.getText().equals("") && password.getText().equals(""))) {
                if (list.getSelectedIndex() != -1) {
                    if (atm.getUsers().usernameIsAvailable(username.getText())) {
                        UserFactory factory = new UserFactory();
                        BankManagerUser tempUser = (BankManagerUser) factory.createUser(UserFactory.BANK_MANAGER, username.getText(), password.getText(), list.getSelectedValue(), answer.getText(), false);
                        atm.getUsers().addAdminAccount(tempUser);
                        newUserFrame.setVisible(false);
                        newUserFrame.dispose();
                        newBankManagerUserScreen();
                        showMessage("Account Successfully created");
                    } else {
                        newUserFrame.setVisible(false);
                        newUserFrame.dispose();
                        newBankManagerUserScreen();
                        showMessage("Account not created, username already taken, please try again");
                    }
                }
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 5;
        submit.setPreferredSize(new Dimension(200, 30));
        newUserPanel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            newUserFrame.setVisible(false);
            newUserFrame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 5;
        exit.setPreferredSize(new Dimension(200, 30));
        newUserPanel.add(exit, constraints);

        newUserFrame.add(newUserPanel);
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newUserFrame.pack();
        newUserFrame.setVisible(true);
        newUserFrame.setLocationRelativeTo(null);
    }

    /**
     * Shows screen for creating a new customer user
     */
    private void newCustomerUserScreen() {
        JFrame newUserFrame = new JFrame("Bank of 207");
        JPanel newUserPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel enterUsername = new JLabel("Please enter username of new customer: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        newUserPanel.add(enterUsername, constraints);

        JTextField username = new JTextField(20);
        constraints.gridx = 1;
        newUserPanel.add(username, constraints);

        JLabel enterPassword = new JLabel("Please enter password for new customer");
        constraints.gridx = 0;
        constraints.gridy = 1;
        newUserPanel.add(enterPassword, constraints);

        JPasswordField password = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        newUserPanel.add(password, constraints);

        JLabel question = new JLabel("Please select a security question from the list below");
        constraints.gridx = 0;
        constraints.gridy = 2;
        newUserPanel.add(question, constraints);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Name of childhood pet");
        listModel.addElement("Mothers maiden name");
        listModel.addElement("Name of elementary school");
        listModel.addElement("Where did you meet your significant other");
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(300, 150));
        constraints.gridx = 0;
        constraints.gridy = 3;
        newUserPanel.add(listScroller, constraints);

        JLabel ans = new JLabel("Please enter security question answer: ");
        constraints.gridy = 4;
        newUserPanel.add(ans, constraints);
        JTextField answer = new JTextField(20);
        constraints.gridx = 1;
        newUserPanel.add(answer, constraints);

        JCheckBox isStudent = new JCheckBox("Student User Account");
        constraints.gridy = 5;
        newUserPanel.add(isStudent, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if (!(username.getText().equals("") && password.getText().equals(""))) {
                if (list.getSelectedIndex() != -1) {
                    if (atm.getUsers().usernameIsAvailable(username.getText())) {
                        UserFactory factory = new UserFactory();
                        CustomerUser tempCustomerUser = (CustomerUser ) factory.createUser(UserFactory.CUSTOMER_USER, username.getText(), password.getText(), list.getSelectedValue(), answer.getText(), isStudent.isSelected() );
                        if(atm.getUsers().createCustomer(tempCustomerUser)) {
                            newUserFrame.setVisible(false);
                            newUserFrame.dispose();
                            newBankManagerUserScreen();
                            showMessage("Account successfully created");
                        } else {
                            newUserFrame.setVisible(false);
                            newUserFrame.dispose();
                            newBankManagerUserScreen();
                            showMessage("Account not created, please try again");
                        }
                    } else {
                        newUserFrame.setVisible(false);
                        newUserFrame.dispose();
                        newCustomerUserScreen();
                        showMessage("Account not created, username already taken, please try again");
                    }
                }
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 6;
        submit.setPreferredSize(new Dimension(200, 30));
        newUserPanel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            newUserFrame.setVisible(false);
            newUserFrame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 6;
        exit.setPreferredSize(new Dimension(200, 30));
        newUserPanel.add(exit, constraints);

        newUserFrame.add(newUserPanel);
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newUserFrame.pack();
        newUserFrame.setVisible(true);
        newUserFrame.setLocationRelativeTo(null);
    }

    /**
     * Screen for creating a new bank teller
     */
    private void newBankTellerUserScreen() {
        JFrame newUserFrame = new JFrame("Bank of 207");
        JPanel newUserPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel enterUsername = new JLabel("Please enter username of new bank teller: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        newUserPanel.add(enterUsername, constraints);

        JTextField username = new JTextField(20);
        constraints.gridx = 1;
        newUserPanel.add(username, constraints);

        JLabel enterPassword = new JLabel("Please enter password for new bank teller:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        newUserPanel.add(enterPassword, constraints);

        JPasswordField password = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        newUserPanel.add(password, constraints);

        JLabel question = new JLabel("Please select a security question from the list below");
        constraints.gridx = 0;
        constraints.gridy = 2;
        newUserPanel.add(question, constraints);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Name of childhood pet");
        listModel.addElement("Mothers maiden name");
        listModel.addElement("Name of elementary school");
        listModel.addElement("Where did you meet your significant other");
        JList<String> list = new JList<>(listModel); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(300, 150));
        constraints.gridx = 0;
        constraints.gridy = 3;
        newUserPanel.add(listScroller, constraints);

        JLabel ans = new JLabel("Please enter security question answer: ");
        constraints.gridy = 4;
        newUserPanel.add(ans, constraints);
        JTextField answer = new JTextField(20);
        constraints.gridx = 1;
        newUserPanel.add(answer, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if (!(username.getText().equals("") && password.getText().equals(""))) {
                if (list.getSelectedIndex() != -1) {
                    if (atm.getUsers().usernameIsAvailable(username.getText())) {
                        UserFactory factory = new UserFactory();
                        BankTellerUser bankTellerUser =(BankTellerUser) factory.createUser(UserFactory.BANK_TELLER, username.getText(), password.getText(), list.getSelectedValue(), answer.getText(), false );
                        if(atm.getUsers().createBankTeller(bankTellerUser)){
                            newUserFrame.setVisible(false);
                            newUserFrame.dispose();
                            newBankTellerUserScreen();
                            showMessage("User successfully created");
                        } else {
                            newUserFrame.setVisible(false);
                            newUserFrame.dispose();
                            newBankTellerUserScreen();
                            showMessage("User not created, please try again");
                        }
                    } else {
                        newUserFrame.setVisible(false);
                        newUserFrame.dispose();
                        newBankTellerUserScreen();
                        showMessage("User not created, username already taken, please try again");
                    }
                }
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 5;
        submit.setPreferredSize(new Dimension(200, 30));
        newUserPanel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            newUserFrame.setVisible(false);
            newUserFrame.dispose();
        });
        constraints.gridx = 1;
        constraints.gridy = 5;
        exit.setPreferredSize(new Dimension(200, 30));
        newUserPanel.add(exit, constraints);

        newUserFrame.add(newUserPanel);
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newUserFrame.pack();
        newUserFrame.setVisible(true);
        newUserFrame.setLocationRelativeTo(null);
    }

    /**
     * Show screen for changing ATM time
     */
    private void setTimeScreen() {
        JFrame timeFrame = new JFrame("Bank of 207");
        JPanel timeScreen = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel timeText = new JLabel("please enter the time in (year month day hour minute second) format '0000 00 00 00 00 00'");
        constraints.gridx = 0;
        constraints.gridy = 0;
        timeScreen.add(timeText, constraints);

        JTextField timeEntered = new JTextField(20);
        constraints.gridy = 1;
        timeScreen.add(timeEntered, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if (ATM.setATMTime(timeEntered.getText())) {
                timeFrame.setVisible(false);
                timeFrame.dispose();
                setTimeScreen();
                showMessage("Time successfully changed");
                atm.checkForFirstOfMonth();
            } else {
                showMessage("Invalid input, time not changed, please try again");
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        timeScreen.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            timeFrame.setVisible(false);
            timeFrame.dispose();
            user.getOptionsScreen(atm);
        });
        constraints.gridx = 1;
        timeScreen.add(exit, constraints);

        timeFrame.add(timeScreen);
        timeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        timeFrame.pack();
        timeFrame.setVisible(true);
        timeFrame.setLocationRelativeTo(null);
    }

    /**
     * Show screen for transaction options such as undoing them and viewing them
     */
    private void transactionsOptions() {
        JFrame transactionsFrame = new JFrame("Bank of 207");
        JPanel transactionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton undoRecentATMTransaction = new JButton("Undo most recent transaction ATM");
        undoRecentATMTransaction.addActionListener(e -> {
            atm.getAccounts().undoTransaction();
            showMessage("Transaction Undone");
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        transactionsPanel.add(undoRecentATMTransaction, constraints);

        JButton undoRecentATMTransactionForUser = new JButton("Undo most recent transaction of bank account");
        undoRecentATMTransactionForUser.addActionListener(e -> {
            transactionsFrame.setVisible(false);
            transactionsFrame.dispose();
            getAccountFromUser();
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        transactionsPanel.add(undoRecentATMTransactionForUser, constraints);

        JButton undoByID = new JButton("Undo transaction by ID");
        undoByID.addActionListener(e -> {
            transactionsFrame.setVisible(false);
            transactionsFrame.dispose();
            undoTransactionByID();
        });

        constraints.gridy = 2;
        transactionsPanel.add(undoByID, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            transactionsFrame.setVisible(false);
            transactionsFrame.dispose();
            this.user.getOptionsScreen(atm);
        });
        constraints.gridy = 3;
        transactionsPanel.add(exit, constraints);

        transactionsFrame.add(transactionsPanel);
        transactionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transactionsFrame.pack();
        transactionsFrame.setVisible(true);
        transactionsFrame.setLocationRelativeTo(null);
    }

    /**
     * Show screen for undoing a transaction given a transaction ID
     */
    private void undoTransactionByID(){
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel instructions = new JLabel("Please enter the ID of the transaction to undo");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(instructions, constraints);

        JTextField id = new JTextField(20);
        constraints.gridy = 1;
        panel.add(id, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if(!id.getText().isEmpty()) {
                atm.getAccounts().undoTransaction(id.getText());
                frame.setVisible(false);
                frame.dispose();
                undoTransactionByID();
                showMessage("Transfer undone");
            } else {
                frame.setVisible(false);
                frame.dispose();
                undoTransactionByID();
                showMessage("Please enter a valid ID");
            }
        });

        constraints.gridy = 2;
        panel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            this.user.getOptionsScreen(atm);
        });
        constraints.gridy = 3;
        panel.add(exit, constraints);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Screen for selecting an account for a given user, bank manager can then undo transaction for that users account
     */
    private void getAccountFromUser() {
        JFrame accountsFrame = new JFrame("Bank of 207");
        JPanel accountPanel = new JPanel(new GridBagLayout());
        //JScrollPane scrPane = new JScrollPane(accountPanel);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        //accountsFrame.add(scrPane);
        JLabel user = new JLabel("Please enter username of customer");
        constraints.gridy = 0;
        constraints.gridx = 0;
        accountPanel.add(user, constraints);

        JTextField username = new JTextField(20);
        constraints.gridx = 1;
        accountPanel.add(username, constraints);


        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            ArrayList<Account> temp = atm.getAccounts().getAccountsForUser((CustomerUser) atm.getUsers().getUserAccount(username.getText()));
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Account acc : temp) {
                listModel.addElement(acc.toString());
            }
            JList<String> list = new JList<>(listModel); //data has type Object[]
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            list.setVisibleRowCount(-1);
            JScrollPane listScroller = new JScrollPane(list);
            listScroller.setPreferredSize(new Dimension(600, 150));
            constraints.gridx = 1;
            constraints.gridy = 3;
            accountPanel.add(listScroller, constraints);

            JButton confirm = new JButton("Confirm");
            constraints.gridx = 2;
            constraints.gridy = 3;
            accountPanel.add(confirm, constraints);
            confirm.addActionListener(e1 -> {
                if (list.getSelectedIndex() != -1) {
                    String[] accountList = list.getSelectedValue().split(":");
                    String accountName = accountList[1].trim();
                    atm.getAccounts().undoTransaction(atm.getAccounts().findAccount(accountName));
                }
                JLabel success = new JLabel("Transaction undone");
                constraints.gridx = 0;
                constraints.gridy = 4;
                accountPanel.add(success, constraints);
                accountsFrame.pack();
                accountsFrame.repaint();
            });
            accountsFrame.pack();
            accountsFrame.repaint();

        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        accountPanel.add(submit, constraints);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            this.user.getOptionsScreen(atm);
            accountsFrame.setVisible(false);
            accountsFrame.dispose();
        });
        constraints.gridx = 1;
        accountPanel.add(exit, constraints);

        accountsFrame.add(accountPanel);
        accountsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        accountsFrame.pack();
        accountsFrame.setVisible(true);
        accountsFrame.setLocationRelativeTo(null);
    }
}
