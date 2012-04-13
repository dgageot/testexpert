package net.gageot.test.rules;

import com.google.common.util.concurrent.*;
import com.google.inject.*;
import org.junit.rules.*;

import java.util.*;

/**
 * JUnit Rule to start/stop a service before/after tests.
 */
public class ServiceRule<T extends Service> extends ExternalResource {
	private static final int TRY_COUNT = 10;
	private static final int DEFAULT_PORT = 8183;

	private final Class<T> serviceClass;
	private final Module[] modules;
	private final Random random = new Random();
	private T service;

	private ServiceRule(Class<T> serviceClass, Module... modules) {
		this.serviceClass = serviceClass;
		this.modules = modules;
	}

	public static <T extends Service> ServiceRule<T> startWithRandomPort(Class<T> serviceClass, Module... modules) {
		return new ServiceRule<T>(serviceClass, modules);
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
		try {
			return serviceClass.getDeclaredConstructor(int.class, Module[].class).newInstance(port, modules);
		} catch (Exception e) {
			return serviceClass.getDeclaredConstructor(int.class).newInstance(port);
		}
	}

	public T service() {
		return service;
	}

	private synchronized int randomPort() {
		return DEFAULT_PORT + random.nextInt(1000);
	}
}
