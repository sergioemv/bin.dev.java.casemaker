/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.testcaseviews;

import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGrid;

import bi.view.lang.CMMessages;

public class CMTestCaseHeaderView extends DefaultHeaderModel {
      final int colCount = 2;
      final String[] colNames = {CMMessages.getString("LABEL_TEST_CASE"),CMMessages.getString("LABEL_DESCRIPTION")}; //$NON-NLS-1$ //$NON-NLS-2$

      public CMTestCaseHeaderView(JSmartGrid jSmartGrid) {
        super(jSmartGrid, JSmartGrid.HORIZONTAL);
        initGUI();
      }

      public void initGUI(){
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