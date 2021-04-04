/**
 * 05/01/2007
 * svonborries
 */
package bi.view.utils.testdata;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.view.lang.CMMessages;




/**
 * @author svonborries
 *
 */
public class CMPanelChooserGlobalLocalReference extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelContainer = null;
	private JRadioButton jRadioButtonGlobal = null;
	private JRadioButton jRadioButtonLocal = null;
	private JPanel jPanelDescription = null;
	private JTextPane jTextPaneDescription = null;
	private ButtonGroup groupRadioButtons;  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public CMPanelChooserGlobalLocalReference() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(280, 127);
		this.setLayout(new BorderLayout());
		this.add(getJPanelContainer(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanelContainer	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelContainer() {
		if (jPanelContainer == null) {
			jPanelContainer = new JPanel();
			TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LINK_ELEMENTS_VALUE_CHOOSER_SUBTITLE"), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder.setTitleColor(java.awt.Color.black);
			titledBorder.setTitle(CMMessages.getString("LINK_ELEMENTS_VALUE_CHOOSER_SUBTITLE")); //$NON-NLS-1$
			titledBorder.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jPanelContainer.setBorder(titledBorder);
			jPanelContainer.setLayout(new BoxLayout(getJPanelContainer(), BoxLayout.Y_AXIS));
			jPanelContainer.add(getJRadioButtonGlobal(), null);
			jPanelContainer.add(getJRadioButtonLocal(), null);
			jPanelContainer.add(getJPanelDescription(), null);
			getGroupRadioButtons().add(getJRadioButtonGlobal());
			getGroupRadioButtons().add(getJRadioButtonLocal());
		}
		return jPanelContainer;
	}

	/**
	 * This method initializes jRadioButtonGlobal	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	public JRadioButton getJRadioButtonGlobal() {
		if (jRadioButtonGlobal == null) {
			jRadioButtonGlobal = new JRadioButton();
			jRadioButtonGlobal.setText(CMMessages.getString("LINK_ELEMENTS_VALUE_GLOBAL_VALUE_LABEL"));
			jRadioButtonGlobal.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonGlobal.setSelected(true);
		}
		return jRadioButtonGlobal;
	}

	/**
	 * This method initializes jRadioButtonLocal	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	public JRadioButton getJRadioButtonLocal() {
		if (jRadioButtonLocal == null) {
			jRadioButtonLocal = new JRadioButton();
			jRadioButtonLocal.setText(CMMessages.getString("LINK_ELEMENTS_VALUE_LOCAL_VALUE_LABEL"));
			jRadioButtonLocal.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
		}
		return jRadioButtonLocal;
	}

	/**
	 * This method initializes jPanelDescription	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setLayout(new BorderLayout());
			jPanelDescription.add(getJTextPaneDescription(), BorderLayout.CENTER);
		}
		return jPanelDescription;
	}

	/**
	 * This method initializes jTextPaneDescription	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	public JTextPane getJTextPaneDescription() {
		if (jTextPaneDescription == null) {
			jTextPaneDescription = new JTextPane();
			jTextPaneDescription.setBackground(this.getBackground());
			jTextPaneDescription.setEditable(false);
			jTextPaneDescription.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
		}
		return jTextPaneDescription;
	}
	
	private ButtonGroup getGroupRadioButtons() {
		if(groupRadioButtons == null)
			groupRadioButtons= new ButtonGroup(); 
		return groupRadioButtons;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
