package com.atm.GUI;

import com.atm.ATM;

import javax.swing.*;
import java.awt.*;

/**
 * Login screen for the ATM
 */
class LoginScreen extends JFrame {

    /**
     * Creates a login screen
     * @param atm ATM being logged into
     */
    LoginScreen(ATM atm) {
        super("Bank of 207");

        JLabel labelUserName = new JLabel("Enter username: ");
        JLabel labelPassword = new JLabel("Enter password: ");
        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        getRootPane().setDefaultButton(loginButton);

        //create the new panel with grid layout
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //add parts to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelUserName, constraints);

        constraints.gridx = 1;
        panel.add(username, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelPassword, constraints);

        constraints.gridx = 1;
        panel.add(password, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        loginButton.addActionListener(e -> {
            // if they click on the button
            if (atm.getUsers().checkLogin(atm.getUsers().getUserAccount(username.getText()), password.getText())) {
                // If the username and password are correct
                // Login Screen disappears
                setVisible(false);
                dispose();
                //Calling the OptionScreen regarding the inserted user
                atm.setCurrentUser(atm.getUsers().getUserAccount(username.getText()));
                atm.getCurrentUser().getOptionsScreen(atm);
            } else {
                JLabel failed = new JLabel("Invalid username/password. Please try again.");
                constraints.gridx = 0;
                constraints.gridy = 3;
                panel.add(failed, constraints);
                pack();
                //draw
                repaint();
            }
        });
    }

}


