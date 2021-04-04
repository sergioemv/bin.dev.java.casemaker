package bi.controller.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import model.ProjectReference;
import model.TestObject;
import model.TestObjectReference;
import bi.controller.WorkspaceManager;

/**
 * this singleton class tries to read the CMXMLFiles as secuencial text to improve the performance
 * @author smoreno
 *
 */
public class CMRawFileObjectReader {

	private static  CMRawFileObjectReader INSTANCE;
	
	public static CMRawFileObjectReader getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new CMRawFileObjectReader();
		}
		return INSTANCE;
	}
	
	public TestObject readTestObject(TestObjectReference p_TestObjectReference, ProjectReference p_ProjectReference)
	{
		TestObject to = WorkspaceManager.getInstance().createTestObject();
		 String accessState = (String)readAccessStateFieldOfTestObject(p_ProjectReference,
				 p_TestObjectReference);
           String user = (String)readUserFieldOfTestObject(p_ProjectReference,
        		   p_TestObjectReference);
             if (accessState != null && user != null) {
             	//if the file doesnt f
                 if (accessState.equals("NOT FOUND") && user.equals("NOT FOUND")) {
                	 to.setAccessState(p_TestObjectReference.getM_AccessState());
                	 to.setUser(p_TestObjectReference.getM_User());
                	 to.setName(p_TestObjectReference.getName());
                 }
                 else {
                	 to.setAccessState(accessState);
                	 to.setUser(user);
                     to.setName(p_TestObjectReference.getName());
                 }
             }
             else {
            	 to = null;
             }
         return to;
	}
	
	 private Object readAccessStateFieldOfTestObject(ProjectReference p_ProjectReference,TestObjectReference p_TestObjectReference){
	      if( p_ProjectReference == null || p_TestObjectReference == null) {
	        return null;
	      }
	      String fileName = WorkspaceManager.getInstance().buildAbsoluteTestObjectFilePath(p_ProjectReference, p_TestObjectReference);
	      return readAccessStateFieldOfTestObject(fileName);
	    }
	 private Object readAccessStateFieldOfTestObject(String p_FileName) {
			try {
					BufferedReader in = new BufferedReader(new FileReader(p_FileName));
					String str = null;
	                String userValue = null;
					while ((str = in.readLine()) != null) {
				      if( str.indexOf("m_AccessState") >= 0) {
	                    userValue = readAccessValue(str);
	                    break;
	                  }
					}
					in.close();
	                if( userValue == null) {
	                  userValue = "NOT FOUND";
	                }
	                return userValue;
				} catch (IOException e) {
	              Logger.getLogger(this.getClass()).error(e);
	              return "NOT FOUND";
				}
	    }
	 private String readAccessValue(String p_String) {
	      //<string field='m_Access' id='i83' value='grueda'/>
		  String[] values1 = p_String.split("value='");
	      String value = "NOT FOUND";
	      if( values1 == null) {
	        return value;
	      }
	      if( values1.length > 1) {
	        String[] values2 = values1[1].split("'/>");
	        if( values2 == null) {
	          return value;
	        }
	        if( values2.length > 0) {
	          value = values2[0];
	        }
	      }
	      return value;
	     }
	 
	 private String readUserValue(String p_String) {
	      //<string field='m_User' id='i83' value='grueda'/>
		  String[] values1 = p_String.split("value='");
	      String value = "NOT FOUND";
	      if( values1 == null){
	        return value;
	      }
	      if( values1.length > 1) {
	        String[] values2 = values1[1].split("'/>");
	        if( values2.length > 0) {
	          value = values2[0];
	        }
	      }
	      return value;
	     }
	 
	 private Object readUserFieldOfTestObject(String p_FileName) {
			try {
					BufferedReader in = new BufferedReader(new FileReader(p_FileName));
					String str = null;
	                String userValue = null;
					while ((str = in.readLine()) != null) {
				      if( str.indexOf("m_User") >= 0) {
	                    userValue = readUserValue(str);
	                    break;
	                  }
					}
					in.close();
	                if( userValue == null) {
	                  userValue = "NOT FOUND";
	                }
	                return userValue;
				} catch (IOException e) {
	              Logger.getLogger(this.getClass()).error(e);
	              return null;
				}
	    }
	    public Object readUserFieldOfTestObject(ProjectReference p_ProjectReference,TestObjectReference p_TestObjectReference){
	        if( p_ProjectReference == null || p_TestObjectReference == null) {
	          return null;
	        }
	        String fileName = WorkspaceManager.getInstance().buildAbsoluteTestObjectFilePath(p_ProjectReference, p_TestObjectReference);
	        return readUserFieldOfTestObject(fileName);
	      }
	    
}
