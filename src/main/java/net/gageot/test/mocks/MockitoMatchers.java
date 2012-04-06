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
