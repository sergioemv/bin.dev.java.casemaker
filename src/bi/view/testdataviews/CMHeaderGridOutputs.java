package bi.view.testdataviews;

import com.eliad.swing.DefaultHeaderModel;

import bi.view.lang.CMMessages;

import com.eliad.swing.JSmartGrid;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMHeaderGridOutputs extends DefaultHeaderModel {
//hcanedo_21_09_2004_begin
      final int colCount = 5;
      final String[] colNames = {CMMessages.getString("LABEL_NAME_REPORTS"),CMMessages.getString("LABEL_FILE_PATH_REPORTS"),CMMessages.getString("LABEL_DATE_REPORTS"),CMMessages.getString("LABEL_OPEN_PARAMETER_REPORTS"),CMMessages.getString("LABEL_OPEN_WITH_REPORTS")}; //$NON-NLS-1$ //$NON-NLS-2$
//hcanedo_21_09_2004_end
      public CMHeaderGridOutputs(JSmartGrid jSmartGrid) {
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
