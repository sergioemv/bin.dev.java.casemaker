package bi.controller.utils;

/**
 * @created by Ccastedo 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import model.BusinessRules;
import model.CMXMLFile;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;



public class CMBaseObjectReader extends JSX.ObjectReader {

	private String m_FileName="";	
	private boolean superclassMessageAppear;
	private static Map<String,CMXMLFileState> readFiles = new HashMap<String,CMXMLFileState>();
	private static Map<String,String> additionalInfo = new HashMap<String,String>();
	private static  long m_maxSizeofFile = 20971520;// 20 mb;
	public CMBaseObjectReader(Reader fileReader) throws IOException {
		super(fileReader);
	}
	public CMBaseObjectReader(Reader fileReader, String fileName) throws IOException {
		super(fileReader);		
		m_FileName = fileName;		
		if (CMApplication.frame != null)
			m_maxSizeofFile = (long) ((CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().getApplicationSetting().getM_MaxSizeofXMLFiles()) *1024 * 1024);
	}   
	   
@Override
protected Object readObjectOverride(){
	Object obj=null;
	if (isCorruptedXMLFile()){		
		return null;
	}
	
	try {
		obj = super.readObjectOverride();
	} catch (IOException e) {
		String message = " - "+'"'+e.getMessage()+'"';
		additionalInfo.put(m_FileName,message);
		Logger.getLogger(this.getClass()).error(e);
	} catch (ClassNotFoundException e) {
		Logger.getLogger(this.getClass()).error(e);
	}catch (OutOfMemoryError e){
		Logger.getLogger(this.getClass()).error(e);
	}
	if (isValidXMLFile(obj))
		return obj;
	else
		return null;
	//return obj;
}

	public static long fileSize(File file) {
		long size = -1;
		if (file.exists()) {
			size = file.length();
		}
		return size;
	}

   private  boolean isCorruptedXMLFile() {
	   if (fileSize(new File(m_FileName)) > m_maxSizeofFile){
		   readFiles.put(m_FileName, CMXMLFileState.BIGGERFILE);
		   if ( additionalInfo.containsKey(m_FileName) )
				additionalInfo.remove(m_FileName);	   
		   return true;
	   }
	   else{
		   if (CMBaseObjectReader.getReadFileState(m_FileName)!= null)
			   if (CMBaseObjectReader.getReadFileState(m_FileName) == CMXMLFileState.BIGGERFILE)
				   readFiles.put(m_FileName, CMXMLFileState.VALID);
	   }
 
	   if ( !readFiles.containsKey(m_FileName) ) { 
		   boolean validateXMLFile=false;
		   validateXMLFile = validateXML(m_FileName);
		   if (validateXMLFile )
					readFiles.put(m_FileName, CMXMLFileState.VALID);
           else 
					readFiles.put(m_FileName, CMXMLFileState.CORRUPTEDXML);			
	   }
	   return  !readFiles.get(m_FileName).getState();
   }
   
    public boolean isValidXMLFile(Object obj){
    	boolean isValidXMLFile = false;
    	if (!isCorruptedXMLFile()){
    		if (isFileOnVersion(obj))
    			isValidXMLFile=true;  		
    	} 
    	return isValidXMLFile;
    }
    
   private boolean isFileOnVersion(Object obj){
		
		if (obj instanceof CMXMLFile){	
			Float currentVersion = Float.valueOf(((CMXMLFile)obj).getCurrentVersion()).floatValue();
			if (((CMXMLFile)obj).getVersion() == null){
				readFiles.put(m_FileName,CMXMLFileState.NEWVERSION);				
				String message = CMMessages.getString("CURRENT_VERSION") + currentVersion + CMMessages.getString("FILE_VERSION");
				additionalInfo.put(m_FileName,message);
			}else{
				Float  fileVersion = Float.valueOf(((CMXMLFile)obj).getVersion()).floatValue();
				if (currentVersion<fileVersion)
					readFiles.put(m_FileName,CMXMLFileState.NEWVERSION);
				else
					readFiles.put(m_FileName,CMXMLFileState.VALID);
			}					
		}else
			readFiles.put(m_FileName,CMXMLFileState.VALID);
		return readFiles.get(m_FileName).getState();
	}
	
   public static boolean isCorruptedXMLFile(String p_FileName) {	   
	   if (fileSize(new File(p_FileName)) > m_maxSizeofFile){
		   readFiles.put(p_FileName, CMXMLFileState.VALID);
		   if ( additionalInfo.containsKey(p_FileName) )
				additionalInfo.remove(p_FileName);
		   return true;
	   }
	   if ( !readFiles.containsKey(p_FileName) ) { 		   
			boolean validateXMLFile=false;
			try {
				validateXMLFile = new CMBaseObjectReader(new FileReader(p_FileName)).validateXML(p_FileName);
			} catch (FileNotFoundException e) {
				readFiles.put(p_FileName, CMXMLFileState.CORRUPTEDXML);		
			} catch (IOException e) {
				readFiles.put(p_FileName, CMXMLFileState.CORRUPTEDXML);		
			}
			if (validateXMLFile)
					readFiles.put(p_FileName, CMXMLFileState.VALID);
            else
					readFiles.put(p_FileName, CMXMLFileState.CORRUPTEDXML);
				
		}
  
	   return readFiles.get(p_FileName).getState();
   }
   
	 private boolean validateXML(String filename) {			
		  SAXParserFactory spf = SAXParserFactory.newInstance();
	      spf.setNamespaceAware(true); 
	      spf.setValidating(true); 	
	 
		try {
			SchemaFactory schemaFactory =  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			String xsdPath = CMApplication.getInstallationDirectory() + BusinessRules.XSD_RES_DIRECTORY;
			Source schemaSource = new StreamSource(new File(xsdPath));
			StreamSource sts = new StreamSource(new File(filename));
			
			Schema schema = schemaFactory.newSchema(schemaSource);		
			Validator validator = schema.newValidator();
			validator.validate(sts);			
	
		} catch (SAXException e) {		
			String code = e.getLocalizedMessage();
			if (code.equals("cvc-complex-type.4: Attribute 'superclasses' must appear on element 'object'.")){
				this.superclassMessageAppear = true;
				return true;
			}
			Logger.getLogger(this.getClass()).error(e);
			return false;
		} catch (IOException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
			return false;
		}
		return true;
	}

	 public boolean isSuperclassMessageAppear() {
			return superclassMessageAppear;
		}
	public static void setReadFileState(String p_FileName, CMXMLFileState p_value) {
		//if ( readFiles.containsKey(p_FileName) ){
			readFiles.put(p_FileName, p_value);
		//}		
	}
	public static CMXMLFileState getReadFileState(String p_FileName) {
		if ( readFiles.containsKey(p_FileName) ){
			return readFiles.get(p_FileName);
		}		
		return null;
	}
	public static void deleteFileFromMap(String p_FileName) {
		if ( readFiles.containsKey(p_FileName) ){
			readFiles.remove(p_FileName);
		}
	}
	public static String getAdditionalInfo(String p_FileName) {
		if ( additionalInfo.containsKey(p_FileName) ){
			return additionalInfo.get(p_FileName);
		}
		return null;
			
	}
	public static void setAdditionalInfo(String p_FileName, Map<String, String> additionalInfo) {
	//	if ( additionalInfo.containsKey(p_FileName) ){
			CMBaseObjectReader.additionalInfo = additionalInfo;
	//	}
		
	}
	

	
;
}