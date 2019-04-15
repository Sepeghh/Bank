package com.atm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A manager for all user accounts for the ATM.
 */
public class UserManager {

	/**
	 * Database connection
	 */
	private final Connection conn;

	/**
	 * Create a UserManager.
	 */
	UserManager(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Check if the user with the given input username exists.
	 *
	 * @param username Username to check if it's available
	 * @return If this username is available.
	 */
	public boolean usernameIsAvailable(String username) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM users WHERE username = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
		return true;
	}

	/**
	 * Get the user given by a username string, null otherwise.
	 *
	 * @param username Username of find this user by.
	 * @return The user that was found or null if no user was found.
	 */
	public User getUserAccount(String username) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT users.username, users.password, users.type, users.student, users.secQue, users.secAns, accounts.id, accounts.type, accounts.balance, accounts.holder_a, accounts.holder_b FROM users LEFT JOIN accounts ON users.primary = accounts.id WHERE users.username = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			boolean hasNext = rs.next();
			if (!hasNext) {
				return null;
			}

			String userAccount = rs.getString(1);
			String userPassword = rs.getString(2);
			String userType = rs.getString(3);
			boolean userStudent = rs.getBoolean(4);
			String secQue = rs.getString(5);
			String secAns = rs.getString(6);

			if (userType.equals(UserFactory.BANK_MANAGER)) {
				return new BankManagerUser(userAccount, userPassword, secQue, secAns);
			}
			CustomerUser user;
			if (userType.equals(UserFactory.BANK_TELLER)) {
				user = new BankTellerUser(userAccount, userPassword, secQue, secAns);
			} else {
				user = new CustomerUser(userAccount, userPassword, secQue, secAns, userStudent);
			}

			String accountId = rs.getString(7);
			String accountType = rs.getString(8);
			int accountBalance = rs.getInt(9);
			String accountHolderB = rs.getString(11);

			CustomerUser userB = null;
			if (accountHolderB != null) {
				stmt = conn.prepareStatement("SELECT username, password, type, student, secQue, secAns FROM users WHERE users.username = ?");
				stmt.setString(1, accountHolderB);
				rs = stmt.executeQuery();
				rs.next();

				if (!rs.getString(2).equals(UserFactory.BANK_MANAGER)) {
					String userAccountB = rs.getString(1);
					String userPasswordB = rs.getString(2);
					boolean userStudentB = rs.getBoolean(4);
					String userSecQueB = rs.getString(5);
					String useSecAnsB = rs.getString(6);

					userB = new CustomerUser(userAccountB, userPasswordB, userSecQueB, useSecAnsB, userStudentB);
				}
			}
			Account account;
			switch (accountType) {
				case AccountsFactory.CHEQUING:
					account = new ChequingAccount(user);
					break;
				case AccountsFactory.SAVINGS:
					account = new SavingsAccount(user);
					break;
				case AccountsFactory.LINE_OF_CREDIT:
					account = new LineOfCreditAccount(user);
					break;
				case AccountsFactory.CREDIT_CARD:
					account = new CreditAccount(user);
					break;
				default:
					return null;
			}
			account.setAccountNumber(accountId);

			account.setBalance(accountBalance);
			if (userB != null) {
				account = new JointAccount(account, userB);
			}
			user.setPrimaryAccount(account);

			return user;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
		return null;
	}

	/**
	 * Return whether the given username and password match
	 *
	 * @param user User to check password for
	 * @param pass Password to check for this user.
	 * @return If the login was successful.
	 */
	public boolean checkLogin(User user, String pass) {
		if (user == null) {
			return false;
		}
		return (user.getPassword().equals(pass));
	}

	/**
	 * Adds admin account to the database if there is not one already
	 * @param user BankManagerUser
	 */
	public void addAdminAccount(BankManagerUser user) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, type) VALUES (?, ?, ?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, UserFactory.BANK_MANAGER);

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}

	}

	/**
	 * Adds a bank teller to the database
	 * @param tempUser BankTellerUser
	 * @return boolean
	 */
	public boolean createBankTeller(BankTellerUser tempUser) {
		if (usernameIsAvailable(tempUser.getUsername())) {
			try {
				conn.setAutoCommit(false);
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
				stmt.setString(1, tempUser.getPrimaryAccount().getName());
				stmt.setString(2, tempUser.getPrimaryAccount().getType());
				stmt.setInt(3, tempUser.getPrimaryAccount().getBalance());
				stmt.setString(4, tempUser.getUsername());

				stmt.executeUpdate();

				stmt = conn.prepareStatement("INSERT INTO users (username, password, type, `primary`, secQue, secAns) VALUES (?, ?, ?, ?, ?, ?)");
				stmt.setString(1, tempUser.getUsername());
				stmt.setString(2, tempUser.getPassword());
				stmt.setString(3, UserFactory.BANK_TELLER);
				stmt.setString(4, tempUser.getPrimaryAccount().getName());
				stmt.setString(5, tempUser.getSecurityQuestion());
				stmt.setString(6, tempUser.getSecurityAnswer());

				stmt.executeUpdate();

				conn.commit();
				conn.setAutoCommit(true);

				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					System.out.println("Error connecting to the database. Check your internet connection.");
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println("Error connecting to the database. Unable to rollback transaction. Check your internet connection.");
				}
			}
		}
		return false;
	}

	/**
	 * Creates a new customer from a username and password.
	 *
	 * @return If the user was created successfully.
	 */
	public boolean createCustomer(CustomerUser tempUser) {
		if (usernameIsAvailable(tempUser.getUsername())) {
			try {
				conn.setAutoCommit(false);
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
				stmt.setString(1, tempUser.getPrimaryAccount().getName());
				stmt.setString(2, tempUser.getPrimaryAccount().getType());
				stmt.setInt(3, 0);
				stmt.setString(4, tempUser.getUsername());
				//stmt.setString(5, null);


				stmt.executeUpdate();

				stmt = conn.prepareStatement("INSERT INTO users (username, password, type, `primary`, secQue, secAns, student) VALUES (?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, tempUser.getUsername());
				stmt.setString(2, tempUser.getPassword());
				stmt.setString(3, UserFactory.CUSTOMER_USER);
				stmt.setString(4, tempUser.getPrimaryAccount().getName());
				stmt.setString(5, tempUser.getSecurityQuestion());
				stmt.setString(6, tempUser.getSecurityAnswer());
				stmt.setBoolean(7, tempUser.isStudent());

				stmt.executeUpdate();

				conn.commit();
				conn.setAutoCommit(true);

				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					System.out.println("Error connecting to the database. Check your internet connection.");
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println("Error connecting to the database. Unable to rollback transaction. Check your internet connection.");
				}
			}
		}
		return false;
	}

	/**
	 * Changes the password of a user.
	 *
	 * @param user        The user to change the password for.
	 * @param newPassword The new password for the user.
	 * @param oldPassword The old password for the user.
	 * @return If the password was changed successfully.
	 */
	public boolean changePassword(User user, String newPassword, String oldPassword) {
		if (checkLogin(user, oldPassword)) {
			try {
				PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
				stmt.setString(1, newPassword);
				stmt.setString(2, user.getUsername());
				stmt.execute();

				return true;
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Error connecting to the database. Check your internet connection.");
			}
		}
		return false;
	}

	/**
	 * Alters if an account is a student account or not
	 * @param student The boolean to set the value to
	 * @param user The user to set the boolean for.
	 */
	public void setStudent(boolean student, CustomerUser user){
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE users SET student = ? WHERE username = ?");
			stmt.setBoolean(1, student);
			stmt.setString(2, user.getUsername());
			stmt.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	/**
	 * Deduct the bank fee for users who are not students.
	 */
	void monthlyBankFee() {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts INNER JOIN (SELECT users.`primary` FROM users WHERE users.student = 0) AS NonStudentUsers ON accounts.id = NonStudentUsers.`primary` SET balance = balance - 500  WHERE accounts.balance > 500");
			stmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}
}
