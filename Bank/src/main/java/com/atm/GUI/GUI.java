package com.atm.GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.atm.*;

/**
 * A graphical user interface for ATM.
 */
class GUI{

    /**
     * ATM currently being used
     */
    private static ATM atm;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://csc-two-o-seven-project.czqxxcp9ck39.us-east-1.rds.amazonaws.com:3306/final-project", "root", "JYfwpv7X8LHgZx4");

        atm = new ATM(conn);
        atm.checkForFirstOfMonth();
        new LoginScreen(atm);
    }

    /**
     * Logs the ATM out of its current user and displays the login screen
     */
    static void logout(){
        new LoginScreen(atm);
    }
}
