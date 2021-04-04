/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeValuesInTDSructureEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private Object m_editingObject;
    private String m_value;
    private String m_Oldvalue;
    private int m_row;
    private int m_column;
    private CMCompoundEdit m_ce;//svonborries_01032006

    public CMChangeValuesInTDSructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure,
        int p_Index, Object p_editingObject,  String p_value,String p_OldValue,
        int p_row, int p_column, CMUndoMediator.FocusInfo p_FocusInfo, UndoableEdit p_ce) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_Index = p_Index;
        this.m_editingObject=p_editingObject;
        this.m_value=p_value;
        this.m_Oldvalue=p_OldValue;
        this.m_row=p_row;
        this.m_column=p_column;
        this.m_ce = (CMCompoundEdit) p_ce;//svonborries_01032006

    }

    public void undo() throws CannotUndoException {
    	super.undo();
    	//svonborries_01032006_begin
    	if(this.m_ce.isInProgress()){
    		m_ce.endAll();
    	}
    	this.m_ce.undo();
//    	svonborries_01032006_end
        //m_CMGridTDStructure.changeValuesInGridTDstructure(m_editingObject,m_Index,m_Oldvalue,m_row,m_column);
    	//m_CMGridTDStructure.updateCellsInGridTDStructure(m_editingObject,m_Index,m_Oldvalue,m_row,m_column);
		 this.m_CMGridTDStructure.setRowSelected(m_row);
		 this.m_CMGridTDStructure.setColumnSelected(m_column);
		 m_CMGridTDStructure.changeSelection(m_row,m_column,false,false);
		 m_CMGridTDStructure.grabFocus();
		// m_CMGridTDStructure.updateCellsInGridTDStructure(m_editingObject,m_Index,m_Oldvalue,m_row,m_column);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);
        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
         m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();

    }

    public void redo() throws CannotRedoException {
    	super.redo();
//    	svonborries_01032006_begin
    	if(this.m_ce.isInProgress()){
    		m_ce.endAll();
    	}
    	this.m_ce.redo();
//    	svonborries_01032006_end
        //m_CMGridTDStructure.changeValuesInGridTDstructure(m_editingObject,m_Index,m_value,m_row,m_column);
    	//m_CMGridTDStructure.updateCellsInGridTDStructure(m_editingObject,m_Index,m_value,m_row,m_column);
		 this.m_CMGridTDStructure.setRowSelected(m_row);
		 this.m_CMGridTDStructure.setColumnSelected(m_column);
		 m_CMGridTDStructure.changeSelection(m_row,m_column,false,false);
		 m_CMGridTDStructure.grabFocus();
		// m_CMGridTDStructure.updateCellsInGridTDStructure(m_editingObject,m_Index,m_value,m_row,m_column);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);
        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
