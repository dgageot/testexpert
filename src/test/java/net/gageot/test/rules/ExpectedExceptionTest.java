package net.gageot.test.rules;

import org.junit.*;

public class ExpectedExceptionTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void canExpectExceptionByType() {
		exception.expect(RuntimeException.class);

		throw new RuntimeException();
	}

	@Test
	public void canExpectWithMessage() {
		exception.expectMessage("BUG");

		throw new RuntimeException("BUG");
	}
}
