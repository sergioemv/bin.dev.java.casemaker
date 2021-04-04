/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.errorviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JViewport;
import javax.swing.tree.DefaultMutableTreeNode;

import model.CMError;
import model.Project2;
import model.Structure;
import model.TestCase;
import model.Workspace2;
import bi.controller.ErrorManager;
import bi.controller.ProjectManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMErrorAssignDateCellView;
import bi.view.cells.CMErrorAssignToCellView;
import bi.view.cells.CMErrorCellView;
import bi.view.cells.CMErrorClosedByCellView;
import bi.view.cells.CMErrorClosingDateCellView;
import bi.view.cells.CMErrorDescriptionCellView;
import bi.view.cells.CMErrorErrorClassCellView;
import bi.view.cells.CMErrorIssueDateCellView;
import bi.view.cells.CMErrorIssuedByCellView;
import bi.view.cells.CMErrorNameCellView;
import bi.view.cells.CMErrorPriorityCellView;
import bi.view.cells.CMErrorRiskLevelCellView;
import bi.view.cells.CMErrorStateCellView;
import bi.view.cells.CMErrorTestCasesCellView;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.mainframeviews.CMFrameView;
import bi.view.treeviews.nodes.CMWorkspaceNode;

import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

public class CMErrorGridView extends CMBaseJSmartGrid {
    public CMErrorGridView(CMErrorScrollView p_CMErrorScrollView) {
    	super();
        m_CMErrorScrollView = p_CMErrorScrollView;
        m_ErrorManager = m_CMErrorScrollView.getM_CMFrame().getCmApplication().getSessionManager().getM_ErrorManager();
        m_CMErrorScrollView.getM_CMFrame().getKeyEventIntercepter().setM_CMErrorGridView(this);//fcastro_13092004
        initGUI();
        //connectToController();
    }

    public JViewport getViewport1() {
        return getViewport(); // getViewport is not public in JSmartGrid
    }
//fcastro_13092004_begin
    public boolean isDeletePossible(){
        int row = this.getSelectionModel().getLeadRow();
        int column = this.getSelectionModel().getLeadColumn();
        Object obj = this.m_CMErrorGridModel.getCellObjectAt(row,column);
        if(obj!=null){
            if(obj instanceof CMErrorNameCellView || obj instanceof CMErrorDescriptionCellView ||
                obj instanceof CMErrorStateCellView || obj instanceof CMErrorErrorClassCellView ||
                obj instanceof CMErrorPriorityCellView || obj instanceof CMErrorIssueDateCellView ||
                obj instanceof CMErrorIssuedByCellView || obj instanceof  CMErrorClosingDateCellView ||
                obj instanceof CMErrorClosedByCellView || obj instanceof CMErrorTestCasesCellView){
                return true;
            }
        }
        return false;
    }
    //fcastro_13092004_end
    public void setM_Structure(Structure p_Structure) {
        m_Structure = p_Structure;
        m_Workspace = getSelectedWorkspace();
        m_Project = ProjectManager.getSelectedProject();
        m_CMErrorGridModel.removeRows(0, this.getRowCount());
        addCMErrorCellViews(m_Structure.getM_CMErrors());
        selectCMErrorCellView(0);
    }

    public Structure getM_Structure() {
        return m_Structure;
    }

    public void initGUI() {
//HCanedo_30112004_Begin
        m_CMErrorGridModel = new CMErrorGridModel(0, 13);
//HCanedo_30112004_End
        m_CMErrorGridStyleModel = new CMErrorGridStyleModel();
        setModels();
        setUIProperties();
        addEventListeners();
    }

    public void setModels() {
        this.setStyleModel(m_CMErrorGridStyleModel);
        this.setModel(m_CMErrorGridModel);
    }

    public void setUIProperties() {
        this.setOpaque(false);
        this.setColumnResizable(true);
        this.setAutoResizeMode(RulerConstants.HORIZONTAL);
        this.setGridColor(new Color(127, 157, 185)); // Read Only Grid Color
        this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setSelectionBackgroundColor(Color.orange);
        this.setSelectionForegroundColor(Color.black);
        this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_ROW);
        this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
        ////////////////////////////////////////////////////////
    }

    public void addEventListeners() {
        this.addGridListener(
            new com.eliad.swing.GridAdapter() {
                public void gridMouseClicked(GridEvent e) {
                    eventGridMouseClicked(e);
                }
            });

    }

    void eventGridMouseClicked(GridEvent e) {
        //fcastro_20092004_begin
        if(!isEnabled()) return;
        else{
            //fcastro_20092004_end

        MouseEvent mouseEvent = (MouseEvent)e.getSourceEvent();
        int row = e.getRow();
        int column = e.getColumn();
        if (mouseEvent.getClickCount() == 2) {
            CMAction.ERROR_EDIT.getAction().actionPerformed(null);
        }
        else {
            if (e.getSourceEvent().getModifiers() == Event.META_MASK) {
                if (row >= 0 && column >= 0) {
                    this.changeSelection(row, column, false, false);
                }
                this.m_CMErrorScrollView.getM_CMFrame().getJPopupMenuErrors().show(this, mouseEvent.getX(), mouseEvent.getY());
            }
        }
        }//fcastro_20092004
    }

    public void selectCMErrorCellView(int p_index) {
        if (p_index >= 0 && this.getRowCount() > 0) {
            this.changeSelection(p_index, 0, false, false);
        }
    }

    public CMErrorCellView createCMErrorCellView(CMError p_CMError) {
        CMErrorCellView errorView = new CMErrorCellView(p_CMError);
        CMErrorNameCellView nameView = new CMErrorNameCellView();
        nameView.setM_CMErrorCellView(errorView);
        CMErrorDescriptionCellView descriptionView = new CMErrorDescriptionCellView();
        descriptionView.setM_CMErrorCellView(errorView);
        CMErrorStateCellView stateView = new CMErrorStateCellView();
        stateView.setM_CMErrorCellView(errorView);
        CMErrorErrorClassCellView errorClassView = new CMErrorErrorClassCellView();
        errorClassView.setM_CMErrorCellView(errorView);
        CMErrorPriorityCellView priorityView = new CMErrorPriorityCellView();
        priorityView.setM_CMErrorCellView(errorView);
        CMErrorIssueDateCellView issueDateView = new CMErrorIssueDateCellView();
        issueDateView.setM_CMErrorCellView(errorView);
        CMErrorIssuedByCellView issuedByView = new CMErrorIssuedByCellView();
        issuedByView.setM_CMErrorCellView(errorView);
        CMErrorClosingDateCellView closingDateView = new CMErrorClosingDateCellView();
        closingDateView.setM_CMErrorCellView(errorView);
        CMErrorClosedByCellView closedByView = new CMErrorClosedByCellView();
        closedByView.setM_CMErrorCellView(errorView);
        CMErrorTestCasesCellView testCasesView = new CMErrorTestCasesCellView();
        testCasesView.setM_CMErrorCellView(errorView);
//HCanedo_30112004_Begin
		CMErrorAssignDateCellView assignDateView = new CMErrorAssignDateCellView();
        assignDateView.setM_CMErrorCellView(errorView);
        CMErrorAssignToCellView assignToView = new CMErrorAssignToCellView();
        assignToView.setM_CMErrorCellView(errorView);
        CMErrorRiskLevelCellView riskLevelView = new CMErrorRiskLevelCellView();
        riskLevelView.setM_CMErrorCellView(errorView);
//HCanedo_30112004_End
        errorView.setM_CMErrorNameCellView(nameView);
        errorView.setM_CMErrorDescriptionCellView(descriptionView);
        errorView.setM_CMErrorStateCellView(stateView);
        errorView.setM_CMErrorErrorClassCellView(errorClassView);
        errorView.setM_CMErrorPriorityCellView(priorityView);
        errorView.setM_CMErrorIssueDateCellView(issueDateView);
        errorView.setM_CMErrorIssuedByCellView(issuedByView);
//HCanedo_30112004_Begin
		errorView.setM_CMErrorAssignDateCellView(assignDateView);
        errorView.setM_CMErrorAssignToCellView(assignToView);
//HCanedo_30112004_End
        errorView.setM_CMErrorClosingDateCellView(closingDateView);
        errorView.setM_CMErrorClosedByCellView(closedByView);
        errorView.setM_CMErrorTestCasesCellView(testCasesView);
//HCanedo_30112004_Begin
		errorView.setM_CMErrorRiskLevelCellView(riskLevelView);
//HCanedo_30112004_End
        errorView.addElement(nameView);
        errorView.addElement(descriptionView);
        errorView.addElement(stateView);
        errorView.addElement(errorClassView);
        errorView.addElement(priorityView);
        errorView.addElement(issueDateView);
        errorView.addElement(issuedByView);
//HCanedo_30112004_Begin
		errorView.addElement(assignDateView);
        errorView.addElement(assignToView);
//HCanedo_30112004_End
        errorView.addElement(closingDateView);
        errorView.addElement(closedByView);
        errorView.addElement(testCasesView);
//HCanedo_30112004_Begin
		errorView.addElement(riskLevelView);
//HCanedo_30112004_End
        return errorView;
    }

    /**
     * this method is never used
     * @param p_CMErrors
     * @return
     */
    public Vector createCMErrorCellViews(Vector p_CMErrors) {
        int numOfErrors = p_CMErrors.size();
        Vector v = new Vector(numOfErrors);
        CMError error = null;
        for (int i = 0; i < numOfErrors; i++) {
            error = (CMError)p_CMErrors.elementAt(i);
            v.addElement(createCMErrorCellView(error));
        }
        return v;
    }

    public void addCMErrorCellView(CMErrorCellView p_CMErrorCellView) {
        this.m_CMErrorGridModel.addRow(p_CMErrorCellView);
        int newSelectionIndex = this.getRowCount() - 1;
        selectCMErrorCellView(newSelectionIndex);
    }

    public void addCMErrorCellViewAt(int p_Index, CMErrorCellView p_CMErrorCellView) {
        int newSelectionIndex = 0;
        if (p_Index >= getRowCount()) {
            this.m_CMErrorGridModel.addRow(p_CMErrorCellView);
            newSelectionIndex = this.getRowCount() - 1;
        }
        else {
            this.m_CMErrorGridModel.insertRow(p_Index, p_CMErrorCellView);
            newSelectionIndex = p_Index;
        }
        selectCMErrorCellView(newSelectionIndex);
    }

    public void addCMErrorCellViews(Vector p_CMErrors) {
        if (p_CMErrors != null) {
            int numOfErrors = p_CMErrors.size();
            CMError error = null;
            for (int i = 0; i < numOfErrors; i++) {
                error = (CMError)p_CMErrors.elementAt(i);
                addCMErrorCellView(createCMErrorCellView(error));
            }
        }
    }

    public void deleteCMErrorCellView(int p_index, CMErrorCellView p_CMErrorCellView) {
        CMError error = p_CMErrorCellView.getM_CMError();
        /// TELL THE CONTROLLER THAT THE SELECTED ERROR MUST BE DELETED
        m_CMErrorScrollView.getM_CMFrame().getCmApplication().getSessionManager().getM_ErrorManager().delete(error,
            m_Structure);
        /// DELETE THE SELECTED VIEW
        this.m_CMErrorGridModel.removeRows(p_index, 1);
        int next = this.getTheNextCMErrorCellView(p_index);
        this.selectCMErrorCellView(next);
    }

    public int getTheNextCMErrorCellView(int p_index) {
        int next = 0;
        if (p_index > 0) {
            next = p_index - 1;
            return next;
        }
        else if (p_index == 0 && m_CMErrorGridModel.getRowCount() > 0) {
            next = 0;
            return next;
        }
        else {
            return -1;
        }
    }

    public void fireEventCauseEffectAdded(CMError p_CMError) {
        CMErrorCellView v = createCMErrorCellView(p_CMError);
        this.addCMErrorCellView(v);
    }

    public void fireEventCMErrorCellDeleted(CMError p_CMError) {
        int index = getCMErrorCellViewIndex(p_CMError);
        if (index >= 0) {
            CMErrorCellView errorView = getSelectedCMErrorCellView(index);
            this.deleteCMErrorCellView(index, errorView);
        }
    }

    public int getCMErrorCellViewIndex(CMError p_CMError) {
        int numOfViews = this.getRowCount();
        CMErrorCellView errorView;
        CMError cmError;
        for (int i = 0; i < numOfViews; i++) {
            errorView = getSelectedCMErrorCellView(i);
            if (p_CMError == errorView.getM_CMError()) {
                return i;
            }
        }
        return -1;
    }

    public void addErrorAt(int p_Index, CMError p_CMError) {
        int newIndex;
        CMErrorCellView errorView = createCMErrorCellView(p_CMError);
        if (p_Index >= m_Structure.getM_CMErrors().size()) {
            m_Structure.getM_CMErrors().addElement(p_CMError);
            newIndex = m_Structure.getM_CMErrors().size();
            addCMErrorCellView(errorView);
        }
        else {
            m_Structure.getM_CMErrors().insertElementAt(p_CMError, p_Index);
            newIndex = p_Index;
            this.addCMErrorCellViewAt(newIndex, errorView);
        }
    }

    public void deleteErrorAt(int p_Index, boolean p_WithConfirmation) {
        CMErrorCellView errorView = this.getSelectedCMErrorCellView(p_Index);
        this.deleteCMErrorCellView(p_Index, errorView);
    }

    public void changeErrorDescription(int p_Index, String p_Description, CMError p_CMError) {
        p_CMError.setM_Description(p_Description);
        updateCellViewAt(p_Index, 1);
    }

    public void updateCellViewAt(int p_Row, int p_Column) {
        int firstRow = p_Row;
        int firstColumn = p_Column;
        int rowCount = 1;
        int columnCount = this.m_CMErrorGridModel.getColumnCount();
        m_CMErrorGridModel.fireGridCellsChanged(firstRow, firstColumn, rowCount, columnCount);
    }


    public int getSelectedCMErrorCellView() {
        int selectedIndex = 0;
        int[] selectedRows = this.getSelectedRows();
        int numSelectedRows = this.getSelectedRowCount();
        if (numSelectedRows > 0) {
            return selectedRows[0];
        }
        else {
            return -1;
        }
    }

    public CMErrorCellView getSelectedCMErrorCellView(int p_index) {
        Object obj = m_CMErrorGridModel.getCellObjectAt(p_index, 0);
        CMErrorNameCellView nameView = (CMErrorNameCellView)obj;
        return nameView.getM_CMErrorCellView();
    }

    //integration_fcastro_17082004_begin

    public void changeErrorData(CMError error, int p_Index, Vector data, Vector oldErrorsOfTestCases,
        Vector oldTestCasesOfError, Vector testCasesToAssign, Vector testCasesToDelete) {
            error.setM_Description((String)data.elementAt(0));
            error.setM_State((String)data.elementAt(1));
            error.setM_ErrorClass((String)data.elementAt(2));
            error.setM_Priority((String)data.elementAt(3));
            error.setM_ClosedBy((String)data.elementAt(4));
            error.setM_ClosingDate((Date)data.elementAt(5));
            error.setM_IssueDate((Date)data.elementAt(6));
            error.setM_IssuedBy((String)data.elementAt(7));
//HCanedo_30112004_Begin
			error.setM_AssignTo((String)data.elementAt(8));
            error.setM_AssignDate((Date)data.elementAt(9));

            changeAssignedTestCasesToError(error,testCasesToAssign,p_Index);
    }

    //integration_fcastro_17082004_end



    public CMError getSelectedCMError() {
        int index = getSelectedCMErrorCellView();
        if (index >= 0) {
            CMErrorCellView selectedView = getSelectedCMErrorCellView(index);
            if (selectedView != null) {
                return selectedView.getM_CMError();
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public Workspace2 getSelectedWorkspace() {
        CMFrameView cmFrame = m_CMErrorScrollView.getM_CMFrame();
        DefaultMutableTreeNode node = cmFrame.getTreeWorkspaceView().getSelectedNode();
        DefaultMutableTreeNode parentNode;
        DefaultMutableTreeNode parentOfParentNode;
        DefaultMutableTreeNode targetNode;
        if (node != null) {
            parentNode = (DefaultMutableTreeNode)node.getParent();
            if (parentNode != null) {
                parentOfParentNode = (DefaultMutableTreeNode)parentNode.getParent();
                if (parentOfParentNode != null) {
                    targetNode = (DefaultMutableTreeNode)parentOfParentNode.getParent();
                    if (targetNode != null) {
                        Object nodeInfo = targetNode.getUserObject();
                        if (nodeInfo != null) {
                            if (nodeInfo instanceof CMWorkspaceNode) {
                                CMWorkspaceNode temp = (CMWorkspaceNode)nodeInfo;
                                return temp.getM_Workspace();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    //fcastro_23082004_begin
    public void changeAssignedTestCasesToError(CMError error, Vector testCasesToAssign, int index) {
        if (testCasesToAssign != null) {

            int numOfTestCases = testCasesToAssign.size();
            for (int i = 0; i < numOfTestCases; i++) {
                TestCase testCase = (TestCase)testCasesToAssign.elementAt(i);
                if (!testCase.getM_CMErrors().contains(error)) {
                    testCase.getM_CMErrors().addElement(error);
                    error.getM_TestCases().addElement(testCase);
                }
            }
            Vector testCasesLeft = m_Structure.getLnkTestCases();
            numOfTestCases =testCasesLeft.size();
            for (int i = 0; i < numOfTestCases;i++){
                TestCase testCase = (TestCase)testCasesLeft.elementAt(i);
                if(!testCasesToAssign.contains(testCase)){
                    if(testCase.getM_CMErrors().contains(error)){
                        testCase.getM_CMErrors().remove(error);
                        error.getM_TestCases().remove(testCase);
                    }
                }
            }
            m_CMErrorGridModel.fireGridRowsChanged(index, 1);//fcastro_13092004
        }
    }



    public CMErrorGridModel getM_CMErrorGridModel() { return m_CMErrorGridModel; }

    public void setM_CMErrorGridModel(CMErrorGridModel m_CMErrorGridModel) { this.m_CMErrorGridModel = m_CMErrorGridModel; }

    public CMErrorGridStyleModel getM_CMErrorGridStyleModel() { return m_CMErrorGridStyleModel; }

    public void setM_CMErrorGridStyleModel(CMErrorGridStyleModel m_CMErrorGridStyleModel) {
        this.m_CMErrorGridStyleModel = m_CMErrorGridStyleModel;
    }

    public CMErrorScrollView getM_CMErrorScrollView() {
        return m_CMErrorScrollView;
    }

    public void setM_CMErrorScrollView(CMErrorScrollView m_CMErrorScrollView) {
        this.m_CMErrorScrollView = m_CMErrorScrollView;
    }

    private CMErrorGridModel m_CMErrorGridModel;
    private CMErrorGridStyleModel m_CMErrorGridStyleModel;

    /** @directed */
    private CMErrorScrollView m_CMErrorScrollView;
    private Structure m_Structure;
    private Workspace2 m_Workspace;
    private Project2 m_Project;
    private ErrorManager m_ErrorManager;
    //////////////////////////////////////////////////////////////////////////////
	@Override
	protected HashMap<Class, Component> getCellClasses() {
		// TODO Auto-generated method stub
		return new HashMap<Class, Component>();
	}
}
