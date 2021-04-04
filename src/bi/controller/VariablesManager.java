package bi.controller;

import java.util.Arrays;
import java.util.Vector;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.utils.CMFormatFactory;
import model.BusinessRules;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TestDataFormat;
import model.Variable;
import model.Variables;

public class VariablesManager {

	public static String returnImplicitExplicitVariable(String p_Variable,TDStructure p_structure){
		
		if(p_Variable.startsWith("$")){
			String l_variableName = p_Variable.substring(p_Variable.indexOf("$") + 1, p_Variable.length());
			if((getVariablesNames(p_structure.getM_Variables()).contains(l_variableName))){
				int index = getVariablesNames(p_structure.getM_Variables()).indexOf(l_variableName);
				String value = "";
				try {
					//value = ((Variable)p_structure.getM_Variables().getVariables().elementAt(index)).getM_Value().getValue().toString();
					value = ((Variable)p_structure.getM_Variables().getVariables().elementAt(index)).getFormattedValue();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TestDataFormat formatter=((Variable)p_structure.getM_Variables().getVariables().elementAt(index)).getFormatter();
				 l_variableName = convertVariableToStandarFormat(value,formatter);
			}
			else{
				String l_variableUpper = l_variableName.toUpperCase();
				if (Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES, l_variableUpper) >= 0) {
                    int indexImplicitVariables = Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES,
                    		l_variableUpper);
                    if (indexImplicitVariables == 0) {
                    	l_variableName = CMApplication.frame.getTreeWorkspaceView().getCurrentProject().getName();
                    }
                    else {
                        if (indexImplicitVariables == 1) {
                        	l_variableName = ((StructureTestData)p_structure.getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex())).getName();
                        }
                        else {
                            if (indexImplicitVariables == 2) {
                                	TestData testData = (TestData) p_structure.getTestDataCombination().getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
                                	l_variableName = testData.getName();
                                
                            }
                            else {
                                if (indexImplicitVariables == 3) {
                                	l_variableName = p_structure.getM_TestObject().getName();
                                }
                                else {
                                	l_variableName = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference().getM_Workspace().getName();
                                }
                            }
                        }
                    }
                }else{return "";}
			}
			return l_variableName;
		}
			
		return "";
		
		
	}
	
	@SuppressWarnings("unchecked")
	public static Vector getVariablesNames(Variables p_Variables){
    	Vector result= new Vector();
    	for(int i =0; i< p_Variables.getVariables().size();i++){
    		result.addElement(((Variable)p_Variables.getVariables().elementAt(i)).getM_Name());
    	}
    	return result;
    }
	
	public static String convertVariableToStandarFormat(String p_VariableValue, TestDataFormat p_FormatVariable){
    	TestDataFormat newDefaultFormat=new TestDataFormat();
    	newDefaultFormat.setRealFormat(p_FormatVariable.getRealFormat());
    	newDefaultFormat.setOriginalFormatter(p_FormatVariable.getOriginalFormatter());
    	return CMFormatFactory.applyAnyFormat(newDefaultFormat,p_VariableValue,p_FormatVariable);
    }
	
	/**
	 * @author svonborries
	 * @since 18/07/2007
	 * @param p_varName, this is a String Object that can be accepted withe prefix "$" or without it.
	 * @return the Variable if it exist, and return null if the Variable doesn't exist
	 */
	public static Variable  getVariable(String p_varName){
		if(p_varName.startsWith("$")){
			p_varName = p_varName.substring(p_varName.indexOf("$") + 1, p_varName.length());
		}
		TDStructure l_tdStructure = CMApplication.frame.getGridTDStructure().getTDStructure();
		if((getVariablesNames(l_tdStructure.getM_Variables()).contains(p_varName))){
			int index = getVariablesNames(l_tdStructure.getM_Variables()).indexOf(p_varName);
			Variable value;
			try {
				value = (Variable)l_tdStructure.getM_Variables().getVariables().elementAt(index);
				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
