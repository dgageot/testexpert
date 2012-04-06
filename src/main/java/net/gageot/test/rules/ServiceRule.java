package net.gageot.test.rules;

import com.google.common.util.concurrent.*;
import org.junit.rules.*;

import java.util.*;

/**
 * JUnit Rule to start/stop a service before/after tests.
 */
public class ServiceRule<T extends Service> extends ExternalResource {
	private static final int TRY_COUNT = 10;
	private static final int DEFAULT_PORT = 8183;

	private final Class<T> serviceClass;
	private final Random random = new Random();
	private T service;

	private ServiceRule(Class<T> serviceClass) {
		this.serviceClass = serviceClass;
	}

	public static <T extends Service> ServiceRule<T> start(Class<T> serviceClass) {
		return new ServiceRule<T>(serviceClass);
	}

	@Override
	protected void before() {
		for (int i = 0; i < TRY_COUNT; i++) {
			try {
				service = createServive(randomPort());
				service.startAndWait();
				return;
			} catch (Exception e) {
				System.err.println("Unable to bind server: " + e);
			}
		}
		throw new IllegalStateException("Unable to start server");
	}

	@Override
	protected void after() {
		if (service != null) {
			service.stopAndWait();
		}
	}

	@SuppressWarnings("unchecked")
	private T createServive(int port) throws Exception {
		return serviceClass.getDeclaredConstructor(int.class).newInstance(port);
	}

	public T service() {
		return service;
	}

	private synchronized int randomPort() {
		return DEFAULT_PORT + random.nextInt(1000);
	}
}
