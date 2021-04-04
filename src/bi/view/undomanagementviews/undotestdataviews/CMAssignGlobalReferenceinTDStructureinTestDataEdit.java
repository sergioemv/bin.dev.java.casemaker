/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotestdataviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.ITypeData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

@SuppressWarnings("serial")
public class CMAssignGlobalReferenceinTDStructureinTestDataEdit extends CMComponentAwareEdit {
    private CMPanelTestDataView m_CMPanelTestDataView;
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_IndexTestData;
    private int m_indexStructure;

    private ITypeData m_OldtypeData;
    private int m_numoftable;
    private int m_numofrow;
    private int m_row;
    private int m_column;
    private CMCompoundEdit m_ce;//svonborries_09032006

    public CMAssignGlobalReferenceinTDStructureinTestDataEdit(CMFrameView p_CMFrameView,CMPanelTestDataView p_CMPanelTestDataView,CMGridTDStructure p_CMGridTDStructure,
        int p_IndexTestData,int p_indexStructure,  ITypeData p_OldTypeData,  int p_numoftable,int p_numofrow,
        int p_row, int p_column, CMUndoMediator.FocusInfo p_FocusInfo, UndoableEdit p_ce) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_CMPanelTestDataView=p_CMPanelTestDataView;
        this.m_IndexTestData = p_IndexTestData;
        this.m_indexStructure=p_indexStructure;
        this.m_OldtypeData=p_OldTypeData;
        this.m_numoftable=p_numoftable;
        this.m_numofrow=p_numofrow;
        this.m_row=p_row;
        this.m_column=p_column;
        this.m_ce = (CMCompoundEdit) p_ce;//svonborries_09032006
    }

    public void undo() throws CannotUndoException {
    	//svonborries_09032006_begin
        super.undo();
/*        if(this.m_ce.isInProgress())
        	this.m_ce.endAll();
        this.m_ce.undo();*/
        //svonborries_09032006_end
        int globalindex =m_CMPanelTestDataView.undoGlobalReferenceinTestData(m_IndexTestData,m_indexStructure,m_row, m_column,(ITypeData) m_OldtypeData.clone());
   	 	m_CMGridTDStructure.ifcancelReferenceInTestDataCancelinStructure(globalindex, m_row-1);
         m_CMPanelTestDataView.update(m_IndexTestData,m_indexStructure);
     	int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
    	//svonborries_09032006_begin
/*        if(this.m_ce.isInProgress())
        	this.m_ce.endAll();
        this.m_ce.redo();*/
        //svonborries_09032006_end
        ITypeData m_typ=m_CMGridTDStructure.setGlobalReferenceStructure(m_numoftable, m_numofrow,null);
        m_CMPanelTestDataView.redoGLobalReference(m_IndexTestData,m_indexStructure,m_row, m_column,  m_indexStructure,m_typ);
        m_CMPanelTestDataView.update(m_IndexTestData,m_indexStructure);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
