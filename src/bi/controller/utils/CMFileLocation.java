/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.utils;

import java.io.File;
import java.util.Arrays;

import bi.view.lang.CMMessages;

import model.BusinessRules;
/**
 * @author ccastedo
 */
public class CMFileLocation {
	
	public CMFileLocation(){}

	private static final String[] INVALID_FOLDER_LOCATIONS = {"reports","Result Comparison","testdata","testobjects","brules"};
	private static final String[] INVALID_FILE_COMPANIONS = {"TestData.ctd.xml","BRFile.txt","testdata","testobjects","brules"};
	private static final String[] INVALID_FILE_EXTENSIONS = {".cpr.xml",".cto.xml",".ctd.xml"};
    public String getFileExtension(String p_CompleteFileName) {
        int index = p_CompleteFileName.indexOf("."); //$NON-NLS-1$
        StringBuffer sBuffer = new StringBuffer();
        if (index > 0) {
            for (int i = index; i < p_CompleteFileName.length(); i++) {
                sBuffer.append(p_CompleteFileName.charAt(i));
            }
            return sBuffer.toString();
        }
        else {
            return p_CompleteFileName;
        }
    }

    public String getFolderName(String p_CompleteParent) {
    	int index = p_CompleteParent.indexOf("/");  
    	
    	if (index > 0) {
    		while (index!=-1){
    			StringBuffer sBuffer = new StringBuffer();
    			for (int i=index+1;i<p_CompleteParent.length();i++){    		
            		sBuffer.append(p_CompleteParent.charAt(i));            		
            	}
    			index = sBuffer.indexOf("/"); 
    			p_CompleteParent = sBuffer.toString();
    		}          
    	}
    	return p_CompleteParent;
    }
    
    public void validateProjectLocation(File p_DestinationPath) throws CMInvalidFileLocationException{
    	String path = p_DestinationPath.getAbsolutePath().replace("\\","/") + BusinessRules.URL_SEPARATOR + "temp";
    	File file = new File(path);
    	if( !file.mkdirs() ){
    		throw new CMInvalidFileLocationException(CMMessages.getString("CANNOT_WRITE_DIRECTORY"));
    	}
    	else{
    		file.delete();
    	}

    	int numberofFiles = 0;
    	String[] l_ListFilesandDirectories = p_DestinationPath.list(); 
    	if (l_ListFilesandDirectories != null){
    		numberofFiles = l_ListFilesandDirectories.length;
    	}
    	
    	String l_FolderName = getFolderName(p_DestinationPath.getAbsolutePath().toString().replace("\\", "/"));
    	for (Object invalidLocation : Arrays.asList(INVALID_FOLDER_LOCATIONS))
		if (l_FolderName.equalsIgnoreCase((String)invalidLocation))
			throw new CMInvalidFileLocationException(CMMessages.getString("CANNOT_CREATE_PROJECT_IN_A_DIRECTORY")
					+" \""+(String)invalidLocation+"\" ");
		
    	if (numberofFiles>=0)
    		for( int i=0;i<numberofFiles;i++) {    		
    			for (Object invalidExtension : Arrays.asList(INVALID_FILE_EXTENSIONS))
    				if (getFileExtension(l_ListFilesandDirectories[i]).equalsIgnoreCase((String)invalidExtension))
        				throw new CMInvalidFileLocationException(CMMessages.getString("CANNOT_CREATE_PROJECT_WITH_A_FILE_ENDED")+"\""+(String)invalidExtension+"\"" );
    			for (Object invalidCompanion : Arrays.asList(INVALID_FILE_COMPANIONS))
            		if (l_ListFilesandDirectories[i].equalsIgnoreCase((String)invalidCompanion))
            			throw new CMInvalidFileLocationException(CMMessages.getString("CANNOT_CREATE_PROJECT_WITH_A_FILE")+" \" "+(String)invalidCompanion+"\"" );		         
        	}
    	
    }
   
}
