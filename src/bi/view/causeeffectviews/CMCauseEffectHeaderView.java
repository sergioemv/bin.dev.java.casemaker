/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.causeeffectviews;

import bi.view.lang.CMMessages;

import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGrid;

public class CMCauseEffectHeaderView extends DefaultHeaderModel {
		//fcastro_19102004_begin
      //final int colCount = 2; OLD
      final int colCount =5; //OLD Value =3;HCanedo_14032006

      //final String[] colNames = {CMMessages.getString("LABEL_CAUSE_EFFECT"),CMMessages.getString("LABEL_DESCRIPTION")}; //$NON-NLS-1$ //$NON-NLS-2$ //OLD
      final String[] colNames = {CMMessages.getString("LABEL_CAUSE_EFFECT"),CMMessages.getString("LABEL_DESCRIPTION"),CMMessages.getString("LABEL_STATE_COLUMN"),CMMessages.getString("LABEL_RISK_LEVEL"),CMMessages.getString("LABEL_USED")}; //HCanedo_14032006_Modified//$NON-NLS-1$ //$NON-NLS-2$
		//fcastro_19102004_end
      public CMCauseEffectHeaderView(JSmartGrid jSmartGrid) {
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