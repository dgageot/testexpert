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
package net.gageot.test.rules;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;

public class ConsoleRecorderTest {
    @Rule
    public ConsoleRecorder systemOut = ConsoleRecorder.recordSystemOut();

    @Rule
    public ConsoleRecorder systemErr = ConsoleRecorder.recordSystemErr();

    @Test
    public void canRecordSystemOut() {
	System.out.println("OUTPUT1");
	System.out.println("OUTPUT2");

	assertThat(systemOut.getOutput()).matches("OUTPUT1\\r?\\nOUTPUT2\\r\\n");
	assertThat(systemErr.getOutput()).isEmpty();
    }

    @Test
    public void canRecordSystemErr() {
	System.err.println("ERROR1");
	System.err.println("ERROR2");

	assertThat(systemErr.getOutput()).matches("ERROR1\\r?\\nERROR2\\r\\n");
	assertThat(systemOut.getOutput()).isEmpty();
    }
}
