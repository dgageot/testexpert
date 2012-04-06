package net.gageot.test.mocks;

import org.junit.runners.*;
import org.junit.runners.model.*;

public class AutoMockRunner extends BlockJUnit4ClassRunner {
	public AutoMockRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected Object createTest() throws Exception {
		return new MockInjector().injectMocks(super.createTest());
	}
}
