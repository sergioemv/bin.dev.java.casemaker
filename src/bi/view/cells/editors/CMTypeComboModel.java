/**
 * 
 */
package bi.view.cells.editors;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import org.apache.log4j.Logger;
import model.ITypeData;
import model.Type;
import bi.view.cells.CMCellTDStructureClassState;
/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public final class CMTypeComboModel extends DefaultComboBoxModel {
	/**
	 * 
	 */
	private final CMGridCellEditorTDStructureType editor;

	/**
	 * @param editor
	 */
	CMTypeComboModel(CMGridCellEditorTDStructureType editor) {
		this.editor = editor;
	}

	Vector Objects = new Vector(0);

	Object selectedObject;

	public void setSelectedItem(Object anObject) {
				Object object = anObject;
				if (anObject instanceof CMCellTDStructureClassState) {
					ITypeData typedata = ((ITypeData)((CMCellTDStructureClassState) anObject).getModel());
				    String typeName =  typedata.getTypeName();			
					if (typeName.equals(Type.binary.toString())) {
						object = this.editor.binary;
					} else if (typeName.equals(Type.bit.toString())) {
						object = this.editor.bit;
					} else if (typeName.equals(Type.bolean.toString())) {
						object = this.editor.bolean;
					} else if (typeName.equals(Type.character.toString())) {
						object = this.editor.character;
					} else if (typeName.equals(Type.dateTime.toString())) {
						object = this.editor.dateTime;
					} else if (typeName.equals(Type.decimal.toString())) {
						object = this.editor.decimal;
					} else if (typeName.equals(Type.floatPoint.toString())) {
						object = this.editor.floatPoint;
					} else if (typeName.equals(Type.integer.toString())) {
						object = this.editor.integer;
					} else if (typeName.equals(Type.money.toString())) {
						object = this.editor.money;
					} else if (typeName.equals(Type.nchar.toString())) {
						object = this.editor.nchar;
					} else if (typeName.equals(Type.ntext.toString())) {
						object = this.editor.ntext;
					} else if (typeName.equals(Type.numeric.toString())) {
						object = this.editor.numeric;
					} else if (typeName.equals(Type.nVarChar.toString())) {
						object = this.editor.nVarChar;
					} else if (typeName.equals(Type.real.toString())) {
						object = this.editor.real;
					} else if (typeName.equals(Type.smallDateTime.toString())) {
						object = this.editor.smallDateTime;						
					} else if (typeName.equals(Type.smallInt.toString())) {
						object = this.editor.smallInt;					
					} else if (typeName.equals(Type.smallMoney.toString())) {
						object = this.editor.smallMoney;
					} else if (typeName.equals(Type.text.toString())) {
						object = this.editor.text;
					} else if (typeName.equals(Type.timeStamp.toString())) {
						object = this.editor.timeStamp;
					} else if (typeName.equals(Type.tinyInt.toString())) {
						object = this.editor.tinyInt;
					} else if (typeName.equals(Type.varBinary.toString())) {
						object = this.editor.varBinary;
					} else if (typeName.equals(Type.varChar.toString())) {
						object = this.editor.varChar;
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