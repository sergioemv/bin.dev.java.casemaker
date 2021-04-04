/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import bi.view.mainframeviews.CMApplication;
/**
 * Title:        CaseMaker
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author
 * @version 1.0
 */

public class CMDefaultDialog extends CMBaseJDialog{
 
  private JPanel jPanelContained = null;
  private CMOkCancelPanel okCancelPanel = null;
  private JPanel jPanelContainer = null;
  


  public CMDefaultDialog() {
	super(CMApplication.frame);
	try {
		jbInit();
		//pack();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
  

  void jbInit() throws Exception {
    this.setModal(true);
    this.setContentPane(getJPanelContainer());
    //this.setBounds(new Rectangle(0, 0, 621, 530));
    this.addKeyListenertoAll(this.getContentPane(),this.getDefaultKeyListener());

  }

protected List getOrder() {
	Vector<Component> componentList = new Vector<Component>(0);
	getJPanelContained();
	try {
	if (jPanelContained.getClass().getMethod("getTabOrder")!=null)
			componentList.addAll((Collection<? extends Component>) jPanelContained.getClass().getMethod("getTabOrder").invoke(jPanelContained));
		} catch (IllegalArgumentException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
			
		} catch (SecurityException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
		} catch (IllegalAccessException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
		} catch (InvocationTargetException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
		} catch (NoSuchMethodException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());	
		}
	componentList.add(getOkCancelPanel().getJButtonOk());
	componentList.add(getOkCancelPanel().getJButtonCancel());
	return componentList;
}

protected void fireButtonOk() {
	this.getOkCancelPanel().getJButtonOk().doClick();
	
}

protected void fireButtonCancel() {
	getOkCancelPanel().getJButtonCancel().doClick();
}

public JButton getDefaultButton() {
	return getOkCancelPanel().getJButtonOk();
}

/**
 * This method initializes jPanelEquivalenceClass	
 * 	
 * @return javax.swing.JPanel	
 */
public JPanel getJPanelContained() {
	if (jPanelContained == null) {
		jPanelContained = new JPanel();
		
	}
	return jPanelContained;
} 
/**
 * This method initializes okCancelPanel	
 * 	
 * @return bi.view.utils.CMOkCancelPanel	
 */
private CMOkCancelPanel getOkCancelPanel() {
	if (okCancelPanel == null) {
		okCancelPanel = new CMOkCancelPanel(this);
	}
	return okCancelPanel;
}
/**
 * This method initializes jPanelContainer	
 * 	
 * @return javax.swing.JPanel	
 */
private JPanel getJPanelContainer() {
	if (jPanelContainer == null) {
		jPanelContainer = new JPanel();
		jPanelContainer.setLayout(new BorderLayout());
		//jPanelContainer.add(getJPanelEquivalenceClass(), BorderLayout.CENTER);
		jPanelContainer.add(getOkCancelPanel(), BorderLayout.SOUTH);
	}
	return jPanelContainer;
}

public void setJPanelContained(JPanel panel) {
	// TODO Auto-generated method stub
	jPanelContained = panel;
	jPanelContainer.add(jPanelContained, BorderLayout.CENTER);
	 this.addKeyListenertoAll(jPanelContained,this.getDefaultKeyListener());
}

}  //  @jve:decl-index=0:visual-constraint="10,10"