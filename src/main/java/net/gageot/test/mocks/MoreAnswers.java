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
