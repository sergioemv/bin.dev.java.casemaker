package bi.view.undomanagementviews.undoviews;

import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoableEdit;

import model.CMError;
import model.Dependency;
import model.EquivalenceClass;
import model.ICMValue;
import model.ITypeData;
import model.StdCombination;
import model.Structure;
import model.StructureTestData;
import model.TDStructure;
import model.TestCase;
import model.TestData;
import model.TestDataFormat;
import model.TestDataSet;
import model.Variable;
import model.edit.CMModelEditFactory;
import model.util.IdSet;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.cells.CMCellTDStructureField;
import bi.view.cells.CMCellTDStructureFormat;
import bi.view.cells.CMCellTDStructureFormula;
import bi.view.cells.CMCellTDStructureKey;
import bi.view.cells.CMCellTDStructureLength;
import bi.view.cells.CMCellTDStructureName;
import bi.view.cells.CMCellTDStructureNewColumn;
import bi.view.cells.CMCellTDStructureObjectTypes;
import bi.view.cells.CMCellTDStructurePrefix;
import bi.view.cells.CMCellTDStructureSuffix;
import bi.view.cells.CMCellTDStructureValue;
import bi.view.edit.CMViewEditFactory;
import bi.view.errorviews.CMErrorGridView;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.stdcombinationviews.CMStdCombinationViews;
import bi.view.testcaseviews.CMTestCaseViews;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.testdataviews.CMVariables;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoerrorviews.CMAssignTestCasesToErrorEdit;
import bi.view.undomanagementviews.undoerrorviews.CMCreateErrorEdit;
import bi.view.undomanagementviews.undoerrorviews.CMDeleteErrorEdit;
import bi.view.undomanagementviews.undoerrorviews.CMEditErrorDescriptionEdit;
import bi.view.undomanagementviews.undoerrorviews.CMEditErrorEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMAddLinkElementEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMAssignFormulainTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMCancelAssigGlobalReferenceInTDSructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMCancelAssignFormatinTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMCancelAssignFormulainTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMChangeKeyValueInTDSructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMChangeValuesInTDSructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMCreateTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMDeleteLinkElementEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMDeleteNewColumnTDStructure;
import bi.view.undomanagementviews.undotdstructureviews.CMDeleteTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMDeleteTDStructureFieldsEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMEditTDStructureDescriptionEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateAllDeletedTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateAllTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateFaultyDeletedTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateFaultyTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateNegDeletedTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateNegTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGeneratePosDeletedTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGeneratePosTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateResultStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateSpcDeletedTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateSpcTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMGenerateTDStructureEdit;
import bi.view.undomanagementviews.undotdstructureviews.CMInsertNewColumnTDStructure;
import bi.view.undomanagementviews.undotdstructureviews.CMInsertTDStructureFieldsEdit;
import bi.view.undomanagementviews.undotestdatasetviews.CMCreateTestDataSetEdit;
import bi.view.undomanagementviews.undotestdatasetviews.CMDeleteTestDataSetEdit;
import bi.view.undomanagementviews.undotestdatasetviews.CMEditTestDataSetEdit;
import bi.view.undomanagementviews.undotestdataviews.CMAssignFormulainTDStructureinTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMAssignGlobalReferenceinTDStructureinTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMAssignGlobalValueinTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMAssignStructureEdit;
import bi.view.undomanagementviews.undotestdataviews.CMAssignTestCaseToTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMCancelAssignFormulainTDStructureinTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMCancelAssignGlobalReferenceinTDStructureinTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMCancelAssignTDStructureInTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMChangeValuesInTDSructureInTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMCreateTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMCutStructureEdit;
import bi.view.undomanagementviews.undotestdataviews.CMDeleteTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMEditInValidGlobalReferenceAndFormulainTestData;
import bi.view.undomanagementviews.undotestdataviews.CMEditTDStructureDescriptionInTestDataEdit;
import bi.view.undomanagementviews.undotestdataviews.CMEditTestDataEdit;
import bi.view.undomanagementviews.undovariablesviews.CMCreateEditVariableEdit;
import bi.view.undomanagementviews.undovariablesviews.CMDeleteVariableEdit;
import bi.view.undomanagementviews.undovariablesviews.CMEditVariableEdit;

/**
 * A mediator object that coordinates undo and redo requests
 */
public class CMUndoMediator  {

//fcastro_KeyListener transfered to CMFrameView
    /**
     * The one and only undo manager.  This is the manager to which you add edit objects when actions occur, and on which you
     * actually call undo and redo.
     */
	private static  CMUndoMediator INSTANCE = null;
    private UndoManager manager = new UndoManager();
    private boolean m_WithConfirmation = true;
    private int m_NumberOfUndoableEditObjects = 0;
    private int m_NumberOfRedoableEditObjects = 0;
	private CMFrameView m_CMFrameView;


    //the constructor is private to enforce the singletton pattern
    private CMUndoMediator() {
    	//TODO add this to the casemaker settings
        manager.setLimit(50);
        m_CMFrameView = CMApplication.frame;
    }

    public UndoManager getUndoManager() {
        return manager;
    }

    public int getM_NumberOfRedoableEditObjects() {
        return m_NumberOfRedoableEditObjects;
    }

    public int getM_NumberOfUndoableEditObjects() {
        return m_NumberOfUndoableEditObjects;
    }

    public void setM_NumberOfUndoableEditObjects(int p_Number) {

	if(p_Number != 0){
		int saveUnsave=	CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable();
		CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave+1);
    }

        this.m_NumberOfUndoableEditObjects = p_Number;
    }

    public void setM_NumberOfRedoableEditObjects(int p_Number) {

	if(p_Number != 0 && m_NumberOfUndoableEditObjects == 0) {
		int saveUnsave=	CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable();
		CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave+1);
    }

        this.m_NumberOfRedoableEditObjects = p_Number;
    }

    /**
     * Super convenient method for taking advantage of the fact that documents always fire undoable edit events: this
     * method simply registers a document with the mediator so that all undoable edit events fired by the
     * document will be handled here automatically.
     */
    public void registerDocument(Document document, final JComponent component, final JTabbedPane tabbedPane,
        final String tabName) {

            document.addUndoableEditListener(
                new UndoableEditListener() {
                    public void undoableEditHappened(UndoableEditEvent e) {

                        Object source = e.getSource();
                        manager.addEdit(new CMDocumentEdit(e.getEdit(), new FocusInfo(component, tabbedPane, tabName)));
                        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);
                    }
                });
    }




    //integration_fcastro_17082004_end
    /////////////////////////////  Error Management ///////////////////////////////////////////////////////////////////
    public void addErrorAt(int p_Index, CMError p_Error) {

            manager.addEdit(
                new CMCreateErrorEdit(p_Index, p_Error));
            setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
            CMApplication.frame.getCMErrorGridView().addErrorAt(p_Index, p_Error);
    }

    public void changeErrorCellDescription(CMErrorGridView p_CMErrorGridView, int p_Index, String p_Description,
        String p_OldDescription, CMError p_Error, JTabbedPane p_TabbedPane, String p_TabName) {

            manager.addEdit(
                new CMEditErrorDescriptionEdit(CMApplication.frame, p_CMErrorGridView, p_Index, p_Description, p_OldDescription, p_Error,
                new FocusInfo(p_CMErrorGridView, p_TabbedPane, p_TabName)));
            setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
            p_CMErrorGridView.addErrorAt(p_Index, p_Error);
    }

    public void deleteErrorAt(int p_Index, CMError p_Error) {

            manager.addEdit(
                new CMDeleteErrorEdit(p_Index, p_Error));
            setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
            CMApplication.frame.getCMErrorGridView().deleteErrorAt(p_Index, m_WithConfirmation);
    }

    //integration_fcastro_17082004_begin
    public void editError(int index, Vector oldData, Vector newData, CMError error,
        Vector testCases, Vector rightList, Vector leftList) {

            int numOfTestCases = testCases.size();
            Vector errorsOfTestCases = new Vector(0);
            for (int i = 0; i < numOfTestCases; i++) {
                errorsOfTestCases.addElement(((TestCase)testCases.elementAt(i)).getM_CMErrors().clone());
            }
            manager.addEdit(
                new CMEditErrorEdit( index, oldData, newData, error, errorsOfTestCases, rightList, leftList));
            setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
            CMApplication.frame.getCMErrorGridView().changeErrorData(error, index, newData, null, null, rightList, leftList);
    }

    //integration_fcastro_17082004_end
    //fcastro_23082004_begin
    public void changeAssignedTestCasesToError(int index, CMError error,
        Vector oldTestCases, Vector newTestCases) {

            manager.addEdit(
                new CMAssignTestCasesToErrorEdit(index, error, oldTestCases, newTestCases));
            setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
            CMApplication.frame.getCMErrorGridView().changeAssignedTestCasesToError(error, newTestCases, index);
    }



    /**
     * An inner class for collecting together information necessary to
     * focus a component that may be on a different tabbed pane than
     * the one that is currently visible.  The class is fairly simple:
     * it contains only (1) a component that you want to give focus to,
     * and (2) the tabbed pane and tab name, if any, that it is on.
     * Note that it is not necessary to have a tabbed pane at all, but if you do, everything will be taken care of.
     */
    public static class FocusInfo {
        private JComponent component;
        private JTabbedPane tabbedPane;
        private String tabName;

        public FocusInfo(JComponent c, JTabbedPane p, String s) {
            this.component = c;
            this.tabbedPane = p;
            this.tabName = s;
        }

        public FocusInfo(JComponent c) {
            this(c, null, null);
        }

        /** Sets the focus to the desired component, after selecting the desired tab, if necessary. */
        public void doFocus() {
            if (tabbedPane != null) {
                int index = tabbedPane.indexOfTab(tabName);
                if (index != -1) {
                    if (index != tabbedPane.getSelectedIndex()) {
                        tabbedPane.setSelectedIndex(index);
                    }
                }
            }
            if (component != null) {
                component.requestFocus();
            }
        }
    }

//hcanedo_23082004_begin
/**
* Metodos para undo redo en Test Data
*/
////////////////////////////////////////////////////Strucucture Views///////////////////////////////

    public void addTDStructureAt(CMGridTDStructure p_CMStructureView, int p_Index, StructureTestData p_StructureTestData, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMCreateTDStructureEdit(CMApplication.frame, p_CMStructureView, p_Index, p_StructureTestData, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void deleteStructureAt(CMGridTDStructure p_CMStructureView, Vector p_ResultCompariosnActual, Vector p_ResultComparisonTarget, Vector p_TestDataSetDeleted, Vector  p_TestDataSetIndex, Vector p_TestDataDeleted, Vector p_TestDataIndex, StructureTestData structTD,int index, Vector p_TDDeletedinTDS, Vector p_StDeletedinTD,JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMDeleteTDStructureEdit(CMApplication.frame, p_CMStructureView,p_ResultCompariosnActual,  p_ResultComparisonTarget, p_TestDataSetDeleted,  p_TestDataSetIndex, p_TestDataDeleted, p_TestDataIndex, structTD, index, p_TDDeletedinTDS, p_StDeletedinTD, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void generateTDStructure(CMGridTDStructure p_CMStructureView, int p_Index,TDStructure p_TDStructure, StructureTestData p_StructureTestData,IdSet clonIdSetStructure,IdSet clonIdSetTestData,IdSet clonIdSetTestDataSet, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateTDStructureEdit(CMApplication.frame, p_CMStructureView, p_Index,p_TDStructure, p_StructureTestData,clonIdSetStructure,clonIdSetTestData, clonIdSetTestDataSet,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

	public void generateAllTDStructure(CMGridTDStructure p_CMStructureView, int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateAllTDStructureEdit(CMApplication.frame, p_CMStructureView, p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }
//svonborries_17052006_begin
	public void generateResultStructure(CMGridTDStructure p_gridTDStructure,int p_index, JTabbedPane p_TabbedPane, String p_TabName){

		manager.addEdit(new CMGenerateResultStructureEdit(p_gridTDStructure, p_index, new FocusInfo(p_gridTDStructure, p_TabbedPane, p_TabName)));
		setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
	}
//svonborries_17052006_end
  	public void generatePosTDStructure(CMGridTDStructure p_CMStructureView, int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGeneratePosTDStructureEdit(CMApplication.frame, p_CMStructureView, p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

  	public void generateFaultyTDStructure(CMGridTDStructure p_CMStructureView, int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateFaultyTDStructureEdit(CMApplication.frame, p_CMStructureView, p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

   	public void generateNegTDStructure(CMGridTDStructure p_CMStructureView, int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateNegTDStructureEdit(CMApplication.frame, p_CMStructureView, p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }
//hcanedo_21102004_begin
   	public void generateSpcTDStructure(CMGridTDStructure p_CMStructureView, int p_Index,Vector p_testcaseintestdata, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateSpcTDStructureEdit(CMApplication.frame, p_CMStructureView, p_Index, p_testcaseintestdata, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }
//hcanedo_21102004_end
    public void changeTDStructureDescription(CMGridTDStructure p_CMStructureView, int p_Index, String p_Description, String p_OldDescription, String p_Type, String p_OldType, StructureTestData p_StructureTestData, JTabbedPane p_TabbedPane, String p_TabName) {

        manager.addEdit(new CMEditTDStructureDescriptionEdit(CMApplication.frame, p_CMStructureView, p_Index, p_Description, p_OldDescription,p_Type,p_OldType, p_StructureTestData, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

	public void insertTDStructureFields(CMGridTDStructure p_CMStructureView, int p_Index, int p_IndexTDS,ITypeData p_TypeData,  JTabbedPane p_TabbedPane, String p_TabName) {

        manager.addEdit(new CMInsertTDStructureFieldsEdit(getM_CMFrameView(), p_CMStructureView,p_Index,p_IndexTDS, p_TypeData,new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

	//CCastedo Begin verrrrr
	public void insertTDStructureNewColumn(CMGridTDStructure p_CMStructureView, int p_IndexTDS, int indexSTD, String columnName, JTabbedPane p_TabbedPane, String p_TabName) {

        manager.addEdit(new CMInsertNewColumnTDStructure(CMApplication.frame, p_CMStructureView, p_IndexTDS, indexSTD, columnName, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);
    }

	public void deleteTDStructureNewColumn(CMGridTDStructure p_CMStructureView, int p_IndexTDS, int p_indexStrucure, String nameNewColumn, JTabbedPane p_TabbedPane, String p_TabName) {

        manager.addEdit(new CMDeleteNewColumnTDStructure(CMApplication.frame, p_CMStructureView,p_IndexTDS, p_indexStrucure, nameNewColumn, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);
    }

	//Ccastedo ends.
 	public void deleteTDStructureFields(CMGridTDStructure p_CMStructureView, int p_Index,  JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_ce) {

        manager.addEdit(new CMDeleteTDStructureFieldsEdit(getM_CMFrameView(), p_CMStructureView,p_Index,new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName),p_ce));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

  	public void changeTDStructureDescriptioninTestData(CMPanelTestDataView p_CMStructureView, int p_IndexTestData, int p_indexStructure, String p_Description, String p_OldDescription, String p_Type, String p_OldType, StructureTestData p_StructureTestData, JTabbedPane p_TabbedPane, String p_TabName) {

        manager.addEdit(new CMEditTDStructureDescriptionInTestDataEdit(CMApplication.frame, p_CMStructureView, p_IndexTestData,p_indexStructure, p_Description, p_OldDescription,p_Type,p_OldType, p_StructureTestData, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

  	public void changeValuesInTDSructure(CMGridTDStructure p_CMStructureView, int p_Index, Object p_editingObject,  String p_value,String p_OldValue,
        int p_row, int p_column, JTabbedPane p_TabbedPane, String p_TabName,UndoableEdit p_ce)
  	{

		CMCompoundEdit ce;
		if(p_ce == null)
			ce = new CMCompoundEdit();
		else
			ce = (CMCompoundEdit) p_ce;
		ce.addEdit(this.selectTypeDataChangeValueEditCommand(p_editingObject,p_Index,p_value,p_row,p_column,p_CMStructureView));//svonborries_01032006
        manager.addEdit(new CMChangeValuesInTDSructureEdit(getM_CMFrameView(), p_CMStructureView, p_Index,p_editingObject,p_value,p_OldValue,p_row, p_column,new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName),ce));//svonborries_01032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
  	}

	public void changeValuesInTDSructureInTestData(CMPanelTestDataView p_CMStructureView, int p_IndexTestData, int p_indexStructure, Object p_editingObject,  String p_value,String p_OldValue,
        int p_row, int p_column, JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_ce)
  	{

        manager.addEdit(new CMChangeValuesInTDSructureInTestDataEdit(CMApplication.frame, p_CMStructureView, p_IndexTestData,p_indexStructure,p_editingObject,p_value,p_OldValue,p_row, p_column,new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName),p_ce));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
  	}

    public void changeKeyValueInTDSructure(CMGridTDStructure p_CMStructureView,  int p_indexStructure,  int p_row, int p_column,Object p_obj, JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_ce)
  	{

        manager.addEdit(new CMChangeKeyValueInTDSructureEdit(getM_CMFrameView(), p_CMStructureView,p_indexStructure,new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName),p_ce));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
  	}

    public void assignGlobalReferenceinTDStructureinTestDataEdit(CMPanelTestDataView p_CMPanelTestDataView,CMGridTDStructure p_CMGridTDStructure,int p_IndexTestData,int p_indexStructure,  ITypeData p_OldTypeData,  int p_numoftable,int p_numofrow,int p_row, int p_column,JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_ce)
    {

        manager.addEdit(new CMAssignGlobalReferenceinTDStructureinTestDataEdit(getM_CMFrameView(), p_CMPanelTestDataView, p_CMGridTDStructure, p_IndexTestData, p_indexStructure, p_OldTypeData, p_numoftable, p_numofrow, p_row, p_column,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName),p_ce));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void cancelAssignGlobalReferenceinTDStructureinTestDataEdit(CMPanelTestDataView p_CMPanelTestDataView,CMGridTDStructure p_CMGridTDStructure,int p_IndexTestData,int p_indexStructure,  ITypeData p_OldTypeData,  int p_numoftable,int p_numofrow,int p_row, int p_column,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMCancelAssignGlobalReferenceinTDStructureinTestDataEdit(CMApplication.frame, p_CMPanelTestDataView, p_CMGridTDStructure, p_IndexTestData, p_indexStructure, p_OldTypeData, p_numoftable, p_numofrow, p_row, p_column,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }


    public void editInValidGlobalReferenceAndFormulainTestData(CMPanelTestDataView p_CMPanelTestDataView,CMGridTDStructure p_CMGridTDStructure,int p_IndexTestData,int p_indexStructure,  ITypeData p_OldTypeData,ITypeData p_NewTypeData,  int p_numoftable,int p_numofrow,int p_row, int p_column,JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_ce)
    {

        manager.addEdit(new CMEditInValidGlobalReferenceAndFormulainTestData(CMApplication.frame, p_CMPanelTestDataView, p_CMGridTDStructure, p_IndexTestData, p_indexStructure, p_OldTypeData,p_NewTypeData, p_numoftable, p_numofrow, p_row, p_column,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName),p_ce));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void assignGlobalValueinTestDataEdit( CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,int p_indexStructure,ITypeData p_OldTypeData,ITypeData p_TypeData,int p_numoftable,int p_row,int p_column,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMAssignGlobalValueinTestDataEdit(CMApplication.frame, p_CMPanelTestDataView, p_IndexTestData, p_indexStructure, p_OldTypeData, p_TypeData,p_numoftable, p_row, p_column,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

	public void cancelAssignGlobalReferenceInTDStructure(CMGridTDStructure p_CMStructureView,  int p_indexStructure,  int p_row, int p_column,Vector p_OldReferenceInTestData, JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_ce)
    {

        manager.addEdit(new CMCancelAssigGlobalReferenceInTDSructureEdit(getM_CMFrameView(), p_CMStructureView,p_indexStructure,p_row, p_column,p_OldReferenceInTestData,new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName),p_ce));//svonborries_06032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

	public void assignGlobalReferenceInTDStructure(CMGridTDStructure p_CMStructureView,  int p_indexStructure,  int p_row, int p_column,Vector p_OldReferenceInTestData, JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_edit)//svonborries_09032006
    {

        manager.addEdit(new bi.view.undomanagementviews.undotdstructureviews.CMAssigGlobalReferenceInTDSructureEdit(getM_CMFrameView(), p_CMStructureView,p_indexStructure,p_row, p_column,p_OldReferenceInTestData,new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName),p_edit));//svonborries_09032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void assignFormulainTDStructureEdit(CMGridTDStructure p_CMStructureView,  int p_indexStructure,  int p_row, int p_column,UndoableEdit p_ce,JTabbedPane p_TabbedPane, String p_TabName)//svonborries_10032006
  	{

        manager.addEdit(new CMAssignFormulainTDStructureEdit(getM_CMFrameView(), p_CMStructureView,p_indexStructure,p_row, p_column,p_ce, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));//svonborries_10032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
  	}

    public void assignFormulainTDStructureinTestDataEdit(int p_IndexTestData,int p_indexStructure, int p_row, int p_column,UndoableEdit p_ce,JTabbedPane p_TabbedPane, String p_TabName)//svonborries_13032006
    {

        manager.addEdit(new CMAssignFormulainTDStructureinTestDataEdit(getM_CMFrameView(), p_IndexTestData, p_indexStructure, p_row, p_column,p_ce,new FocusInfo(getM_CMFrameView().getPanelTestDataView(), p_TabbedPane, p_TabName)));//svonborries_13032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void cancelAssignFormulainTDStructureEdit(CMGridTDStructure p_CMStructureView,  int p_indexStructure,  int p_row, int p_column,UndoableEdit p_ce,JTabbedPane p_TabbedPane, String p_TabName)//svonborries_11032006
  	{

        manager.addEdit(new CMCancelAssignFormulainTDStructureEdit(getM_CMFrameView(), p_CMStructureView,p_indexStructure,p_row, p_column,p_ce, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));//svonborries_11032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
  	}
//svonborries_15032006_begin
    public void deleteLinkElementEdit(CMGridTDStructure p_CMStructureView, UndoableEdit p_ce,JTabbedPane p_TabbedPane, String p_TabName){

    	manager.addEdit(new CMDeleteLinkElementEdit(new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName), getM_CMFrameView(), p_ce));
    	setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void addLinkElementEdit(CMGridTDStructure p_CMStructureView, UndoableEdit p_ce,JTabbedPane p_TabbedPane, String p_TabName){

    	manager.addEdit(new CMAddLinkElementEdit(new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName), getM_CMFrameView(), p_ce));
    	setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }
//  svonborries_15032006_end
    public void cancelAssignFormulainTDStructureinTestDataEdit(CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,int p_indexStructure, int p_row, int p_column,UndoableEdit p_ce,JTabbedPane p_TabbedPane, String p_TabName)//svonborries_13032006
    {

        manager.addEdit(new CMCancelAssignFormulainTDStructureinTestDataEdit(getM_CMFrameView(), p_CMPanelTestDataView, p_IndexTestData, p_indexStructure, p_row, p_column,p_ce,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));//svonborries_13032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void createTestDataEdit(CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,TestData p_TestData,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMCreateTestDataEdit(CMApplication.frame, p_CMPanelTestDataView, p_IndexTestData, p_TestData,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void editTestDataEdit(CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,String p_Description,String p_OldDescription,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMEditTestDataEdit(CMApplication.frame, p_CMPanelTestDataView, p_IndexTestData, p_Description, p_OldDescription,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void assignTestCaseToTestDataEdit(CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,String p_TestCase,String p_OldTestCase,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMAssignTestCaseToTestDataEdit(CMApplication.frame, p_CMPanelTestDataView, p_IndexTestData, p_TestCase, p_OldTestCase,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void assignStructureEdit(CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,StructureTestData p_StructureTestData,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMAssignStructureEdit(CMApplication.frame, p_CMPanelTestDataView, p_IndexTestData, p_StructureTestData,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void createTestDataSetEdit(CMGridTDStructure p_CMStructureView,  TestDataSet p_TestDataSet,int p_index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMCreateTestDataSetEdit(CMApplication.frame, p_CMStructureView,  p_TestDataSet, p_index, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }
	public void createVariableEdit(CMVariables p_CMVariables,int p_IndexVariable,Variable p_Variable,/*String value, String type, String format,TestDataFormat formatter,String name,String description, String oldvalue, String oldtype, String oldformat,TestDataFormat oldformatter,String oldname,String olddescription,*/JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMCreateEditVariableEdit(CMApplication.frame, p_CMVariables, p_IndexVariable,p_Variable,/*name,description,  type, format, formatter, value,  oldname,olddescription,  oldtype, oldformat,oldformatter, oldvalue,*/new FocusInfo(p_CMVariables, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void editVariableEdit(CMVariables p_CMVariables,int p_IndexVariable,ICMValue value, String type, String format,TestDataFormat formatter,String name,String description, ICMValue oldvalue, String oldtype, String oldformat,TestDataFormat oldformatter,String oldname,String olddescription,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMEditVariableEdit(CMApplication.frame, p_CMVariables, p_IndexVariable,name,description,  type, format, formatter, value,  oldname,olddescription,  oldtype, oldformat,oldformatter, oldvalue,new FocusInfo(p_CMVariables, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void deleteVariableEdit(CMVariables p_CMVariables,int p_IndexVariable,Variable p_Variable,/*String value, String type, String format,TestDataFormat oldFormater, String name,String description, String oldvalue, String oldtype, String oldformat,String oldname,String olddescription,*/JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMDeleteVariableEdit(CMApplication.frame, p_CMVariables, p_IndexVariable,p_Variable,/*name,description,  type, format, value,  oldname,olddescription,  oldtype, oldformat,oldFormater,oldvalue,*/new FocusInfo(p_CMVariables, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void deleteTestDataAt(CMGridTDStructure gridTDStructure, Vector p_ResultCompariosnActual, Vector p_ResultComparisonTarget,Vector p_TestDataSetDeleted,Vector  p_TestDataSetIndex,TestData td,int indexTD,Vector  p_TDDeletedinTDS, JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMDeleteTestDataEdit(CMApplication.frame,gridTDStructure, p_ResultCompariosnActual, p_ResultComparisonTarget, p_TestDataSetDeleted, p_TestDataSetIndex, td,indexTD,p_TDDeletedinTDS,new FocusInfo(gridTDStructure, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void deleteTestDataSetAt(CMGridTDStructure gridTDStructure, Vector p_ResultCompariosnActual, Vector p_ResultComparisonTarget,TestDataSet td,int indexTD, JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMDeleteTestDataSetEdit(CMApplication.frame,gridTDStructure, p_ResultCompariosnActual, p_ResultComparisonTarget, td,indexTD,new FocusInfo(gridTDStructure, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void cancelAssignTDStructureInTestDataAt(CMGridTDStructure gridTDStructure,Vector p_ResultCompariosnActual,Vector  p_ResultComparisonTarget,Vector p_TestDataSetDeleted,Vector  p_TestDataSetIndex,TestData tdDeleted,int  indexTD,StructureTestData p_StructureTestData,int index ,boolean isdeletedTD,Vector p_TDDeletedinTDS,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMCancelAssignTDStructureInTestDataEdit(CMApplication.frame,gridTDStructure, p_ResultCompariosnActual,  p_ResultComparisonTarget, p_TestDataSetDeleted,  p_TestDataSetIndex, tdDeleted, indexTD, p_StructureTestData,index, isdeletedTD, p_TDDeletedinTDS, new FocusInfo(gridTDStructure, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

    public void editTestDataSetAt(CMGridTDStructure gridTDStructure,Vector m_Selecteds,String description,Vector cancelSelected,String oldDescription,int index,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMEditTestDataSetEdit(CMApplication.frame,gridTDStructure, m_Selecteds, description,cancelSelected,oldDescription,index, new FocusInfo(gridTDStructure, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

	public void generateAllDeletedTDStructure(CMGridTDStructure p_CMStructureView,TDStructure p_TDStructure ,int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateAllDeletedTDStructureEdit(CMApplication.frame, p_CMStructureView,p_TDStructure,p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

  	public void generatePosDeletedTDStructure(CMGridTDStructure p_CMStructureView, TDStructure p_TDStructure ,int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGeneratePosDeletedTDStructureEdit(CMApplication.frame, p_CMStructureView, p_TDStructure,p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

  	public void generateFaultyDeletedTDStructure(CMGridTDStructure p_CMStructureView, TDStructure p_TDStructure ,int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateFaultyDeletedTDStructureEdit(CMApplication.frame, p_CMStructureView,p_TDStructure, p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

   	public void generateNegDeletedTDStructure(CMGridTDStructure p_CMStructureView, TDStructure p_TDStructure ,int p_Index, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateNegDeletedTDStructureEdit(CMApplication.frame, p_CMStructureView, p_TDStructure, p_Index,  new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }
//hcanedo_21102004_begin
   	public void generateSpcDeletedTDStructure(CMGridTDStructure p_CMStructureView, TDStructure p_TDStructure ,int p_Index, Vector p_testcaseintestdata, JTabbedPane p_TabbedPane, String p_TabName){

        manager.addEdit(new CMGenerateSpcDeletedTDStructureEdit(CMApplication.frame, p_CMStructureView,p_TDStructure,  p_Index,p_testcaseintestdata, new FocusInfo(p_CMStructureView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }


    public void cutStructureTestData(CMGridTDStructure gridTDStructure,Vector p_ResultCompariosnActual,Vector  p_ResultComparisonTarget,Vector p_TestDataSetDeleted,Vector  p_TestDataSetIndex,TestData tdDeleted,int  indexTD,StructureTestData p_StructureTestData,int index ,boolean isdeletedTD,Vector p_TDDeletedinTDS,JTabbedPane p_TabbedPane, String p_TabName)
    {

        manager.addEdit(new CMCutStructureEdit(CMApplication.frame,gridTDStructure, p_ResultCompariosnActual,  p_ResultComparisonTarget, p_TestDataSetDeleted,  p_TestDataSetIndex, tdDeleted, indexTD, p_StructureTestData,index, isdeletedTD, p_TDDeletedinTDS, new FocusInfo(gridTDStructure, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }


    public void pasteStructureTestData(CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,StructureTestData p_StructureTestData,JTabbedPane p_TabbedPane, String p_TabName)
	{

        manager.addEdit(new CMAssignStructureEdit(CMApplication.frame, p_CMPanelTestDataView, p_IndexTestData, p_StructureTestData,new FocusInfo(p_CMPanelTestDataView, p_TabbedPane, p_TabName)));
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;
    }

	public void cancelAssignFormatinTDStructureEdit(CMGridTDStructure structure, int indexstructure, int row, int column, JTabbedPane p_TabbedPane, String p_TabName, UndoableEdit p_ce) {//svonborries_14032006

        manager.addEdit(new CMCancelAssignFormatinTDStructureEdit(getM_CMFrameView(), structure,indexstructure,row, column,new FocusInfo(structure, p_TabbedPane, p_TabName),p_ce));//svonborries_14032006
        setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);;

	}




	/**
	*@autor smoreno
	 */
	public static CMUndoMediator getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CMUndoMediator();
		return INSTANCE;

	}


//hcanedo_21102004_end
	//svonborries_22062006_begin
	@SuppressWarnings({"unchecked"})
	public UndoableEdit selectTypeDataChangeValueEditCommand(Object p_editingObject, int indexStructure, String p_value,
        int p_row, int p_column, CMGridTDStructure p_Grid){
		CMCompoundEdit command = new CMCompoundEdit();
		if(p_editingObject == null){
			return null;
		}
		command.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),indexStructure));
		StructureTestData std = (StructureTestData)getM_CMFrameView().getGridTDStructure().getTDStructure().getM_StructureTestData().elementAt(indexStructure);
		ITypeData s = (ITypeData)std.getTypeData().elementAt(getM_CMFrameView().getGridTDStructure().getCmGridModel().numOfCell(p_row));
		if (s == null){
			return null;
		}
		if (p_editingObject instanceof CMCellTDStructureKey){
			command.addEdit(CMModelEditFactory.INSTANCE.createChangeKeyInTypeDataModelEdit(s,p_value));
			s.setKey(p_value);

		}
		else if (p_editingObject instanceof CMCellTDStructureField){
			if(CMGridTDStructure.uniqueValueFieldName(std,p_value,p_editingObject,indexStructure)){
				command.addEdit(CMModelEditFactory.INSTANCE.createChangeFieldInTypeDataModelEdit(s, p_value));
				s.setField(p_value);

			}
			else{
				JOptionPane.showMessageDialog(getM_CMFrameView(), CMMessages.getString("TESTDATA_NAME_FIELD_EQUALS_MENSSAGE_ERROR"),
                        CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(p_editingObject instanceof CMCellTDStructureName){
			if(CMGridTDStructure.uniqueValueFieldName(std,p_value,p_editingObject,indexStructure)){
				command.addEdit(CMModelEditFactory.INSTANCE.createChangeNameInTypeDataModelEdit(s,p_value));
				s.setName(p_value);

			}
			else{
				JOptionPane.showMessageDialog(getM_CMFrameView(), CMMessages.getString("TESTDATA_NAME_FIELD_EQUALS_MENSSAGE_ERROR"),
                        CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (p_editingObject instanceof CMCellTDStructureObjectTypes){
			CMCellTDStructureObjectTypes selectedItem =
                (CMCellTDStructureObjectTypes)getM_CMFrameView().getGridTDStructure().getCellObjectAt(p_row, p_column);
        	if (selectedItem != null){
        		/*command.addEdit(CMModelEditFactory.INSTANCE.createChangeObjectTypeInTypeDataModelEdit(s,p_value));
        		s.setToolVendorOT(p_value);*///ccastedo 27.09.06
        		int value = CMApplication.frame.getGridTDStructure().indexFromjComboBoxOT(p_value);
        		command.addEdit(CMModelEditFactory.INSTANCE.createChangeObjectTypeInTypeDataModelEdit(s,value));
        		s.setStateOT(value);

        	}
		}

		else if(p_editingObject instanceof CMCellTDStructureNewColumn){
			int i= p_column-5;
            Vector columns = new Vector();
            columns = (Vector) s.getNewColumns().clone();
            columns.setElementAt((String)p_value,i);
            command.addEdit(CMModelEditFactory.INSTANCE.createChangeNewColumnInTypeDataModelEdit(s,p_value,p_column));
            s.setNewColumns(columns);

		}

		else if(p_editingObject instanceof CMCellTDStructureLength){
			p_value = this.checkLenght(p_value);
			command.addEdit(CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit(s,p_value));
			s.setLength(p_value);
			p_Grid.changeValuesInGridTDstructure(p_editingObject, indexStructure, p_value,
			        p_row, p_column);
		}

		else if(p_editingObject instanceof CMCellTDStructurePrefix){
			command.addEdit(CMModelEditFactory.INSTANCE.createChangePrefixInTypeDataModelEdit(s,p_value));
			s.setPrefix(p_value);

		}

		else if (p_editingObject instanceof CMCellTDStructureSuffix){
			command.addEdit(CMModelEditFactory.INSTANCE.createChangeSuffixInTypeDataModelEdit(s,p_value));
			s.setSuffix(p_value);


		}

		else if(p_editingObject instanceof CMCellTDStructureClassState){
			/*CMCellTDStructureClassState selectedItem =
                (CMCellTDStructureClassState)m_CMFrameView.getGridTDStructure().getCmGridModel().getCellObjectAt(p_row, p_column);*/
			command.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInTypeDataModelEdit(s,p_value));
			s.setType(p_value);
			command.addEdit(CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit(s,CMCellTDStructureClassState.getDefaultValue(s.getTypeName())));
            s.setLength(CMCellTDStructureClassState.getDefaultValue(s.getTypeName()));

		}

		else if (p_editingObject instanceof CMCellTDStructureFormula){
			/*command.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(s,p_value));
			s.setFormula(p_value);*/

		}

		else if (p_editingObject instanceof CMCellTDStructureFormat){
			command.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(s,p_value));
			s.setFormat(p_value);

		}

		else if(p_editingObject instanceof CMCellTDStructureValue){
			//command.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(s,p_value));
			//s.setValue(CMFormatFactory.applyAnyFormat(s.getM_Formatter(), p_value, s.getM_Formatter()));

		}
		p_Grid.update(CMIndexTDStructureUpdate.getInstance().getIndex());//svonborries_09052006
		return command;
	}

	/**
	*@autor smoreno
	 * @param p_edit
	 */
	public void doEdit(UndoableEdit p_edit) {
		if (p_edit instanceof CMCompoundEdit)
			if (((CMCompoundEdit)p_edit).isInProgress())
				((CMCompoundEdit)p_edit).endAll();

			manager.addEdit(p_edit);
			setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);

	}

	public void doMassiveEdit(UndoableEdit edit){
		CMCompoundMassiveEdit cme = new CMCompoundMassiveEdit();

		cme.addEdit(edit);

		if (cme.isInProgress())
				cme.endAll();

		manager.addEdit(cme);
		setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects+1);
	}
	//svonborries_22062006_end
	//svonborries_30032006_begin
	public String checkLenght (String length)
	{
    	try{
    	if(Double.parseDouble(length)<=0)
            return new String("1");
    	else
    		return new String(length);
    	}
    	catch(Exception ex)
    	{
			return new String("1");
    	}

	}
	//svonborries_30032006_end

	/**
	 * @return Returns the m_CMFrameView.
	 * svonborries
	 */
	private CMFrameView getM_CMFrameView() {
		if (m_CMFrameView==null)
			m_CMFrameView=CMApplication.frame;
		return m_CMFrameView;
	}

	/**
	 * Merges the last i edits from the undomanager
	 * @param i
	 */
	public void mergeLastEdits(int i) {
		// TODO Auto-generated method stub
		UndoManager um = new UndoManager();
		CMCompoundEdit ce = new CMCompoundEdit();
		for (UndoableEdit edit : this.getUndoManager().getEdits())
			if (this.getUndoManager().getEdits().indexOf(edit)<=this.getUndoManager().getEdits().size()-i-1)
				um.addEdit(edit);
			else
				ce.addEdit(edit);
		ce.endAll();
		um.addEdit(ce);
		this.manager = um;
		//this.getUndoManager().trimEditsNotDie(this.getUndoManager().getEdits().size()-i,this.getUndoManager().getEdits().size()-1);

		setM_NumberOfUndoableEditObjects(m_NumberOfUndoableEditObjects-i+1);
		setM_NumberOfRedoableEditObjects(m_NumberOfRedoableEditObjects-i+1);
	}







}
