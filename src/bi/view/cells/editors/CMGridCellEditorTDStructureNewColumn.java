package bi.view.cells.editors;

import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.ITypeData;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellTDStructureNewColumn;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
/**
 * @author ccastedo 
 * 	
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureNewColumn extends CMBaseGridCellEditor {

	
	public CMGridCellEditorTDStructureNewColumn() {
		super(new JTextField(),null);
		
	}
	
	public boolean stopCellEditing() {		
		
		if (getCell()!=null)
		{
			CMCellTDStructureNewColumn cellNewColumn =  (CMCellTDStructureNewColumn) getCell();	
			int row =  ((CMGridTDStructure) cellNewColumn.getGrid()).getSelectionModel().getLeadRow();
			int column =  ((CMGridTDStructure) cellNewColumn.getGrid()).getSelectionModel().getLeadColumn();
			((CMGridTDStructure) cellNewColumn.getGrid()).setRowSelected(row);
			((CMGridTDStructure) cellNewColumn.getGrid()).setColumnSelected(column);
			
		   ITypeData typedata = ((CMGridTDStructure) getCell().getGrid()).getSelectedTypeData();
		  // get the new newcolumn value for the typedata
		  String value = ((JTextField)this.getComponent()).getText();	 
		  
          int i= column-5;
          //get the old newcolumn value for the typedata
          String oldValue = typedata.getNewColumns().elementAt(i).toString();
          
		  if (typedata.getStructureTestData()!=null)
			  if (!value.equalsIgnoreCase(oldValue)){
				  UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeNewColumnInTypeDataModelEdit(typedata, value,column);
				  Vector newcolumns = typedata.getNewColumns();
				  newcolumns.setElementAt(value.toString(), i);
				  typedata.setNewColumns(newcolumns);
				  
				  CMUndoMediator.getInstance().doEdit(ue);					 	
				}
		  
		}
		//finally stop the edition
		return super.stopCellEditing();
	}
	

}
