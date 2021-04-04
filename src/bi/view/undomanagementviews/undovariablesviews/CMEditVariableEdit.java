package bi.view.undomanagementviews.undovariablesviews;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.testdataviews.CMVariables;
import bi.view.mainframeviews.CMFrameView;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CannotRedoException;

import model.ICMValue;
import model.TestDataFormat;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMEditVariableEdit extends CMComponentAwareEdit {


     private CMVariables m_CMVariablesView;
    private int m_IndexTestData;
	private String m_name;
    private String m_description;
    private String m_type;
    private String m_format;
    private ICMValue m_value;
   private String m_oldname;
    private String m_olddescription;
    private String m_oldtype;
    private String m_oldformat;
    private ICMValue m_oldvalue;
    private int m_IndexVariable;
    private TestDataFormat m_formatter;
    private TestDataFormat m_oldformatter;
   
    public CMEditVariableEdit(CMFrameView p_CMFrameView,CMVariables p_CMVariablesView,int p_IndexVariable, String p_name, String p_description, String p_type, String p_format,TestDataFormat formatter,ICMValue p_value,String p_oldname,String p_olddescription,String p_oldtype,String p_oldformat,TestDataFormat oldFormatter,ICMValue p_oldvalue,CMUndoMediator.FocusInfo p_FocusInfo) {
		super(p_FocusInfo, p_CMFrameView);
        this.m_CMVariablesView=p_CMVariablesView;
		this.m_name= p_name;
    	this.m_description=p_description;
    	this.m_type=p_type;
		this.m_format=p_format;
		this.m_value=p_value;
		this.m_oldname= p_oldname;
		this.m_olddescription=p_olddescription;
		this.m_oldtype=p_oldtype;
		this.m_oldformat=p_oldformat;
		this.m_oldvalue=p_oldvalue;
		this.m_IndexVariable=p_IndexVariable;
		 m_formatter=formatter;
			m_oldformatter=oldFormatter;
    }
      public void undo() throws CannotUndoException {
        super.undo();
		m_CMVariablesView.undoRedoEditVariable(m_IndexVariable,m_oldvalue,m_oldtype,m_oldformat,m_oldformatter,m_olddescription,m_oldname);
        int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects()-1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();

    }

    public void redo() throws CannotRedoException {
        super.redo();
		m_CMVariablesView.undoRedoEditVariable(m_IndexVariable,m_value,m_type,m_format,m_formatter,m_description,m_name);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
       setUndoState();
        setRedoState();
    }

}



