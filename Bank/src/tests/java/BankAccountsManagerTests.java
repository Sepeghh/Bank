import com.atm.*;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class BankAccountsManagerTests {

	private static Connection conn;

	@BeforeClass
	public static void setup() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://csc-two-o-seven-project.czqxxcp9ck39.us-east-1.rds.amazonaws.com:3306/final-project-testing", "root", "JYfwpv7X8LHgZx4");
	}

	@Test
	public void testCreateChequingAccount() throws SQLException {
		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		manager.createAccount("CHEQUING", user, null);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(0, rs.getInt(3));
		assertNull(rs.getString(5));
	}

	@Test
	public void testCreateLineOfCreditAccount() throws SQLException {
		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		manager.createAccount("LINE_OF_CREDIT", user, null);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals("LINE_OF_CREDIT", rs.getString(2));
		assertEquals(0, rs.getInt(3));
		assertNull(rs.getString(5));
	}

	@Test
	public void testCreateCreditCardAccount() throws SQLException {
		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		manager.createAccount("CREDIT_CARD", user, null);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals("CREDIT_CARD", rs.getString(2));
		assertEquals(0, rs.getInt(3));
		assertNull(rs.getString(5));
	}

	@Test
	public void testCreateSavingsAccount() throws SQLException {
		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false );

		manager.createAccount("SAVINGS", user, null);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals("SAVINGS", rs.getString(2));
		assertEquals(0, rs.getInt(3));
		assertNull(rs.getString(5));
	}

	@Test
	public void testCreateUSDAccount() throws SQLException {
		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		manager.createAccount("US", user, null);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals("US", rs.getString(2));
		assertEquals(0, rs.getInt(3));
		assertNull(rs.getString(5));
	}

	@Test
	public void testCreateAccountRequest() throws SQLException {
		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		manager.requestAccount(user, null, "CHEQUING");

		PreparedStatement statement = conn.prepareStatement("SELECT username, account_type FROM account_requests WHERE username = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals("CHEQUING", rs.getString(2));
	}

	@Test
	public void testGetAccountRequests() throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO account_requests (username, account_type) VALUES (?, ?), (?, ?), (?, ?)");
		stmt.setString(1, "testing@mail.utoronto.ca");
		stmt.setString(2, "LINE_OF_CREDIT");
		stmt.setString(3, "testing-1@mail.utoronto.ca");
		stmt.setString(4, "CHEQUING");
		stmt.setString(5, "testing-1@mail.utoronto.ca");
		stmt.setString(6, "CREDIT_CARD");
		stmt.execute();

		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);

		ArrayList<String[]> requests = manager.getAccountRequest();
		assertEquals("testing@mail.utoronto.ca", requests.get(0)[0]);
		assertEquals("LINE_OF_CREDIT", requests.get(0)[2]);
		assertEquals("testing-1@mail.utoronto.ca", requests.get(1)[0]);
		assertEquals("CHEQUING", requests.get(1)[2]);
		assertEquals("testing-1@mail.utoronto.ca", requests.get(2)[0]);
		assertEquals("CREDIT_CARD", requests.get(2)[2]);
	}

	@Test
	public void testGetAccount() throws SQLException {
		String accountId = UUID.randomUUID().toString();

		PreparedStatement statement = conn.prepareStatement("INSERT INTO users (username, password, type, `primary`) VALUES (?, ?, ?, ?)");
		statement.setString(1, "testing@mail.utoronto.ca");
		statement.setString(2, "1234");
		statement.setString(3, "CUSTOMER_USER");
		statement.setString(4, accountId);
		statement.execute();

		statement = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?), (?, ?, ?, ?), (?, ?, ?, ?)");
		statement.setString(1, accountId);
		statement.setString(2, "CHEQUING");
		statement.setInt(3, 0);
		statement.setString(4, "testing@mail.utoronto.ca");
		statement.setString(5, UUID.randomUUID().toString());
		statement.setString(6, "CREDIT_CARD");
		statement.setInt(7, 1000);
		statement.setString(8, "testing@mail.utoronto.ca");
		statement.setString(9, UUID.randomUUID().toString());
		statement.setString(10, "LINE_OF_CREDIT");
		statement.setInt(11, 2000);
		statement.setString(12, "not-testing@mail.utoronto.ca");
		statement.execute();

		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		ArrayList<Account> accounts = manager.getAccountsForUser(user);

		assertEquals(2, accounts.size());

		if (!(accounts.get(0) instanceof ChequingAccount)) {
			Account temp = accounts.get(1);
			accounts.set(1, accounts.get(0));
			accounts.set(0, temp);
		}

		assertTrue(accounts.get(0) instanceof ChequingAccount);
		assertEquals(0, accounts.get(0).getBalance());
		assertEquals("testing@mail.utoronto.ca", accounts.get(0).getUser().getUsername());

		assertTrue(accounts.get(1) instanceof CreditAccount);
		assertEquals(-1000, accounts.get(1).getBalance());
		assertEquals("testing@mail.utoronto.ca", accounts.get(1).getUser().getUsername());
	}

	@Test
	public void testDeposit() throws SQLException {
		String accountId = UUID.randomUUID().toString();
		PreparedStatement statement = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
		statement.setString(1, accountId);
		statement.setString(2, "CHEQUING");
		statement.setInt(3, 0);
		statement.setString(4, "testing@mail.utoronto.ca");
		statement.execute();

		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);
		Account a = new ChequingAccount(user);
		a.setAccountNumber(accountId);

		manager.deposit(a, 10000);

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(10000, rs.getInt(3));
		assertNull(rs.getString(5));

		assertEquals(10000, a.getBalance());
	}

	@Test
	public void testWithdraw() throws SQLException {
		String accountId = UUID.randomUUID().toString();
		PreparedStatement statement = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
		statement.setString(1, accountId);
		statement.setString(2, "CHEQUING");
		statement.setInt(3, 1000);
		statement.setString(4, "testing@mail.utoronto.ca");
		statement.execute();

		ATM atm = new ATM(conn);
		atm.getCashManager().addFunds(10, 10, 10, 10);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);
		Account a = new ChequingAccount(user);
		a.setAccountNumber(accountId);
		a.setBalance(1000);

		manager.withdraw(500, a);

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(500, rs.getInt(3));
		assertNull(rs.getString(5));

		assertEquals(500, a.getBalance());
	}

	@Test
	public void testPayBill() throws SQLException {
		String accountId = UUID.randomUUID().toString();
		PreparedStatement statement = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
		statement.setString(1, accountId);
		statement.setString(2, "CHEQUING");
		statement.setInt(3, 1000);
		statement.setString(4, "testing@mail.utoronto.ca");
		statement.execute();

		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);
		Account a = new ChequingAccount(user);
		a.setAccountNumber(accountId);
		a.setBalance(1000);

		manager.payBill(500, "UofT", a, "CAD");

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(500, rs.getInt(3));
		assertNull(rs.getString(5));

		assertEquals(500, a.getBalance());

		statement = conn.prepareStatement("SELECT id, type, `from`, `to`, amount, timestamp FROM transactions WHERE `from` = ?");
		statement.setString(1, a.getName());
		rs = statement.executeQuery();

		assertTrue(rs.next());
		assertEquals("PAY_BILL", rs.getString(2));
		assertEquals(a.getName(), rs.getString(3));
		assertNull(rs.getString(4));
		assertEquals(500, rs.getInt(5));
		assertTrue(Timestamp.valueOf(LocalDateTime.now()).after(rs.getTimestamp(6)));
	}

	@Test
	public void testTransfer() throws SQLException {
		String accountId1 = UUID.randomUUID().toString();
		String accountId2 = UUID.randomUUID().toString();
		String accountId3 = UUID.randomUUID().toString();

		PreparedStatement statement = conn.prepareStatement("INSERT INTO users (username, password, type, `primary`) VALUES (?, ?, ?, ?)");
		statement.setString(1, "testing@mail.utoronto.ca");
		statement.setString(2, "1234");
		statement.setString(3, "CUSTOMER_USER");
		statement.setString(4, accountId1);
		statement.execute();

		statement = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?), (?, ?, ?, ?), (?, ?, ?, ?)");
		statement.setString(1, accountId1);
		statement.setString(2, "CHEQUING");
		statement.setInt(3, 1500);
		statement.setString(4, "testing@mail.utoronto.ca");
		statement.setString(5, accountId2);
		statement.setString(6, "CHEQUING");
		statement.setInt(7, 1000);
		statement.setString(8, "testing@mail.utoronto.ca");
		statement.setString(9, accountId3);
		statement.setString(10, "CHEQUING");
		statement.setInt(11, 2000);
		statement.setString(12, "not-testing@mail.utoronto.ca");
		statement.execute();

		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);
		CustomerUser user1 = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);
		CustomerUser user2 = new CustomerUser("not-testing@mail.utoronto.ca", "1234", "", "", false);

		Account a1 = new ChequingAccount(user1);
		a1.setBalance(1500);
		a1.setAccountNumber(accountId1);
		Account a2 = new ChequingAccount(user1);
		a2.setBalance(1000);
		a2.setAccountNumber(accountId2);
		Account a3 = new ChequingAccount(user2);
		a3.setBalance(2000);
		a3.setAccountNumber(accountId3);

		manager.transfer(a1, a2, 100);

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE id = ?");
		statement.setString(1, accountId1);
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId1, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(1400, rs.getInt(3));
		assertNull(rs.getString(5));
		assertEquals(1400, a1.getBalance());

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE id = ?");
		statement.setString(1, accountId2);
		rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId2, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(1100, rs.getInt(3));
		assertNull(rs.getString(5));
		assertEquals(1100, a2.getBalance());


		manager.transfer(a1, a3, 100);

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE id = ?");
		statement.setString(1, accountId1);
		rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId1, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(1200, rs.getInt(3));
		assertNull(rs.getString(5));
		assertEquals(1200, a1.getBalance());

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE id = ?");
		statement.setString(1, accountId3);
		rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId3, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(2100, rs.getInt(3));
		assertNull(rs.getString(5));
		assertEquals(2100, a3.getBalance());
	}

	@Test
	public void testCompoundInterest() throws SQLException {
		String accountId1 = UUID.randomUUID().toString();
		String accountId2 = UUID.randomUUID().toString();
		String accountId3 = UUID.randomUUID().toString();

		PreparedStatement statement = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?), (?, ?, ?, ?), (?, ?, ?, ?)");
		statement.setString(1, accountId1);
		statement.setString(2, "SAVINGS");
		statement.setInt(3, 1500);
		statement.setString(4, "testing@mail.utoronto.ca");
		statement.setString(5, accountId2);
		statement.setString(6, "CHEQUING");
		statement.setInt(7, 1000);
		statement.setString(8, "testing@mail.utoronto.ca");
		statement.setString(9, accountId3);
		statement.setString(10, "SAVINGS");
		statement.setInt(11, 2000);
		statement.setString(12, "not-testing@mail.utoronto.ca");
		statement.execute();

		ATM atm = new ATM(conn);
		BankAccountsManager manager = new BankAccountsManager(conn, atm);

		manager.compoundInterest();

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE id = ?");
		statement.setString(1, accountId1);
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId1, rs.getString(1));
		assertEquals("SAVINGS", rs.getString(2));
		assertEquals(1502, rs.getInt(3));
		assertNull(rs.getString(5));


		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE id = ?");
		statement.setString(1, accountId2);
		rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId2, rs.getString(1));
		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(1000, rs.getInt(3));
		assertNull(rs.getString(5));

		statement = conn.prepareStatement("SELECT id, type, balance, holder_a, holder_b FROM accounts WHERE id = ?");
		statement.setString(1, accountId3);
		rs = statement.executeQuery();

		rs.next();

		assertEquals(accountId3, rs.getString(1));
		assertEquals("SAVINGS", rs.getString(2));
		assertEquals(2002, rs.getInt(3));
		assertNull(rs.getString(5));
	}

	@After
	public void deleteFile() throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute("DELETE FROM users");
		statement.execute("DELETE FROM accounts");
		statement.execute("DELETE FROM transactions");
		statement.execute("DELETE FROM account_requests");
	}
}
