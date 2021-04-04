/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package  bi.view.dependencycombinationviews;

import bi.view.lang.CMMessages;

import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGrid;
/**
 * Title:        CaseMaker
 * Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */
public class CMHeaderGridModelDependencies extends DefaultHeaderModel {
      final int colCount = 2;
      final String[] colNames = {CMMessages.getString("LABEL_DEPENDENCY"),CMMessages.getString("LABEL_DESCRIPTION")}; //$NON-NLS-1$ //$NON-NLS-2$

      public CMHeaderGridModelDependencies(JSmartGrid jSmartGrid) {
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
