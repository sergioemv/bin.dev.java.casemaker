/**
 * 05/01/2007
 * svonborries
 */
package bi.controller.testdata.linkelement;

import bi.view.lang.CMMessages;
import bi.view.utils.testdata.CMPanelChooserGlobalLocalReference;

/**
 * @author svonborries
 *
 */
public class CMLinkElementChooserGlobalLocalEditController {
	
	private CMPanelChooserGlobalLocalReference panelGlobalLocal = null;
	private boolean isGlobalSelected = false;
	private String selectDescription;

	/**
	 * @return the panelGlobalLocal
	 * 05/01/2007
	 * svonborries
	 */
	public CMPanelChooserGlobalLocalReference getPanelGlobalLocal() {
		if(panelGlobalLocal == null){
			panelGlobalLocal = new CMPanelChooserGlobalLocalReference();
			panelGlobalLocal.getJRadioButtonGlobal().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					radioButtonGlobalActionPerformed();
				}
			});
			panelGlobalLocal.getJRadioButtonGlobal().addMouseListener(new java.awt.event.MouseAdapter() {   
				
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					panelGlobalLocal.getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION")); 
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					panelGlobalLocal.getJTextPaneDescription().setText(selectDescription);
				}
			});
			panelGlobalLocal.getJRadioButtonLocal().addFocusListener(new java.awt.event.FocusAdapter() {   
				public void focusLost(java.awt.event.FocusEvent e) {    
					panelGlobalLocal.getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					panelGlobalLocal.getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION")); 
				}
			});
			
			
			
			
			panelGlobalLocal.getJRadioButtonLocal().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					radiobuttonLocalActionPerformed();
				}
			});
			panelGlobalLocal.getJRadioButtonLocal().addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					panelGlobalLocal.getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_LOCAL_VALUE_DESCRIPTION")); 
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					panelGlobalLocal.getJTextPaneDescription().setText(selectDescription);
				}
			});
			panelGlobalLocal.getJRadioButtonLocal().addFocusListener(new java.awt.event.FocusAdapter() {   
				public void focusLost(java.awt.event.FocusEvent e) {    
					panelGlobalLocal.getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					panelGlobalLocal.getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_LOCAL_VALUE_DESCRIPTION")); 
				}
			});
			panelGlobalLocal.getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION"));
			selectDescription = CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION");
			isGlobalSelected = true;
		}
		return panelGlobalLocal;
	}
	

	private void radioButtonGlobalActionPerformed() {
		getPanelGlobalLocal().getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION"));
		isGlobalSelected = true;
		selectDescription = CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION");
	}

	private void radiobuttonLocalActionPerformed() {
		getPanelGlobalLocal().getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_LOCAL_VALUE_DESCRIPTION"));
		isGlobalSelected = false;
		selectDescription = CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_LOCAL_VALUE_DESCRIPTION");
	}
	

	/**
	 * @return the isGlobalSelected
	 * 05/01/2007
	 * svonborries
	 */
	public boolean isGlobalSelected() {
		return isGlobalSelected;
	}

}
