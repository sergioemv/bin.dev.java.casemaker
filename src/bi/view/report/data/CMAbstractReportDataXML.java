package bi.view.report.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.BusinessRules;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public abstract class CMAbstractReportDataXML implements CMReportData {


	private Map<String,Object> reportParameters;
    private String filePath = "";
    private XmlObject reportDataStructure = null;
    private XmlOptions saveOptions = new XmlOptions();
    private String defaultXmlFilename = "out.xml";
    protected Logger logger =Logger.getLogger("bi.view.report.data");
    
    public CMAbstractReportDataXML() {
    	saveOptions.setCharacterEncoding("iso-8859-1");
    	saveOptions.setSavePrettyPrint();
	}
    
	public String getFilePath() {
		if (filePath.equalsIgnoreCase(""))
				setfilePath(getReportFolder() + BusinessRules.URL_SEPARATOR+defaultXmlFilename);

		return filePath;
	}
	
	public void setfilePath(String fileName) {
		this.filePath = fileName;
	}	
	public boolean loadData() {
		File file = new File(getFilePath());
		try {
			reportDataStructure = this.createReportDataStructure();
			if (reportDataStructure!=null)
			{				
			file.mkdirs();			
			file.delete();
			file.createNewFile();
			logger.debug("save the xml file");
			reportDataStructure.save(file,saveOptions);
			return file.exists();
			}
			else
				return false;
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 *  Create the report structure based on the 
	 * @return
	 */
	public abstract XmlObject createReportDataStructure();
	public boolean isReportDataGenerated() {
		
		return (reportDataStructure!=null)&&(new File(getFilePath()).exists());
	}

	private Map<String, Object> getReportParameters() {
		if (reportParameters == null)
			reportParameters = new HashMap<String, Object>();
		return reportParameters;
	}	
	public void setParameter(String key, Object ob) {
		getReportParameters().put(key, ob);
	}
	
	public Object getParameter(String key) {
		return getReportParameters().get(key);
	}
}
