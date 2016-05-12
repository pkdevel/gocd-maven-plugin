package io.ruck.maven.gocd.plugin;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.response.execution.ExecutionResult;
import com.thoughtworks.go.plugin.api.task.Console;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutionContext;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;

public final class MavenTaskExecutor implements TaskExecutor {
	
	private static final Logger LOGGER = Logger.getLoggerFor(MavenTaskExecutor.class);
	
	private static final String TRUE = "true";
	
	@Override
	public ExecutionResult execute(final TaskConfig tc, final TaskExecutionContext tec) {
		final Console console = tec.console();
		
		final ProcessBuilder mvn = this.createMavenCommand(tc, tec);
		
		try {
			final Process process = mvn.start();
			
			console.readErrorOf(process.getErrorStream());
			console.readOutputOf(process.getInputStream());
			
			final int exitCode = process.waitFor();
			process.destroy();
			if (exitCode != 0) {
				return ExecutionResult.failure("Build failure");
			}
		}
		catch (final Exception t) {
			console.printLine(t.getMessage());
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			console.printLine(sw.toString());
			return ExecutionResult.failure("Build failure: " + t.getMessage(), t);
		}
		
		return ExecutionResult.success("Build success");
	}
	
	private ProcessBuilder createMavenCommand(final TaskConfig tc, final TaskExecutionContext tec) {
		final List<String> command = new ArrayList<String>(12);
		final Map<String, String> env = tec.environment().asMap();
		if (env.containsKey("M2_HOME")) {
			command.add(new File(env.get("M2_HOME"), "mvn").getPath());
		}
		else {
			command.add("mvn");
		}
		
		if (StringUtils.equals(tc.getValue(MavenTask.DEBUG_KEY), TRUE)) {
			command.add("-X");
		}
		if (StringUtils.equals(tc.getValue(MavenTask.QUIET_KEY), TRUE)) {
			command.add("-q");
		}
		if (StringUtils.equals(tc.getValue(MavenTask.OFFLINE_KEY), TRUE)) {
			command.add("-o");
		}
		if (StringUtils.equals(tc.getValue(MavenTask.BATCH_KEY), TRUE)) {
			command.add("-B");
		}
		if (!StringUtils.isBlank(tc.getValue(MavenTask.PROFILES_KEY))) {
			command.add("-P");
			command.add(tc.getValue(MavenTask.PROFILES_KEY));
		}
		
		command.addAll(Arrays.asList(tc.getValue(MavenTask.ARGUMENTS_KEY).split("\\s+")));
		
		LOGGER.debug("Building command: " + command);
		
		final ProcessBuilder builder = new ProcessBuilder(command);
		builder.environment().putAll(env);
		builder.directory(new File(tec.workingDir()));
		return builder;
	}
}
