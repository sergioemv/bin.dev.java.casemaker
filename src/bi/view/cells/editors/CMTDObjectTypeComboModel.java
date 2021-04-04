/**
 * 
 */
package bi.view.cells.editors;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import org.apache.log4j.Logger;
import model.ITypeData;
import bi.view.cells.CMCellTDStructureObjectTypes;
import bi.view.mainframeviews.CMApplication;

/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public final class CMTDObjectTypeComboModel extends DefaultComboBoxModel {
	/**
	 * 
	 */
	private final CMGridCellEditorTDStructureObjectTypes editor;

	/**
	 * @param editor
	 */
	CMTDObjectTypeComboModel(CMGridCellEditorTDStructureObjectTypes editor) {
		this.editor = editor;
	}

	Vector Objects = new Vector(0);

	Object selectedObject;

	public void setSelectedItem(Object anObject) {
				Object object = anObject;
				if (anObject instanceof CMCellTDStructureObjectTypes) {
					ITypeData element = ((ITypeData)((CMCellTDStructureObjectTypes) anObject).getModel());
				    String objecttypeName =  element.getToolVendorOT(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject());			
				    for (int i = 0; i < this.getSize(); i++){
				    	 if (objecttypeName.equals(((CMCellTDStructureObjectTypes)anObject).getStateNameOT())) {
								object = this.editor.getCellEditorValue();
							
							} else { 
								Logger.getLogger(this.getClass()).error("no se encontro el valor");
								return;
							}
				    }
				   
				}
	
				if (object != selectedObject)
				{
					selectedObject = object;
					//this sentence causes a nullpointer but the grid is not notified of the change 
					//fireContentsChanged(this, -1, -1);
					
				}
			}

	public Object getSelectedItem() {
		return selectedObject;
	}

	public int getSize() {

		return this.Objects.size();
	}

	public Object getElementAt(int index) {

		return Objects.get(index);
	}


	
	@SuppressWarnings("unchecked")
	public void addElement(Object obj) {
		if (!Objects.contains(obj))
			this.Objects.add(obj);
	}

	public void removeElement(Object obj) {
		
		if (Objects.contains(obj));
		     Objects.remove(obj);
		
	}

	@SuppressWarnings("unchecked")
	public void insertElementAt(Object obj, int index) {
		if (!Objects.contains(obj))
			this.Objects.insertElementAt(obj, index);
		 
		
	}

	public void removeElementAt(int index) {		
	     Objects.remove(index);		
	}
}
