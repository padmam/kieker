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

package kieker.tools.traceAnalysis.plugins.visualization.dependencyGraph;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Andre van Hoorn
 */
public class DependencyGraphNode<T> {

	private final T entity;
	private final int id;
	private final Map<Integer, WeightedBidirectionalDependencyGraphEdge<T>> incomingDependencies = 
		new TreeMap<Integer, WeightedBidirectionalDependencyGraphEdge<T>>(); // NOPMD (UseConcurrentHashMap)
	private final Map<Integer, WeightedBidirectionalDependencyGraphEdge<T>> outgoingDependencies = // NOPMD (see below)
		new TreeMap<Integer, WeightedBidirectionalDependencyGraphEdge<T>>(); // NOPMD (UseConcurrentHashMap)

	public DependencyGraphNode(final int id, final T entity) {
		this.id = id;
		this.entity = entity;
	}

	public final T getEntity() {
		return this.entity;
	}

	public final Collection<WeightedBidirectionalDependencyGraphEdge<T>> getIncomingDependencies() {
		return this.incomingDependencies.values();
	}

	public final Collection<WeightedBidirectionalDependencyGraphEdge<T>> getOutgoingDependencies() {
		return this.outgoingDependencies.values();
	}

	public void addOutgoingDependency(final DependencyGraphNode<T> destination) {
		WeightedBidirectionalDependencyGraphEdge<T> e = this.outgoingDependencies.get(destination.getId());
		if (e == null) {
			e = new WeightedBidirectionalDependencyGraphEdge<T>();
			e.setSource(this);
			e.setDestination(destination);
			this.outgoingDependencies.put(destination.getId(), e);
		}
		e.incOutgoingWeight();
	}

	public void addIncomingDependency(final DependencyGraphNode<T> source) {
		WeightedBidirectionalDependencyGraphEdge<T> e = this.incomingDependencies.get(source.getId());
		if (e == null) {
			e = new WeightedBidirectionalDependencyGraphEdge<T>();
			e.setSource(this);
			e.setDestination(source);
			this.incomingDependencies.put(source.getId(), e);
		}
		e.incIncomingWeight();
	}

	public final int getId() {
		return this.id;
	}
}
