/**
 * 
 */
package bi.view.cells.editors;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import model.Element;

import org.apache.log4j.Logger;

import bi.view.cells.CMCellElementGuiObject;

/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public final class CMObjectTypeComboModel extends DefaultComboBoxModel {
	/**
	 * 
	 */
	private final CMGridCellEditorElementGuiObject editor;

	/**
	 * @param editor
	 */
	CMObjectTypeComboModel(CMGridCellEditorElementGuiObject editor) {
		this.editor = editor;
	}

	Vector Objects = new Vector(0);

	Object selectedObject;

	public void setSelectedItem(Object anObject) {
				Object object = anObject;
				if (anObject instanceof CMCellElementGuiObject) {
					Element element = ((Element)((CMCellElementGuiObject) anObject).getModel());
				    String objecttypeName =  element.getGUIObjectName();			
				    for (int i = 0; i < this.getSize(); i++){
				    	 if (objecttypeName.equals(((CMCellElementGuiObject)anObject).getElement().getGUIObjectName())) {
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

///**
// * 
// */
//package bi.view.cells.editors;
//
//import java.util.Vector;
//
//import javax.swing.ComboBoxModel;
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.MutableComboBoxModel;
//import javax.swing.event.ListDataListener;
//
//import org.apache.log4j.Logger;
//
//import model.Element;
//import model.EquivalenceClass;
//import model.ObjectTypes;
//import model.State;
//import bi.view.cells.CMCellElementGuiObject;
//import bi.view.cells.CMCellEquivalenceClassState;
//
//public final class CMObjectTypeComboModel extends DefaultComboBoxModel {
//	/**
//	 * 
//	 */
//	private final CMGridCellEditorElementGuiObject editor;
//
//	/**
//	 * @param editor
//	 */
//	CMObjectTypeComboModel(CMGridCellEditorElementGuiObject editor) {
//		this.editor = editor;
//	}
//
//	Vector Objects = new Vector(0);
//
//	Object selectedObject;
//
//	public void setSelectedItem(Object anObject) {
//				Object object = anObject;
//				//for (int i = 0; i < this.getSize(); i++){
//					if (anObject instanceof CMCellElementGuiObject) {
//						/*if (((CMCellElementGuiObject) anObject).getElement()
//								.getStateNameOT().equalsIgnoreCase(
//										((ObjectTypes) this.getElementAt(i))
//												.getM_Name()))
//							object = this.getElementAt(i);		*/				
//						object = this.editor.getCellEditorValue();
//					}
//				//}
//				
//	
//				if (object != selectedObject)
//				{
//					selectedObject = object;
//					//this sentence causes a nullpointer but the grid is not notified of the change 
//					//fireContentsChanged(this, -1, -1);
//					
//				}
//			}
//
//	public Object getSelectedItem() {
//		return selectedObject;
//	}
//
//	public int getSize() {
//
//		return this.Objects.size();
//	}
//
//	public Object getElementAt(int index) {
//
//		return Objects.get(index);
//	}
//
//
//	
//	public void addElement(Object obj) {
//		if (!Objects.contains(obj))
//			this.Objects.add(obj);
//	}
//
//	public void removeElement(Object obj) {
//		
//		if (Objects.contains(obj));
//		     Objects.remove(obj);
//		
//	}
//
//	public void insertElementAt(Object obj, int index) {
//		if (!Objects.contains(obj))
//			this.Objects.insertElementAt(obj, index);
//		 
//		
//	}
//
//	public void removeElementAt(int index) {
//		
//	     Objects.remove(index);
//		
//	}
//}