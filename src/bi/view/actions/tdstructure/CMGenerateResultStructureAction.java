/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.BusinessRules;
import model.CMDefaultValue;
import model.Effect;
import model.ExpectedResult;
import model.ITypeData;
import model.ResultStructureTestData;
import model.Structure;
import model.StructureTestData;
import model.TDStructure;
import model.TestCase;
import model.TestData;
import model.TestDataFormat;
import model.TypeDataGlobal;
import model.TypeDataLocal;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTDStructureView;
import bi.view.testdataviews.CMPanelTestDataSetView;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMGenerateResultStructureAction extends AbstractAction implements
		Action {

	private CMFrameView m_frame;
	private Structure m_structure;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTDStructureView m_panelTDStructureView;
	private CMPanelTestDataView m_panelTestDataView;
	private CMPanelTestDataSetView m_panelTestDataSetView;
	public static boolean IS_CALLED_FROM_OUTSIDE = false;
	private CMCompoundEdit m_ce;

	public CMGenerateResultStructureAction(){
		super(CMMessages.getString("TESTDATA_GENERATE_RESULT_STRUCTURE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_GENERATE_RESULT_STRUCTURE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("TESTDATA_GENERATE_RESULT_STRUCTURE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_6, Event.CTRL_MASK));


	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_structure = StructureManager.getSelectedStructure();
		this.m_gridTDStructure = this.m_frame.getGridTDStructure();
		this.m_panelTDStructureView = m_frame.getPanelTDStructureView();
		this.m_panelTestDataSetView = m_frame.getPanelTestDataSetView();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();

		m_ce = new CMCompoundEdit();
		TestCase l_testCase;
		Vector expectedResults = new Vector();
		try {
			m_ce.addDelegateEdit(new CMDelegate(){

				public void execute() {
					    //CMIndexTDStructureUpdate.getInstance().setindex(0);
		    	        //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
					 	//CMIndexTDStructureUpdate.getInstance().setindex(CMIndexTDStructureUpdate.getInstance().getIndex()-1);
					    m_panelTDStructureView.update();
		    	        //m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
		    	        m_panelTestDataView.update();
		    	        m_panelTestDataSetView.update();
		    	        //CMIndexTDStructureUpdate.getInstance().setindex(m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1);
		                //m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
		                m_frame.getPanelTDStructureView().update();
		                m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
				}});
			m_ce.addEdit(new CMComponentAwareEdit());
	        //if exist any test cases, continue with the generation
        	if(m_structure.getLnkTestCases().firstElement()!= null){
            	//This Loop take the Effects, and the Expected Results, and put them into a Vector
        		//withis loop we generate the Global Structure
            	for (int i = 0; i < m_structure.getLnkTestCases().size(); i++) {
            		l_testCase = (TestCase) m_structure.getLnkTestCases().elementAt(i);
            		for (Iterator iter = l_testCase.getRelatedEffects().iterator(); iter.hasNext();) {
            			Effect l_effect = (Effect) iter.next();
            			for (Iterator iterator = l_effect.getLnkExpectedResults().iterator(); iterator.hasNext();) {
    						ExpectedResult expResult = (ExpectedResult) iterator.next();
    						if(indexofExpectedResult(expectedResults,expResult) == -1)
    							expectedResults.add(expResult);
    					}
    				}
            	}
            	if(expectedResults.size() != 0){
            	//create the global expected results
            	final TDStructure tdStructure = m_frame.getTreeWorkspaceView().getSelectedTDStructure();
            	//tdStructure.deleteIds();
                //tdStructure.setM_StructureTestData(new Vector());
               // tdStructure.setTestDataCombination(new TestDataCombinations());
                //tdStructure.setM_TestDataSet(new Vector());
            	//tdStructure.setM_TestCaseInTDStructure(m_structure.getLnkTestCases());
            	//TODO
                m_gridTDStructure.setTDStructure(tdStructure);
                ResultStructureTestData createStructure= new ResultStructureTestData();
                m_ce.addEdit(m_gridTDStructure.setNameDescription(createStructure));
                m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInStructureTestData
                		(createStructure,BusinessRules.TYPETDSTRUCTURE_RESULT));
                createStructure.setType(BusinessRules.TYPETDSTRUCTURE_RESULT);
                m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalIndexInStructureTestData
                		(createStructure,m_gridTDStructure.getTDStructure().getM_StructureTestData().size()));
                createStructure.setGlobalIndex(m_gridTDStructure.getTDStructure().getM_StructureTestData().size());

                for (int i = 0; i < expectedResults.size(); i++) {
                	ExpectedResult expResult = (ExpectedResult)expectedResults.elementAt(i);
                    ITypeData newTypeData = new TypeDataGlobal();
                    m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit
                    		(newTypeData,BusinessRules.FORMULAS_FORMAT_STRING));
                    newTypeData.setFormat(BusinessRules.FORMULAS_FORMAT_STRING);
                    TestDataFormat formatter = new TestDataFormat();
                    m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit
                    		(newTypeData,formatter));
                    newTypeData.setFormatter(formatter);
                    String name = expResult.getName();
                    m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameInTypeDataModelEdit(newTypeData,name));
                    newTypeData.setName(name);
                    m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFieldInTypeDataModelEdit(newTypeData,name));
                    newTypeData.setField(name);
                    m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTypeDataToStructureTestDataModelEdit
                    		(createStructure,newTypeData));
                    createStructure.getTypeData().addElement(newTypeData);
                    newTypeData.setStructureTestData(createStructure);
				}
                m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddStructureTestDataToTDStructureModelEdit(m_gridTDStructure.getTDStructure(),createStructure));
                m_gridTDStructure.getTDStructure().getM_StructureTestData().addElement(createStructure);
                m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStrucTestDataToResultStructTestData
                		(createStructure,((CMGenerateTestDataCombinationsFromAllTestCasesAction)
                				CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES.getAction()).
                				getCreateStructure()));
                createStructure.setStructureTestDataLinked(((CMGenerateTestDataCombinationsFromAllTestCasesAction)
                		CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES.getAction()).getCreateStructure());


            	//generate the testdata for the result structure
                if(createStructure.getStructureTestDataLinked() == null)
                {
                for (int i = 0; i < m_structure.getLnkTestCases().size(); i++) {
                	//vector that has the results of the expected results for each test case
                	Vector listOfExpectedResul = (Vector)getExpectedResultsFromTestCase((TestCase)m_structure.getLnkTestCases().elementAt(i),createStructure);
                	if(canGenerateStructure(listOfExpectedResul)){
                	TDStructure newTDStructure = new TDStructure();
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestdataCOmbinationToTDStructureModelEdit
                			(newTDStructure,m_gridTDStructure.getTDStructure().getTestDataCombination()));
                	newTDStructure.setTestDataCombination(m_gridTDStructure.getTDStructure().getTestDataCombination());
                	TestData newTestData = new TestData(newTDStructure);
                	String testDataName = newTestData.generateName(m_gridTDStructure.getTDStructure(),m_ce);
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameTestDataModelEdit
                			(newTestData,testDataName));
                	newTestData.setName(testDataName);
                	String testdataDescrip = newTestData.generateDescription();
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionTestDataModelEdit
                			(newTestData,testdataDescrip));
                	newTestData.setDescription(testdataDescrip);
                	TestCase testCase = (TestCase) m_structure.getLnkTestCases().elementAt(i);
                	String testCaseInTestData = new String(testCase.getName()) + new String(testCase.getStateName());
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestCaseInTestDataModelEdit
                			(newTestData,testCaseInTestData));
                	newTestData.setM_TestCaseinTestData(testCaseInTestData);
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelInTestDataModelEdit
                			(newTestData,testCase.getRiskLevel()));
                	newTestData.setM_RiskLevel(testCase.getRiskLevel());
                	StructureTestData s_TestData = new StructureTestData();
                	m_ce.addEdit(m_gridTDStructure.setNameDescription2(s_TestData));
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInStructureTestData
                			(s_TestData,BusinessRules.TYPETDSTRUCTURE_RESULT));
                	s_TestData.setType(BusinessRules.TYPETDSTRUCTURE_RESULT);
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalIndexInStructureTestData
                			(s_TestData,m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1));
                	s_TestData.setGlobalIndex(m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1);
                	//int index = m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size();
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddStructureTestDataToTDStructureModelEdit
                			(newTestData.getM_TDStructure(),s_TestData));
                	newTestData.getM_TDStructure().getM_StructureTestData().addElement(s_TestData);
                	StructureTestData stdPopulated=null;
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestDataToTDStructureModelEdit
                			(m_gridTDStructure.getTDStructure(),newTestData));
                	m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().add(newTestData);//insert it in tab TestData
                	for (int j = 0; j < expectedResults.size(); j++) {
						ExpectedResult expResult = (ExpectedResult)listOfExpectedResul.elementAt(j);
						ITypeData typeData = new TypeDataLocal();
						stdPopulated = s_TestData;
						CMDefaultValue defaultValue = new CMDefaultValue(expResult.getValue());
						m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit
								(typeData,defaultValue));
						typeData.setValue(defaultValue);
						//typeData.setStringValue(expResult.getValue());//MODIFY TO ADAPT THE RESULT THAT WE WANT IN THE VALUE
						this.m_gridTDStructure.setGlobalReferenceStructure(s_TestData.getGlobalIndex(), s_TestData.getTypeData().size(),m_ce);//OJO CON ESTE PUEDE ESTAR MAL
						m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typeData,"G"));
						typeData.setGlobal("G");
						m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTypeDataToStructureTestDataModelEdit
	                    		(s_TestData,typeData));
						s_TestData.getTypeData().addElement(typeData);
						typeData.setStructureTestData(s_TestData);
					}
                	if(stdPopulated != null){
                		for(int l=0; l < stdPopulated.getTypeData().size();l++){
                			ITypeData TypeDataRefered= (ITypeData) createStructure.getTypeData().elementAt(l);
                			ITypeData toRefer=(ITypeData) stdPopulated.getTypeData().elementAt(l);
                			m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeReferenceTypeDataInTypeDataModelEdit
                					(toRefer,TypeDataRefered));
                			((TypeDataLocal)toRefer).setM_ReferenceTypeData(TypeDataRefered);
                		}
                	}
                }
				}
                }
                //CODE TO GENERATE STRUCTURE RESULT, WHEN U GENERATE FROM ALL TEST CASES
                else{
                	StructureTestData structureGlobal = createStructure.getStructureTestDataLinked();
                	int globalIndex = structureGlobal.getGlobalIndex();
                	for (int i = 0; i < m_structure.getLnkTestCases().size(); i++) {
                		String testCaseName = ((TestCase)m_structure.getLnkTestCases().elementAt(i)).getName();
                		Vector listOfExpectedResul = (Vector)getExpectedResultsFromTestCase((TestCase)m_structure.getLnkTestCases().elementAt(i),createStructure);
                		if(canGenerateStructure(listOfExpectedResul)){
                		ResultStructureTestData s_TestData = new ResultStructureTestData();
                    	m_ce.addEdit(m_gridTDStructure.setNameDescription2(s_TestData));
                    	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInStructureTestData
                    			(s_TestData,BusinessRules.TYPETDSTRUCTURE_RESULT));
                    	s_TestData.setType(BusinessRules.TYPETDSTRUCTURE_RESULT);
                    	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalIndexInStructureTestData
                    			(s_TestData,m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1));
                    	s_TestData.setGlobalIndex(m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1);
                    	for (int j = 0; j < expectedResults.size(); j++) {
    						ExpectedResult expResult = (ExpectedResult)listOfExpectedResul.elementAt(j);
    						ITypeData typeData = new TypeDataLocal();
    						CMDefaultValue defaultValue = new CMDefaultValue(expResult.getValue());
    						m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit
    								(typeData,defaultValue));
    						typeData.setValue(defaultValue);
    						//typeData.setStringValue(expResult.getValue());//MODIFY TO ADAPT THE RESULT THAT WE WANT IN THE VALUE
    						this.m_gridTDStructure.setGlobalReferenceStructure(s_TestData.getGlobalIndex(), s_TestData.getTypeData().size(),m_ce);//OJO CON ESTE PUEDE ESTAR MAL
    						m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typeData,"G"));
    						typeData.setGlobal("G");
    						m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTypeDataToStructureTestDataModelEdit
    	                    		(s_TestData,typeData));
    						s_TestData.getTypeData().addElement(typeData);
    						typeData.setStructureTestData(s_TestData);
    					}
                    	if(s_TestData != null){
                    		for(int l=0; l < s_TestData.getTypeData().size();l++){
                    			ITypeData TypeDataRefered= (ITypeData) createStructure.getTypeData().elementAt(l);
                    			ITypeData toRefer=(ITypeData) s_TestData.getTypeData().elementAt(l);
                    			m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeReferenceTypeDataInTypeDataModelEdit
                    					(toRefer,TypeDataRefered));
                    			((TypeDataLocal)toRefer).setM_ReferenceTypeData(TypeDataRefered);
                    		}
                    	}
                		for (int n = 0; n < m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size(); n++) {
                			TestData testData = (TestData) m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().elementAt(n);
                			String testCaseNameTD = cutLastChartInString(testData.getM_TestCaseinTestData());
                			int globalIndex2 = ((StructureTestData)testData.getM_TDStructure().getM_StructureTestData().firstElement()).getGlobalIndex();
                			if(globalIndex == globalIndex2){
                				if(testCaseNameTD.equalsIgnoreCase(testCaseName)){
                					StructureTestData rstdClone = s_TestData.clones();//svonborries_05062006
                					m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddStructureTestDataToTDStructureModelEdit
                							(testData.getM_TDStructure(),rstdClone));
                                	testData.getM_TDStructure().getM_StructureTestData().addElement(rstdClone);
                				}
                			}
                		}
                	}
                	}
                }
                m_ce.addEdit(CMViewEditFactory.INSTANCE.createAddTDCombinationToPanelTDViewViewEdit(m_panelTestDataView,m_gridTDStructure.getTDStructure().getTestDataCombination()/*.cloneTestDataCombinations()*/));
                m_panelTestDataView.setM_TestDataCombinations(m_gridTDStructure.getTDStructure().getTestDataCombination());
                //m_ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1));
                CMIndexTDStructureUpdate.getInstance().setindex(m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1);
                m_ce.addDelegateEdit(new CMDelegate(){
                	//redo
					public void execute() {
						    //CMIndexTDStructureUpdate.getInstance().setindex(0);
			    	        //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
						    m_panelTDStructureView.update();
			    	        //m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
			    	        m_panelTestDataView.update();
			    	        m_panelTestDataSetView.update();
			    	        //CMIndexTDStructureUpdate.getInstance().setindex(CMIndexTDStructureUpdate.getInstance().getIndex()+1);
			    	        //CMIndexTDStructureUpdate.getInstance().setindex(m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1);
			                m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			                m_frame.getPanelTDStructureView().update();
					}});
    	        //CMIndexTDStructureUpdate.getInstance().setindex(0);
    	        //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
                //	DEBAGEA TODINGO, TENES QUE REVISAR, TODO, PARA VER LO DE LOS INDICES, Y QUE OBJETO TIENE QUE COSA
                //TestDataCombinations cloneTDC = m_panelTestDataView.getM_TestDataCombinations().cloneTestDataCombinations();

    	        m_panelTDStructureView.update();
    	        m_panelTestDataView.update();
    	        m_panelTestDataSetView.update();

                m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
                m_frame.getPanelTDStructureView().update();

                CMUndoMediator.getInstance().doEdit(m_ce);

            	}
            	else{
            		if(IS_CALLED_FROM_OUTSIDE == false)
            			JOptionPane.showMessageDialog(m_frame, CMMessages.getString("TESTDATA_GENERATE_RESULT_STRUCTURE_WITHOUT_EXPECTEDRESULT"),
                            CMMessages.getString("TESTDATA_GENERATE_RESULT_STRUCTURE"), JOptionPane.OK_OPTION);
            	}
            }

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(m_frame, CMMessages.getString("TESTDATA_GENERATE_RESULT_STRUCTURE_DIALOG"),
                    CMMessages.getString("TESTDATA_GENERATE_RESULT_STRUCTURE"), JOptionPane.OK_OPTION);
		}


	}

	/**
	 * @param listOfExpectedResul
	 * @return
	 */
	private boolean canGenerateStructure(Vector listOfExpectedResul) {
		for (int i = 0; i < listOfExpectedResul.size(); i++) {
			ExpectedResult expResul = (ExpectedResult) listOfExpectedResul.elementAt(i);
			if(!expResul.getValue().equalsIgnoreCase(""))
				return true;
		}
		return false;
	}

	/**
	 * @param testCaseinTestData
	 * @return
	 */
	private String cutLastChartInString(String testCaseinTestData) {
		String subSTR = testCaseinTestData.substring(0,testCaseinTestData.length()-1);
		return subSTR.toString();
	}

	/**
	 * @param p_testCase
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Vector getExpectedResultsFromTestCase(TestCase p_testCase, StructureTestData createStructure) {
		Vector listOfExpResul = new Vector();
		for (int i = 0; i < createStructure.getTypeData().size(); i++) {
			ITypeData typeData = (ITypeData) createStructure.getTypeData().elementAt(i);
			ExpectedResult expResul = new ExpectedResult();
			expResul.setName(typeData.getName());
			expResul.setValue(""); //$NON-NLS-1$
			listOfExpResul.add(expResul);
		}

		for (Iterator iter = p_testCase.getRelatedEffects().iterator(); iter.hasNext();) {
			Effect effect = (Effect) iter.next();

			for (Iterator iterator = effect.getLnkExpectedResults().iterator(); iterator
					.hasNext();) {
				ExpectedResult expResult1 = (ExpectedResult) iterator.next();
				if(indexofExpectedResult(listOfExpResul,expResult1) == -1){
					listOfExpResul.add(expResult1);
				}
				else{
					int index = indexofExpectedResult(listOfExpResul,expResult1);
					ExpectedResult expResult2 = (ExpectedResult)listOfExpResul.elementAt(index);
					listOfExpResul.setElementAt(combineExpectedResult(expResult2,expResult1,p_testCase.getRelatedEffects()),index);
				}
			}
		}
		return listOfExpResul;
	}

	/**
	 * @param expResult1
	 * @param expResult2
	 * @param name
	 * @return
	 */
	public ExpectedResult combineExpectedResult(ExpectedResult expResult1, ExpectedResult expResult2, Collection<Effect> effects) {
		ExpectedResult expResul = null;

		boolean allNumeric=true;

		for (Effect effect : effects)
			if (effect.getExpectedResultbyName(expResult1.getName())!=null)
			if (effect.getExpectedResultbyName(expResult1.getName()).getNumberValue()==null)
				allNumeric = false;
		if(expResult1.getNumberValue()!=null&& expResult2.getNumberValue()!=null&&(allNumeric)){
			Number num1 = expResult1.getNumberValue();
			Number num2 = expResult2.getNumberValue();
			Number result = num1.doubleValue() + num2.doubleValue();
			expResul = new ExpectedResult();
			expResult1.setFormatPattern(null);
			expResul.setValue(expResult1.getDecimalFormat().format(result));

		}
		else{
			String result = expResult1.getValue().concat(expResult2.getValue());
			expResul = new ExpectedResult();
			expResul.setValue(result);
		}
		expResul.setName(expResult1.getName());
		return expResul;
	}

	/**
	 * @param expectedResults
	 * @param expResult
	 * @return
	 */
	private int indexofExpectedResult(Vector expectedResults, ExpectedResult expResult) {
		for (int i = 0; i < expectedResults.size(); i++) {
			ExpectedResult result = (ExpectedResult) expectedResults.elementAt(i);
			if(expResult.getName().equalsIgnoreCase(result.getName())){
				return i;
			}
		}
		return -1;
	}

}