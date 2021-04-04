package bi.view.report;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import model.TestCaseExternalReports;
import bi.view.report.data.CMReportData;
import bi.view.utils.event.CMProgressSource;


/**
 * @author smoreno
 * Interface for common transformed reports
 * a report is composed of data and format
 */
public interface CMReport extends CMProgressSource{
	

	

	/**
	 * Generate the report by generating the data and binding to the format
	*@autor smoreno
	 * @return the generated report file
	 * @throws TransformerException
	 * @throws IOException
	 */
	public File generate() throws IOException, TransformerException;

	/**
	*@autor smoreno
	 * @return
	 * @throws TransformerException
	 */
	public File transform() throws  IOException, TransformerException;

	public TestCaseExternalReports getFormat();

	public CMReportData getReportData();

	public void setOutputFilename(String filename);
	
	
}
