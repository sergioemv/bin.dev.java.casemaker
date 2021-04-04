/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.cells.renderers;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import bi.view.mainframeviews.CMApplication;
import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public class CMGridTDStructureRenderer extends DefaultGridCellRenderer{
	public CMGridTDStructureRenderer() {
		super();		
	}
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);	      
		
		
		if (CMApplication.frame.isIsPanelTestDataSelected())
		{			
			setHorizontalAlignment(JLabel.LEFT);
			setBackground(new Color(235, 235, 228));
		}
		else{			
			setHorizontalAlignment(JLabel.LEFT);
		}
        return c;
	}
}
