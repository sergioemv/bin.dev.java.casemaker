/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

/**
 * @author smoreno
 *
 */
public class CMCellDefaultHeaderRenderer extends DefaultGridCellRenderer{
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);	      
			setBackground(new Color(36,38,116));
	        setForeground(new Color(252,254,252));
			setHorizontalAlignment(JLabel.LEFT);
			setFont(new Font("Dialog",Font.PLAIN,12));
	        if( !isSelected) {
				setBorder(BorderFactory.createRaisedBevelBorder());
		}
        return c;
	}
}
