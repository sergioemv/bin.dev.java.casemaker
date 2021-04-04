/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.cells.renderers;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

import model.ExpectedResult;
import bi.view.cells.CMCellExpectedResultValue;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

/**
 * @author smoreno
 *
 */
public class CMCellExpectedResultRenderer extends DefaultGridCellRenderer {

/* (non-Javadoc)
 * @see com.eliad.model.defaults.DefaultGridCellRenderer#getComponent(java.lang.Object, boolean, boolean, boolean, int, int, com.eliad.model.GridContext)
 */

private static Font textFont = new Font("Dialog", Font.PLAIN, 12);
private static Font numberFont = new Font("Dialog", Font.BOLD, 12);
@Override
public Component getComponent(Object value, boolean p_arg1, boolean p_arg2, boolean p_arg3, int p_arg4, int p_arg5, GridContext p_arg6) {

	Component c = super.getComponent(value, p_arg1, p_arg2, p_arg3, p_arg4, p_arg5,
			p_arg6);
    this.setHorizontalAlignment(JLabel.LEFT);
    Number number = ((ExpectedResult)((CMCellExpectedResultValue)value).getModel()).getNumberValue();
    if ( number == null)
    	this.setFont(textFont);
    else
    	this.setFont(numberFont);
    

    return c;
}

}
