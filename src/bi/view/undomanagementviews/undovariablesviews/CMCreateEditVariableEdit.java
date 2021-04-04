package bi.view.undomanagementviews.undovariablesviews;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Variable;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMVariables;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMCreateEditVariableEdit extends CMComponentAwareEdit {


     private CMVariables m_CMVariablesView;
   /* private int m_IndexTestData;
	private String m_name;
    private String m_description;
    private String m_type;
    private String m_format;
    private String m_value;
   private String m_oldname;
    private String m_olddescription;
    private String m_oldtype;
    private String m_oldformat;
    private String m_oldvalue;
    private int m_IndexVariable;
    private TestDataFormat m_formatter;
    private TestDataFormat m_oldformatter;*/
    private int m_IndexVariable;
    private Variable m_Variable;
    
    public CMCreateEditVariableEdit(CMFrameView p_CMFrameView,CMVariables p_CMVariablesView,int p_IndexVariable, Variable p_Variable,/*String p_name, String p_description, String p_type, String p_format,TestDataFormat formatter,String p_value,String p_oldname,String p_olddescription,String p_oldtype,String p_oldformat,TestDataFormat oldFormatter,String p_oldvalue,*/CMUndoMediator.FocusInfo p_FocusInfo) {
		super(p_FocusInfo, p_CMFrameView);
        this.m_CMVariablesView=p_CMVariablesView;
        m_Variable=p_Variable;
        this.m_IndexVariable=p_IndexVariable;
		/*this.m_name= p_name;
    	this.m_description=p_description;
    	this.m_type=p_type;
		this.m_format=p_format;
		this.m_value=p_value;
		this.m_oldname= p_oldname;
		this.m_olddescription=p_olddescription;
		this.m_oldtype=p_oldtype;
		this.m_oldformat=p_oldformat;
		this.m_oldvalue=p_oldvalue;
		
		m_formatter=formatter;
		m_oldformatter=oldFormatter;*/
        
    }
      public void undo() throws CannotUndoException {
        super.undo();
		m_CMVariablesView.undoCreateVariable(m_IndexVariable);
     	int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects()-1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();

    }

    public void redo() throws CannotRedoException {
        super.redo();
		m_CMVariablesView.redoCreateVariable(m_IndexVariable,m_Variable);//m_value,m_type,m_format,m_formatter, m_description,m_name);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
       setUndoState();
        setRedoState();
    }

}



