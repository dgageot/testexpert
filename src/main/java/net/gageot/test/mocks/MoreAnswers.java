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

import org.mockito.invocation.*;
import org.mockito.stubbing.*;

import java.math.*;

import static org.mockito.Mockito.*;

public class MoreAnswers {
	private MoreAnswers() {
		// static class
	}

	@SuppressWarnings("unchecked")
	public static <T extends Number> Answer<T> TIMES(final double factor) {
		return new Answer<T>() {
      @Override
      public T answer(InvocationOnMock invocation) throws Throwable {
				BigDecimal input = new BigDecimal(((T) invocation.getArguments()[0]).doubleValue());
				return (T) input.multiply(new BigDecimal(factor));
			}
		};
	}

	public static Stubber execute(final Runnable action) {
		return doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock i) {
				action.run();
				return null;
			}
		});
	}

	public static Stubber executeAsync(final Runnable action) {
		return doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock i) {
				new Thread(action).start();
				return null;
			}
		});
	}
}
