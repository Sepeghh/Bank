package com.atm;

import java.io.*;

/**
 * Manage the cash in an ATM.
 */
public class CashManager {

	/**
	 * The number of $5 bills.
	 */
	private static int numberOfFive = 0;

	/**
	 * The number of $10 bills.
	 */
	private static int numberOfTen = 0;

	/**
	 * The number of $20 bills.
	 */
	private static int numberOfTwenty = 0;

	/**
	 * The number of $50 bills.
	 */
	private static int numberOfFifty = 0;

	/**
	 * A new CashManager that loads amount of cash in machine from saved file.
	 */
	CashManager() {
		cashFromFile();
		checkBalance();
	}

	/**
	 * Allow bank manager to add funds to the ATM.
	 *
	 * @param five   The number of $5 bills to add to the ATM.
	 * @param ten    The number of $10 bills to add to the ATM.
	 * @param twenty The number of $20 bills to add to the ATM.
	 * @param fifty  The number of $50 bills to add to the ATM.
	 */
	public void addFunds(int five, int ten, int twenty, int fifty) {
		numberOfFifty += fifty;
		numberOfTwenty += twenty;
		numberOfTen += ten;
		numberOfFive += five;
		cashToFile();
		checkBalance();
	}

	/**
	 * Get change for user's withdrawal.
	 * This method removes the bills from the ATM while getting change.
	 *
	 * @param amount The amount to get change from.
	 */
	void getChange(int amount) {
		if (amount == 0) {
			System.out.println("Get change successfully!");
		} else {
			if (numberOfFifty > 0 && amount >= 5000) {
				numberOfFifty--;
				amount -= 5000;
				getChange(amount);
			} else if (numberOfTwenty > 0 && amount >= 2000) {
				numberOfTwenty--;
				amount -= 2000;
				getChange(amount);
			} else if (numberOfTen > 0 && amount >= 1000) {
				numberOfTen--;
				amount -= 1000;
				getChange(amount);
			} else if (numberOfFive > 0 && amount >= 500) {
				numberOfFive--;
				amount -= 500;
				getChange(amount);
			}
		}
		checkBalance();
		cashToFile();
	}

	/**
	 * Check if the the ATM could withdraw the given amount money.
	 *
	 * @param amount Amount to check if the ATM can withdraw.
	 * @return If the ATM has this much cash in bills on hand.
	 */
	boolean canWithdraw(int amount) {
		return canWithdrawHelper(amount, numberOfFive, numberOfTen, numberOfTwenty, numberOfFifty);
	}

	/**
	 * Helper method for canWithdraw.
	 *
	 * @param amount Amount to check if the ATM can withdraw.
	 * @param five   Number of fives left.
	 * @param ten    Number of tens left.
	 * @param twenty Number of twenties left.
	 * @param fifty  Number of fifties left.
	 * @return boolean
	 */
	private boolean canWithdrawHelper(int amount, int five, int ten, int twenty, int fifty) {
		if (amount % 500 != 0) {
			return false;
		} else if (amount == 0){
			return true;
		} else if (amount < 0) {
			return false;
		} else {
			boolean Five = false;
			boolean Ten = false;
			boolean Twenty = false;
			boolean Fifty = false;
			if (five > 0) {
				five--;
				amount -= 500;
				Five = canWithdrawHelper(amount, five, ten, twenty, fifty);
			} else if (ten > 0) {
				ten--;
				amount -= 1000;
				Ten = canWithdrawHelper(amount, five, ten, twenty, fifty);
			} else if (twenty > 0) {
				twenty--;
				amount -= 2000;
				Twenty = canWithdrawHelper(amount, five, ten, twenty, fifty);
			} else if (fifty > 0) {
				fifty--;
				amount -= 5000;
				Fifty = canWithdrawHelper(amount, five, ten, twenty, fifty);
			}
			return Fifty || Twenty || Ten || Five;
		}
	}

	/**
	 * Writes the amount of cash in the ATM to a file.
	 * Formatted: fives, tens, twenty's, fifty's
	 */
	private void cashToFile() {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter("./cash.txt"));
			file.write(numberOfFive + "," + numberOfTen + "," + numberOfTwenty + "," + numberOfFifty);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the ATM's cash from a file.
	 */
	private void cashFromFile() {
		if (new File("./cash.txt").exists()) {
			try {
				String line;
				BufferedReader file = new BufferedReader(new FileReader("./cash.txt"));
				line = file.readLine();
				if(line != null) {
					String[] info = line.split(",");
					numberOfFive = Integer.valueOf(info[0]);
					numberOfTen = Integer.valueOf(info[1]);
					numberOfTwenty = Integer.valueOf(info[2]);
					numberOfFifty = Integer.valueOf(info[3]);
					file.close();
				} else {
					try {
						BufferedWriter file1 = new BufferedWriter(new FileWriter("./cash.txt"));
						file1.write("0,0,0,0");
						file.close();
						numberOfFive = 0;
						numberOfTen = 0;
						numberOfTwenty = 0;
						numberOfFifty = 0;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			numberOfFive = 0;
			numberOfTen = 0;
			numberOfTwenty = 0;
			numberOfFifty = 0;
		}
	}

	/**
	 * Check the balance of the ATM sending an alert if necessary.
	 */
	private void checkBalance() {
		try {
			if (numberOfFive < 20 || numberOfTen < 20 || numberOfTwenty < 20 || numberOfFifty < 20) {
				String alert = "Need to restock cash in ATM!! Time of alert: " + ATM.getTime().toString();
				BufferedWriter file = new BufferedWriter(new FileWriter("./alerts.txt", true));
				file.write(alert);
				file.newLine();
				file.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
