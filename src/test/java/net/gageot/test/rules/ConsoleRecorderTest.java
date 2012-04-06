package net.gageot.test.rules;

import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class ConsoleRecorderTest {
	@Rule
	public ConsoleRecorder systemOut = ConsoleRecorder.recordSystemOut();

	@Rule
	public ConsoleRecorder systemErr = ConsoleRecorder.recordSystemErr();

	@Test
	public void canRecordSystemOut() {
		System.out.println("OUTPUT1");
		System.out.println("OUTPUT2");

		assertThat(systemOut.getOutput()).contains("OUTPUT1\nOUTPUT2\n");
		assertThat(systemErr.getOutput()).isEmpty();
	}

	@Test
	public void canRecordSystemErr() {
		System.err.println("ERROR1");
		System.err.println("ERROR2");

		assertThat(systemErr.getOutput()).isEqualTo("ERROR1\nERROR2\n");
		assertThat(systemOut.getOutput()).isEmpty();
	}
}
