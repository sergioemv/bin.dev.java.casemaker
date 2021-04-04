package bi.view.cells.editors;

import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.ITypeData;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellTDStructurePrefix;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
/**
 * @author ccastedo 
 * 	
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructurePreffix extends CMBaseGridCellEditor {

	
	public CMGridCellEditorTDStructurePreffix() {
		super(new JTextField(),null);
		
	}
	
	public boolean stopCellEditing() {		
		
		if (getCell()!=null)
		{
		   CMCellTDStructurePrefix cellPreffix =  (CMCellTDStructurePrefix) getCell();	
		   int row =  ((CMGridTDStructure) cellPreffix.getGrid()).getSelectionModel().getLeadRow();
		   int column =  ((CMGridTDStructure) cellPreffix.getGrid()).getSelectionModel().getLeadColumn();
		   ((CMGridTDStructure) cellPreffix.getGrid()).setRowSelected(row);
		   ((CMGridTDStructure) cellPreffix.getGrid()).setColumnSelected(column);
		   ITypeData typedata = ((CMGridTDStructure) getCell().getGrid()).getSelectedTypeData();
		  // get the new preffix for the typedata
		  String value = ((JTextField)this.getComponent()).getText();
		  //get the old preffix for the typedata
		  String oldValue = typedata.getPrefix();
		  
		  
		  
		  if (typedata.getStructureTestData()!=null)
			  if (!value.equalsIgnoreCase(oldValue)){
				  UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangePrefixInTypeDataModelEdit(typedata, value);
				  typedata.setPrefix(value);
				  CMUndoMediator.getInstance().doEdit(ue);					 	
				}
		  
		}
		//finally stop the edition
		return super.stopCellEditing();
	}
	

}
