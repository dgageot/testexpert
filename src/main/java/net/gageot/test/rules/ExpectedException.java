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
