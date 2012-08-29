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

/**
 * Static utility class used to force out of memory conditions.
 */
public final class Memory {
	private Memory() {
		//static class
	}

	/**
	 * Force an out of memory condition.<br />
	 * Can be useful to test weak references.
	 */
	@SuppressWarnings("unused")
	public static void forceOutOfMemory() {
		try {
			Object[] ignored = new Object[(int) Runtime.getRuntime().maxMemory()];
		} catch (Throwable e) {
			// Ignore OME
		}
	}
}
