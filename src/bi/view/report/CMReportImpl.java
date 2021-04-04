package bi.view.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;
import model.BusinessRules;
import model.TestCaseExternalReports;
import bi.controller.SessionManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.report.data.CMReportData;
import bi.view.utils.event.CMProgressEventHandler;
import bi.view.utils.event.CMProgressListener;


public class CMReportImpl implements CMReport {

	public enum Progress{
	XML_GENERATION_STARTED,
	XML_GENERATION_FINISHED,
	XML_TRANSFORMATION_STARTED,
	XML_TRANSFORMATION_FINISHED;
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		switch (this) {
		case XML_GENERATION_STARTED:
			return CMMessages.getString("REPORT_STATE_GENERATION_STARTED");
		case XML_GENERATION_FINISHED:
			return CMMessages.getString("REPORT_STATE_GENERATION_FINISHED");
		case XML_TRANSFORMATION_STARTED:
			return CMMessages.getString("REPORT_STATE_TRANSFORMATION_STARTED");
		case XML_TRANSFORMATION_FINISHED:
			return CMMessages.getString("REPORT_STATE_TRANSFORMATION_FINISHED");
		default:
			return "";
		}
	}
	}

	private CMReportData reportData;
	private  TestCaseExternalReports reportFormat;
	private String outputFilename ="";
	private Logger logger = Logger.getLogger("bi.view.report");
	private CMProgressEventHandler handler = new CMProgressEventHandler();
	
	
	public CMReportImpl(CMReportData reportData, TestCaseExternalReports reportFormat, String outputFile) {
		super();
		this.reportData = reportData;
		this.reportFormat = reportFormat;
		this.outputFilename = outputFile;
	}

	public CMReportImpl(TestCaseExternalReports format, String newName) {
		this.reportFormat = format;
		this.outputFilename = newName;
		this.reportData = reportFormat.getReporDataSourceType().getReportData();
		
		// TODO Auto-generated constructor stub
	}

	public File generate() throws IOException, TransformerException  {
		handler.fireProgressEventHappen(this,Progress.XML_GENERATION_STARTED.toString());
		if (reportData.loadData())
		{
			handler.fireProgressEventHappen(this,Progress.XML_GENERATION_FINISHED.toString());
			return transform();
		}

		else
		return null;
	}

	public File transform() throws IOException, TransformerException {
		if (reportData.isReportDataGenerated())
		{

	    TransformerFactory tFactory = TransformerFactory.newInstance();


	    	//create the XSLT transformer

	    	logger.debug("starting the Transformation");
	    	handler.fireProgressEventHappen(this,Progress.XML_TRANSFORMATION_STARTED.toString());
	    	String filepath = reportFormat.getFilePath();
			Transformer transformer = tFactory.newTransformer(
	                new StreamSource(filepath));
			
			File l_outputFile = null;
			
			l_outputFile = new File(getOutputFilename());
			
			l_outputFile.mkdirs();
			l_outputFile.delete();
			l_outputFile.createNewFile();
			FileOutputStream fileOutPutStream= new FileOutputStream(l_outputFile);

			//do the tranformation
			transformer.transform(new StreamSource(reportData.getFilePath()),new StreamResult(fileOutPutStream));
			fileOutPutStream.close();
			logger.debug("finished the Transformation");
			handler.fireProgressEventHappen(this,Progress.XML_TRANSFORMATION_FINISHED.toString());
			//pass the images
			transferImages();
			return l_outputFile;


		}
		return null;
	}
	private void transferImages() {
		String imageFolder ="";
		imageFolder = getOutputFilename().substring(0, getOutputFilename().lastIndexOf("/")+1)+BusinessRules.REPORT_IMAGEFLD+"/";
        SessionManager.transferImageFromJar(CMIcon.CASEMAKER_LOGO.getFilename(),imageFolder+CMIcon.CASEMAKER_LOGO.getFilename()); //$NON-NLS-1$ //$NON-NLS-2$
        SessionManager.transferImageFromJar(CMIcon.EQUIVALENCECLASS_IN_COMBINATION.getFilename(),imageFolder+CMIcon.EQUIVALENCECLASS_IN_COMBINATION.getFilename()); //$NON-NLS-1$ //$NON-NLS-2$
        SessionManager.transferImageFromJar(CMIcon.CASEMAKER_LOGO_SMALL.getFilename(),imageFolder+CMIcon.CASEMAKER_LOGO_SMALL.getFilename()); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public String getOutputFilename() {		
		if (!outputFilename.endsWith(getFormat().getExtension()))
			outputFilename = outputFilename +"."+ getFormat().getExtension();
		return outputFilename;

	}
	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}

	public TestCaseExternalReports getFormat() {
		// TODO Auto-generated method stub
		return this.reportFormat;
	}

	public CMReportData getReportData() {
		// TODO Auto-generated method stub
		return this.reportData;
	}

	/* (non-Javadoc)
	 * @see bi.view.report.CMReportSource#addReportListener(bi.view.report.CMReportListener)
	 */
	public void addProgressListener(CMProgressListener p_rl) {
		// TODO Auto-generated method stub
		handler.addProgressListener(p_rl);
	}

	/* (non-Javadoc)
	 * @see bi.view.report.CMReportSource#removeReportListener(bi.view.report.CMReportListener)
	 */
	public void removeProgressListener(CMProgressListener p_rl) {
		handler.removeProgressListener(p_rl);

	}

	
	
}
