/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.utils;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JButton;

import com.jidesoft.swing.JideButton;

/**
 * @author smoreno
 *
 */
public class CMToolBarButton extends JideButton {
/**
 *
 */
public CMToolBarButton() {
	// TODO Auto-generated constructor stub
	super();
	initialize();
}

/**
 * @param p_action
 */
public CMToolBarButton(Action p_action) {
	super(p_action);
	initialize();
}

/**
*@autor smoreno
 */
private void initialize() {
	  this.setPreferredSize(new Dimension(23, 23));
	  this.setButtonStyle(JideButton.TOOLBAR_STYLE);

}
/* (non-Javadoc)
 * @see javax.swing.AbstractButton#setText(java.lang.String)
 */
@Override
public void setText(String p_text) {
	// do nothing

}
}
