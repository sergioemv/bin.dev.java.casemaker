package bi.view.mainframeviews;

import java.awt.KeyEventDispatcher;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JTabbedPane;

import bi.view.actions.CMAction;
import bi.view.causeeffectviews.CMCauseEffectStructureGridView;
import bi.view.cells.CMCellEmpty;
import bi.view.dependencycombinationviews.CMCombinationViews;
import bi.view.dependencycombinationviews.CMDependencyElementViews;
import bi.view.dependencycombinationviews.CMGridDependencies;
import bi.view.dependencycombinationviews.CMPanelDependencies;
import bi.view.elementviews.CMElementViews;
import bi.view.elementviews.CMGridElementDependCombiKeyEventIntercepter;
import bi.view.elementviews.CMGridElements;
import bi.view.elementviews.CMGridElementsKeyEventIntercepter;
import bi.view.errorviews.CMErrorGridView;
import bi.view.stdcombinationviews.CMDescriptionStdCombinationViews;
import bi.view.stdcombinationviews.CMStdCombinationViews;
import bi.view.testcaseviews.CMDescriptionTestCaseViews;
import bi.view.testcaseviews.CMTestCaseViews;
import bi.view.testdataviews.CMDeleteKeyInterceptor;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.treeviews.CMTreeWorkspaceView;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMPopupMenuKeyIntercepter;
import bi.view.workspaceviews.CMKeyIntercepterWorkspaceView;


/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMKeyEventIntercepter implements KeyEventDispatcher {
    private CMFrameView m_CMFrameView;
    private CMGridElements m_CMGridElements;
    private CMUndoMediator m_CMUndoMediator;
    private CMPanelTestDataView m_CMPanelTestDataView;
    private CMCauseEffectStructureGridView m_CMCauseEffectStructureGridView;
    private CMGridDependencies m_CMGridDependencies;
    private CMPanelDependencies m_CMPanelDependencies;
    private CMCombinationViews m_CMCombinationViews;
    private CMTestCaseViews m_CMTestCaseViews;
	private CMDescriptionTestCaseViews m_CMDescriptionTestCaseViews;
    private CMStdCombinationViews m_CMStdCombinationViews;
    private CMDescriptionStdCombinationViews m_CMDescriptionStdCombinationViews;
    private CMErrorGridView m_CMErrorGridView;
    //fcastro_20092004_begin
    private int combinationHeaderRowSelected = -1;
	private int columnSelectedInCMDependencyElementViews = -1;
    private int testCaseHeaderRowSelected = -1;
    private int stdCombinationHeaderRowSelected = -1;
    private int columnSelectedInTestCaseElementViews = -1;
    private int columnSelectedInStdCombinationElementViews = -1;
    private CMDependencyElementViews m_CMDependencyElementViews;
    private CMElementViews  m_TestCasesElementViews;
    private CMElementViews m_StdCombinationsElementViews;
    //fcastro_20092004_end
    private CMGridTDStructure m_GridTDStructure;//svonborries_17102005


    public CMKeyEventIntercepter(CMFrameView p_CMFrameView) {
        m_CMFrameView = p_CMFrameView;

    }

    //KeyListener Methods. To support keyboard shortcuts.
    //Smoreno -- this method must be refactored!!!!!!!
    public boolean dispatchKeyEvent(KeyEvent e) {

        if ((e.getID() == KeyEvent.KEY_PRESSED)&&(this.m_CMFrameView.isFocused())) {

             if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            	 //svonborries_14112005_begin
            	 if(!this.m_CMFrameView.isIsPanelResultComparationSelected()){
            		 if (e.getSource() instanceof CMBaseJComboBox){
            			 CMBaseJComboBox cmb = (CMBaseJComboBox)e.getSource();
            			 if(cmb.isRetainFocus()){return false;}
            		 }else{
            			 if(e.getSource() instanceof CMGridTDStructure){
            				 this.m_GridTDStructure = (CMGridTDStructure) e.getSource();
                 			//int l_Row = m_GridTDStructure.getSelectionModel().getLeadRow()-1;
                 			//int l_Colum = m_GridTDStructure.getSelectionModel().getLeadColumn();
                 				//if(!(m_GridTDStructure.getCmGridModel().isCellEditable(l_Row+1,l_Colum))){
                 					CMDeleteKeyInterceptor l_DeleteKeyInterceptor = new CMDeleteKeyInterceptor(this.m_CMFrameView);
                     				return l_DeleteKeyInterceptor.dispatchKeyEvent(e);
             			 }
            			 
            			}

            	 }//svonborries_14112005_end
 
				if(e.getSource()==m_CMCauseEffectStructureGridView){
                    if(m_CMCauseEffectStructureGridView.isDeletePossible()){
                        CMAction.EFFECT_DELETE.getAction().actionPerformed(null);
                    }
                    else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                    return true;
                }
                else if(e.getSource()==m_CMGridDependencies){
					if(m_CMGridDependencies.isDeletePossible()){
						CMAction.DEPENDENCY_DELETE.getAction().actionPerformed(null);
                    }
                    else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                    return true;
                }
                else if(e.getSource()==m_CMCombinationViews){
                    if(m_CMCombinationViews.isDeletePossible()){
						CMAction.COMBINATION_DELETE.getAction().actionPerformed(null);
                    }
                    else{
                        Toolkit.getDefaultToolkit().beep();
                    }
					return true;
                }
                else if(e.getSource()==m_CMTestCaseViews){
					if(m_CMTestCaseViews.isDeletePossible()){
                        CMAction.TESTCASE_DELETE.getAction().actionPerformed(null);
                    }
                    else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                    return true;
                }
				else if(e.getSource()==m_CMDescriptionTestCaseViews){

					CMAction.TESTCASE_DELETE.getAction().actionPerformed(null);
                    return true;
                }
                else if(e.getSource()== m_CMStdCombinationViews){
                    if(m_CMStdCombinationViews.isDeletePossible()){
                        CMAction.STDCOMBINATION_DELETE.getAction().actionPerformed(null);
                    }
                    else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                    return true;
                }
                else if(e.getSource()==m_CMDescriptionStdCombinationViews){
                    if(m_CMDescriptionStdCombinationViews.isDeletePossible()){
                        CMAction.STDCOMBINATION_DELETE.getAction().actionPerformed(null);
                    }
                    else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                    return true;
                }
                else if(e.getSource()==m_CMErrorGridView){
                          CMAction.ERROR_DELETE.getAction().actionPerformed(null);
                    /*}
                    else{
                        Toolkit.getDefaultToolkit().beep();
                    }*/
                    return true;
                }
            //hmendez04102005begin
              /* else{
                    return false;
                }*/
            //hmendez04102005end
            }


            else if(e.getKeyCode()!=java.awt.event.KeyEvent.VK_TAB){

//          CCastedo begin 06-10-05
            if((e.getSource()==m_CMPanelDependencies || e.getSource() == m_CMCombinationViews
            		|| e.getSource() == m_CMGridDependencies || e.getSource() == m_CMDependencyElementViews//|| isTabbedPaneSelected(m_CMFrameView.getm_TabbedPaneView(),CMMessages.getString("TAB_LABEL_DEPENDENCIES_COMBINATIONS"))
            		)&& (!e.isAltDown()) && (!e.isShiftDown())){
            	CMGridElementDependCombiKeyEventIntercepter l_CMGridElementsKeyIntercepter = new CMGridElementDependCombiKeyEventIntercepter(m_CMPanelDependencies,m_CMCombinationViews,m_CMGridDependencies,m_CMFrameView);
            	return l_CMGridElementsKeyIntercepter.dispatchKeyEvent(e);
            }
            else if((e.getSource()==m_CMGridElements)){
            	CMGridElementsKeyEventIntercepter l_CMGridElementsKeyIntercepter = new CMGridElementsKeyEventIntercepter(m_CMGridElements,m_CMFrameView);
            	return l_CMGridElementsKeyIntercepter.dispatchKeyEvent(e);
            }   //CCastedo ends 06-10-05
            //CCastedo begin 06-10-05
            else if((e.getSource()==m_CMTestCaseViews) && (e.isShiftDown())){
            	if (e.getKeyCode()==KeyEvent.VK_F10){
            		int row = m_CMTestCaseViews.getSelectionModel().getLeadRow();
            		int column = m_CMTestCaseViews.getSelectionModel().getLeadColumn();

            		int r = m_CMTestCaseViews.getRowPosition(row);
        			int c = m_CMTestCaseViews.getColumnPosition(column);

                    if ( row == 0 ){
                    	m_CMTestCaseViews.updateCommandos(null);
                    	m_CMFrameView.getJPopupMenuTestCases().show(m_CMTestCaseViews,c,r);
                    }
                    else{
                    	if((m_CMTestCaseViews.getM_Structure().getLnkTestCases().size()>0)&& !(m_CMTestCaseViews.getCMGridModel().getCellObjectAt(row,column)instanceof CMCellEmpty)){
                    		m_CMTestCaseViews.updateCommandos(null);
                        	m_CMFrameView.getJPopupMenuTestCases().show(m_CMTestCaseViews,c,r);
                    	}
                    }
                    return true;
            	}


            }


            }
            //CCastedo ends 06-10-05




            ////////////////////////NO REGISTERED SHORTCUT///////////////////////////////////
            else {

                return false;//don't discard event
            }
        }
        //hmendez_04102005_begin
        ////////////////////////DELEGATION SHORCUT TO WORKSPACE///////////////////////////////////
        else
        	if (e.getSource() instanceof CMTreeWorkspaceView)
        	{
              CMKeyIntercepterWorkspaceView l_cmKeyIntercepterWorkspaceView = new CMKeyIntercepterWorkspaceView(this.m_CMFrameView);
              return l_cmKeyIntercepterWorkspaceView.dispatchKeyEvent(e);

            }
        //hmendez_04102005_end
        ////////////////////////////////NOT KEYPRESSED///////////////////////////////////
        else {
            return false;//don't discard event
        }
        //Ccastedo begins 12-10-05
        if(e.getSource()==m_CMGridElements){
        	CMGridElementsKeyEventIntercepter l_CMGridElementsKeyIntercepter = new CMGridElementsKeyEventIntercepter(m_CMGridElements,m_CMFrameView);
        	return l_CMGridElementsKeyIntercepter.dispatchKeyEvent(e);
        }
        //Ccastedo ends 12-10-05
//      svonborries_07102005_begin
        if (e.getKeyCode()==KeyEvent.VK_F10){
        	if(e.isShiftDown()){
        	CMPopupMenuKeyIntercepter l_PopUpKeyIntercepter = new CMPopupMenuKeyIntercepter(this.m_CMFrameView);
        	return l_PopUpKeyIntercepter.dispatchKeyEvent(e);}
        }
//      svonborries_07102005_end

        //hmendez04102005begin
        return false;
        //hmendez04102005end
    }

    //Ccastedo begins 06-10-05
    public boolean isTabbedPaneSelected(JTabbedPane tabbedPane, String p_TabName)
    {
    	boolean sw=false;
    	int index = tabbedPane.indexOfTab(p_TabName);
        if (index != -1) {
            if (index == tabbedPane.getSelectedIndex()) {
                sw=true;
            }
        }
        return sw;
    }

    //Ccastedo Ends 06-10-05

    //fcastro_20092004_begin

    public void setCombinationHeaderRowSelected(int row){
        this.combinationHeaderRowSelected =row;
    }

    public void setM_CMDependencyElementViews(CMDependencyElementViews view){
        this.m_CMDependencyElementViews =view;
    }

    public void setColumnSelectedInCMDependencyElementViews(int column){
        this.columnSelectedInCMDependencyElementViews =column;
    }
    public void setTestCaseHeaderRowSelected(int row){
        this.testCaseHeaderRowSelected = row;
    }
    public void setM_TestCasesElementViews(CMElementViews elementViews){
        this.m_TestCasesElementViews = elementViews;
    }
    public void setM_StdCombinationsElementViews(CMElementViews elementViews){
        this.m_StdCombinationsElementViews = elementViews;
    }
    public void setColumnSelectedInTestCaseElementViews(int column){
        this.columnSelectedInTestCaseElementViews = column;
    }
    public void setColumnSelectedInStdCombinationElementViews(int column){
        this.columnSelectedInStdCombinationElementViews = column;
    }
    public void setStdCombinationHeaderRowSelected(int row){
        this.stdCombinationHeaderRowSelected = row;
    }
    //fcastro_20092004_end

    public void setM_CMGridElements(CMGridElements m_CMGridElements) {
        this.m_CMGridElements = m_CMGridElements;
    }

    public void setM_CMUndoMediator(CMUndoMediator m_CMUndoMediator) {
        this.m_CMUndoMediator = m_CMUndoMediator;
    }

    public void setM_CMPanelTestDataView(CMPanelTestDataView m_CMPanelTestDataView) {
        this.m_CMPanelTestDataView = m_CMPanelTestDataView;
    }

    public void setM_CMCauseEffectStructureGridView(CMCauseEffectStructureGridView m_CMCauseEffectStructureGridView){
            this.m_CMCauseEffectStructureGridView = m_CMCauseEffectStructureGridView;
        }

    public void setM_CMGridDependencies(CMGridDependencies m_CMGridDependencies){
            this.m_CMGridDependencies = m_CMGridDependencies;
        }
    public void setM_CMPanelDependencies(CMPanelDependencies p_CMPanelDependencies){
        this.m_CMPanelDependencies = p_CMPanelDependencies;
    }
    public void setM_CMCombinationViews(CMCombinationViews p_CMCombinationViews){
        this.m_CMCombinationViews = p_CMCombinationViews;
    }
    public void setM_CMTestCaseViews(CMTestCaseViews p_CMTestCaseViews){
        this.m_CMTestCaseViews = p_CMTestCaseViews;
    }
    public void setM_CMDescriptionTestCaseViews(CMDescriptionTestCaseViews p_CMDescriptionTestCaseViews){
        this.m_CMDescriptionTestCaseViews = p_CMDescriptionTestCaseViews;
    }
    public void setM_CMStdCombinationViews(CMStdCombinationViews p_CMStdCombinationViews){
        this.m_CMStdCombinationViews = p_CMStdCombinationViews;
    }
    public void setM_CMDescriptionStdCombinationViews(CMDescriptionStdCombinationViews p_CMDescriptionStdCombinationViews){
        this.m_CMDescriptionStdCombinationViews =  p_CMDescriptionStdCombinationViews;
    }
    public void setM_CMErrorGridView(CMErrorGridView p_CMErrorGridView){
        this.m_CMErrorGridView = p_CMErrorGridView;
    }

}
