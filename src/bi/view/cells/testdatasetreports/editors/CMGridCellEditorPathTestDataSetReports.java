package bi.view.cells.testdatasetreports.editors;


import java.util.EventObject;
import javax.swing.JTextField;
import bi.view.cells.CMBaseCell;
import bi.view.cells.editors.CMBaseGridCellEditor;

/**
 * @author ccastedo
 *
 */
public class CMGridCellEditorPathTestDataSetReports extends
	CMBaseGridCellEditor {
	
	public CMGridCellEditorPathTestDataSetReports(JTextField pathTextField, CMBaseCell p_cell) {		
		super(pathTextField,p_cell);
		//pathTextField.addKeyListener(keyListener);	
	}
	@Override
	public boolean shouldSelectCell(EventObject arg0) {
	
		return true;
	}
	/*private KeyListener keyListener =new KeyAdapter()
	{
		public void keyPressed(KeyEvent e) {
			if ((e.isControlDown()&&e.getKeyCode()>=e.VK_C))
			;
			else		stopCellEditing();
		}
	};*/
	@Override
	public boolean stopCellEditing() {
		super.stopCellEditing();
		return false;
	}
}
	

