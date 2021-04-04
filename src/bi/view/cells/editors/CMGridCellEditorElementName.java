package bi.view.cells.editors;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;

import model.Element;
import model.edit.CMModelEditFactory;
import bi.controller.ElementManager;
import bi.view.cells.CMCellElement;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMGridCellEditorElementName extends CMBaseGridCellEditor {

	
	public CMGridCellEditorElementName() {
		super(new JTextField(),null);
		
	}
	
	public boolean stopCellEditing() {
		if (getCell()!=null)
		{
		  // get the related element
		  Element element = ((CMCellElement)getCell()).getElement();
		  // get the new name for the element
		  String value = ((JTextField)this.getComponent()).getText();
		  //get the old name for the element
		  String oldValue = element.getName();
		  //if the element is assigned to the structure (otherwise the edition is not valid)
		  if (element.getLnkStructure()!=null)
		  {
		  if (!value.equalsIgnoreCase(oldValue))
	     //vefify that the name is unique within the structure
		  if (!ElementManager.INSTANCE.elementNameExists(element.getLnkStructure(),value))
			  //call the undo mediator to register the edit
			//  CMApplication.frame.getM_CMUndoMediator().editElementName(value, oldValue,element);
		  {
			  UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeNameModelEdit(element, value);
			  element.setName(value);
			  CMUndoMediator.getInstance().doEdit(ue);
		  }
		  else
			  
		  {   //stop the edtion
			  boolean flag = super.stopCellEditing();
			  //show the message
			  JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("CANNOT_CHANGE_ELEMENT_NAME"));
			  return flag;
		  }
		  //CMApplication.frame.getM_CMUndoMediator().editElementName(value, oldValue,element);
	
		  }
		}
		//finally stop the edition
		return super.stopCellEditing();
	}
	

}
