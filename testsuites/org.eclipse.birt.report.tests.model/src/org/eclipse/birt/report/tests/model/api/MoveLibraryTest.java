package org.eclipse.birt.report.tests.model.api;

import java.io.File;

import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DesignEngine;
import org.eclipse.birt.report.model.api.LibraryHandle;
import org.eclipse.birt.report.model.api.TextItemHandle;
import org.eclipse.birt.report.tests.model.BaseTestCase;

import com.ibm.icu.util.ULocale;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestCases for Library operation.
 * <p>
 * <table border="1" cellpadding="2" cellspacing="2" style="border-collapse:
 * collapse" bordercolor="#111111">
 * <th width="20%">Method</th>
 *
 * <tr>
 * <td>{@link #testCopyLibA()}</td>
 * </tr>
 *
 * <tr>
 * <td>{@link #testMoveLibrary()}</td>
 * </tr>
 * </table>
 *
 */
public class MoveLibraryTest extends BaseTestCase {
	String fileName = "BlankReport.xml";

	private static String LibA = "LibA.xml";
	private static String LibB = "LibB.xml";
	private static String LibC = "LibC.xml";

	private String LibD = "LibA.xml";

	public MoveLibraryTest(String name) {
		super(name);
	}

	public static Test suite() {

		return new TestSuite(MoveLibraryTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		removeResource();

		copyInputToFile(INPUT_FOLDER + "/" + fileName);
		copyInputToFile(INPUT_FOLDER + "/" + LibA);
		copyInputToFile(INPUT_FOLDER + "/" + LibB);
		copyInputToFile(INPUT_FOLDER + "/" + LibC);
		openDesign(fileName);

	}

	public void tearDown() {
		removeResource();
	}

	/**
	 * Test saveAs library
	 *
	 * @throws Exception
	 */
	public void testCopyLibA() throws Exception {
		sessionHandle = DesignEngine.newSession(ULocale.ENGLISH);
		assertNotNull(sessionHandle);

		libraryHandle = sessionHandle.openLibrary(getTempFolder() + "/" + INPUT_FOLDER + "/" + LibA);
		assertNotNull(libraryHandle);

		String TempFile = this.genOutputFile(LibD);
		libraryHandle.saveAs(TempFile);
		// super.saveLibraryAs(LibD);
		// libraryHandle.saveAs(LibD);
	}

	/**
	 * Test remove library used in report
	 *
	 * @throws Exception
	 */
	public void testMoveLibrary() throws Exception {
		openDesign(fileName);
		designHandle.includeLibrary(LibD, "LibD");
		LibraryHandle libHandle = designHandle.getLibrary("LibD");

		TextItemHandle textLibHandle = (TextItemHandle) libHandle.findElement("text1");
		assertNotNull("Text should not be null", textLibHandle);
		DataItemHandle dataLibHandle = (DataItemHandle) libHandle.findElement("data1");
		assertNotNull("Data should not be null", dataLibHandle);
		// SharedStyleHandle styleLibHandle = (SharedStyleHandle)libHandle.findStyle(
		// "style1" );
		// assertNotNull("Style should not be null", styleLibHandle);

		TextItemHandle textHandle = (TextItemHandle) designHandle.getElementFactory().newElementFrom(textLibHandle,
				"text1");
		DataItemHandle dataHandle = (DataItemHandle) designHandle.getElementFactory().newElementFrom(dataLibHandle,
				"data1");
		// StyleHandle styleHandle =
		// (StyleHandle)designHandle.getElementFactory().newStyle( "style1" );

		designHandle.getBody().add(dataHandle);
		designHandle.getBody().add(textHandle);
		// designHandle.getStyles().add( styleHandle );

		assertEquals("yellow", dataHandle.getExtends().getStringProperty("backgroundColor"));
		assertEquals("red", textHandle.getExtends().getStringProperty("backgroundColor"));

		String TempFile = this.genOutputFile("SavedReport.xml");
		designHandle.saveAs(TempFile);

		// Delete any lingering library
		String tempLibFile = this.genOutputFile(LibD);
		File f = new File(tempLibFile);
		f.delete();

		designHandle.saveAs(TempFile);

		openDesign(TempFile, false);
		assertNotNull((TextItemHandle) designHandle.findElement("text1"));
		assertNotNull((DataItemHandle) designHandle.findElement("data1"));
		assertEquals(null, ((TextItemHandle) designHandle.findElement("text1")).getStringProperty("backgroundColor"));
		assertEquals(null, ((DataItemHandle) designHandle.findElement("data1")).getStringProperty("backgroundColor"));

	}
}
