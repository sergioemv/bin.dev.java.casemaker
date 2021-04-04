package bi.controller.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import bi.view.mainframeviews.CMApplication;
import model.BusinessRules;
import model.Project2;
import model.TestObjectReference;
import JSX.parser.XMLReader;

/**
 * @author svonborries
 * Abstract class the is used to validate any file.
 */
public abstract class CMFileValidator extends XMLReader {
	
	public static String DELETED_TESTOBJECTREFERENCES = "";
	public static int DELETED_TESTOBJECTREFERENCES_COUNT = 0;
	@SuppressWarnings("unchecked")
	private static Vector <String> PROJECTS_NAME_VALIDATED = new Vector();
	private static Vector <String> PROJECTS_PATH_VALIDATED = new Vector <String>();
	/**
	 * @author svonborries
	 * @param recive a file path and return true if this is true, and false if it trows an exeption.
	 *
	 */
	public static boolean validateFileExist(String p_filePath){
		try {
			@SuppressWarnings("unused") FileReader l_File = new FileReader(p_filePath);
		} catch (FileNotFoundException e) {
			Logger.getLogger(CMFileValidator.class).error(e);
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static Project2 fixTestObjectReferences(Project2 p_Project, String p_ProjectPath){
		StringBuffer absoluteFilePath = null;
		CMFileValidator.DELETED_TESTOBJECTREFERENCES = "";
		CMFileValidator.DELETED_TESTOBJECTREFERENCES_COUNT = 0;
		try {
			Vector vec = new Vector();
			vec.addAll(p_Project.getTestObjectReferences());
			if (CMApplication.frame.getTreeWorkspaceView().getSelectedNode().isRoot()){//svonborries_03022006
				if (!PROJECTS_NAME_VALIDATED.contains(p_Project.getName()) && !PROJECTS_PATH_VALIDATED.contains(p_ProjectPath)){
					for (Iterator iter = vec.iterator(); iter.hasNext();) {
						TestObjectReference l_TestObjectReference = (TestObjectReference) iter.next();
					
						String l_testObjectPath = l_TestObjectReference.getFilePath();
						absoluteFilePath = new StringBuffer();
						absoluteFilePath.append(CMFileValidator.fixProjectFilePath(p_ProjectPath,p_Project.getName()));
						absoluteFilePath.append(l_testObjectPath);
					
					
						if (!CMFileValidator.validateFileExist(absoluteFilePath.toString())){
							p_Project.getTestObjectReferences().remove(l_TestObjectReference);
							if(CMFileValidator.DELETED_TESTOBJECTREFERENCES_COUNT>0){
								CMFileValidator.DELETED_TESTOBJECTREFERENCES = CMFileValidator.DELETED_TESTOBJECTREFERENCES + ", " + l_TestObjectReference.getName(); 
								CMFileValidator.DELETED_TESTOBJECTREFERENCES_COUNT += 1;
							}
							else{
								CMFileValidator.DELETED_TESTOBJECTREFERENCES = l_TestObjectReference.getName();
								CMFileValidator.DELETED_TESTOBJECTREFERENCES_COUNT += 1;
							}
						}
					}
					CMFileValidator.PROJECTS_NAME_VALIDATED.addElement(p_Project.getName());
					CMFileValidator.PROJECTS_PATH_VALIDATED.addElement(p_ProjectPath);
				}
			}
			
			//CMFileValidator.VALIDATOR_COUNT+=1;//svonborries_03022006
		} catch (Exception e) {
			Logger.getLogger(CMFileValidator.class).error(e);
			return p_Project;
		}
		
		return p_Project;
			
	}
	
			private static String fixProjectFilePath(String p_FilePath, String p_ProjectName){
				String l_filePath = p_FilePath;
				if (!l_filePath.equalsIgnoreCase("")){
					int l_ProjectCount = p_ProjectName.length();
					int l_StringCount = l_filePath.length() - 8;
					l_StringCount -= l_ProjectCount;
					String l_resultString = l_filePath.substring(0,l_StringCount);
					return l_resultString;
				}
				return null;
			}
			
	public static File copyToolVentorToResDir (File p_ToolVendorFile){
		
			String fileName = p_ToolVendorFile.getName();
			File outPutFile = new File(BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+
					BusinessRules.TOOLVENDOR+BusinessRules.URL_SEPARATOR+fileName);
			FileChannel in = null, out= null;
			
			try {
				in = new FileInputStream(p_ToolVendorFile).getChannel();
				out = new FileOutputStream(outPutFile).getChannel();
				in.transferTo( 0, in.size(), out);
				/*long size = in.size();
				MappedByteBuffer buffer = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
				
				out.write(buffer);*/
				
				 if (in != null) in.close();
		         if (out != null) out.close();
		         
		         return outPutFile;
			} catch (Exception e) {
				Logger.getLogger(CMFileValidator.class).error(e);
			}
			
		return null;
	}
	
	 public static String relativePathToolVendor(File p_file){
	    	String l_fileName = p_file.getName();
	    	StringBuffer relativeName = new StringBuffer();
	    	relativeName.append(BusinessRules.TOOLVENDOR);
	    	relativeName.append(BusinessRules.URL_SEPARATOR);
	    	relativeName.append(l_fileName);
	    	return relativeName.toString();
	    }
	     
}
		
