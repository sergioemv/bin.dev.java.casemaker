/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

/**
 * An edit object that stores a component for refocusing to.
 * Make subclasses of this class and arrange for their undo
 * and redo methods to call super.undo() and super.redo().
 */
package bi.view.undomanagementviews.undoviews;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import bi.view.actions.CMAction;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.undoviews.CMUndoMediator.FocusInfo;

public class CMComponentAwareEdit extends AbstractUndoableEdit {
    private CMUndoMediator.FocusInfo focusInfo;
	protected CMFrameView m_CMFrameView;
    /**
     * <code>UndoManager</code> doesn't finish of doing <code>undo</code> until it find and
     * <code>UndoableEdit</code> object marked with <code>significant</code> to <code>true</code>.
     * By default it should be <code>true</code>
     */
    protected boolean significant = true;

    public CMComponentAwareEdit(CMUndoMediator.FocusInfo focusInfo, CMFrameView p_CMFrameView) {
        this.focusInfo = focusInfo;
        this.m_CMFrameView = p_CMFrameView;
    }
    
    /**
     * <b>Warning:</b> the use of this constructor causes that <code>focusInfo</code> and 
     * <code>m_FrameView</code> are in <code>null</code>, so be care.
     * Use it when you want to mark this object in insignificant state, i. e., <code>significant</code> in 
     * <code>false</code>, so that you don't need to use  <code>focusInfo</code> and <code>m_FrameView</code>.
     * @see #setSignificant(boolean)
     */
    public CMComponentAwareEdit(boolean significant) {
        this.significant = significant;
        this.focusInfo = null;
        this.m_CMFrameView = null;
    }

    /**
	 * @param p_focusInfo
	 */
	public CMComponentAwareEdit(FocusInfo p_focusInfo) {
		 this.focusInfo = p_focusInfo;
	        this.m_CMFrameView = CMApplication.frame;
	}

	/**
	 * 
	 */
	public CMComponentAwareEdit() {
		// TODO Auto-generated constructor stub
		this.focusInfo = CMApplication.frame.getCurrentFocusInfo();
		this.m_CMFrameView = CMApplication.frame;
	}

	public void undo() throws CannotUndoException {
        if (focusInfo != null) {
            focusInfo.doFocus();
        }        
    }

    public void redo() throws CannotRedoException {
        if (focusInfo != null) {
            focusInfo.doFocus();
        }        
    }
    public boolean canUndo() {return true;}
    public boolean canRedo() {return true;}

	public void setUndoState() {
      if( m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() == 0){
        CMAction.UNDO.setEnabled(false);
      }
      else{
    	  CMAction.UNDO.setEnabled(true);
      }
    }

    public void setRedoState() {
	  if(m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects() == 0){
		  CMAction.REDO.setEnabled(false);
      }
      else{
    	  CMAction.REDO.setEnabled(true);
      }
    }
    
    /**
     * @return Returns the significant.
     */
    public boolean isSignificant() {
        return significant;
    }
    /**
     * By default <code>significant</code> is <code>true</code>. So if you set it in <code>false</code>,
     * it will cause that <code>UndoManager</code> continues doing undo/redo with the next 
     * <code>UndoableEdit</code> object. This is mainly done to support the undo/redo of several 
     * <code>UndoableEdit</code> objects.
     * @param significant The significant to set.
     */
    public void setSignificant(boolean significant) {
        this.significant = significant;
    }
    
    /**
     * @return Returns the focusInfo.
     */
    public CMUndoMediator.FocusInfo getFocusInfo() {
        return focusInfo;
    }
    /**
     * @param focusInfo The focusInfo to set.
     */
    public void setFocusInfo(CMUndoMediator.FocusInfo focusInfo) {
        this.focusInfo = focusInfo;
    }
    
    /**
     * @return Returns the m_CMFrameView.
     */
    public CMFrameView getCMFrameView() {
        return m_CMFrameView;
    }
    /**
     * @param frameView The m_CMFrameView to set.
     */
    public void setCMFrameView(CMFrameView frameView) {
        m_CMFrameView = frameView;
    }
    
}
