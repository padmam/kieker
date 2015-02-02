/***************************************************************************
 * Copyright 2015 Kieker Project (http://kieker-monitoring.net)
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

package kieker.tools.opad.record;

import java.nio.ByteBuffer;

import kieker.common.record.factory.IRecordFactory;
import kieker.common.util.registry.IRegistry;

/**
 * @author Tom Frotscher
 * 
 * @since 1.10
 */
public final class NamedDoubleRecordFactory implements IRecordFactory<NamedDoubleRecord> {
	
	@Override
	public NamedDoubleRecord create(final ByteBuffer buffer, final IRegistry<String> stringRegistry) {
		return new NamedDoubleRecord(buffer, stringRegistry);
	}
	
	@Override
	public NamedDoubleRecord create(final Object[] values) {
		return new NamedDoubleRecord(values);
	}
	
	public int getRecordSizeInBytes() {
		return NamedDoubleRecord.SIZE;
	}
}