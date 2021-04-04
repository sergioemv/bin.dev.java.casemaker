/**
 * 
 */
package bi.view.cells.editors;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import model.EquivalenceClass;
import model.State;

import org.apache.log4j.Logger;

import bi.view.cells.CMCellEquivalenceClassState;

public final class CMStateComboModel extends DefaultComboBoxModel {
	/**
	 * 
	 */
	private final CMGridCellEditorEquivalenceClassState editor;

	/**
	 * @param editor
	 */
	CMStateComboModel(CMGridCellEditorEquivalenceClassState editor) {
		this.editor = editor;
	}

	Vector Objects = new Vector(0);

	Object selectedObject;

	public void setSelectedItem(Object anObject) {
				Object object = anObject;
				if (anObject instanceof CMCellEquivalenceClassState) {
					EquivalenceClass eq = ((CMCellEquivalenceClassState) anObject)
							.getEquivalenceClass();
					if (eq.getState() == State.FAULTY.intValue()) {
						object = this.editor.faulty;
					} else if (eq.getState() == State.IRRELEVANT.intValue()) {
						object = this.editor.irrelevant;
					} else if (eq.getState() == State.NEGATIVE.intValue()) {
						object = this.editor.negative;
					} else if (eq.getState() == State.POSITIVE.intValue()) {
						object = this.editor.positive;
					} else { 
						Logger.getLogger(this.getClass()).error("no se encontro el valor");
						return;
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


	
	public void addElement(Object obj) {
		if (!Objects.contains(obj))
			this.Objects.add(obj);
	}

	public void removeElement(Object obj) {
		
		if (Objects.contains(obj));
		     Objects.remove(obj);
		
	}

	public void insertElementAt(Object obj, int index) {
		if (!Objects.contains(obj))
			this.Objects.insertElementAt(obj, index);
		 
		
	}

	public void removeElementAt(int index) {
		
	     Objects.remove(index);
		
	}
}