package bi.view.cells.testdatasetreports.renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import bi.view.cells.CMFilePathCell;
import bi.view.cells.CMReportOutputView;
import bi.view.cells.renderers.CMBaseGridCellRenderer;
import com.eliad.model.GridContext;
/**
 * @author ccastedo
 *
 */

public class CMTestDataSetReportsPathCellRenderer extends CMBaseGridCellRenderer {

	public CMTestDataSetReportsPathCellRenderer() {
		super();
		
	}
	
	 public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		    CMReportOutputView reportOutput=((CMFilePathCell)value).getM_CMReportOutputView();
			Component c = super.getComponent(reportOutput.getM_CMFilePathCell(), isSelected, hasFocus, isEditable, row, column, context);


			this.setHorizontalAlignment(JLabel.LEFT);
            if (!isSelected) {
                this.setBackground(new Color(235, 235, 228));
            }
			return c;
	 }
}
