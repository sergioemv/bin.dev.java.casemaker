package bi.view.elementviews;


import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;
import model.Combination;
import model.State;
import bi.controller.CombinationManager;
import bi.controller.editcontrol.CMStateEditController;
import bi.view.actions.CMAction;
import bi.view.dependencycombinationviews.CMCombinationViews;
import bi.view.dependencycombinationviews.CMGridDependencies;
import bi.view.dependencycombinationviews.CMPanelDependencies;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMInformationDetailsDialog;
import bi.view.utils.CMStateView;

//CCastedo begins 06-10-05 
public class CMGridElementDependCombiKeyEventIntercepter{
	 private CMPanelDependencies m_panelDependencies;
	 private CMCombinationViews m_combinationViews = null;	
	
	public CMGridElementDependCombiKeyEventIntercepter(CMPanelDependencies p_panelDependencies, CMCombinationViews p_combinationViews, CMGridDependencies p_gridDependencies, CMFrameView p_frameView) {
		m_panelDependencies = p_panelDependencies;		
		m_combinationViews  = p_combinationViews;		
	}

	

	@SuppressWarnings("static-access")
	public boolean dispatchKeyEvent(KeyEvent e) {	
        
        if(e.getKeyCode()==KeyEvent.VK_N && e.isControlDown()){  
        	
            if ( e.getSource()== m_combinationViews){                	
            	CMAction.COMBINATION_CREATE.getAction().actionPerformed(null);
            }
            else{
            	int sizeCombinations = m_panelDependencies.getM_CMCombinationViews().getColumnCount();
                if (sizeCombinations == 0){
            		
            		int confirmation = JOptionPane.showConfirmDialog(m_panelDependencies,CMMessages.getString("WANTS_TO_CREATE_DEPENDENCY_OR_COMBINATION"),CMMessages.getString("TITLE_CREATE_DEPENDENCY_OR_COMBINATION"),JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
            		
                    if( confirmation == JOptionPane.YES_OPTION) {
                    	CMAction.DEPENDENCY_CREATE.getAction().actionPerformed(null);
                    }
                    else
                    	if (confirmation == JOptionPane.NO_OPTION)
                    		CMAction.COMBINATION_CREATE.getAction().actionPerformed(null);
                    	else
                    		if(confirmation == JOptionPane.CANCEL_OPTION)
                    			;
            	}
                else
                	CMAction.DEPENDENCY_CREATE.getAction().actionPerformed(null);
            }            
        	return true;
        }  
        //ccastedo begins 19.01.07
        if ( e.getSource()== m_combinationViews){
        	int row = ((CMCombinationViews)e.getSource()).getSelectionModel().getLeadRow();
    		int column = ((CMCombinationViews)e.getSource()).getSelectionModel().getLeadColumn();    		
    		if(e.getKeyCode()==KeyEvent.VK_ADD && e.isControlDown()){ 
    			CMCompoundEdit ce = new CMCompoundEdit();
    			ce.addEdit(CombinationManager.INSTANCE.changeCombinationState(State.POSITIVE));
    			ce.addEdit(changeStateInTestCases());
    			((CMCombinationViews)e.getSource()).changeSelection(row, column, false, false);
    			CMUndoMediator.getInstance().doEdit(ce);
    	   		 return true;
    	   	 }
    	   	 if(e.getKeyCode()==KeyEvent.VK_SUBTRACT && e.isControlDown()){  
    	   		CMCompoundEdit ce = new CMCompoundEdit();
    	   		ce.addEdit(CombinationManager.INSTANCE.changeCombinationState(State.NEGATIVE));
    	   		ce.addEdit(changeStateInTestCases());
    	   		((CMCombinationViews)e.getSource()).changeSelection(row, column, false, false);
    	   		CMUndoMediator.getInstance().doEdit(ce);
    	   		 return true;
    	   	 }
    	   	 if(e.getKeyCode()==KeyEvent.VK_I && e.isControlDown()){
    	   		CMCompoundEdit ce = new CMCompoundEdit();
    	   		ce.addEdit(CombinationManager.INSTANCE.changeCombinationState(State.IRRELEVANT));
    	   		ce.addEdit(changeStateInTestCases());
    	   		((CMCombinationViews)e.getSource()).changeSelection(row, column, false, false);
    	   		CMUndoMediator.getInstance().doEdit(ce);
    	   		 return true;
    	   	 }
    	   	 if(e.getKeyCode()==KeyEvent.VK_F && e.isControlDown()){
    	   		CMCompoundEdit ce = new CMCompoundEdit();
    	   		ce.addEdit(CombinationManager.INSTANCE.changeCombinationState(State.FAULTY));
    	   		ce.addEdit(changeStateInTestCases());
    	   		((CMCombinationViews)e.getSource()).changeSelection(row, column, false, false);
    	   		CMUndoMediator.getInstance().doEdit(ce);
    	   		 return true;
    	   	 }
    	   	
        }        
     //ccastedo ends 19.01.07       
    return false;
	}
	
	@SuppressWarnings("static-access")
	private UndoableEdit changeStateInTestCases(){
		CMCompoundEdit ce = new CMCompoundEdit();
		Combination combination = CombinationManager.INSTANCE.getSelectedCombination();
		CMStateEditController stateEditController = new CMStateEditController();  
    	stateEditController.setStateView(new CMStateView());
    	stateEditController.setStateBean(combination);         			
		ce.addEdit(stateEditController.applyChanges());
		if (stateEditController.getWarningMessages().size()>0)
			showWarningMessages(stateEditController.getWarningMessages().toArray());
		return ce;	
	}
	private void showWarningMessages(Object[] messages) {
		CMInformationDetailsDialog dlg = new CMInformationDetailsDialog();
		StringBuilder stringBuilder = new StringBuilder();
		for (Object string : messages){
			stringBuilder.append("- ");
			stringBuilder.append(string);
			stringBuilder.append("\n");
			stringBuilder.append("\n");
		}
		dlg.getJTextPaneMessajes().setText(stringBuilder.toString());
		dlg.setTitle(CMMessages.getString("BUTTON_TOOLTIP_EDIT_COMBINATION"));
		dlg.setVisible(true);
	}
}
