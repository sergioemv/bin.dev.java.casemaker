package bi.view.cells.editors;

import java.util.Iterator;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import model.Element;
import model.ObjectTypes;
import model.Technology;
import model.edit.CMModelEditFactory;
import bi.controller.ToolVendorManager;
import bi.view.cells.CMBaseCell;
import bi.view.cells.CMCellElement;
import bi.view.cells.CMCellElementGuiObject;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMBaseJComboBox;

public class CMGridCellEditorElementGuiObject extends CMBaseGridCellEditor {

	/**
	 *
	 */
	private static final long serialVersionUID = 1429923004956960591L;

	/**
	 *  The combo that is showed
	 */
	private CMBaseJComboBox objectTypeComboBox = null;

	private String m_TechnologyName = "";

	private String m_toolVendor = "";

	private ToolVendorManager toolVendorManager;

	/**
	 *  The model assigned to te combo
	 */
	private ComboBoxModel combomodel = new DefaultComboBoxModel() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		Object selectedObject;

		public void setSelectedItem(Object anObject) {
			Object object = anObject;
			if (object instanceof CMCellElementGuiObject) {
				for (int i = 0; i < this.getSize(); i++)
					if (((CMCellElementGuiObject) anObject).getElement()
							.getGUIObjectName().equalsIgnoreCase(
									((ObjectTypes) this.getElementAt(i))
											.getM_Name()))
						object = this.getElementAt(i);
			}

			if (object != selectedObject)
				selectedObject = object;
		}

		public Object getSelectedItem() {

			return selectedObject;
		}
	};

	public CMGridCellEditorElementGuiObject(CMBaseJComboBox arg0) {
		super(arg0, null);
		objectTypeComboBox = arg0;
		objectTypeComboBox.setModel(combomodel);

	}

	/* (non-Javadoc)
	 * @see com.eliad.model.defaults.DefaultGridCellEditor#stopCellEditing()
	 */
	public boolean stopCellEditing() {
		//get the element edited
		Element element = ((CMCellElement) getCell()).getElement();
		//get the new value from the combo
		int newGUIObject = objectTypeComboBox.getSelectedIndex();
		//get the new name stored in the combo
		String newStateNameOT = objectTypeComboBox.getSelectedItem().toString();
		//get the old name stored in the element
		String oldStateNameOT = element.getGUIObjectName();
		//if they are different then make the change
		if (!newStateNameOT.equalsIgnoreCase(oldStateNameOT)) super.stopCellEditing();
		//call the undo mediator
		CMUndoMediator.getInstance().doEdit(CMModelEditFactory.INSTANCE.createChangeGUIObjectModelEdit(element, newGUIObject));
		element.setGUIObject(newGUIObject);
		// stop the edition
		return super.stopCellEditing();
	}

	/**
	 * @author smoreno
	 * Initialize the combo editor with the values of the technology of the test
	 * object
	 * @param m_Technology
	 * The technology to filter the object types
	 */
	public void initialize(Technology p_Technology) {
		objectTypeComboBox.removeAllItems();

		if (p_Technology != null) {

			for (Iterator i = p_Technology.getM_ObjectTypesValue().iterator(); i
					.hasNext();) {

				objectTypeComboBox.addItem((ObjectTypes) i.next());
			}

		}

	}

	/* (non-Javadoc)
	 * @see bi.view.cells.editors.CMBaseGridCellEditor#setCell(bi.view.cells.CMBaseCell)
	 */
	public void setCell(CMBaseCell cell) {

		super.setCell(cell);
		if (getCell() != null)
			// initialize the values of the technology if the technology has
			// changed or the technology is empty
			if ((!((Element) getCell().getModel()).getLnkStructure()
					.getTestObject().getToolVendorTechnology()
					.equalsIgnoreCase(m_TechnologyName))
					|| (!((Element) getCell().getModel()).getLnkStructure()
							.getTestObject().getToolVendor()
							.equalsIgnoreCase(m_toolVendor))) {
				//instance the toolvendor manager
				toolVendorManager = ToolVendorManager.INSTANCE;
				// get the technology name
				m_TechnologyName = ((Element) getCell().getModel())
						.getLnkStructure().getTestObject()
						.getToolVendorTechnology();
				//get the tool vendor
				m_toolVendor = ((Element) getCell().getModel())
						.getLnkStructure().getTestObject().getToolVendor();
				//if they are not null
				if ((!m_TechnologyName.equalsIgnoreCase(""))
						&& (!m_toolVendor.equalsIgnoreCase("")))
					//initialize the combo
					initialize(toolVendorManager.findTechnologyByName(
							m_toolVendor, m_TechnologyName));
			}
		// syncronize the selection
		combomodel.setSelectedItem(cell);
	}
}
