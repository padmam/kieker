package kieker.test.tools.junit.traceAnalysis.util;

import java.util.HashMap;
import java.util.Map;

import kieker.analysis.plugin.AbstractAnalysisPlugin;
import kieker.analysis.plugin.port.OutputPort;
import kieker.analysis.plugin.port.Plugin;
import kieker.analysis.repository.AbstractRepository;
import kieker.common.configuration.Configuration;

import org.junit.Assert;

@Plugin(
		outputPorts = {
			@OutputPort(name = SimpleSourcePlugin.OUTPUT_PORT_NAME, eventTypes = { })
		})
public class SimpleSourcePlugin extends AbstractAnalysisPlugin {

	public static final String OUTPUT_PORT_NAME = "output";

	public SimpleSourcePlugin() {
		super(new Configuration(), new HashMap<String, AbstractRepository>());
	}

	public void deliver(final Object data) {
		Assert.assertTrue(super.deliver(SimpleSourcePlugin.OUTPUT_PORT_NAME, data));
	}

	@Override
	protected Configuration getDefaultConfiguration() {
		return null;
	}

	@Override
	public Configuration getCurrentConfiguration() {
		return null;
	}

	@Override
	public boolean execute() {
		return true;
	}

	@Override
	public void terminate(final boolean error) {}

	@Override
	protected Map<String, AbstractRepository> getDefaultRepositories() {
		return new HashMap<String, AbstractRepository>();
	}

	@Override
	public Map<String, AbstractRepository> getCurrentRepositories() {
		return new HashMap<String, AbstractRepository>();
	}
}