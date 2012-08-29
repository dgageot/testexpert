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

import org.hamcrest.*;
import org.junit.internal.matchers.*;
import org.junit.rules.*;
import org.junit.runners.model.*;

import java.util.*;

public class ExpectedException implements TestRule {
	final org.junit.rules.ExpectedException delegate = org.junit.rules.ExpectedException.none();

	public static ExpectedException none() {
		return new ExpectedException();
	}

	private ExpectedException() {
		// Nothing special
	}

  @Override
  public Statement apply(final Statement base, final org.junit.runner.Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				new RunRules(base, Arrays.<TestRule>asList(delegate), description).evaluate();
			}
		};
	}

	public void expectRuntimeExceptionWithCause(Class<? extends Throwable> causeClass) {
		delegate.expect(RuntimeException.class);
		delegate.expect(hasCause(causeClass));
	}

	public void expectRuntimeException(String message) {
		expect(RuntimeException.class).expectMessage(message);
	}

	public void expectAssertionError(String message) {
		expect(AssertionError.class).expectMessage(message);
	}

	public void expectNullPointerException(String message) {
		expect(NullPointerException.class).expectMessage(message);
	}

	public void expectIllegalArgumentException(String message) {
		expect(IllegalArgumentException.class).expectMessage(message);
	}

	public void expectIllegalStateException(String message) {
		expect(IllegalStateException.class).expectMessage(message);
	}

	public void expect(Throwable error) {
		expect(error.getClass()).expectMessage(error.getMessage());
	}

	public ExpectedException expect(Class<? extends Throwable> type) {
		delegate.expect(type);
		return this;
	}

	public ExpectedException expectMessage(String message) {
		delegate.expectMessage(message);
		return this;
	}

	private Matcher<Throwable> hasCause(final Class<? extends Throwable> causeClass) {
		return new TypeSafeMatcher<Throwable>() {
      @Override
      public void describeTo(Description description) {
				description.appendText("exception with cause " + causeClass.getClass().getSimpleName());
			}

			@Override
			public boolean matchesSafely(Throwable item) {
				return (null != item.getCause()) && causeClass.isAssignableFrom(item.getCause().getClass());
			}
		};
	}
}
