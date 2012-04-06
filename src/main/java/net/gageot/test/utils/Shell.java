package net.gageot.test.utils;

import org.apache.commons.exec.*;

import java.io.*;

import static java.lang.String.*;

/**
 * Execute external commands.
 */
public class Shell {
	/**
	 * Execute an external command.
	 *
	 * @return exitCode or -1 if an exception occured
	 */
	public int execute(String command, Object... arguments) {
		DefaultExecutor executor = new DefaultExecutor();

		CommandLine commandLine = CommandLine.parse(format(command, arguments));

		try {
			return executor.execute(commandLine);
		} catch (IOException e) {
			return -1;
		}
	}
}
