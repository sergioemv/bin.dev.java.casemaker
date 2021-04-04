package bi.view.aboutviews;

import bi.view.lang.CMMessages;

import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGrid;

@SuppressWarnings("serial")
public class CMFileDetailsGridHeaderModel extends DefaultHeaderModel {

	final int colCount = 2;
	 final String[] colNames = {CMMessages.getString("FILENAME_VERSION_LABEL"),CMMessages.getString("FILE_VERSION_LABEL")}; //$NON-NLS-1$ //$NON-NLS-2$

	 public CMFileDetailsGridHeaderModel(JSmartGrid jSmartGrid) {
		 super(jSmartGrid, JSmartGrid.HORIZONTAL);
	 }
	 public int getRowCount() {
		 return 1;
	 }
	 public int getColumnCount() {
		 return colCount;
	 }
	 public Object getValueAt(int row, int column) {
		 return colNames[column];
	 }
}