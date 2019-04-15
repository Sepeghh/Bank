package com.atm;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Holds the data for a transaction in the ATM.
 *
 * @author adam.comer@mail.utoronto.ca
 */
public class Transaction {

	private String id;

	/**
	 * Type of the transaction.  Either TRANSFER or PAY_BILL.
	 */
	private String type;

	/**
	 * The account that this transaction is coming from
	 */
	private String from;

	/**
	 * The account that this transaction is going to. This can be null in the
	 * case of paying a bill.
	 */
	private String to;

	/**
	 * Amount of the transaction in pennies.
	 */
	private int amount;

	/**
	 * The timestamp of the transaction.
	 */
	private LocalDateTime timestamp;

	/**
	 * Creates a new Transaction that holds the associated data for a
	 * transaction in the bank.
	 * <p>
	 * Allows us to override the time to any time.
	 *
	 * @param type Type of the transaction. Either TRANSFER or PAY_BILL.
	 * @param from Account number for the account the transaction is going from.
	 * @param to   Account number for the account the transaction is going to. This value will be null if this transaction is a bill pay.
	 */
	public Transaction(String type, String from, String to, int amount, LocalDateTime timestamp) {
		if (!type.equals("TRANSFER") && !type.equals("PAY_BILL")) {
			throw new Error("Invalid transaction type. Must be TRANSFER or PAY_BILL");
		}
		this.type = type;
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	/**
	 * Creates a string format of this class.
	 *
	 * @return A string formatted version of this class.
	 */
	@Override
	public String toString() {
		if (type.equals("TRANSFER")) {
			return String.format("Transfer At: %s, From: %s, To: %s, Amount: $" + amount / 100 + "." + amount % 100, timestamp, from, to);
		} else {
			return String.format("Pay Bill At: %s, From: %s, Amount: $" + amount / 100 + "." + amount % 100, timestamp, from);
		}
	}

	/**
	 * Gets the account that this transaction is from.
	 *
	 * @return The account that this transaction is from.
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Gets the type of this transaction.
	 *
	 * @return The type of this transaction
	 */
	public String getType() {
		return type;
	}

	/**
	 * The amount, in pennies, of the transaction.
	 *
	 * @return The amount of the transaction.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * The account that this transaction is going to.
	 *
	 * @return The account that this transaction is going to.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * The timestamp of this transaction.
	 *
	 * @return The timestamp of this transaction.
	 */
	private LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transaction) {
			Transaction t = (Transaction) obj;

			if (!type.equals(t.getType())) {
				return false;
			} else if (!Objects.equals(to, t.getTo())) {
				return false;
			} else if (!from.equals(t.getFrom())) {
				return false;
			} else if (amount != t.getAmount()) {
				return false;
			} else return timestamp.equals(t.getTimestamp());
		}
		return false;
	}
}
