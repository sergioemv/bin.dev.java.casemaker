package bi.view.cells.editors;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.ITypeData;
import model.StructureTestData;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellTDStructureName;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
/**
 * @author ccastedo 
 * 	
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureName extends CMBaseGridCellEditor {

	
	public CMGridCellEditorTDStructureName() {
		super(new JTextField(),null);
		
	}
	
	public boolean stopCellEditing() {
		
		
		if (getCell()!=null)
		{
		   CMCellTDStructureName cellName =  (CMCellTDStructureName) getCell();	
		   ITypeData typedata = ((CMGridTDStructure) getCell().getGrid()).getSelectedTypeData();
		  // get the new name for the typedata
		  String value = ((JTextField)this.getComponent()).getText();
		  //get the old name for the typedata
		  String oldValue = typedata.getName();
		  int row =  ((CMGridTDStructure) cellName.getGrid()).getSelectionModel().getLeadRow();
		  int column =  ((CMGridTDStructure) cellName.getGrid()).getSelectionModel().getLeadColumn();
		  ((CMGridTDStructure) cellName.getGrid()).setRowSelected(row);
		  ((CMGridTDStructure) cellName.getGrid()).setColumnSelected(column);
			
		  if (typedata.getStructureTestData()!=null)
			  if (!value.equalsIgnoreCase(oldValue)){
				     
					  StructureTestData std =((ITypeData) getCell().getModel()).getStructureTestData();
					  int indexTyp = ((CMGridTDStructure) getCell().getGrid()).getCmGridModel().numOfCell(((CMGridTDStructure) getCell().getGrid()).getSelectionModel().getLeadRow());
					  
					  if (CMGridTDStructure.uniqueValueFieldName(std, value, getCell(), indexTyp))
						 
					  {
						  UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeNameInTypeDataModelEdit(typedata, value);
						  typedata.setName(value);
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
