package com.atm;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Contains and manages all bank accounts.
 */
public class BankAccountsManager {

	/**
	 * Currency exchange this manager uses
	 */
	private final CurrencyExchange c;

	/**
	 * database connection.
	 */
	private final Connection conn;

	/**
	 * ATM this manager belongs to
	 */
	private final ATM atm;

	/**
	 * Constructs this account manager reading from the file to get current users and their bank accounts.
	 */
	public BankAccountsManager(Connection conn, ATM atm) {
		this.conn = conn;
		this.atm = atm;
		c = new CurrencyExchange();
	}

	/**
	 * Transfer money from one account to another account.
	 *
	 * @param a      Account to transfer the funds from.
	 * @param b      Account to transfer the funds to.
	 * @param amount The amount of the transfer(in pennies).
	 * @return A boolean representing the state of the transfer.
	 */
	public boolean transfer(Account a, Account b, int amount) {
		if (a.getType().equalsIgnoreCase(AccountsFactory.CREDIT_CARD) || amount <= 0) {
			return false;
		}
		if (!a.canTakeOut(amount) || (!(a.getUser().isStudent()) && !a.canTakeOut(amount + 100))) {
			return false;
		}
		try {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
			// Waive fee for students and transactions between same account/ owned by same user.
			if (a.getUser().isStudent() || a.getUser().getUsername().equals(b.getUser().getUsername())
					|| a.getName().equals(b.getName())) {
				a.moneyOut(amount);
				stmt.setInt(1, amount);
			} else {
				a.moneyOut(amount + 100);
				stmt.setInt(1, amount + 100);
			}
			stmt.setString(2, a.getName());
			stmt.execute();

			stmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
			if (a.getType().equalsIgnoreCase(AccountsFactory.US) ^ b.getType().equalsIgnoreCase(AccountsFactory.US)) {
				if (a.getType().equalsIgnoreCase(AccountsFactory.US)) {
					amount = c.usdToCad(amount);
				} else {
					amount = c.cadToUsd(amount);
				}
			}
			b.moneyIn(amount);
			stmt.setInt(1, amount);
			stmt.setString(2, b.getName());
			stmt.execute();

			atm.getTransactions().createNewTransfer(a, b, amount);

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
		return false;
	}

	/**
	 * Transfer money from an account to a user.
	 *
	 * @param acc    Account to transfer the funds from.
	 * @param user   User to transfer the funds to.
	 * @param amount The amount of the transfer(in pennies).
	 * @return A boolean representing the state of the transfer.
	 */
	public boolean transfer(Account acc, CustomerUser user, int amount) {
		return transfer(acc, user.getPrimaryAccount(), amount);
	}

	/**
	 * Withdraw money from ATM.
	 *
	 * @param amount The amount to withdraw in pennies.
	 * @param acc    The account to withdraw from
	 * @return A boolean representing the state of the withdraw.
	 */
	public boolean withdraw(int amount, Account acc) {
		if (!acc.canTakeOut(amount)) {
			return false;
		}
		if (!atm.getCashManager().canWithdraw(amount)) {
			return false;
		}
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
			stmt.setInt(1, amount);
			stmt.setString(2, acc.getName());
			stmt.execute();

			acc.moneyOut(amount);
			atm.getCashManager().getChange(amount);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
			return false;
		}
	}

	/**
	 * Calculates the compound interest of all of the savings accounts in the ATM.
	 */
	public void compoundInterest() {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance * 1.001 WHERE type = ?");
			stmt.setString(1, AccountsFactory.SAVINGS);
			stmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	/**
	 * Undo the last transaction for an account.
	 *
	 * @param acc The account to undo the last transaction from.
	 */
	public void undoTransaction(Account acc) {
		Transaction t = atm.getTransactions().getLastTransaction(acc);
		undoTransaction(t);
	}

	/**
	 * Undo the last transaction in the ATM.
	 */
	public void undoTransaction() {
		Transaction t = atm.getTransactions().getLastTransaction();
		undoTransaction(t);
	}

	/**
	 * Undo transaction by id.
	 * @param id String
	 */
	public void undoTransaction(String id){
		Transaction t = atm.getTransactions().getTransactionById(id);
		undoTransaction(t);
	}

	/**
	 * Undo transaction by id helper.
	 * @param t Transaction
	 */
	private void undoTransaction(Transaction t) {
		if (t == null || t.getType().equals("PAY_BILL")) {
			return;
		}
		Account fromAcc = findAccount(t.getFrom());
		Account toAcc = findAccount(t.getTo());
		if (toAcc.getType().equalsIgnoreCase(AccountsFactory.CREDIT_CARD)) {
			return;
		}

		int amount = t.getAmount();
		try {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
			stmt.setInt(1, amount);
			stmt.setString(2, toAcc.getName());
			stmt.execute();

			toAcc.moneyOut(amount);

			stmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
			if (toAcc.getType().equalsIgnoreCase(AccountsFactory.US) ^ fromAcc.getType().equalsIgnoreCase(AccountsFactory.US)) {
				if (fromAcc.getType().equalsIgnoreCase(AccountsFactory.US)) {
					amount = c.cadToUsd(amount);
				} else {
					amount = c.usdToCad(amount);
				}
			}
			fromAcc.moneyIn(amount);
			stmt.setInt(1, amount);
			stmt.setString(2, fromAcc.getName());
			stmt.execute();

			atm.getTransactions().createNewTransfer(toAcc, fromAcc, amount);

			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	/**
	 * Find account given by the account name.
	 *
	 * @param accountName Account name to find.
	 * @return The account if found or null if not found.
	 */
	public Account findAccount(String accountName) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT accounts.id, accounts.type, accounts.balance, accounts.holder_a, accounts.holder_b, users.username, users.password, users.type, users.student, users.secQue, users.secAns FROM accounts LEFT JOIN users ON accounts.holder_a = users.username OR accounts.holder_b = users.username WHERE accounts.id = ?");
			stmt.setString(1, accountName);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String username = rs.getString(6);
				String password = rs.getString(7);
				String userType = rs.getString(8);
				boolean userStudent = rs.getBoolean(9);
				String secQue = rs.getString(10);
				String seqAns = rs.getString(11);

				if (userType.equals(UserFactory.BANK_MANAGER)) {
					return null;
				}
				CustomerUser user = new CustomerUser(username, password, secQue, seqAns, userStudent);

				String accountId = rs.getString(1);
				String accountType = rs.getString(2);
				int accountBalance = rs.getInt(3);

				AccountsFactory factory = new AccountsFactory();
				Account account = factory.createAccount(accountType, user, null);
				if(account == null){
					System.out.println("Account type not recognized, please try again");
					return null;
				}
				account.setAccountNumber(accountId);
				account.setBalance(accountBalance);
				return account;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Deposit txt format: cheque or cash, amount.
	 * Read deposits file of the form accountName,amount
	 * * Will only accept deposit amount of multiples of 5.
	 */
	public void deposit(Account acc, int numFive, int numTen, int numTwenty, int numFifty) {
		int amount = (5 * numFive + 10 * numTen + 20 * numTwenty + 50 * numFifty) * 100;
		atm.getCashManager().addFunds(numFive, numTen, numTwenty, numFifty);
		deposit(acc, amount);

	}

	/**
	 * This deposit method is for depositing cheques.
	 *
	 * @param acc    Account
	 * @param amount int
	 */
	public void deposit(Account acc, int amount) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
			stmt.setInt(1, amount);
			stmt.setString(2, acc.getName());
			stmt.execute();

			acc.moneyIn(amount);

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");

		}
	}

	/**
	 * Pay a bill to a non-users account.
	 *
	 * @param amount      Amount of the bill to pay.
	 * @param companyName Company to pay the bill to.
	 * @param acc         The account to draw the funds from.
	 * @return boolean
	 */
	public boolean payBill(int amount, String companyName, Account acc, String currency) {
		int convertedAmount;
		if (currency.equals("CAD") && acc.getType().equalsIgnoreCase(AccountsFactory.US)) {
			convertedAmount = c.cadToUsd(amount);
		} else if (currency.equals("USD") && !(acc.getType().equalsIgnoreCase(AccountsFactory.US))) {
			convertedAmount = c.usdToCad(amount);
		} else {
			convertedAmount = amount;
		}
		if (!acc.canTakeOut(convertedAmount)) {
			return false;
		}

		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
			stmt.setInt(1, convertedAmount);
			stmt.setString(2, acc.getName());
			stmt.execute();

			acc.moneyOut(convertedAmount);
			atm.getTransactions().createNewPayBill(acc, convertedAmount);

			BufferedWriter file = new BufferedWriter(new FileWriter("./outgoing.txt", true));
			file.write("Payed bill: $" + amount / 100 + "." + amount % 100 + " to " + companyName + " in " + currency + ".");
			file.newLine();
			file.close();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
		return false;
	}

	/**
	 * Write accountList to a file to be saved.
	 * writes in the format account type, account username, account balance, date of creation, account number, isPrimary
	 */
	public void createAccount(String accountType, CustomerUser user, CustomerUser user1) {
		AccountsFactory factory = new AccountsFactory();
		Account account = factory.createAccount(accountType, user, null);
		if(account == null){
			System.out.println("Account type not recognized, please try again");
			return;
		}

		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a, holder_b) VALUES (?, ?, ?, ?, ?)");
			stmt.setString(1, account.getName());
			stmt.setString(2, accountType);
			stmt.setInt(3, 0);
			stmt.setString(4, user.getUsername());
			if (user1 == null) {
				stmt.setString(5, null);
			} else {
				stmt.setString(5, user1.getUsername());
			}
			stmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	/**
	 * Returns a list of the accounts belonging to a specific user.
	 *
	 * @param user CustomerUser
	 * @return ArrayList<Account>
	 */
	public ArrayList<Account> getAccountsForUser(CustomerUser user) {
		ArrayList<Account> accounts = new ArrayList<>();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ? OR holder_b = ?");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getUsername());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String accountId = rs.getString(1);
				String accountType = rs.getString(2);
				int accountBalance = rs.getInt(3);

				AccountsFactory factory = new AccountsFactory();
				Account account = factory.createAccount(accountType, user, null);
				if(account == null){
					System.out.println("Account type not recognized, please try again");
					break;
				}
				account.setAccountNumber(accountId);
				account.setBalance(accountBalance);
				accounts.add(account);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
		return accounts;
	}

	/**
	 * Write an account request to a file
	 *
	 * @param user        The user who is requesting a new bank account.
	 * @param accountType The type of account this user is requesting.
	 */
	public void requestAccount(CustomerUser user, CustomerUser jointUser, String accountType) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO account_requests (username, `username-2`, account_type) VALUES (?, ?, ?)");
			stmt.setString(1, user.getUsername());
			if (jointUser == null) {
				stmt.setString(2, null);
			} else {
				stmt.setString(2, jointUser.getUsername());
			}
			stmt.setString(3, accountType);
			stmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	/**
	 * Get the list of account requests from a file.
	 *
	 * @return A list of account requests from disk.
	 */
	public ArrayList<String[]> getAccountRequest() {
		ArrayList<String[]> accountRequests = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT username, `username-2`, account_type FROM account_requests");

			while (rs.next()) {
				accountRequests.add(new String[]{rs.getString(1), rs.getString(2), rs.getString(3)});
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");

		}
		return accountRequests;
	}

	/**
	 * Delete request listed in account request panel.
	 * @param request String[]
	 */
	public void deleteAccountRequest(String[] request) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM account_requests WHERE username = ? AND account_type = ? LIMIT 1");
			stmt.setString(1, request[0]);
			stmt.setString(2, request[2]);
			stmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	/**
	 * Get the net total of accounts for a specified user.
	 *
	 * @return int
	 */
	public int getNetTotal(CustomerUser user) {
		ArrayList<Account> temp = getAccountsForUser(user);
		int total = 0;
		for (Account acc : temp) {
			if (acc.getType().equals(AccountsFactory.CREDIT_CARD) || acc.getType().equals(AccountsFactory.LINE_OF_CREDIT)){
				total -= acc.getBalance();
			}else {
				total += acc.getBalance();
			}
		}
		return total;
	}
}
