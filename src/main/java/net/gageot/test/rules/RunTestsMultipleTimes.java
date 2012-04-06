package net.gageot.test.rules;

import com.google.common.base.*;
import com.google.common.collect.*;
import org.junit.rules.*;
import org.junit.runner.*;
import org.junit.runners.model.*;

import java.util.*;
import java.util.concurrent.*;

public class RunTestsMultipleTimes implements TestRule {
	final int threads;
	final int times;

	public RunTestsMultipleTimes(int times) {
		this(times, 1);
	}

	public RunTestsMultipleTimes(int times, int threads) {
		this.times = times;
		this.threads = threads;
	}

	public Statement apply(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				ExecutorService executor = Executors.newFixedThreadPool(threads);

				List<Future<?>> results = Lists.newArrayList();

				for (int i = 0; i < times; i++) {
					results.add(executor.submit(new Runnable() {
						public void run() {
							try {
								base.evaluate();
							} catch (Throwable e) {
								throw Throwables.propagate(e);
							}
						}
					}));
				}

				for (Future<?> result : results) {
					try {
						result.get();
					} catch (ExecutionException e) {
						throw e.getCause();
					}
				}

				executor.shutdown();
			}
		};
	}
}
