package net.gageot.test.utils;

/**
 * Find out if we are running under Eclipse.
 */
public class Eclipse {
	private Eclipse() {
		// static class
	}

	public static boolean isRunUnderEclipse() {
		return System.getProperty("java.class.path").contains("org.eclipse.osgi") || System.getProperty("target", "").equalsIgnoreCase("eclipse");
	}
}
