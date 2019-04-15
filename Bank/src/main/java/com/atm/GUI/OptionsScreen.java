package com.atm.GUI;

import com.atm.ATM;
import com.atm.User;

import javax.swing.*;
import java.awt.*;

/**
 * A super class option screen, all users get these options
 */
class OptionsScreen{

    /**
     * the User who is working with the Option Screen
     */
    final User user;

    /**
     * ATM currently being Used
     */
    final ATM atm;

    /**
     * Creates an option screen with an interacting user and the ATM they're interacting with
     */
    OptionsScreen(User user, ATM atm){
        this.user = user;
        this.atm = atm;
    }

    /**
     * Screen for changing a users password
     */
    void changePasswordScreen() {
        JFrame changePasswordFrame = new JFrame("Bank of 207");
        JPanel changePasswordPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //Ask for the old password
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel enterOld = new JLabel("Please enter old password");
        changePasswordPanel.add(enterOld, constraints);
        constraints.gridx = 1;
        //get the old password
        JPasswordField oldPass = new JPasswordField(20);
        changePasswordPanel.add(oldPass, constraints);

        //Ask for the new password
        JLabel enterNew = new JLabel("Please enter new password");
        constraints.gridx = 0;
        constraints.gridy = 1;
        changePasswordPanel.add(enterNew, constraints);
        //get the new password
        JPasswordField enterNewFirst = new JPasswordField(20);
        constraints.gridx = 1;
        changePasswordPanel.add(enterNewFirst, constraints);

        //Confirm the password
        JLabel newConfirm = new JLabel("Please confirm new password");
        constraints.gridx = 0;
        constraints.gridy = 2;
        changePasswordPanel.add(newConfirm, constraints);
        //get the new password for confirmation
        JPasswordField enterNewConfirm = new JPasswordField(20);
        constraints.gridx = 1;
        changePasswordPanel.add(enterNewConfirm, constraints);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if (enterNewFirst.getText().equals(enterNewConfirm.getText())) {
                //if the new passwords match each other
                if(atm.getUsers().changePassword(user, enterNewFirst.getText(), oldPass.getText())){
                    //if the old password is correct, it changes the password
                    showMessage( "Password successfully changed" );
                    changePasswordPanel.repaint();
                } else {
                    showMessage( "Incorrect old password, please try again" );
                    changePasswordPanel.repaint();
                }
            } else {
                //if the new passwords doesn't match
                showMessage( "New Password did not match, please try Again");
                changePasswordPanel.repaint();
            }
        });
        constraints.gridy = 3;
        constraints.gridx = 0;
        submit.setPreferredSize(new Dimension(150, 30));
        changePasswordPanel.add(submit, constraints);
        //Exit Button
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            user.getOptionsScreen(atm);
            changePasswordFrame.setVisible(false);
            changePasswordFrame.dispose();
        });
        constraints.gridx = 1;
        exit.setPreferredSize(new Dimension(150, 30));

        changePasswordPanel.add(exit, constraints);
        //add the panel to the frame
        changePasswordFrame.add(changePasswordPanel);
        changePasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changePasswordFrame.pack();
        //display the frame
        changePasswordFrame.setVisible(true);
        changePasswordFrame.setLocationRelativeTo(null);
    }

    /**
     * show a new Screen which displays the input message
     * @param Message the message which will be displayed
     */
    void showMessage(String Message){
        JFrame frame = new JFrame("Bank of 207");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel message = new JLabel( Message );
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add( message,constraints );

        JButton exit = new JButton( "Exit" );
        constraints.anchor = GridBagConstraints.CENTER;
        exit.addActionListener( e -> {
            frame.setVisible( false );
            frame.dispose();
        } );
        constraints.gridy = 1;
        //add the Exit button to the panel
        panel.add( exit,constraints );
        //add the panel to the frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.getRootPane().setDefaultButton( exit );
        //display the frame
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
