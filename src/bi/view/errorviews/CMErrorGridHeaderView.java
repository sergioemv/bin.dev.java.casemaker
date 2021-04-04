/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.errorviews;

import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGrid;

import bi.view.grids.CMBaseJSmartGrid;
import bi.view.lang.CMMessages;

public class CMErrorGridHeaderView extends DefaultHeaderModel {
//HCanedo_30112004_Begin
      final int colCount = 13;
      final String[] colNames = {CMMessages.getString("LABEL_ERROR"),CMMessages.getString("LABEL_DESCRIPTION"), CMMessages.getString("LABEL_STATE_COLUMN"), CMMessages.getString("LABEL_ERROR_CLASS"), CMMessages.getString("LABEL_PRIORITY"), CMMessages.getString("LABEL_ISSUE_DATE_COLUMN"), CMMessages.getString("LABEL_ISSUED_BY"),CMMessages.getString("LABEL_ASSIGN_DATE_COLUMN"), CMMessages.getString("LABEL_ASSIGN_TO"), CMMessages.getString("LABEL_CLOSING_DATE_COLUMN"), CMMessages.getString("LABEL_CLOSED_BY"), CMMessages.getString("LABEL_TEST_CASES"), CMMessages.getString("LABEL_RISK_LEVEL")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
//HCanedo_30112004_End
      public CMErrorGridHeaderView(CMBaseJSmartGrid jSmartGrid) {
        super(jSmartGrid, JSmartGrid.HORIZONTAL);
        initGUI();
      }
      public void initGUI() {
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