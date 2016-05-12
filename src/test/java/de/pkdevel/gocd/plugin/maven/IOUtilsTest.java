package de.pkdevel.gocd.plugin.maven;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

import de.pkdevel.gocd.plugin.maven.IOUtils;

public final class IOUtilsTest {
	
	@Test
	public void testToString() throws Exception {
		final ByteArrayInputStream bais = new ByteArrayInputStream("initial".getBytes("UTF-8"));
		assertEquals("initial", IOUtils.toString(bais, 1));
	}
	
	@Test
	public void testCopy() throws Exception {
		final StringReader sr = new StringReader("initial");
		final StringWriter sw = new StringWriter();
		IOUtils.copy(sr, sw, 1);
		assertEquals("initial", sw.toString());
	}
	
}
