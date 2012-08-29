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
package net.gageot.test.mocks;

import com.google.common.collect.*;
import com.google.common.collect.Iterables;
import org.mockito.*;
import org.mockito.internal.matchers.*;

import java.util.*;

import static org.mockito.Matchers.*;

public class MockitoMatchers {
	private MockitoMatchers() {
		// static class
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> eqIterable(final T... values) {
		return argThat(new ArgumentMatcher<List<T>>() {
			@Override
			public boolean matches(Object argument) {
				return Iterables.elementsEqual((Iterable<T>) argument, ImmutableList.copyOf(values));
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> T neq(T wanted) {
		return (T) argThat(new Not(new Equals(wanted)));
	}

	public static Object[] varArgOfLength(final int expectedLength) {
		return argThat(new VarArgsLengthMatcher(expectedLength));
	}

	@SuppressWarnings("serial")
	private static final class VarArgsLengthMatcher extends ArgumentMatcher<Object[]> implements VarargMatcher {
		private final int expectedLength;

		VarArgsLengthMatcher(int expectedLength) {
			this.expectedLength = expectedLength;
		}

		@Override
		public boolean matches(Object argument) {
			return ((Object[]) argument).length == expectedLength;
		}
	}
}
