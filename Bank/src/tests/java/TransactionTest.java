import com.atm.Transaction;
import org.junit.*;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

	@Test
	public void testCreateTransactionTransfer() {
		Transaction t = new Transaction("TRANSFER", "1234", "4321", 100);
	}

	@Test
	public void testCreateTransactionPayBill() {
		Transaction t = new Transaction("PAY_BILL", "1234", null, 100);
	}

	@Test
	public void testTransferToString() {
		LocalDateTime time = LocalDateTime.now();
		Transaction t = new Transaction("TRANSFER", "1234", "4321", 100, time);

		assertEquals("Transfer At: " + time + ", From: 1234, To: 4321, Amount: $1.0", t.toString());
	}

	@Test
	public void testPayBillToString() {
		LocalDateTime time = LocalDateTime.now();
		Transaction t = new Transaction("PAY_BILL", "1234", null, 100, time);

		assertEquals("Pay Bill At: " + time + ", From: 1234, Amount: $1.0", t.toString());
	}
}
