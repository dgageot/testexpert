package net.gageot.test.assertions;

import com.google.common.base.*;
import org.fest.assertions.*;

public class Conditions {
	private Conditions() {
		// static class
	}

	public static <T, V> Condition<T> isEqual(final Function<? super T, V> transform, final V expectedValue) {
		return new Condition<T>() {
			@Override
			public boolean matches(T object) {
				return Objects.equal(transform.apply(object), expectedValue);
			}
		};
	}
}
