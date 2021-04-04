/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdata;


import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import model.Structure;
import model.TestCase;
import model.TestData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogAssignTestCaseToTestData;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAssignTestCaseToTestDataAction extends AbstractAction implements
		Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataView m_panelTestDataView;


	public CMAssignTestCaseToTestDataAction(){
		super(CMMessages.getString("TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATA_ASSIGN_TESTCASE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString
	    		("ASSIGN_TEST_CASE_TO_THE_CURRENT_TEST_DATA_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();
		CMCompoundEdit ce = new CMCompoundEdit();

		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_panelTestDataView.update(CMIndexTDStructureUpdate.getInstance().getindexTestData(),0);
			}

		});
		ce.addEdit(new CMComponentAwareEdit());


        try {
            Structure structure = m_gridTDStructure.getTDStructure().getM_TestObject().getStructure();
            Vector testCaseInTestData = new Vector();
            boolean swEnable = false;
            for (int i = 0; i < structure.getLnkTestCases().size(); i++) {
                TestCase testCase = ((TestCase)m_gridTDStructure.getTDStructure().getM_TestObject().
                		getStructure().getLnkTestCases().elementAt(i));
                TestData aux = (TestData)m_gridTDStructure.getTDStructure().getTestDataCombination().
                		getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
                if (testCase.getName().equalsIgnoreCase(aux.getM_TestCaseinTestData())) {
                    testCaseInTestData.addElement(testCase);
                    swEnable = true;
                }
            }
            CMDialogAssignTestCaseToTestData dlg =
                new CMDialogAssignTestCaseToTestData(this.m_frame, structure, testCaseInTestData);
            dlg.setIspanelTestData(true);
            if (swEnable)
                dlg.disableButtonDelete();
            dlg.setVisible(true);
            if (dlg.isEventJButtonOKClicked()) {
                TestData testData = (TestData)m_gridTDStructure.getTDStructure().getTestDataCombination().
                	getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
                if (testCaseInTestData.size() > 0) {
                    String oldTestCase=testData.getM_TestCaseinTestData();
                    String newTestCase=new String(((TestCase)testCaseInTestData.elementAt(0)).getName())+
                    new String(((TestCase)testCaseInTestData.elementAt(0)).getStateName());
                    String tcInTD = new String(((TestCase)testCaseInTestData.elementAt(0)).
                    		getName())+new String(((TestCase)testCaseInTestData.elementAt(0)).getStateName());
                    ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestCaseInTestDataModelEdit(testData,tcInTD));
                    testData.setM_TestCaseinTestData(tcInTD);
                    int riskLevel = ((TestCase)testCaseInTestData.elementAt(0)).getRiskLevel();
                    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelInTestDataModelEdit(testData,riskLevel));
                    testData.setM_RiskLevel(riskLevel);

    	            ce.addDelegateEdit(new CMDelegate(){

    	    			public void execute() {
    	    				m_panelTestDataView.update(CMIndexTDStructureUpdate.getInstance().getindexTestData(),0);
    	    			}

    	    		});

					if(!oldTestCase.equals(newTestCase)){
						if(ce.hasEdits())
				        	CMUndoMediator.getInstance().doEdit(ce);
					}
						/*this.m_frame.getM_CMUndoMediator().assignTestCaseToTestDataEdit
						(m_panelTestDataView,CMIndexTDStructureUpdate.getInstance().getindexTestData(),
								newTestCase,oldTestCase,this.m_frame.getm_TabbedPaneView(),
								CMMessages.getString("TESTDATA_TESTDATA"));*/
                }
                m_panelTestDataView.update(CMIndexTDStructureUpdate.getInstance().getindexTestData(),0);
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this.m_frame, CMMessages.getString("TESTDATA_ERROR_EXIST_TESTDATA"),
            		CMMessages.getString("TESTDATA_TITLE_ERROR"),
                JOptionPane.ERROR_MESSAGE);
        }
	}
}
