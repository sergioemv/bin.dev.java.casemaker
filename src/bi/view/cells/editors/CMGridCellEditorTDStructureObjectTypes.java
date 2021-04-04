package bi.view.cells.editors;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import model.ITypeData;
import model.ObjectTypes;
import model.Technology;
import model.TypeDataGlobal;
import model.edit.CMModelEditFactory;
import bi.controller.ToolVendorManager;
import bi.view.cells.CMBaseCell;
import bi.view.cells.CMCellTDStructureObjectTypes;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMBaseJComboBox;
/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureObjectTypes extends CMBaseGridCellEditor{
/**
 *  The combo that is showed
 */
private CMBaseJComboBox typesCombo = null;

private String m_TechnologyName = "";

private String m_toolVendor = "";

private ToolVendorManager toolVendorManager;


/**
 *  The model assigned to the combo
 */
ComboBoxModel combomodel = new DefaultComboBoxModel();

public CMGridCellEditorTDStructureObjectTypes() {
	super(new CMBaseJComboBox(new Vector(0)), null);
	// initialize the combo
	typesCombo =((CMBaseJComboBox) getComponent());
	typesCombo.setModel(combomodel);
}

/* (non-Javadoc)
 * @see com.eliad.model.defaults.DefaultGridCellEditor#stopCellEditing()
 */
public boolean stopCellEditing() {
	
	CMCellTDStructureObjectTypes cellObjectType =  (CMCellTDStructureObjectTypes) getCell();
	
	initialize(toolVendorManager.findTechnologyByName(
			m_toolVendor, m_TechnologyName));
	
	String newStateNameOT = ((CMBaseJComboBox) this.getComponent()).getSelectedItem().toString();
	int newIndex = ((CMBaseJComboBox) this.getComponent()).getSelectedIndex();
	String oldStateNameOT = cellObjectType.getStateNameOT();
	
	//Logger.getLogger(this.getClass()).debug(arg0)
	int row =  ((CMGridTDStructure) cellObjectType.getGrid()).getSelectionModel().getLeadRow();
	int column =  ((CMGridTDStructure) cellObjectType.getGrid()).getSelectionModel().getLeadColumn();
	((CMGridTDStructure) cellObjectType.getGrid()).setRowSelected(row);
	((CMGridTDStructure) cellObjectType.getGrid()).setColumnSelected(column);
	if (row==-1 || column == -1)
		return super.stopCellEditing();
	
	if (!newStateNameOT.equalsIgnoreCase(oldStateNameOT))
	{
		CMCompoundEdit ce = new CMCompoundEdit();
		ce.addEdit(new CMComponentAwareEdit());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeObjectTypeInTypeDataModelEdit((ITypeData) cellObjectType.getModel(), newIndex));
		((TypeDataGlobal) cellObjectType.getModel()).setStateOT(newIndex);
		CMUndoMediator.getInstance().doEdit(ce);
	}
	this.getComponent().repaint();
	return super.stopCellEditing();
}

/**
 * @author ccastedo
 * Initialize the combo editor with the values of the technology of the test
 * object
 * @param m_Technology
 * The technology to filter the object types
 */
public void initialize(Technology p_Technology) {
	((CMBaseJComboBox) this.getComponent()).removeAllItems();	  
	  
	   if (p_Technology != null) {
		  
			for (Iterator i = p_Technology.getM_ObjectTypesValue().iterator(); i
					.hasNext();) {	
				
				((CMBaseJComboBox) this.getComponent()).addItem((ObjectTypes) i.next());				
			   	
			}

		}
}

/* (non-Javadoc)
 * @see bi.view.cells.editors.CMBaseGridCellEditor#setCell(bi.view.cells.CMBaseCell)
 */
public void setCell(CMBaseCell cell) {

	super.setCell(cell);
	typesCombo.setParent(getCell().getGrid());
	
	if (getCell() != null)
		
		if ((!(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getToolVendorTechnology()
				.equalsIgnoreCase(m_TechnologyName)))
				|| (!(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getToolVendor()
						.equalsIgnoreCase(m_toolVendor)))) {
			
			toolVendorManager = ToolVendorManager.INSTANCE;
			
			m_TechnologyName = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject()
					.getToolVendorTechnology();
			
			m_toolVendor = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getToolVendor();
			
			if ((!m_TechnologyName.equalsIgnoreCase(""))
					&& (!m_toolVendor.equalsIgnoreCase("")))
				
				initialize(toolVendorManager.findTechnologyByName(
						m_toolVendor, m_TechnologyName));
		}
	// syncronize the selection
	combomodel.setSelectedItem(cell);
}
}
