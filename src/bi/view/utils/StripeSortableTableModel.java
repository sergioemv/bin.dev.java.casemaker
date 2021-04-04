package bi.view.utils;

import java.awt.Color;

import javax.swing.table.TableModel;

import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.SortableTableModel;

public class StripeSortableTableModel extends SortableTableModel {

	private int fixedCols;

	public StripeSortableTableModel(TableModel arg0, int fixedCols) {
		super(arg0);
		this.fixedCols = fixedCols;
	}
	 protected final Color BACKGROUND1 = new Color(253, 253, 244);
     protected final Color BACKGROUND2 = new Color(255, 255, 255);

     CellStyle cellStyle = new CellStyle();

     public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
         cellStyle.setHorizontalAlignment(-1);
         cellStyle.setForeground(Color.BLACK);
         if (columnIndex>(fixedCols-1)){
         if (rowIndex % 2 == 0 ) {
             cellStyle.setBackground(BACKGROUND1);
         }
         else {
             cellStyle.setBackground(BACKGROUND2);
         }
         }
         else
         	cellStyle.setBackground(Color.LIGHT_GRAY);
         return cellStyle;
     }
         

     public boolean isCellStyleOn() {
         return true;
     }
}
