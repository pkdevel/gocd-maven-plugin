package de.pkdevel.gocd.plugin.maven;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public final class IOUtils {
	
	public static String toString(final InputStream is) throws UnsupportedEncodingException, IOException {
		return toString(is, 16384);
	}
	
	static String toString(final InputStream is, final int bufsize) throws UnsupportedEncodingException, IOException {
		final InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		final StringWriter sw = new StringWriter();
		copy(isr, sw, bufsize);
		return sw.toString();
	}
	
	public static void copy(final Reader r, final Writer w) throws IOException {
		copy(r, w, 16384);
	}
	
	static void copy(final Reader r, final Writer w, final int bufsize) throws IOException {
		final char[] buf = new char[bufsize];
		int read;
		while ((read = r.read(buf)) > 0) {
			w.write(buf, 0, read);
		}
	}
}
