package net.gageot.test.utils;

/**
 * Static utility class used to force out of memory conditions.
 */
public final class Memory {
	private Memory() {
		//static class
	}

	/**
	 * Force an out of memory condition.<br />
	 * Can be useful to test weak references.
	 */
	@SuppressWarnings("unused")
	public static void forceOutOfMemory() {
		try {
			Object[] ignored = new Object[(int) Runtime.getRuntime().maxMemory()];
		} catch (Throwable e) {
			// Ignore OME
		}
	}
}
