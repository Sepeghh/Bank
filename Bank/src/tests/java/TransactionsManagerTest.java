import com.atm.*;
import org.junit.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

public class TransactionsManagerTest {

	private static Connection conn;

	@BeforeClass
	public static void setup() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://csc-two-o-seven-project.czqxxcp9ck39.us-east-1.rds.amazonaws.com:3306/final-project-testing", "root", "JYfwpv7X8LHgZx4");
	}

	@Test
	public void testCreateTransactionTransfer() throws SQLException {
		TransactionsManager manager = new TransactionsManager(conn);

		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);
		Account a1 = new ChequingAccount(user);
		Account a2 = new ChequingAccount(user);

		manager.createNewTransfer(a1, a2, 100);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, `from`, `to`, amount, timestamp FROM transactions WHERE `from` = ?");
		statement.setString(1, a1.getName());
		ResultSet rs = statement.executeQuery();

		assertTrue(rs.next());
		assertEquals("TRANSFER", rs.getString(2));
		assertEquals(a1.getName(), rs.getString(3));
		assertEquals(a2.getName(), rs.getString(4));
		assertEquals(100, rs.getInt(5));
		assertTrue(Timestamp.valueOf(LocalDateTime.now()).after(rs.getTimestamp(6)));
	}

	@Test
	public void testCreateTransactionPayBill() throws SQLException {
		TransactionsManager manager = new TransactionsManager(conn);

		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);
		Account a1 = new ChequingAccount(user);

		manager.createNewPayBill(a1, 200);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, `from`, `to`, amount, timestamp FROM transactions WHERE `from` = ?");
		statement.setString(1, a1.getName());
		ResultSet rs = statement.executeQuery();

		assertTrue(rs.next());
		assertEquals("PAY_BILL", rs.getString(2));
		assertEquals(a1.getName(), rs.getString(3));
		assertNull(rs.getString(4));
		assertEquals(200, rs.getInt(5));
		assertTrue(Timestamp.valueOf(LocalDateTime.now()).after(rs.getTimestamp(6)));
	}

	@Test
	public void testGetLastTransaction() throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, `to`, amount, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "TRANSFER-2");
		stmt.setString(3, "1234");
		stmt.setString(4, "4321");
		stmt.setInt(5, 500);
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, `to`, amount, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "TRANSFER");
		stmt.setString(3, "2345");
		stmt.setString(4, "5432");
		stmt.setInt(5, 1000);
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, amount, timestamp) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "PAY_BILL");
		stmt.setString(3, "3456");
		stmt.setInt(4, 1500);
		stmt.execute();

		TransactionsManager manager = new TransactionsManager(conn);

		Transaction t = manager.getLastTransaction();

		assertEquals("PAY_BILL", t.getType());
		assertEquals("3456", t.getFrom());
		assertNull(t.getTo());
		assertEquals(1500, t.getAmount());
	}

	@Test
	public void testGetLastTransactionUser() throws SQLException {
		String accountId1 = UUID.randomUUID().toString();
		String accountId2 = UUID.randomUUID().toString();
		String accountId3 = UUID.randomUUID().toString();
		String accountId4 = UUID.randomUUID().toString();

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, type, `primary`) VALUES (?, ?, ?, ?)");
		stmt.setString(1, "testing@mail.utoronto.ca");
		stmt.setString(2, "9999");
		stmt.setString(3, "CUSTOMER_USER");
		stmt.setString(4, accountId1);

		stmt = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
		stmt.setString(1, accountId1);
		stmt.setString(2, "CHEQUING");
		stmt.setInt(3, 0);
		stmt.setString(4, "testing@mail.utoronto.ca");
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, `to`, amount, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "TRANSFER");
		stmt.setString(3, accountId1);
		stmt.setString(4, accountId2);
		stmt.setInt(5, 500);
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, `to`, amount, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "TRANSFER");
		stmt.setString(3, accountId2);
		stmt.setString(4, accountId3);
		stmt.setInt(5, 1000);
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, amount, timestamp) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "PAY_BILL");
		stmt.setString(3, accountId4);
		stmt.setInt(4, 1500);
		stmt.execute();

		CustomerUser u = new CustomerUser("testing@mail.utoronto.ca", "9999", "", "", false);

		TransactionsManager manager = new TransactionsManager(conn);

		Transaction t = manager.getLastTransaction(u);

		assertEquals("TRANSFER", t.getType());
		assertEquals(accountId1, t.getFrom());
		assertEquals(accountId2, t.getTo());
		assertEquals(500, t.getAmount());
	}

	@Test
	public void testGetLastTransactionAccount() throws SQLException {
		CustomerUser user = new CustomerUser("testing@mail.utoronto.ca", "9999", "", "", false);

		Account a1 = new ChequingAccount(user);
		Account a2 = new ChequingAccount(user);

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, type, `primary`) VALUES (?, ?, ?, ?)");
		stmt.setString(1, "testing@mail.utoronto.ca");
		stmt.setString(2, "9999");
		stmt.setString(3, "CUSTOMER_USER");
		stmt.setString(4, a1.getName());

		stmt = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
		stmt.setString(1, a1.getName());
		stmt.setString(2, "CHEQUING");
		stmt.setInt(3, 0);
		stmt.setString(4, "testing@mail.utoronto.ca");
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
		stmt.setString(1, a2.getName());
		stmt.setString(2, "CHEQUING");
		stmt.setInt(3, 0);
		stmt.setString(4, "testing@mail.utoronto.ca");
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, `to`, amount, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "TRANSFER");
		stmt.setString(3, a1.getName());
		stmt.setString(4, a2.getName());
		stmt.setInt(5, 500);
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, `to`, amount, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "TRANSFER");
		stmt.setString(3, a2.getName());
		stmt.setString(4, a1.getName());
		stmt.setInt(5, 1000);
		stmt.execute();

		stmt = conn.prepareStatement("INSERT INTO transactions (id, type, `from`, amount, timestamp) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP(3))");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, "PAY_BILL");
		stmt.setString(3, UUID.randomUUID().toString());
		stmt.setInt(4, 1500);
		stmt.execute();

		CustomerUser u = new CustomerUser("testing@mail.utoronto.ca", "9999", "", "", false);

		TransactionsManager manager = new TransactionsManager(conn);

		Transaction t = manager.getLastTransaction(a2);

		assertEquals("TRANSFER", t.getType());
		assertEquals(a2.getName(), t.getFrom());
		assertEquals(a1.getName(), t.getTo());
		assertEquals(1000, t.getAmount());
	}

	@After
	public void deleteFile() throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute("DELETE FROM users");
		statement.execute("DELETE FROM accounts");
		statement.execute("DELETE FROM transactions");
	}
}
