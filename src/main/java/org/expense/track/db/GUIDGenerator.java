package org.expense.track.db;

import java.util.UUID;

/**
 * Generates a GUID. Wraps a java.util.UUID.
 */
public class GUIDGenerator {

	public static long getGUID() {
		return Math.abs(UUID.randomUUID().getLeastSignificantBits());
	}

}
