/***************************************************************************
 * Copyright 2012 by
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

package kieker.test.common.junit.record;

import junit.framework.Assert;
import junit.framework.TestCase;
import kieker.common.record.controlflow.OperationExecutionRecord;

/**
 * Creates {@link OperationExecutionRecord}s via the available constructors and
 * checks the values passed values via getters.
 * 
 * @author Andre van Hoorn
 * 
 */
public class TestOperationExecutionRecordConstructors extends TestCase {

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, long, long, long, String, int, int)}
	 */
	public void testSignatureStringSessionIDTraceIDTinToutEoiEss() {
		final String sessionId = "IaYyf8m9B";
		final long traceId = 3486095; // any number will do
		final String hostName = "srv-gvNH6CgYFS";
		final long tin = 33444; // any number will do
		final long tout = 33449; // any number will do
		final int eoi = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_EOI;
		final int ess = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_ESS;
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQ_SIGNATURE_BOOKSTORE_SEARCH_BOOK, sessionId, traceId,
						tin, tout, hostName, eoi, ess);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQ_SIGNATURE_BOOKSTORE_SEARCH_BOOK);
		this.checkTraceId(opExecutionRecord, traceId);
		this.checkTinTout(opExecutionRecord, tin, tout);
		this.checkEoiEss(opExecutionRecord, eoi, ess);
		this.checkHostName(opExecutionRecord, hostName);
		this.checkSessionId(opExecutionRecord, sessionId);
		this.checkIsEntryPoint(opExecutionRecord, false); // default, as documented in the constructor
		this.checkObject(opExecutionRecord, null);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, long, long, long, String, int, int)}
	 */
	public void testSignatureStringSessionIDTraceIDTinToutEoiEssNoEntryButRetVal() {
		final String sessionId = "IaYyf8m9B";
		final long traceId = 3486095; // any number will do
		final String hostName = "srv-gvNH6CgYFS";
		final long tin = 33444; // any number will do
		final long tout = 33449; // any number will do
		final int eoi = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_EOI;
		final int ess = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_ESS;
		final boolean entryPoint = false;
		final Object obj = new Object();
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQ_SIGNATURE_BOOKSTORE_SEARCH_BOOK, sessionId, traceId,
						tin, tout, hostName, eoi, ess, entryPoint, obj);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQ_SIGNATURE_BOOKSTORE_SEARCH_BOOK);
		this.checkTraceId(opExecutionRecord, traceId);
		this.checkTinTout(opExecutionRecord, tin, tout);
		this.checkEoiEss(opExecutionRecord, eoi, ess);
		this.checkHostName(opExecutionRecord, hostName);
		this.checkSessionId(opExecutionRecord, sessionId);
		this.checkIsEntryPoint(opExecutionRecord, entryPoint);
		this.checkObject(opExecutionRecord, obj);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, long, long, long, String, int, int)}
	 */
	public void testSignatureStringSessionIDTraceIDTinToutEoiEssEntryNoRetVal() {
		final String sessionId = "IaYyf8m9B";
		final long traceId = 3486095; // any number will do
		final String hostName = "srv-gvNH6CgYFS";
		final long tin = 33444; // any number will do
		final long tout = 33449; // any number will do
		final int eoi = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_EOI;
		final int ess = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_ESS;
		final boolean entryPoint = true;
		final Object obj = null;
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQ_SIGNATURE_BOOKSTORE_SEARCH_BOOK, sessionId, traceId,
						tin, tout, hostName, eoi, ess, entryPoint, obj);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQ_SIGNATURE_BOOKSTORE_SEARCH_BOOK);
		this.checkTraceId(opExecutionRecord, traceId);
		this.checkTinTout(opExecutionRecord, tin, tout);
		this.checkEoiEss(opExecutionRecord, eoi, ess);
		this.checkHostName(opExecutionRecord, hostName);
		this.checkSessionId(opExecutionRecord, sessionId);
		this.checkIsEntryPoint(opExecutionRecord, entryPoint);
		this.checkObject(opExecutionRecord, obj);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, long)}
	 */
	// TODO: to be removed in 1.6 as the tested constructor will be removed there
	public void testComponentOpTraceID() {
		final long traceId = 3486095; // any number will do
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
						BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK, traceId);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE + "." + BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
		this.checkTraceId(opExecutionRecord, traceId);
		this.checkIsEntryPoint(opExecutionRecord, false); // default, as documented in the constructor
		this.checkObject(opExecutionRecord, null);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, long, long, long)}
	 */
	// TODO: to be removed in 1.6 as the tested constructor will be removed there
	public void testComponentOpTraceIDTinTout() {
		final long traceId = 3486095; // any number will do
		final long tin = 33444; // any number will do
		final long tout = 33449; // any number will do
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
						BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK, traceId, tin, tout);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE + "." + BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
		this.checkTraceId(opExecutionRecord, traceId);
		this.checkTinTout(opExecutionRecord, tin, tout);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, long, long)}
	 */
	// TODO: to be removed in 1.6 as the tested constructor will be removed there
	public void testComponentTinTout() {
		final long tin = 33444; // any number will do
		final long tout = 33449; // any number will do
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
						BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK, tin, tout);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE + "." + BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);

		this.checkTinTout(opExecutionRecord, tin, tout);

		this.checkIsEntryPoint(opExecutionRecord, false); // default, as documented in the constructor
		this.checkObject(opExecutionRecord, null);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, String, long, long, long)}
	 */
	// TODO: to be removed in 1.6 as the tested constructor will be removed there
	public void testComponentOpSessionTraceIDTinTout() {
		final String sessionId = "eqJSin80n";
		final long traceId = 3486095; // any number will do
		final long tin = 33444; // any number will do
		final long tout = 33449; // any number will do
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
						BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK, sessionId, traceId, tin, tout);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE + "." + BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
		this.checkTraceId(opExecutionRecord, traceId);
		this.checkTinTout(opExecutionRecord, tin, tout);
		this.checkSessionId(opExecutionRecord, sessionId);

		this.checkIsEntryPoint(opExecutionRecord, false); // default, as documented in the constructor
		this.checkObject(opExecutionRecord, null);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	/**
	 * Tests {@link OperationExecutionRecord#OperationExecutionRecord(String, String, long, long, long, String, int, int)}
	 */
	// TODO: to be removed in 1.6 as the tested constructor will be removed there
	public void testComponentOpSessionIDTraceIDTinToutEoiEss() {
		final String sessionId = "IaYyf8m9B";
		final long traceId = 3486095; // any number will do
		final String hostName = "srv-gvNH6CgYFS";
		final long tin = 33444; // any number will do
		final long tout = 33449; // any number will do
		final int eoi = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_EOI;
		final int ess = BookstoreOperationExecutionRecordFactory.EXEC0_0__BOOKSTORE_SEARCHBOOK_ESS;
		final OperationExecutionRecord opExecutionRecord =
				new OperationExecutionRecord(BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
						BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK, sessionId, traceId,
						tin, tout, hostName, eoi, ess);

		this.checkClassSignatureValues(opExecutionRecord,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK,
				BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE + "." + BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
		this.checkTraceId(opExecutionRecord, traceId);
		this.checkTinTout(opExecutionRecord, tin, tout);
		this.checkEoiEss(opExecutionRecord, eoi, ess);
		this.checkHostName(opExecutionRecord, hostName);
		this.checkSessionId(opExecutionRecord, sessionId);

		this.checkIsEntryPoint(opExecutionRecord, false); // default, as documented in the constructor
		this.checkObject(opExecutionRecord, null);

		this.checkToFromArrayAllFields(opExecutionRecord, BookstoreOperationExecutionRecordFactory.FQCLASS_BOOKSTORE,
				BookstoreOperationExecutionRecordFactory.OP_NAMEWITHARG_BOOKSTORE_SEARCH_BOOK);
	}

	private void checkSessionId(final OperationExecutionRecord opExecutionRecord, final String expectedSessionId) {
		Assert.assertEquals("Unexpected session ID", expectedSessionId, opExecutionRecord.getSessionId());
	}

	private void checkHostName(final OperationExecutionRecord opExecutionRecord, final String expectedHostName) {
		Assert.assertEquals("Unexpected host name", expectedHostName, opExecutionRecord.getHostName());
	}

	private void checkTraceId(final OperationExecutionRecord opExecutionRecord, final long expectedTraceId) {
		Assert.assertEquals("Unexpected trace ID", expectedTraceId, opExecutionRecord.getTraceId());
	}

	private void checkTinTout(final OperationExecutionRecord opExecutionRecord, final long tin, final long tout) {
		Assert.assertEquals("tin's differ", tin, opExecutionRecord.getTin());
		Assert.assertEquals("tout's differ", tout, opExecutionRecord.getTout());
	}

	private void checkEoiEss(final OperationExecutionRecord opExecutionRecord, final int eoi, final int ess) {
		Assert.assertEquals("eoi's differ", eoi, opExecutionRecord.getEoi());
		Assert.assertEquals("ess's differ", eoi, opExecutionRecord.getEss());
	}

	private void checkClassSignatureValues(final OperationExecutionRecord opExecutionRecord, final String expectedFqClassname, final String expectedOpArgString,
			final String expectedOperationSignature) {
		Assert.assertEquals("Signature string varies",
				expectedOperationSignature,
				opExecutionRecord.getOperationSignature());

		/* Now we'll test some (deprecated) legacy getters */
		Assert.assertEquals("FQ classnames differ", expectedFqClassname, opExecutionRecord.getClassName());
		Assert.assertEquals("operation names differ", expectedOpArgString, opExecutionRecord.getOperationName());
	}

	private void checkIsEntryPoint(final OperationExecutionRecord opExecutionRecord, final boolean entryPoint) {
		Assert.assertEquals("Unexpected entry point information", entryPoint, opExecutionRecord.isEntryPoint());
	}

	private void checkObject(final OperationExecutionRecord opExecutionRecord, final Object obj) {
		Assert.assertEquals("Unexpected object attached", obj, opExecutionRecord.getRetVal());
	}

	private void checkToFromArrayAllFields(final OperationExecutionRecord opExecutionRecord, final String expectedFqClassname, final String expectedOpArgString) {
		final Object[] serializedRecord = opExecutionRecord.toArray();
		final OperationExecutionRecord deserializedRecord = new OperationExecutionRecord(serializedRecord);

		Assert.assertEquals("Records not equal", opExecutionRecord, deserializedRecord);

		this.checkClassSignatureValues(deserializedRecord, expectedFqClassname, expectedOpArgString, opExecutionRecord.getOperationSignature());
		this.checkEoiEss(deserializedRecord, opExecutionRecord.getEoi(), opExecutionRecord.getEss());
		this.checkHostName(deserializedRecord, opExecutionRecord.getHostName());
		this.checkSessionId(deserializedRecord, opExecutionRecord.getSessionId());
		this.checkTinTout(deserializedRecord, opExecutionRecord.getTin(), opExecutionRecord.getTout());
		this.checkTraceId(deserializedRecord, opExecutionRecord.getTraceId());
	}
}
