

package  bi.controller;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.CMError;
import model.Structure;
import model.TestCase;
import model.edit.CMModelEditFactory;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

public class ErrorManager {
	public static final ErrorManager INSTANCE = new ErrorManager();
    public ErrorManager() {
      ids = new Vector(BusinessRules.MAX_NUMBER_OF_IDS);
      for( int i = 0; i < BusinessRules.MAX_NUMBER_OF_IDS; i++) {
        ids.addElement(new Boolean(false));
      }
    }

    public CMError createCMError(Structure p_structure) {
      CMError ce = new CMError();
      ce.setId(this.getNextId(p_structure));
      return ce;
    }

     public int getNextId(Structure p_structure) {
 		  int numOfCMErrorsInStructure = p_structure.getM_CMErrors().size();
          CMError effect = null;
          int existingId = 0;
          Boolean id = null;
					for( int i = 0; i < BusinessRules.MAX_NUMBER_OF_IDS; i++) {
						ids.setElementAt(new Boolean(false),i);
					}
					for( int i = 0; i < numOfCMErrorsInStructure; i++) {
						 effect = (CMError) p_structure.getM_CMErrors().elementAt(i);
						 existingId = effect.getId();
						 ids.setElementAt(new Boolean(true), existingId-1);
					}
          for( int i = 0; i < BusinessRules.MAX_NUMBER_OF_IDS; i++) {
					  id = (Boolean) ids.elementAt(i);
            if( id.booleanValue() == false) {
              return i+1;
            }
          }
          return -1;
     }


    private int generateId() {
      Boolean obj = new Boolean(false);
      for(int i = 0; i < BusinessRules.MAX_NUMBER_OF_IDS; i++) {
        obj = (Boolean) ids.elementAt(i);
        if( obj.booleanValue() == false) {
          ids.setElementAt(new Boolean(true), i);
          return i;
        }
      }
      return -1;
    }


 public void delete(CMError p_CMError, Structure p_Structure) {
   //delete from the Structure
   if( p_Structure.getM_CMErrors().contains(p_CMError)) {
   		p_Structure.getM_CMErrors().removeElement(p_CMError);
   }
   m_TestCaseManager.deleteCMErrorOfTestCases(p_CMError, p_Structure);
 }

 public TestCaseManager getM_TestCaseManager(){
         return m_TestCaseManager;
     }

 public void setM_TestCaseManager(TestCaseManager p_TestCaseManager){
         m_TestCaseManager = p_TestCaseManager;
     }


     public String generateNewName(String p_FileName, int p_index) {
    StringBuffer fileNameBuffer = new  StringBuffer(p_FileName);
    int index = p_FileName.indexOf(".");
    StringBuffer sBuffer = new StringBuffer();
    sBuffer.append(p_index);
    fileNameBuffer.insert(index,sBuffer.toString());
    return fileNameBuffer.toString();
  }



/*  void viewFile(String p_Viewer, String p_FilePath){
    StringBuffer completePath = new StringBuffer();
    completePath.append(p_Viewer);
    completePath.append(" ");
    completePath.append(p_FilePath);
    try {
			Process theProcess = Runtime.getRuntime().exec(completePath.toString());
    }
    catch(IOException exception) {
      exception.printStackTrace();
    }
  }
*/

    /**
     *  Removes the reference from a list of errors to a test case
     * @param errors
     * @param p_TestCase
     * @return
     */
    public UndoableEdit removeTestCaseIfAssigned(Vector errors,TestCase p_TestCase){
    	CMCompoundEdit ce = new CMCompoundEdit();
        int numOfErrors =errors.size();
        for(int i =0; i< numOfErrors;i++){
			CMError error =(CMError)errors.elementAt(i);
			Vector testCases = error.getM_TestCases();
			if(testCases.contains(p_TestCase)){
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestCaseModelEdit(error, p_TestCase));
				testCases.remove(p_TestCase);
            }
        }
		return ce;
    }

    //fcastro_20092004_begin
    public void removeAllAssignedTestCases(Vector errors){
        int numOfErrors = errors.size();
        for(int i =0; i< numOfErrors;i++){
            CMError error = (CMError)errors.elementAt(i);
            error.setM_TestCases(new Vector(0));
        }
    }
    //fcastro_20092004_end


  public  Vector getTestCasesWithTheError(CMError p_CMError, Structure p_Structure){
    int numOfTestCases = p_Structure.getTestCases().size();
    Vector testCasesWithTheError = new Vector(0);

    TestCase testCase = null;
    for( int i = 0; i < numOfTestCases; i++) {
      testCase = (TestCase) p_Structure.getTestCases().get(i);
      if( testCase.getM_CMErrors() != null) {
        if( testCase.getM_CMErrors().contains(p_CMError) ) {
          testCasesWithTheError.addElement(testCase);
        }
      }
    }
    return testCasesWithTheError;
  }


 private Vector ids = null;

 /**
  * @directed
  */

 /**
  * @directed
  */
 private TestCaseManager m_TestCaseManager;
/**
*@autor smoreno
 * @return
 */
public static CMError getSelectedCMError() {

	return CMApplication.frame.getCMErrorGridView().getSelectedCMError();
}
}
