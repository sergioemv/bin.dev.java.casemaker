package bi.view.cells.editors;

import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import model.ITypeData;
import model.Type;
import bi.controller.editcontrol.CMTypeEditController;
import bi.view.cells.CMBaseCell;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.cells.CMTypeWrapper;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMTypeView;
/**
 * @author ccastedo
 *
 */

@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureType extends CMBaseGridCellEditor {

	CMTypeWrapper binary = new CMTypeWrapper(Type.binary); 
	CMTypeWrapper bit = new CMTypeWrapper(Type.bit);
	CMTypeWrapper bolean = new CMTypeWrapper(Type.bolean);
	CMTypeWrapper character = new CMTypeWrapper(Type.character);
	CMTypeWrapper dateTime = new CMTypeWrapper(Type.dateTime); 
	CMTypeWrapper decimal = new CMTypeWrapper(Type.decimal);
	CMTypeWrapper floatPoint = new CMTypeWrapper(Type.floatPoint);
	CMTypeWrapper integer = new CMTypeWrapper(Type.integer);
	CMTypeWrapper money = new CMTypeWrapper(Type.money);
	CMTypeWrapper nchar = new CMTypeWrapper(Type.nchar);
	CMTypeWrapper ntext = new CMTypeWrapper(Type.ntext);
	CMTypeWrapper numeric = new CMTypeWrapper(Type.numeric);
	CMTypeWrapper nVarChar = new CMTypeWrapper(Type.nVarChar);
	CMTypeWrapper real = new CMTypeWrapper(Type.real);
	CMTypeWrapper smallDateTime = new CMTypeWrapper(Type.smallDateTime);
	CMTypeWrapper smallInt = new CMTypeWrapper(Type.smallInt);
	CMTypeWrapper smallMoney = new CMTypeWrapper(Type.smallMoney);
	CMTypeWrapper text = new CMTypeWrapper(Type.text);
	CMTypeWrapper timeStamp = new CMTypeWrapper(Type.timeStamp);
	CMTypeWrapper tinyInt = new CMTypeWrapper(Type.tinyInt);
	CMTypeWrapper varBinary = new CMTypeWrapper(Type.varBinary);
	CMTypeWrapper varChar = new CMTypeWrapper(Type.varChar);
	

	CMTypeWrapper[] types = {binary, bit, bolean, character, dateTime, decimal, floatPoint, integer,
		                     money, nchar, ntext, numeric, nVarChar, real, smallDateTime, smallInt,
		                     smallMoney, text, timeStamp, tinyInt, varBinary, varChar};
	ComboBoxModel combomodel = new CMTypeComboModel(this);

	CMBaseJComboBox typesCombo = null;
	
	private CMTypeEditController TypeEditController;
	public CMGridCellEditorTDStructureType() {

		super(new CMBaseJComboBox(new Vector(0)), null);
		// initialize the combo
		typesCombo =((CMBaseJComboBox) getComponent());
		typesCombo.setModel(combomodel);


	}

	public boolean stopCellEditing() {		
		if (getCell() != null) {
			CMCellTDStructureClassState cellType =  (CMCellTDStructureClassState) getCell();
			int row =  ((CMGridTDStructure) cellType.getGrid()).getSelectionModel().getLeadRow();
			int column =  ((CMGridTDStructure) cellType.getGrid()).getSelectionModel().getLeadColumn();
			((CMGridTDStructure) cellType.getGrid()).setRowSelected(row);
			((CMGridTDStructure) cellType.getGrid()).setColumnSelected(column);
			ITypeData typedata = (ITypeData) getCell().getModel();
			
			int newType = ((JComboBox) this.getComponent()).getSelectedIndex();
			if (newType != typedata.getTypeIndex()){
				getTypeEditController().setTypeBean(typedata);
				getTypeEditController().getTypeView().setType(newType);
				CMUndoMediator.getInstance().doEdit(getTypeEditController().applyChanges());
			}
			
			this.getComponent().repaint();
			return super.stopCellEditing();
		}
		return super.stopCellEditing();
	}


	
	public void setCell(CMBaseCell cell) {
		// put the cell value
		super.setCell(cell);
		//update the editor type
		typesCombo.setParent(getCell().getGrid());
		((CMBaseJComboBox) getComponent()).removeAllItems();
		
		((CMBaseJComboBox) getComponent()).addItem(binary);
		((CMBaseJComboBox) getComponent()).addItem(bit);
		((CMBaseJComboBox) getComponent()).addItem(bolean);
		((CMBaseJComboBox) getComponent()).addItem(character);
		((CMBaseJComboBox) getComponent()).addItem(dateTime);
		((CMBaseJComboBox) getComponent()).addItem(decimal);
		((CMBaseJComboBox) getComponent()).addItem(floatPoint);
		((CMBaseJComboBox) getComponent()).addItem(integer);		
		((CMBaseJComboBox) getComponent()).addItem(money);
		((CMBaseJComboBox) getComponent()).addItem(nchar);
		((CMBaseJComboBox) getComponent()).addItem(ntext);
		((CMBaseJComboBox) getComponent()).addItem(numeric);
		((CMBaseJComboBox) getComponent()).addItem(nVarChar);
		((CMBaseJComboBox) getComponent()).addItem(real);
		((CMBaseJComboBox) getComponent()).addItem(smallDateTime);
		((CMBaseJComboBox) getComponent()).addItem(smallInt);		
		((CMBaseJComboBox) getComponent()).addItem(smallMoney);
		((CMBaseJComboBox) getComponent()).addItem(text);
		((CMBaseJComboBox) getComponent()).addItem(timeStamp);
		((CMBaseJComboBox) getComponent()).addItem(tinyInt);
		((CMBaseJComboBox) getComponent()).addItem(varBinary);
		((CMBaseJComboBox) getComponent()).addItem(varChar);
		
		combomodel.setSelectedItem(cell);


	}

	public CMTypeEditController getTypeEditController() {
		if (TypeEditController == null){
			TypeEditController = new CMTypeEditController();
			TypeEditController.setTypeBean((ITypeData) getCell().getModel());
			TypeEditController.setTypeView(new CMTypeView());
		}
		return TypeEditController;
	}
	
}
