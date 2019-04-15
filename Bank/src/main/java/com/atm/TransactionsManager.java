package com.atm;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The TransactionsManager manages all of the transactions in the ATM. This
 * class is used to add and remove transactions from the ATM.
 *
 * @author adam.comer@mail.utoronto.ca
 */
public class TransactionsManager {

	/**
	 * The list of transactions in the ATM ordered by timestamp.
	 */
	private final Connection conn;

	public TransactionsManager(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Creates a new transfer transaction between two accounts.
	 *
	 * @param fromAccount The account the transfer is from.
	 * @param toAccount   The account the transfer is to.
	 * @param amount      The amount in pennies that the transaction is in.
	 */
	public void createNewTransfer(Account fromAccount, Account toAccount, int amount) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, `to`, amount, timestamp) VALUES (?, ?, ?, ?, ?, NOW())");
			stmt.setString(1, UUID.randomUUID().toString());
			stmt.setString(2, "TRANSFER");
			stmt.setString(3, fromAccount.getName());
			stmt.setString(4, toAccount.getName());
			stmt.setInt(5, amount);

			stmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	/**
	 * Creates a new pay-bill transaction from an account.
	 *
	 * @param fromAccount The account the pay-bill is from.
	 * @param amount      The amount in pennies that the transaction is in.
	 */
	public void createNewPayBill(Account fromAccount, int amount) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, amount, timestamp) VALUES (?, ?, ?, ?, NOW())");
			stmt.setString(1, UUID.randomUUID().toString());
			stmt.setString(2, "PAY_BILL");
			stmt.setString(3, fromAccount.getName());
			stmt.setInt(4, amount);

			stmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
	}

	public Transaction getTransactionById(String id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT type, `to`, `from`, amount, `timestamp` FROM transactions WHERE id = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String type = rs.getString(1);
				String to = rs.getString(2);
				String from = rs.getString(3);
				int amount = rs.getInt(4);
				LocalDateTime timestamp = rs.getTimestamp(5).toLocalDateTime();
				Transaction t = new Transaction(type, from, to, amount, timestamp);
				t.setId(id);
				return t;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
		return null;
	}

	/**
	 * Gets the last transaction in the ATM.
	 *
	 * @return The last transaction in the ATM
	 */
	public Transaction getLastTransaction() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT type, `to`, `from`, amount, `timestamp`, id FROM transactions ORDER BY `timestamp` DESC LIMIT 1");

			if (rs.next()) {
				String type = rs.getString(1);
				String to = rs.getString(2);
				String from = rs.getString(3);
				int amount = rs.getInt(4);
				LocalDateTime timestamp = rs.getTimestamp(5).toLocalDateTime();
				String id = rs.getString(6);
				Transaction t = new Transaction(type, from, to, amount, timestamp);
				t.setId(id);
				return t;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Error connecting to the database. Check your internet connection.");
		}
		return null;
	}

	/**
	 * Gets the last transaction in the ATM for this user.
	 *
	 * @param user The User to get their last transaction.
	 * @return The last transaction of this user.
	 */
	public Transaction getLastTransaction(CustomerUser user) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT transactions.type, transactions.`to`, transactions.`from`, transactions.amount, transactions.timestamp, transactions.id FROM transactions INNER JOIN accounts ON accounts.id = transactions.`from` WHERE accounts.holder_a = ? OR accounts.holder_b = ? ORDER BY transactions.timestamp DESC LIMIT 1");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getUsername());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String type = rs.getString(1);
				String to = rs.getString(2);
				String from = rs.getString(3);
				int amount = rs.getInt(4);
				LocalDateTime timestamp = rs.getTimestamp(5).toLocalDateTime();
				String id = rs.getString(6);
				Transaction t = new Transaction(type, from, to, amount, timestamp);
				t.setId(id);
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the last transaction from the ATM for this Account.
	 *
	 * @param account The Account to get their last transaction.
	 * @return The last transaction of this account.
	 */
	public Transaction getLastTransaction(Account account) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT type, `to`, `from`, amount, `timestamp`, id FROM transactions WHERE `from` = ? ORDER BY `timestamp` DESC LIMIT 1");
			stmt.setString(1, account.getName());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String type = rs.getString(1);
				String to = rs.getString(2);
				String from = rs.getString(3);
				int amount = rs.getInt(4);
				LocalDateTime timestamp = rs.getTimestamp(5).toLocalDateTime();
				String id = rs.getString(6);
				Transaction t = new Transaction(type, from, to, amount, timestamp);
				t.setId(id);
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
