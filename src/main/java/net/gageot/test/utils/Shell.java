/*
 * This file is part of TestExpert.
 *
 * Copyright (C) 2012
 * "David Gageot" <david@gageot.net>,
 *
 * TestExpert is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TestExpert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TestExpert. If not, see <http://www.gnu.org/licenses/>.
 */
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
