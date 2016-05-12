package io.ruck.maven.gocd.plugin;

import java.io.IOException;

import com.thoughtworks.go.plugin.api.task.TaskView;

public final class MavenTaskView implements TaskView {
	
	@Override
	public String displayValue() {
		return "Maven";
	}
	
	@Override
	public String template() {
		try {
			return IOUtils.toString(this.getClass().getResourceAsStream("/views/maventask.template.html"));
		}
		catch (final IOException ex) {
			return "Failed to find template: " + ex.getMessage();
		}
	}
	
}
