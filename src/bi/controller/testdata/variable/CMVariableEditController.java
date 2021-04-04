/**
 * 13/12/2006
 * svonborries
 */
package bi.controller.testdata.variable;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.BusinessRules;
import model.CMLinkElement;
import model.ICMValue;
import model.Project2;
import model.StructureTestData;
import model.TestData;
import model.Variable;
import model.Variables;
import bi.controller.ProjectManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.utils.testdata.CMPanelVariable;

/**
 * @author svonborries
 *
 */
public class CMVariableEditController {
	
	private CMPanelVariable panelVariable = null;
	private Variables m_Variables = null;
	//private Variable m_Variable = null;

	/**
	 * @return the panelVariable
	 * 13/12/2006
	 * svonborries
	 */
	public CMPanelVariable getPanelVariable() {
		if(panelVariable == null){
			panelVariable = new CMPanelVariable();
			panelVariable.getJListVariables().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListVariablesValueChange();
				}
			});
			fillJListVariables();
		}
		
		return panelVariable;
	}

	private void fillJListVariables() {
        m_Variables=CMApplication.frame.getGridTDStructure().getTDStructure().getM_Variables();
        //if(m_Variables.getVariables().size()!=0)
        //{
        	try {
        		panelVariable.getJListVariables().setListData(getVariablesNames(m_Variables));
            	panelVariable.getJListVariables().setSelectedIndex(0);
				//panelVariable.getJTextFieldValue().setText(((Variable)m_Variables.getVariables().firstElement()).getM_Value().getValue().toString());
            	panelVariable.getJTextFieldValue().setText(((Variable)m_Variables.getVariables().firstElement()).getFormattedValue());
				panelVariable.getJTextFieldType().setText(((Variable)m_Variables.getVariables().firstElement()).getM_Type());
	            panelVariable.getJTextFieldFormat().setText(((Variable)m_Variables.getVariables().firstElement()).getFormat());
	        	panelVariable.getJTextAreaDescription().setText(((Variable)m_Variables.getVariables().firstElement()).getM_Description());
			} catch (Exception e) {

			}
            
        //}
	}
	
    private Vector getVariablesNames(Variables p_Variables){
    	Vector<String> result= new Vector<String>();
    	for(int i =0; i< p_Variables.getVariables().size();i++){
    		result.addElement(((Variable)p_Variables.getVariables().elementAt(i)).getM_Name());
    	}
    	result.addAll(Arrays.asList(BusinessRules.TESTDATA_IMPICIT_VARIABLES));
    	return result;
    }
    

	private void jListVariablesValueChange() {
		int selectedIndex = getPanelVariable().getJListVariables().getSelectedIndex();
        if(selectedIndex != -1)
    	{
        	String result = "";
			try {
        		//m_Variable = (Variable)m_Variables.getVariables().elementAt(selectedIndex);
//        		getPanelVariable().getJTextFieldValue().setText(((Variable)m_Variables.getVariables().elementAt(selectedIndex))
//        				.getM_Value().getValue().toString());
        		getPanelVariable().getJTextFieldValue().setText(((Variable)m_Variables.getVariables().elementAt(selectedIndex))
				.getFormattedValue());
            	getPanelVariable().getJTextFieldType().setText(((Variable)m_Variables.getVariables().elementAt(selectedIndex))
            			.getM_Type());
            	getPanelVariable().getJTextFieldFormat().setText(((Variable)m_Variables.getVariables().elementAt(selectedIndex))
            			.getFormat());
    			getPanelVariable().getJTextAreaDescription().setText(((Variable)m_Variables.getVariables().elementAt(selectedIndex))
    					.getM_Description());
			} catch (Exception e) {
				String implicitVariable = (String) getPanelVariable().getJListVariables().getSelectedValue();
				if (Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES, implicitVariable) >= 0) {
					int indexImplicitVariables = Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES,
	                        implicitVariable);
					Project2 actualProject = ProjectManager.getSelectedProject();
                    CMGridTDStructure cmgrid = CMApplication.frame.getGridTDStructure();
                    if(CMApplication.frame.isIsPanelTestDataSelected())
                    	cmgrid = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
					if (indexImplicitVariables == 0) {
                        result  = actualProject.getName();
                    }
                    else {
                        if (indexImplicitVariables == 1) {
							result = ((StructureTestData)cmgrid.getTDStructure().getM_StructureTestData().elementAt
									(CMIndexTDStructureUpdate.getInstance().getIndex())).getName();
                        }
                        else {
                            if (indexImplicitVariables == 2) {
                                if (CMApplication.frame.isIsPanelTestDataSelected()) {
                                    result = ((TestData)cmgrid.getTDStructure().getTestDataCombination().getM_TestDatas()
                                    		.elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData())).getName();;
                                }
                                else {
                                    JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("FORMULAS_ERROR_NO_ALLOWED_VARIABLE"),
                                        CMMessages.getString("TESTDATA_TITLE_MENSSAGE_ERROR"), JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else {
                                if (indexImplicitVariables == 3) {
                                    result = CMApplication.frame.getGridTDStructure().getTDStructure().getM_TestObject().getName();
                                }
                                else {
                                    result = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference().getM_Workspace().getName();
                                }
                            }
                        }
                    }
                }
				getPanelVariable().getJTextFieldValue().setText(result);
				getPanelVariable().getJTextFieldFormat().setText("");
				getPanelVariable().getJTextFieldType().setText("");
				getPanelVariable().getJTextAreaDescription().setText("");
			}
    	}
	}

	public ICMValue getLinkElement(){
		return new CMLinkElement("$"+(String)getPanelVariable().getJListVariables().getSelectedValue());
	}
}
