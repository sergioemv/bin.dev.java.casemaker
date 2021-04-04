package bi.view.businessrulesviews.brimport;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.xmlbeans.XmlException;

import bi.controller.BRParseProcessManager;


public class CMBRJRulesImporterTest extends TestCase {

	private CMBRJRulesImporter importer;
	private ArrayList<File>  importedFiles;
	private  BRParseProcessManager parseProcessManager;
	protected void setUp() throws Exception {
		super.setUp();
		importer = new CMBRJRulesImporter();
		parseProcessManager = new BRParseProcessManager();
		importedFiles = new ArrayList<File>();
		importedFiles.add(new File(new File("").getAbsolutePath()+"/test/res/JRulesImport/ruleset.xrl"));
	}
	public void testDoImport() throws IOException, XmlException {
		for (File file : importedFiles){
			String result;
			try {
				result = importer.doImport(file,null,new HashMap<String, Object>());
				System.out.print(result);
				assertEquals(parseProcessManager.doCheck(new StringReader(result),0,true),"ok");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}