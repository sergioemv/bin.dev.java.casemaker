package bi.view.cells.editors;

import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.ITypeData;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellTDStructureLength;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
/**
 * @author ccastedo 
 * 	
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureLength extends CMBaseGridCellEditor {

	
	public CMGridCellEditorTDStructureLength() {
		super(new JTextField(),null);
		
	}
	
	public boolean stopCellEditing() {		
		
		if (getCell()!=null)
		{
			CMCellTDStructureLength cellLength =  (CMCellTDStructureLength) getCell();	
			int row =  ((CMGridTDStructure) cellLength.getGrid()).getSelectionModel().getLeadRow();
			int column =  ((CMGridTDStructure) cellLength.getGrid()).getSelectionModel().getLeadColumn();
			((CMGridTDStructure) cellLength.getGrid()).setRowSelected(row);
			((CMGridTDStructure) cellLength.getGrid()).setColumnSelected(column);
			  
		   ITypeData typedata = ((CMGridTDStructure) getCell().getGrid()).getSelectedTypeData();
		  // get the new length for the typedata
		  String value = ((JTextField)this.getComponent()).getText();
		  //get the old length for the typedata
		  String oldValue = typedata.getLength();
		  
		  if (typedata.getStructureTestData()!=null)
			  if (!value.equalsIgnoreCase(oldValue)){
				  UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit(typedata, value);
				  typedata.setLength(value);
				  CMUndoMediator.getInstance().doEdit(ue);					 	
				}
		  
		}
		//finally stop the edition
		return super.stopCellEditing();
	}
	

}
