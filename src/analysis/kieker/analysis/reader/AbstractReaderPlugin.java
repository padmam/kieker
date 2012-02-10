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

package kieker.analysis.reader;

import java.util.Map;

import kieker.analysis.plugin.AbstractPlugin;
import kieker.analysis.repository.AbstractRepository;
import kieker.common.configuration.Configuration;

/**
 * This class should be used as a base for every reader used within <i>Kieker</i>.
 * 
 * @author Nils Christian Ehmke
 */
public abstract class AbstractReaderPlugin extends AbstractPlugin implements IMonitoringReader {

	/**
	 * Each Plugin requires a constructor with a single Configuration object.
	 * 
	 * @param configuration
	 *            The configuration which should be used to initialize the object.
	 */
	public AbstractReaderPlugin(final Configuration configuration, final Map<String, AbstractRepository> repositories) {
		super(configuration, repositories);
	}
}