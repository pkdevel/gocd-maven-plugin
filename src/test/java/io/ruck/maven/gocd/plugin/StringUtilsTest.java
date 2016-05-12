package io.ruck.maven.gocd.plugin;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class StringUtilsTest {
	
	@Test
	public void testIsBlank() {
		assertTrue(StringUtils.isBlank(null));
		assertTrue(StringUtils.isBlank(""));
		assertTrue(StringUtils.isBlank(" \n "));
		assertFalse(StringUtils.isBlank("a"));
		assertFalse(StringUtils.isBlank(" a \n b "));
	}
	
	@Test
	public void testEquals() {
		assertTrue(StringUtils.equals(null, null));
		assertFalse(StringUtils.equals(null, "a"));
		assertFalse(StringUtils.equals("b", null));
		assertTrue(StringUtils.equals("b", "b"));
		assertFalse(StringUtils.equals("b", "a"));
	}
	
}
