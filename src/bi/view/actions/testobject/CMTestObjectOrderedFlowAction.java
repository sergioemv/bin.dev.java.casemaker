package bi.view.actions.testobject;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import model.BusinessRules;
import model.TestObject;

import bi.controller.SessionManager;
import bi.controller.StructureManager;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.treeviews.nodes.CMTestObjectNode;

public class CMTestObjectOrderedFlowAction extends AbstractAction implements
		CMEnabledAction {
	private boolean selected;
public CMTestObjectOrderedFlowAction() {
	// TODO Auto-generated constructor stub
	super(CMMessages.getString("TESTOBJECT_PANEL_CHECKBOX_ORIGIN_TITLE"));
}
	public boolean calculateEnabled() {
		if (StructureManager.getSelectedStructure()==null) return false;
		selected = StructureManager.getSelectedStructure().getTestObject().getOrigin()!=null;
		CMTestObjectNode testObjectNodeInfo = CMApplication.frame.getTreeWorkspaceView().getSelectedTestObjectNodeInfo();
		CMProjectNode projectNode = (CMProjectNode) CMApplication.frame.getTreeWorkspaceView().getCurrentProjectNode();
		try{
		boolean testObjectCHOutSameUser = testObjectNodeInfo.getTestObject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) &&
		testObjectNodeInfo.getTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());

		boolean projectChOutSameUser = projectNode.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)&&
		projectNode.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());
		return selected && testObjectCHOutSameUser && projectChOutSameUser ;
		}catch (Exception e)
		{ return false;}
		
	}

	public void actionPerformed(ActionEvent e) {
		if (!calculateEnabled()) return;
		TestObject testObject = StructureManager.getSelectedStructure().getTestObject();
		if (selected){
			 int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_UNCHECK_TESTOBJECT_ORIGIN"),
					 testObject+" - "+CMMessages.getString("LABEL_UNCHECK_ORIGIN"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
	    	  if (confirmation == JOptionPane.YES_OPTION){
	    		  testObject.setOrigin(null);
	    		  selected = false;
	    	  }
	    	  if (e.getSource() instanceof JCheckBox)
	    		  ((JCheckBox)e.getSource()).setSelected(selected);
		}
		}
			
	}


