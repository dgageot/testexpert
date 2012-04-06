package net.gageot.test.rules;

import com.google.common.collect.*;
import org.junit.rules.*;

import java.lang.Thread.*;
import java.util.*;

public class UncaughtException extends ExternalResource {
	private UncaughtExceptionHandler currentUncaughtExceptionHandler;
	final List<Throwable> uncaughtExceptions = Lists.newArrayList();

	@Override
	protected void before() throws Throwable {
		currentUncaughtExceptionHandler = Thread.currentThread().getUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				uncaughtExceptions.add(e);
			}
		});
	}

	@Override
	protected void after() {
		Thread.setDefaultUncaughtExceptionHandler(currentUncaughtExceptionHandler);
	}

	public boolean hasUncaughtException() {
		return !uncaughtExceptions.isEmpty();
	}

	public Throwable firstUncaughtException() {
		return uncaughtExceptions.get(0);
	}
}
