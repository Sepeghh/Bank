import com.atm.ATM;
import com.atm.CustomerUser;
import com.atm.User;
import com.atm.UserManager;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.UUID;

import static org.junit.Assert.*;


public class UserManagerTests {

	private static Connection conn;

	@BeforeClass
	public static void setup() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://csc-two-o-seven-project.czqxxcp9ck39.us-east-1.rds.amazonaws.com:3306/final-project-testing", "root", "JYfwpv7X8LHgZx4");
	}


	@Test
	public void testAddAccount() throws SQLException {
		UserManager manager = new UserManager(conn);
		boolean successful = manager.createCustomer("testing@mail.utoronto.ca", "1234", "", "", false);
		assertTrue(successful);

		PreparedStatement statement = conn.prepareStatement("SELECT id, type, balance, holder_a FROM accounts WHERE holder_a = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		ResultSet rs = statement.executeQuery();

		rs.next();

		assertEquals("CHEQUING", rs.getString(2));
		assertEquals(0, rs.getInt(3));

		String accountId = rs.getString(1);

		statement = conn.prepareStatement("SELECT username, password, type, `primary` FROM users WHERE username = ?");
		statement.setString(1, "testing@mail.utoronto.ca");
		rs = statement.executeQuery();

		rs.next();

		assertEquals("testing@mail.utoronto.ca", rs.getString(1));
		assertEquals("1234", rs.getString(2));
		assertEquals("CUSTOMER_USER", rs.getString(3));
		assertEquals(accountId, rs.getString(4));
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

		statement = conn.prepareStatement("INSERT INTO accounts (id, type, balance, holder_a) VALUES (?, ?, ?, ?)");
		statement.setString(1, accountId);
		statement.setString(2, "CHEQUING");
		statement.setInt(3, 0);
		statement.setString(4, "testing@mail.utoronto.ca");
		statement.execute();

		UserManager manager = new UserManager(conn);

		User user = manager.getUserAccount("testing@mail.utoronto.ca");
		assertNotNull(user);
		assertEquals("testing@mail.utoronto.ca", user.getUsername());
		assertEquals("1234", user.getPassword());

		user = manager.getUserAccount("testing@invalid-email.com");
		assertNull(user);
	}

	// TODO: Test getRecentTransaction(CustomerUser user)


	@Test
	public void testUpdatePassword() throws SQLException {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO users (username, password, type, `primary`) VALUES (?, ?, ?, ?)");
		statement.setString(1, "testing@mail.utoronto.ca");
		statement.setString(2, "1234");
		statement.setString(3, "CUSTOMER_USER");
		statement.setString(4, "123456789");
		statement.execute();

		UserManager manager = new UserManager(conn);
		User user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		manager.changePassword(user, "4321", "1234");

		statement = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
		statement.setString(1, "testing@mail.utoronto.ca");

		ResultSet rs = statement.executeQuery();
		rs.next();

		assertEquals("4321", rs.getString(1));
	}

	@Test
	public void testLogin() {
		User user = new CustomerUser("testing@mail.utoronto.ca", "1234", "", "", false);

		UserManager manager = new UserManager(conn);

		assertTrue(manager.checkLogin(user, "1234"));
		assertFalse(manager.checkLogin(user, "4321"));
	}

	@After
	public void deleteFile() throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute("DELETE FROM users");
		statement.execute("DELETE FROM accounts");
	}
}
