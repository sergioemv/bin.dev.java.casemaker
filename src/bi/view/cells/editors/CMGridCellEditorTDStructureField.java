package bi.view.cells.editors;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.ITypeData;
import model.StructureTestData;
import model.edit.CMModelEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
/**
 * @author ccastedo 
 * 	
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureField extends CMBaseGridCellEditor {

	
	public CMGridCellEditorTDStructureField() {
		super(new JTextField(),null);
		
	}
	
	public boolean stopCellEditing() {
		
		
		if (getCell()!=null)
		{
		  		
		   ITypeData typedata = ((CMGridTDStructure) getCell().getGrid()).getSelectedTypeData();
		  // get the new field for the typedata
		  String value = ((JTextField)this.getComponent()).getText();
		  //get the old field for the typedata
		  String oldValue = typedata.getField();
		  
		  if (typedata.getStructureTestData()!=null)
			  if (!value.equalsIgnoreCase(oldValue)){
				     
					  StructureTestData std =((ITypeData) getCell().getModel()).getStructureTestData();
					  int indexTyp = ((CMGridTDStructure) getCell().getGrid()).getCmGridModel().numOfCell(((CMGridTDStructure) getCell().getGrid()).getSelectionModel().getLeadRow());
					  
					  if (CMGridTDStructure.uniqueValueFieldName(std, value, getCell(), indexTyp))
						 
					  {
						  UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeFieldInTypeDataModelEdit(typedata, value);
						  typedata.setField(value);
						  CMUndoMediator.getInstance().doEdit(ue);
					  }
					  else
						  
					  {   //stop the edtion
						  boolean flag = super.stopCellEditing();
						  //show the message
						  JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("TESTDATA_NAME_FIELD_EQUALS_MENSSAGE_ERROR"),
			                        CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$

						  return flag;
					  }
					 	
				}
		  
		}
		//finally stop the edition
		return super.stopCellEditing();
	}
	

}
