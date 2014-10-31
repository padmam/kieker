/***************************************************************************
 * Copyright 2014 Kieker Project (http://kieker-monitoring.net)
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

package kieker.examples.analysis.opad;

import java.util.List;

import kieker.analysis.AnalysisController;
import kieker.analysis.IAnalysisController;
import kieker.analysis.IProjectContext;
import kieker.analysis.exception.AnalysisConfigurationException;
import kieker.analysis.plugin.filter.forward.ListCollectionFilter;
import kieker.common.configuration.Configuration;
import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;
import kieker.tools.opad.filter.ForecastingFilter;
import kieker.tools.opad.filter.UniteMeasurementPairFilter;
import kieker.tools.opad.model.ForecastMeasurementPair;
import kieker.tools.tslib.ForecastMethod;

public final class ExperimentStarter {

	private static final Log LOG = LogFactory.getLog(ExperimentStarter.class);

	private ExperimentStarter() {}

	public static void main(final String[] args) throws IllegalStateException, AnalysisConfigurationException, InterruptedException {
		for (final ForecastMethod fm : ForecastMethod.values()) {
			ExperimentStarter.startWikipediaExperiment(fm);
		}

		// ExperimentStarter.startExperiment();
	}

	private static void startWikipediaExperiment(final ForecastMethod fcMethod) throws IllegalStateException, AnalysisConfigurationException {
		final Configuration controllerConfig = new Configuration();
		controllerConfig.setProperty(IProjectContext.CONFIG_PROPERTY_NAME_RECORDS_TIME_UNIT, "HOURS");
		final IAnalysisController analysisController = new AnalysisController(controllerConfig);

		final Configuration readerConfig = new Configuration();
		readerConfig.setProperty(SimpleTimeSeriesFileReader.CONFIG_PROPERTY_NAME_INPUT_FILE,
				"examples/analysis/opad-anomaly-detection/src/kieker/examples/analysis/opad/experiment-data/wikiGer24_Oct11_21d.csv");

		final SimpleTimeSeriesFileReader tsReader = new SimpleTimeSeriesFileReader(readerConfig, analysisController);

		final Configuration forecastConfig = new Configuration();
		forecastConfig.setProperty(ForecastingFilter.CONFIG_PROPERTY_NAME_FC_METHOD, fcMethod.name());
		forecastConfig.setProperty(ForecastingFilter.CONFIG_PROPERTY_NAME_DELTA_TIME, "1");
		forecastConfig.setProperty(ForecastingFilter.CONFIG_PROPERTY_NAME_DELTA_UNIT, "HOURS");
		final ForecastingFilter forecaster = new ForecastingFilter(forecastConfig, analysisController);

		final UniteMeasurementPairFilter uniteFilter = new UniteMeasurementPairFilter(new Configuration(), analysisController);

		// final TeeFilter tee = new TeeFilter(new Configuration(), analysisController);

		final ListCollectionFilter<ForecastMeasurementPair> listCollector = new ListCollectionFilter<ForecastMeasurementPair>(new Configuration(),
				analysisController);

		analysisController.connect(
				tsReader, SimpleTimeSeriesFileReader.OUTPUT_PORT_NAME_TS_POINTS,
				forecaster, ForecastingFilter.INPUT_PORT_NAME_TSPOINT);

		analysisController.connect(
				tsReader, SimpleTimeSeriesFileReader.OUTPUT_PORT_NAME_TS_POINTS,
				uniteFilter, UniteMeasurementPairFilter.INPUT_PORT_NAME_TSPOINT);

		analysisController.connect(
				forecaster, ForecastingFilter.OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT,
				uniteFilter, UniteMeasurementPairFilter.INPUT_PORT_NAME_FORECAST);

		analysisController.connect(
				uniteFilter, UniteMeasurementPairFilter.OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT,
				// tee, TeeFilter.INPUT_PORT_NAME_EVENTS);
				//
				// analysisController.connect(
				// tee, TeeFilter.OUTPUT_PORT_NAME_RELAYED_EVENTS,
				listCollector, ListCollectionFilter.INPUT_PORT_NAME);

		// Start the analysis
		analysisController.run();

		try {
			Thread.sleep(3000);
		} catch (final InterruptedException e) {
			LOG.warn("An exception occurred", e);
		}
		analysisController.terminate();

		ExperimentStarter.analyzeData(listCollector.getList(), fcMethod.name());

	}

	private static void analyzeData(final List<ForecastMeasurementPair> resultList, final String methodName) {
		double diffSum = 0;
		double difference = 0;
		// StringBuilder sb;
		for (final ForecastMeasurementPair fmPair : resultList) {
			difference = Math.abs(fmPair.getForecasted() - fmPair.getValue());
			diffSum += difference;
			// sb = new StringBuilder();
			// sb.append("[");
			// sb.append(fmPair.getTime());
			// sb.append("]");
			// sb.append(" Forecasted: ");
			// sb.append(fmPair.getForecasted());
			// sb.append(", Measured: ");
			// sb.append(fmPair.getValue());
			// sb.append(", Difference: ");
			// sb.append(difference);
			// System.out.println(sb.toString());
		}
		System.out.println("[" + methodName + "] The average difference was: " + (diffSum / resultList.size()));
	}

	// private static void startExperiment() throws IllegalStateException, AnalysisConfigurationException {
	// final Configuration controllerConfig = new Configuration();
	// controllerConfig.setProperty(IProjectContext.CONFIG_PROPERTY_NAME_RECORDS_TIME_UNIT, "MINUTES");
	// final IAnalysisController analysisController = new AnalysisController(controllerConfig);
	//
	// final Configuration readerConfig = new Configuration();
	// readerConfig.setProperty(SimpleTimeSeriesFileReader.CONFIG_PROPERTY_NAME_INPUT_FILE,
	// "examples/analysis/opad-anomaly-detection/src/kieker/examples/analysis/opad/experiment-data/data.csv");
	// readerConfig.setProperty(SimpleTimeSeriesFileReader.CONFIG_PROPERTY_NAME_TS_INTERVAL, "15");
	// final SimpleTimeSeriesFileReader tsReader = new SimpleTimeSeriesFileReader(readerConfig, analysisController);
	//
	// final Configuration aggregatorConfig = new Configuration();
	// aggregatorConfig.setProperty(TimeSeriesPointAggregatorFilter.CONFIG_PROPERTY_NAME_AGGREGATION_METHOD, "MEANJAVA");
	// aggregatorConfig.setProperty(TimeSeriesPointAggregatorFilter.CONFIG_PROPERTY_NAME_AGGREGATION_SPAN, "60");
	// aggregatorConfig.setProperty(TimeSeriesPointAggregatorFilter.CONFIG_PROPERTY_NAME_AGGREGATION_TIMEUNIT, "MINUTES");
	// final TimeSeriesPointAggregatorFilter tsPointAggregator = new TimeSeriesPointAggregatorFilter(aggregatorConfig, analysisController);
	//
	// final Configuration forecastConfig = new Configuration();
	// forecastConfig.setProperty(ForecastingFilter.CONFIG_PROPERTY_NAME_FC_METHOD, "MEANJAVA");
	// forecastConfig.setProperty(ForecastingFilter.CONFIG_PROPERTY_NAME_DELTA_TIME, "1");
	// forecastConfig.setProperty(ForecastingFilter.CONFIG_PROPERTY_NAME_DELTA_UNIT, "HOURS");
	// final ForecastingFilter forecaster = new ForecastingFilter(forecastConfig, analysisController);
	//
	// final UniteMeasurementPairFilter uniteFilter = new UniteMeasurementPairFilter(new Configuration(), analysisController);
	//
	// final TeeFilter tee = new TeeFilter(new Configuration(), analysisController);
	//
	// analysisController.connect(
	// tsReader, SimpleTimeSeriesFileReader.OUTPUT_PORT_NAME_TS_POINTS,
	// tsPointAggregator, TimeSeriesPointAggregatorFilter.INPUT_PORT_NAME_TSPOINT);
	//
	// analysisController.connect(
	// tsPointAggregator, TimeSeriesPointAggregatorFilter.OUTPUT_PORT_NAME_AGGREGATED_TSPOINT,
	// forecaster, ForecastingFilter.INPUT_PORT_NAME_TSPOINT);
	//
	// analysisController.connect(
	// tsPointAggregator, TimeSeriesPointAggregatorFilter.OUTPUT_PORT_NAME_AGGREGATED_TSPOINT,
	// uniteFilter, UniteMeasurementPairFilter.INPUT_PORT_NAME_TSPOINT);
	//
	// analysisController.connect(
	// forecaster, ForecastingFilter.OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT,
	// uniteFilter, UniteMeasurementPairFilter.INPUT_PORT_NAME_FORECAST);
	//
	// analysisController.connect(
	// uniteFilter, UniteMeasurementPairFilter.OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT,
	// tee, TeeFilter.INPUT_PORT_NAME_EVENTS);
	//
	// // Start the analysis
	// analysisController.run();
	//
	// }
}