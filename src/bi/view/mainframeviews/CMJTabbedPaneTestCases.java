//hmendez_08102005_begin
package bi.view.mainframeviews;

import java.awt.Cursor;
import java.util.List;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BusinessRules;
import bi.controller.TestCaseManager;
import bi.view.resultscomparationviews.CMResultsComparationPanelView;
import bi.view.treeviews.nodes.CMBusinessRulesNode;
import bi.view.treeviews.nodes.CMResultsComparationNode;
import bi.view.treeviews.nodes.CMTestCasesNode;
import bi.view.treeviews.nodes.CMTestDataNode;
import bi.view.treeviews.nodes.CMTestManagementNode;
import bi.view.utils.CMBaseJTabbedPane;

import com.eliad.swing.JSmartGrid;

public class CMJTabbedPaneTestCases extends CMBaseJTabbedPane {
    private CMFrameView m_FrameView;
    private boolean testCasesModified =false;
	public CMJTabbedPaneTestCases(CMFrameView p_FrameView) {
	m_FrameView = p_FrameView;
	this.addChangeListener(new ChangeListener(){
		public void stateChanged(ChangeEvent e) {

			//if the test cases are modified reload all the grids
			try {
				if(m_FrameView.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestCasesNode){
					
						if (testCasesModified)
						{
							m_FrameView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							TestCaseManager.INSTANCE.reloadTestCaseViews();
							setTestCasesModified(false);
							m_FrameView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					}
					
					
				
			
			} catch (Exception exc) {
				// do nothing
			}
			
		}
		
	});
	}
	public List getOrder() {
   	   Vector componentList = new Vector();
   	   if (m_FrameView.getPanelWorkspaceView().isShowing())
   		   return m_FrameView.getPanelWorkspaceView().getOrder();
   	   if (m_FrameView.getPanelProjectView().isShowing()){
   		   m_FrameView.initializeTheUndoManager();
   		   return m_FrameView.getPanelProjectView().getOrder();
   	   }
   	   if (m_FrameView.getPanelTestObjectView().isShowing())
   		   return m_FrameView.getPanelTestObjectView().getOrder();
   		   
   	   //if (!m_FrameView.getPanelTestObjectView().getJRadioButtonCheckedIn().isSelected())
   	if(m_FrameView.getTreeWorkspaceView().getCurrentTestObject()!= null && m_FrameView.getTreeWorkspaceView().getCurrentTestObject().getAccessState() != BusinessRules.ACCESS_STATE_CHECKED_IN)
//hmendez_08102005_end   		   
//Hcanedo_08102005_Begin
   	   if(m_FrameView.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestDataNode){
   		//if(m_FrameView.getTreeWorkspaceView().getCurrentTestObject().getM_AccessState() != BusinessRules.ACCESS_STATE_CHECKED_IN)
   		switch (this.getSelectedIndex()) {
		case 0:
			JSmartGrid structureDescription=m_FrameView.getPanelTDStructureView().getScrollPaneStructureDescriptionView().getM_cmStructureView();
			JSmartGrid structure=m_FrameView.getGridTDStructure();
			if(structureDescription != null && structureDescription.getRowCount()>0)
			componentList.add(structureDescription);
			if(structure != null && structure.getRowCount()>0)
			componentList.add(structure);
			break;
		case 1:
			JSmartGrid testdataDescription=m_FrameView.getPanelTestDataView().getM_CMScrollPaneTestDataCombinationsDescription().getM_CMTestDataCombinationView();
			JSmartGrid structureDescription2=m_FrameView.getPanelTestDataView().getM_CMScrollpaneStructureDescription().getM_cmStructureView();
			JSmartGrid structure2=m_FrameView.getPanelTestDataView().getM_CMGridTDStructure();
			if(testdataDescription != null && testdataDescription.getRowCount()>0)
			componentList.add(testdataDescription);
			if(structureDescription2 != null && structureDescription2.getRowCount()>0)
			componentList.add(structureDescription2);
			if(structure2 != null && structure2.getRowCount()>0)
			componentList.add(structure2);
			break;
		case 2:
			JSmartGrid testdataset=m_FrameView.getPanelTestDataSetView().getM_CMScrollPaneTestDataSetDescription().getM_CMTestDataSetView();
			JSmartGrid testdata=m_FrameView.getPanelTestDataSetView().getM_CMScrollPaneTestDataDescription().getM_CMTestDataCombinationView();
			if(testdataset != null && testdataset.getRowCount()>0)
			componentList.add(testdataset);
			if(testdata != null && testdata.getRowCount()>0)
			componentList.add(testdata);
			break;
			
		case 3:
			JSmartGrid outputs=m_FrameView.getPanelTestDataSetReportsView().getM_CMGridOutputs();
			if(outputs != null && outputs.getRowCount()>0)
			componentList.add(m_FrameView.getPanelTestDataSetReportsView().getM_CMGridOutputs());
			break;
		case 4: 
			componentList.addAll(m_FrameView.getPanelVariablesView().getOrder());
		default:
			break;
		}
   	   }
   	   else if(m_FrameView.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMResultsComparationNode){
   		switch (this.getSelectedIndex()) {
		case 0:
			CMResultsComparationPanelView resultComarisonpanel=m_FrameView.getPanelResultComparation();
			JSmartGrid testdatasetActual=resultComarisonpanel.getJScrollPaneTestDataSetActual().getM_CMTestDataSetView();
			JSmartGrid testdatasetTarget=resultComarisonpanel.getJScrollPaneTestDataSetTarget().getM_CMTestDataSetView();
			JSmartGrid testDataActual=resultComarisonpanel.getJScrollPaneTestDataActual().getM_CMTestDataCombinationView();
			JSmartGrid testDataTarget=resultComarisonpanel.getJScrollPaneTestDataTarget().getM_CMTestDataCombinationView();
			JSmartGrid structureDescriptionActual=resultComarisonpanel.getJScrollPaneStructureHeaderActual().getM_cmStructureView();
			JSmartGrid structureDescriptionTarget=resultComarisonpanel.getJScrollPaneStructureHeaderTarget().getM_cmStructureView();
			JSmartGrid gridStructureActual=resultComarisonpanel.getGridTDStructureActual();
			JSmartGrid gridStructureTarget=resultComarisonpanel.getGridTDStructureTarget();
			
			if(testdatasetTarget != null && testdatasetTarget.getRowCount()>0)
				componentList.add(testdatasetTarget);
			if(testdatasetActual != null && testdatasetActual.getRowCount()>0)
				componentList.add(testdatasetActual);
			
			if(testDataTarget != null && testDataTarget.getRowCount()>0)
				componentList.add(testDataTarget);
			if(testDataActual != null && testDataActual.getRowCount()>0)
				componentList.add(testDataActual);
			
			if(structureDescriptionTarget != null && structureDescriptionTarget.getRowCount()>0)
				componentList.add(structureDescriptionTarget);
			if(structureDescriptionActual != null && structureDescriptionActual.getRowCount()>0)
				componentList.add(structureDescriptionActual);
			
			if(gridStructureTarget != null && gridStructureTarget.getRowCount()>0)
				componentList.add(gridStructureTarget);
			if(gridStructureActual != null && gridStructureActual.getRowCount()>0)
				componentList.add(gridStructureActual);
			
			/*if(resultComarisonpanel.getJEditorPane1()!= null){
				componentList.add(resultComarisonpanel.getJEditorPane1());
			}*/
			break;
		default:
			break;
		}
   	   }
//   	HCanedo_08102005_end
//hmendez_10102005_begin   	   
   	   else if(m_FrameView.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestCasesNode){

   	   	   switch (this.getSelectedIndex()){
   	   	   //Tab1
   	   	   case 0:
   	   		   if (m_FrameView.getElementsGrid().getRowCount() > 0)
   	   		   {
   	   		     componentList.add(m_FrameView.getElementsGrid());
   	   		   }
   	   		   break;
   	  	   //tab 2
   		   case 1:
   			   if (m_FrameView.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().getRowCount()>0)
   			     componentList.add(m_FrameView.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView());
   		   	  break;
   	 	   //tab 3
   		   case 2:
   			   JSmartGrid structureCMGridDependencies=m_FrameView.getPanelDependencyCombiView().getCMGridDependencies();
   			   JSmartGrid structureCMDependency=m_FrameView.getPanelDependencyCombiView().getM_CMDependencyElementView();
   			   JSmartGrid structureCMCombinationViews=m_FrameView.getPanelDependencyCombiView().getM_CMCombinationViews();
   				if(structureCMGridDependencies != null && structureCMGridDependencies.getRowCount()>0)
   			      componentList.add(m_FrameView.getPanelDependencyCombiView().getCMGridDependencies());
   				if(structureCMDependency != null && structureCMDependency.getRowCount()>0)
   		          componentList.add(m_FrameView.getPanelDependencyCombiView().getM_CMDependencyElementView());
   				if(structureCMCombinationViews != null && structureCMCombinationViews.getColumnCount()>0)			
   		          componentList.add(m_FrameView.getPanelDependencyCombiView().getM_CMCombinationViews());	          
   		   break;
   	 	   //tab 3	   
   		   case 3:
   			   if (m_FrameView.getJSPlitPaneTestCaseStructureView().getM_CMDescriptionTestCaseViews().getRowCount()>0)
   			     componentList.add(m_FrameView.getJSPlitPaneTestCaseStructureView().getM_CMDescriptionTestCaseViews());
   			   if (m_FrameView.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMElementViews().getRowCount()>0)
   			     componentList.add(m_FrameView.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMElementViews());
   			   if (m_FrameView.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().getColumnCount()>0)
   			     componentList.add(m_FrameView.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews());		   
   		   break;
   		   //tab 3		   
   		   case 4:
   			   if (m_FrameView.getJSplitPaneStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().getRowCount()>0)
   			     componentList.add(m_FrameView.getJSplitPaneStdCombinationStructureView().getM_CMDescriptionStdCombinationViews());
   			   if (m_FrameView.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMElementViews().getRowCount()>0)
   			     componentList.add(m_FrameView.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMElementViews());
   			   if (m_FrameView.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().getColumnCount()>0)
   			     componentList.add(m_FrameView.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews());		   
   		   break;
   	     }
//hmendez_10102005_end  
//hmendez_10102005_begin
//Hcanedo_08102005_begin   	   
   	   }
   	   else if(m_FrameView.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMBusinessRulesNode){
   	
   		   componentList.add(m_FrameView.getBusinessRulesPanelView().getFileFormatCombo());
   		   componentList.add(m_FrameView.getBusinessRulesPanelView().getFontName());
   		   componentList.add(m_FrameView.getBusinessRulesPanelView().getFontSize());
   		   componentList.add(m_FrameView.getBusinessRulesPanelView().getCMBusinessRuleEditor().getJTextPaneEditor());//HCanedo_27032006 change jTextArea
   	   }
//Hcanedo_08102005_end
//hmendez_12102005begin
   	   else if(m_FrameView.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestManagementNode){
   		   if (m_FrameView.getErrorScrollView().getM_CMErrorGridView().getRowCount()>0)
   		     componentList.add(m_FrameView.getErrorScrollView().getM_CMErrorGridView());
   	   } 	   
//hmendez_12102005end   	   
   	 componentList.add(m_FrameView.getTreeWorkspaceView());
     return componentList;
	}
	public boolean isTestCasesModified() {
		return testCasesModified;
	}
	public void setTestCasesModified(boolean testCasesModified) {
		this.testCasesModified = testCasesModified;
	}

}
//hmendez_08102005_end