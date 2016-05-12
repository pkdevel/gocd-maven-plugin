package io.ruck.maven.gocd.plugin;

public class StringUtils {
	
	public static boolean isBlank(final String s) {
		return s == null || s.trim().length() == 0;
	}
	
	public static boolean equals(final String a, final String b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}
	
}
