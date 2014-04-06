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

package kieker.panalysis.examples.wordcount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kieker.panalysis.Distributor;
import kieker.panalysis.Merger;
import kieker.panalysis.RepeaterSource;
import kieker.panalysis.base.Analysis;
import kieker.panalysis.base.IStage;
import kieker.panalysis.base.Pipeline;
import kieker.panalysis.base.TerminationPolicy;
import kieker.panalysis.concurrent.ConcurrentWorkStealingPipe;
import kieker.panalysis.concurrent.WorkerThread;

/**
 * @author Christian Wulf
 * 
 * @since 1.10
 */
public class ConcurrentCountWordsAnalysis extends Analysis {

	public static final String START_DIRECTORY_NAME = ".";

	private static final int SECONDS = 1000;

	private RepeaterSource repeaterSource;
	private WorkerThread[] threads;

	@Override
	public void init() {
		super.init();

		this.repeaterSource = new RepeaterSource(START_DIRECTORY_NAME, 4000);
		this.repeaterSource.setId(99);

		int numThreads = Runtime.getRuntime().availableProcessors();
		numThreads = 1; // only fur testing purposes

		this.threads = new WorkerThread[numThreads];
		final Map<Integer, List<ConcurrentWorkStealingPipe>> pipeGroups = new HashMap<Integer, List<ConcurrentWorkStealingPipe>>();

		for (int i = 0; i < this.threads.length; i++) {
			final Pipeline<ConcurrentWorkStealingPipe> pipeline = new Pipeline<ConcurrentWorkStealingPipe>(pipeGroups);
			this.buildPipeline(pipeline);

			final WorkerThread thread = new WorkerThread();
			thread.setPipeline(pipeline);
			this.threads[i] = thread;
		}

		for (final List<ConcurrentWorkStealingPipe> samePipes : pipeGroups.values()) {
			for (final ConcurrentWorkStealingPipe pipe : samePipes) {
				pipe.copyAllOtherPipes(samePipes);
			}
		}

	}

	private void buildPipeline(final Pipeline<ConcurrentWorkStealingPipe> pipeline) {
		// create stages
		final RepeaterSource repeaterSource = this.repeaterSource;
		final DirectoryName2Files findFilesStage = pipeline.addStage(new DirectoryName2Files());
		final Distributor distributor = pipeline.addStage(new Distributor());
		final CountWordsStage countWordsStage0 = pipeline.addStage(new CountWordsStage());
		final CountWordsStage countWordsStage1 = pipeline.addStage(new CountWordsStage());
		final Merger merger = pipeline.addStage(new Merger());
		final OutputWordsCountSink outputWordsCountStage = pipeline.addStage(new OutputWordsCountSink());
		// TODO consider to use: pipeline.add(stage).asStartStage().assignUniqueId()

		pipeline.setStartStages(findFilesStage);

		// connect stages by pipes
		ConcurrentWorkStealingPipe pipe = new ConcurrentWorkStealingPipe()
				.source(repeaterSource, RepeaterSource.OUTPUT_PORT.OUTPUT)
				.target(findFilesStage, DirectoryName2Files.INPUT_PORT.DIRECTORY_NAME);
		pipeline.add(pipe).toGroup(0);

		pipe = new ConcurrentWorkStealingPipe()
				.source(findFilesStage, DirectoryName2Files.OUTPUT_PORT.FILE)
				.target(distributor, Distributor.INPUT_PORT.OBJECT);
		pipeline.add(pipe).toGroup(1);

		pipe = new ConcurrentWorkStealingPipe()
				.source(distributor, Distributor.OUTPUT_PORT.OUTPUT0)
				.target(countWordsStage0, CountWordsStage.INPUT_PORT.FILE);
		pipeline.add(pipe).toGroup(2);

		pipe = new ConcurrentWorkStealingPipe()
				.source(distributor, Distributor.OUTPUT_PORT.OUTPUT1)
				.target(countWordsStage1, CountWordsStage.INPUT_PORT.FILE);
		pipeline.add(pipe).toGroup(3);

		pipe = new ConcurrentWorkStealingPipe()
				.source(countWordsStage0, CountWordsStage.OUTPUT_PORT.WORDSCOUNT)
				.target(merger, Merger.INPUT_PORT.INPUT0);
		pipeline.add(pipe).toGroup(4);

		pipe = new ConcurrentWorkStealingPipe()
				.source(countWordsStage1, CountWordsStage.OUTPUT_PORT.WORDSCOUNT)
				.target(merger, Merger.INPUT_PORT.INPUT1);
		pipeline.add(pipe).toGroup(5);

		pipe = new ConcurrentWorkStealingPipe()
				.source(merger, Merger.OUTPUT_PORT.OBJECT)
				.target(outputWordsCountStage, OutputWordsCountSink.INPUT_PORT.FILE_WORDCOUNT_TUPLE);
		pipeline.add(pipe).toGroup(6);
	}

	@Override
	public void start() {
		super.start();

		for (final WorkerThread thread : this.threads) {
			thread.start();
		}

		this.repeaterSource.execute();

		System.out.println("Waiting for the worker threads to terminate...");
		for (final WorkerThread thread : this.threads) {
			thread.terminate(TerminationPolicy.TERMINATE_STAGE_AFTER_UNSUCCESSFUL_EXECUTION);
			try {
				thread.join(60 * SECONDS);
			} catch (final InterruptedException e) {
				throw new IllegalStateException();
			}
		}

		System.out.println("Analysis finished.");
	}

	public static void main(final String[] args) {
		final ConcurrentCountWordsAnalysis analysis = new ConcurrentCountWordsAnalysis();
		analysis.init();
		final long start = System.currentTimeMillis();
		analysis.start();
		final long end = System.currentTimeMillis();
		// analysis.terminate();
		final long duration = end - start;
		System.out.println("duration: " + duration + " ms"); // NOPMD (Just for example purposes)

		analysis.analyzeThreads();
	}

	private void analyzeThreads() {
		long maxDuration = -1;
		WorkerThread maxThread = null;

		System.out.println(this.repeaterSource);
		System.out.println(this.repeaterSource.getOutputPipe(RepeaterSource.OUTPUT_PORT.OUTPUT));

		// FIXME resolve bug; see analysis results below;
		// solution: use a generic distributor to distribute between the threads' start stages
		// {RepeaterSource: numPushedElements=4000, numTakenElements=0}
		// {DirectoryName2Files: numPushedElements=59985, numTakenElements=3999}

		for (final WorkerThread thread : this.threads) {
			for (final IStage stage : thread.getPipeline().getStages()) {
				System.out.println(stage);
			}

			// System.out.println("findFilesStage: " + ((DirectoryName2Files) thread.getStages().get(0)).getNumFiles()); // NOPMD (Just for example purposes)

			final OutputWordsCountSink sink = ((OutputWordsCountSink) thread.getPipeline().getStages().get(5));
			System.out.println("outputWordsCountStage: " + sink.getNumFiles()); // NOPMD (Just for example purposes)

			final long duration = thread.getDuration();
			if (duration > maxDuration) {
				maxDuration = duration;
				maxThread = thread;
			}
		}

		System.out.println("maxThread: " + maxThread.toString() + " takes " + maxDuration + " ms");
	}
}
