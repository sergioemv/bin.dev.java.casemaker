/**
 * 06/11/2006
 * svonborries
 */
package bi.view.utils.testdata;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

import bi.view.lang.CMMessages;

/**
 * @author svonborries
 *
 */
public class CMPanelFormulaPreview extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JTextArea jTextAreaPreview = null;
	private JLabel jLabelPreview = null;
	/**
	 * This is the default constructor
	 */
	public CMPanelFormulaPreview() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.weightx = 1.0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getJSplitPane(), gridBagConstraints);
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setBottomComponent(getJPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabelPreview = new JLabel();
			jLabelPreview.setText(CMMessages.getString("TESTDATA_FORMULAPREVIEW_LABEL"));
			jLabelPreview.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setPreferredSize(new Dimension(629, 100));
			jPanel.setMinimumSize(new Dimension(629, 100));
			jPanel.setMaximumSize(new Dimension(629, 100));
			jPanel.add(jLabelPreview, BorderLayout.NORTH);
			jPanel.add(getJTextAreaPreview(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextAreaPreview	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaPreview() {
		if (jTextAreaPreview == null) {
			jTextAreaPreview = new JTextArea();
			jTextAreaPreview.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			jTextAreaPreview.setFont(new Font("Courier", Font.PLAIN, 13));
			jTextAreaPreview.setCaretColor(new Color(212, 208, 200));
			jTextAreaPreview.setBackground(this.getBackground());
			jTextAreaPreview.setEditable(false);
			jTextAreaPreview.setLineWrap(true);
			jTextAreaPreview.setWrapStyleWord(true);
			jTextAreaPreview.setDisabledTextColor(new Color(212, 208, 200));
		}
		return jTextAreaPreview;
	}

}
