import com.atm.ATM;
import com.atm.CashManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CashMangerTests {

	@Before
	@After
	public void deleteFile() {
		File cashFile = new File("./cash.txt");
		cashFile.delete();

		File alertsFile = new File("./alerts.txt");
		alertsFile.delete();
	}

	private void saveCashToDisk(int five, int ten, int twenty, int fifty) {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter("./cash.txt"));
			file.write(five + "," + ten + "," + twenty + "," + fifty);
			file.close();
		} catch (Exception ignore) {}
	}

	private int[] getCashFromDisk() {
		int[] cash = new int[4];
		try {
			BufferedReader file = new BufferedReader(new FileReader("./cash.txt"));
			String line = file.readLine();
			String[] info = line.split(",");
			cash[0] = Integer.valueOf(info[0]);
			cash[1] = Integer.valueOf(info[1]);
			cash[2] = Integer.valueOf(info[2]);
			cash[3] = Integer.valueOf(info[3]);

		} catch (Exception ignore){}
		return cash;
	}

	private ArrayList<String> getAlerts() {
		ArrayList<String> lines = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./alerts.txt"));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception ignore) {}
		return lines;
	}

//	@Test
//	public void testReadNoneFromFile() throws SQLException, ClassNotFoundException {
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//
//		assertEquals(0, cm.getBalance());
//	}
//
//	@Test
//	public void testReadFromFile() throws SQLException, ClassNotFoundException {
//		saveCashToDisk(1, 2, 3, 4);
//
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//
//		assertEquals(28500, cm.getBalance());
//	}
//
//	@Test
//	public void testCheckBalanceNoAlert() throws SQLException, ClassNotFoundException {
//		saveCashToDisk(20, 20, 20, 20);
//
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//
//		cm.checkBalance();
//		assertEquals(0, getAlerts().size());
//	}
//
//	@Test
//	public void testCheckBalanceAlert() throws SQLException, ClassNotFoundException {
//		saveCashToDisk(5, 5, 5, 5);
//
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//
//		cm.checkBalance();
//		assertEquals(1, getAlerts().size());
//
//		cm.checkBalance();
//		assertEquals(2, getAlerts().size());
//	}
//
//	@Test
//	public void testAddFunds() throws SQLException, ClassNotFoundException {
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//		cm.addFunds(100, 150, 200, 250);
//
//		assertArrayEquals(new int[]{100, 150, 200, 250}, getCashFromDisk());
//		assertEquals(0, getAlerts().size());
//	}
//
//	@Test
//	public void testAddFundsAlert() throws SQLException, ClassNotFoundException {
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//		cm.addFunds(1, 2, 3, 4);
//
//		assertArrayEquals(new int[]{1, 2, 3, 4}, getCashFromDisk());
//		assertEquals(1, getAlerts().size());
//	}
//
//	@Test
//	public void testValidCanWithdraw() throws SQLException, ClassNotFoundException {
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//		cm.addFunds(100, 150, 200, 250);
//
//		assertTrue(cm.canWithdraw(1000));
//	}
//
//	@Test
//	public void testInvalidCanWithdraw() throws SQLException, ClassNotFoundException {
//		ATM atm = new ATM();
//		CashManager cm = new CashManager(atm);
//		cm.addFunds(1, 2, 3, 4);
//
//		assertFalse(cm.canWithdraw(30000));
//		assertFalse(cm.canWithdraw(100));
//		assertFalse(cm.canWithdraw(-100));
//	}
}
