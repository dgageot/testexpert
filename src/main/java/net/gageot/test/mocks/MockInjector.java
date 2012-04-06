package net.gageot.test.mocks;

import org.mockito.*;
import org.mockito.exceptions.base.*;

import java.lang.reflect.*;

import static org.mockito.Mockito.*;

class MockInjector {
	private static final String STUB_PREFIX = "stub";
	private static final String MOCK_PREFIX = "mock";

	Object injectMocks(final Object test) {
		int mockCount = 0;

		try {
			for (Field field : test.getClass().getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
					if (field.getName().startsWith(MOCK_PREFIX) || field.getName().startsWith(STUB_PREFIX)) {
						mockCount++;
					}
					continue;
				}

				if (field.getName().startsWith(MOCK_PREFIX)) {
					Object mock = mock(field);
					setFieldValue(test, field, mock);
					mockCount++;
				} else if (field.getName().startsWith(STUB_PREFIX)) {
					Object mock = Mockito.mock(field.getType(), withSettings().name(field.getName()).defaultAnswer(RETURNS_DEEP_STUBS));
					setFieldValue(test, field, mock);
					mockCount++;
				} else if (field.getType().isAssignableFrom(ArgumentCaptor.class)) {
					ArgumentCaptor<?> captor = ArgumentCaptor.forClass(field.getType());
					setFieldValue(test, field, captor);
					mockCount++;
				}
			}
		} catch (MockitoException e) {
			throw new IllegalStateException("Unable to initialize mocks in class: " + test.getClass().getSimpleName(), e);
		}

		if (0 == mockCount) {
			throw new IllegalStateException(test.getClass().getSimpleName() + " test should not use " + MockInjector.class.getSimpleName() + " runner as it doesn't declare any mock");
		}

		return test;
	}

	private static Object mock(Field field) {
		return Mockito.mock(field.getType(), withSettings().name(field.getName()).defaultAnswer(RETURNS_DEFAULTS));
	}

	private void setFieldValue(Object test, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(test, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to access a field", e);
		}
	}
}
