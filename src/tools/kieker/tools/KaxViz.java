/***************************************************************************
 * Copyright 2011 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
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
package kieker.tools;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import kieker.analysis.AnalysisController;
import kieker.analysis.plugin.AbstractAnalysisPlugin;
import kieker.analysis.plugin.AbstractPlugin;
import kieker.analysis.plugin.AbstractReaderPlugin;
import kieker.analysis.plugin.PluginInputPortReference;
import kieker.analysis.repository.AbstractRepository;
import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;

/**
 * A simple visualization of Analysis Configurations.
 * 
 * @author Jan Waller
 */
public final class KaxViz extends JFrame {
	private static final long serialVersionUID = 1969467089938687452L;
	private static final Log LOG = LogFactory.getLog(KaxViz.class);

	private static final String USAGE =
			"Usage: java -cp lib/jgraphx-*.jar kieker.tools.KaxViz analysisproject.kax\n" +
					"where options include\n" +
					"      -cp lib/jgraphx-*.jar   replace 'lib/jgraphx-*.jar' with the correct\n" +
					"                              library location, e.g., lib/jgraphx-1.9.2.0.jar\n" +
					"      analysisproject.kax     the analysis project file loaded and visualized.";

	private final AnalysisController analysisController;
	private final mxGraph graph;

	public KaxViz(final String filename, final AnalysisController analysisController) {
		super(filename + " - " + analysisController.getProjectName());
		this.analysisController = analysisController;

		// Menu
		final JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		final JMenu layoutMenu = new JMenu("Layout");
		menuBar.add(layoutMenu);
		final JMenuItem autoLayout = new JMenuItem("Auto Layout");
		autoLayout.addActionListener(new ActionListener() {
			@Override
			public final void actionPerformed(final ActionEvent arg0) {
				KaxViz.this.autoGraphLayout();
			}
		});
		layoutMenu.add(autoLayout);

		// setup basic graph
		final mxGraph graph = new mxGraph();
		// graph.setGridEnabled(true); // true
		// graph.setGridSize(10); // 10
		// graph.setDefaultOverlap(0.5); // 0.5
		// graph.setDefaultParent(null); // null
		// graph.setAlternateEdgeStyle(null); // null
		// graph.setEnabled(true); // true
		graph.setCellsLocked(false); // true
		graph.setCellsEditable(false); // true
		graph.setCellsResizable(false); // true
		// graph.setCellsMovable(true); // true
		// graph.setCellsBendable(true); // true
		// graph.setCellsSelectable(true); // true
		graph.setCellsDeletable(false); // true
		graph.setCellsCloneable(false); // true
		graph.setCellsDisconnectable(false); // true
		// graph.setLabelsClipped(false); // false
		// graph.setEdgeLabelsMovable(true); // true
		// graph.setVertexLabelsMovable(false); // false
		graph.setDropEnabled(false); // true
		graph.setSplitEnabled(false); // true
		// graph.setAutoSizeCells(false); // false
		// graph.setMaximumGraphBounds(null); // null
		// graph.setMinimumGraphSize(null); // null
		// graph.setBorder(0); // 0
		graph.setKeepEdgesInForeground(true); // false
		graph.setCollapseToPreferredSize(false); // true
		graph.setAllowNegativeCoordinates(false); // true
		// graph.setConstrainChildren(true); // true
		graph.setExtendParents(false); // true
		graph.setExtendParentsOnAdd(false); // true
		// graph.setResetViewOnRootChange(true); // true
		// graph.setResetEdgesOnResize(false); // false
		// graph.setResetEdgesOnMove(false); // false
		graph.setResetEdgesOnConnect(false); // true
		// graph.setAllowLoops(false); // false
		// graph.setMultiplicities(null); // null
		// graph.setDefaultLoopStyle(mxEdgeStyle.Loop); // mxEdgeStyle.Loop
		// graph.setMultigraph(true); // true
		// graph.setConnectableEdges(false); // false
		// graph.setAllowDanglingEdges(false); // false
		// graph.setCloneInvalidEdges(false); // false
		graph.setDisconnectOnMove(false); // true
		// graph.setLabelsVisible(true); // true
		// graph.setHtmlLabels(false); // false
		// graph.setSwimlaneNesting(true); // true
		// graph.setChangesRepaintThreshold(1000); // 1000
		// graph.setAutoOrigin(false); // false
		// graph.setEventsEnabled(true); // true

		// setup the graphComponent
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.setConnectable(false); // Inhibit edge creation in the graph.
		graphComponent.setGridVisible(true); // Show the grid
		graphComponent.setFoldingEnabled(false); // prevent folding of vertexes
		this.getContentPane().add(graphComponent);
		this.graph = graph;

		// add the actual graph
		this.displayGraph();
	}

	private void displayGraph() {
		final mxGraph graph = this.graph;
		final Map<AbstractPlugin, mxCell> mapPlugin2Graph = new HashMap<AbstractPlugin, mxCell>();
		final Map<AbstractPlugin, Map<String, mxCell>> mapPluginInputPorts2Graph = new HashMap<AbstractPlugin, Map<String, mxCell>>();
		final Map<AbstractPlugin, Map<String, mxCell>> mapPluginOutputPorts2Graph = new HashMap<AbstractPlugin, Map<String, mxCell>>();
		final Map<AbstractRepository, mxCell> mapRepository2Graph = new HashMap<AbstractRepository, mxCell>();
		// draw the graph
		graph.getModel().beginUpdate();
		try {
			// step 1: add all plugins!
			for (final AbstractReaderPlugin reader : this.analysisController.getReaders()) {
				final mxCell vertex = this.createReader(reader);
				mapPlugin2Graph.put(reader, vertex);
				mapPluginOutputPorts2Graph.put(reader, this.createOutputPorts(reader, vertex));
			}
			for (final AbstractAnalysisPlugin filter : this.analysisController.getFilters()) {
				final mxCell vertex = this.createFilter(filter);
				mapPlugin2Graph.put(filter, vertex);
				mapPluginInputPorts2Graph.put(filter, this.createInputPorts(filter, vertex));
				mapPluginOutputPorts2Graph.put(filter, this.createOutputPorts(filter, vertex));
			}
			for (final AbstractRepository repo : this.analysisController.getRepositories()) {
				final mxCell cell = this.createRepository(repo);
				mapRepository2Graph.put(repo, cell);
			}
			// step 2: connect all plugins!
			for (final AbstractReaderPlugin reader : this.analysisController.getReaders()) {
				final Map<String, mxCell> outputPorts = mapPluginOutputPorts2Graph.get(reader);
				for (final String outputPort : reader.getAllOutputPortNames()) {
					for (final PluginInputPortReference inputPortReference : reader.getConnectedPlugins(outputPort)) {
						final mxCell output = outputPorts.get(outputPort);
						final mxCell input = mapPluginInputPorts2Graph.get(inputPortReference.getPlugin()).get(inputPortReference.getInputPortName());
						// final String description = "[" + outputPort + "] -> [" + inputPortReference.getInputPortName() + "]";
						graph.insertEdge(null, null, "", output, input);
					}
				}
				for (final Entry<String, AbstractRepository> repository : reader.getCurrentRepositories().entrySet()) {
					final mxCell output = mapPlugin2Graph.get(reader);
					final mxCell input = mapRepository2Graph.get(repository.getValue());
					graph.insertEdge(null, null, repository.getKey(), output, input);
				}
			}
			for (final AbstractAnalysisPlugin filter : this.analysisController.getFilters()) {
				final Map<String, mxCell> outputPorts = mapPluginOutputPorts2Graph.get(filter);
				for (final String outputPort : filter.getAllOutputPortNames()) {
					for (final PluginInputPortReference inputPortReference : filter.getConnectedPlugins(outputPort)) {
						final mxCell output = outputPorts.get(outputPort);
						final mxCell input = mapPluginInputPorts2Graph.get(inputPortReference.getPlugin()).get(inputPortReference.getInputPortName());
						// final String description = "[" + outputPort + "] -> [" + inputPortReference.getInputPortName() + "]";
						graph.insertEdge(null, null, "", output, input);
					}
				}
				for (final Entry<String, AbstractRepository> repository : filter.getCurrentRepositories().entrySet()) {
					final mxCell output = mapPlugin2Graph.get(filter);
					final mxCell input = mapRepository2Graph.get(repository.getValue());
					graph.insertEdge(null, null, repository.getKey(), output, input);
				}
			}
			// step 3: auto layout!
			this.autoGraphLayout();
		} finally {
			// finish the drawing
			graph.getModel().endUpdate();
		}
	}

	final void autoGraphLayout() {
		final mxGraph graph = this.graph;
		new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
	}

	private final Map<String, mxCell> createInputPorts(final AbstractPlugin plugin, final mxCell vertex) {
		final Map<String, mxCell> port2graph = new HashMap<String, mxCell>();
		final String[] portNames = plugin.getAllInputPortNames();
		for (int i = 0; i < portNames.length; i++) {
			final mxGeometry portGeometry = new mxGeometry((i + 1d) / (portNames.length + 1), 0, 10, 10);
			portGeometry.setOffset(new mxPoint(-5, -5));
			portGeometry.setRelative(true);
			final mxCell port = new mxCell(portNames[i], portGeometry, null);
			port.setVertex(true);
			this.graph.addCell(port, vertex);
			port2graph.put(portNames[i], port);
		}
		return port2graph;
	}

	private final Map<String, mxCell> createOutputPorts(final AbstractPlugin plugin, final mxCell vertex) {
		final Map<String, mxCell> port2graph = new HashMap<String, mxCell>();
		final String[] portNames = plugin.getAllOutputPortNames();
		for (int i = 0; i < portNames.length; i++) {
			final mxGeometry portGeometry = new mxGeometry((i + 1d) / (portNames.length + 1), 1, 10, 10);
			portGeometry.setOffset(new mxPoint(-5, -5));
			portGeometry.setRelative(true);
			final mxCell port = new mxCell(portNames[i], portGeometry, null);
			port.setVertex(true);
			this.graph.addCell(port, vertex);
			port2graph.put(portNames[i], port);
		}
		return port2graph;
	}

	private final mxCell createReader(final AbstractReaderPlugin plugin) {
		final mxCell vertex = new mxCell("<<Reader>>\n" + plugin.getName() + " : " + plugin.getPluginName(), new mxGeometry(0, 0, 200, 50), null);
		vertex.setVertex(true);
		this.graph.addCell(vertex);
		return vertex;
	}

	private final mxCell createFilter(final AbstractAnalysisPlugin plugin) {
		final mxCell vertex = new mxCell("<<Filter>>\n" + plugin.getName() + " : " + plugin.getPluginName(), new mxGeometry(0, 0, 200, 50), null);
		vertex.setVertex(true);
		this.graph.addCell(vertex);
		return vertex;
	}

	private final mxCell createRepository(final AbstractRepository repository) {
		final mxCell vertex = new mxCell("<<Repository>>\n : " + repository.getClass().getSimpleName(), new mxGeometry(0, 0, 200, 50), null);
		vertex.setVertex(true);
		this.graph.addCell(vertex);
		return vertex;
	}

	/**
	 * Starts the Visualization of a .kax file.
	 * 
	 * @param args
	 *            the name of the .kax file
	 */
	public final static void main(final String[] args) {
		if (args.length == 0) {
			System.err.println(KaxViz.USAGE);
			System.exit(1);
		}
		try {
			final KaxViz frame = new KaxViz(args[0], new AnalysisController(new File(args[0])));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			frame.setSize(800, 600);
			frame.setVisible(true);
		} catch (final Exception ex) {
			KaxViz.LOG.error("Error", ex);
		}
	}
}