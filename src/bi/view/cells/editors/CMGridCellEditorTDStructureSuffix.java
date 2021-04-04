package bi.view.cells.editors;

import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.ITypeData;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellTDStructureSuffix;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
/**
 * @author ccastedo 
 * 	
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureSuffix extends CMBaseGridCellEditor {

	
	public CMGridCellEditorTDStructureSuffix() {
		super(new JTextField(),null);
		
	}
	
	public boolean stopCellEditing() {
		
		if (getCell()!=null)
		{
		  	CMCellTDStructureSuffix cellSuffix = (CMCellTDStructureSuffix) getCell();
		  	int row =  ((CMGridTDStructure) cellSuffix.getGrid()).getSelectionModel().getLeadRow();
			int column =  ((CMGridTDStructure) cellSuffix.getGrid()).getSelectionModel().getLeadColumn();
			((CMGridTDStructure) cellSuffix.getGrid()).setRowSelected(row);
			((CMGridTDStructure) cellSuffix.getGrid()).setColumnSelected(column);
			  
		   ITypeData typedata = ((CMGridTDStructure) getCell().getGrid()).getSelectedTypeData();
		  // get the new suffix for the typedata
		  String value = ((JTextField)this.getComponent()).getText();
		  //get the old suffix for the typedata
		  String oldValue = typedata.getSuffix();
		 
		  
		  if (typedata.getStructureTestData()!=null)
			  if (!value.equalsIgnoreCase(oldValue)){
				  UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeSuffixInTypeDataModelEdit(typedata, value);
				  typedata.setSuffix(value);
				  CMUndoMediator.getInstance().doEdit(ue);					 	
				}
		  
		}
		//finally stop the edition
		return super.stopCellEditing();
	}
	

}
