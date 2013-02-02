/***************************************************************************
 * Copyright 2012 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package kieker.tools.traceAnalysis.filter.flow;

import kieker.analysis.IProjectContext;
import kieker.analysis.plugin.annotation.InputPort;
import kieker.analysis.plugin.annotation.Plugin;
import kieker.analysis.plugin.annotation.Property;
import kieker.analysis.plugin.filter.flow.TraceEventRecords;
import kieker.common.configuration.Configuration;
import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;
import kieker.common.record.flow.trace.AbstractTraceEvent;
import kieker.common.record.flow.trace.Trace;
import kieker.tools.traceAnalysis.filter.AbstractTraceProcessingFilter;

/**
 * Counts and reports the number of incoming valid/invalid {@link TraceEventRecords}.
 * 
 * @author Andre van Hoorn
 * 
 */
@Plugin(
		description = "Counts and reports the number of incoming valid/invalid event record traces",
		configuration = {
			@Property(name = EventRecordTraceCounter.CONFIG_PROPERTY_NAME_LOG_INVALID, defaultValue = "true")
		})
public class EventRecordTraceCounter extends AbstractTraceProcessingFilter {

	public static final String INPUT_PORT_NAME_VALID = "validEventRecordTraces";
	public static final String INPUT_PORT_NAME_INVALID = "invalidEventRecordTraces";

	public static final String CONFIG_PROPERTY_NAME_LOG_INVALID = "logInvalidTraces";

	private static final long TRACE_ID_IF_NONE = -1;

	private static final Log LOG = LogFactory.getLog(EventRecordTraceCounter.class);

	private final boolean logInvalidTraces;

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param configuration
	 *            The configuration for this component.
	 * @param projectContext
	 *            The project context for this component.
	 * 
	 * @since 1.7
	 */
	public EventRecordTraceCounter(final Configuration configuration, final IProjectContext projectContext) {
		super(configuration, projectContext);

		this.logInvalidTraces = configuration.getBooleanProperty(CONFIG_PROPERTY_NAME_LOG_INVALID);
	}

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param configuration
	 *            The configuration for this component.
	 * 
	 * @deprecated
	 */
	@Deprecated
	public EventRecordTraceCounter(final Configuration configuration) {
		this(configuration, null);
	}

	public Configuration getCurrentConfiguration() {
		final Configuration config = new Configuration();
		config.setProperty(CONFIG_PROPERTY_NAME_LOG_INVALID, Boolean.toString(this.logInvalidTraces));
		return config;
	}

	@InputPort(name = INPUT_PORT_NAME_VALID, eventTypes = { TraceEventRecords.class }, description = "Receives valid event record traces")
	public void inputValidTrace(final TraceEventRecords validTrace) {
		super.reportSuccess(validTrace.getTrace().getTraceId());
	}

	@InputPort(name = INPUT_PORT_NAME_INVALID, eventTypes = { TraceEventRecords.class }, description = "Receives invalid event record traces")
	public void inputInvalidTrace(final TraceEventRecords invalidTrace) {
		if (this.logInvalidTraces) {
			LOG.error("Invalid trace: " + invalidTrace);
		}

		final Trace trace = invalidTrace.getTrace();
		if (trace != null) {
			super.reportError(invalidTrace.getTrace().getTraceId());
		} else {
			final AbstractTraceEvent[] events = invalidTrace.getTraceEvents();
			if ((events != null) && (events.length > 0)) {
				super.reportError(events[0].getTraceId());
			} else {
				super.reportError(TRACE_ID_IF_NONE); // we can't do any better
			}
		}
	}
}