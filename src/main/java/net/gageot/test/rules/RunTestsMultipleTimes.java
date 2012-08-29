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

  @Override
  public Statement apply(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				ExecutorService executor = Executors.newFixedThreadPool(threads);

				List<Future<?>> results = Lists.newArrayList();

				for (int i = 0; i < times; i++) {
					results.add(executor.submit(new Runnable() {
            @Override
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
