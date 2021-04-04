package bi.view.preferences.lang;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.DefaultListCellRenderer;

import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.PartialLineBorder;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

public class CMLanguagesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelCRUDButtons = null;
	private JideButton jButtonAddLanguage = null;
	private JideButton jButtonEditLanguage = null;
	private JideButton jButtonDeleteLanguage = null;
	private JPanel jPanelLanguagesContainer = null;
	private JScrollPane jScrollPaneContent = null;
	private JList jListLanguajes = null;
	private DefaultListModel languageListModel;
	private JideButton jButtonSetAsDefault = null;
	/**
	 * This is the default constructor
	 */
	public CMLanguagesPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(574, 247);
		this.setLayout(new BorderLayout());
		this.add(getJPanelCRUDButtons(), BorderLayout.SOUTH);
		this.add(getJPanelLanguagesContainer(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanelCRUDButtons
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCRUDButtons() {
		if (jPanelCRUDButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(10);
			jPanelCRUDButtons = new JPanel();
			jPanelCRUDButtons.setPreferredSize(new Dimension(20, 40));
			jPanelCRUDButtons.setLayout(flowLayout);
			jPanelCRUDButtons.add(getJButtonAddLanguage(), null);
			jPanelCRUDButtons.add(getJButtonEditLanguage(), null);
			jPanelCRUDButtons.add(getJButtonDeleteLanguage(), null);
			jPanelCRUDButtons.add(getJButtonSetAsDefault(), null);
		}
		return jPanelCRUDButtons;
	}

	/**
	 * This method initializes jButtonAddLanguage
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonAddLanguage() {
		if (jButtonAddLanguage == null) {
			jButtonAddLanguage = new JideButton();
			jButtonAddLanguage.setButtonStyle(JideButton.TOOLBAR_STYLE);
			jButtonAddLanguage.setIcon(CMIcon.INSERT_PREF.getImageIcon());
			jButtonAddLanguage.setText(CMMessages.getString("LABEL_ADD_LANGUAGES")); //$NON-NLS-1$
			jButtonAddLanguage.setPreferredSize(new Dimension(130, 26));
		}
		return jButtonAddLanguage;
	}

	/**
	 * This method initializes jButtonEditLanguage
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonEditLanguage() {
		if (jButtonEditLanguage == null) {
			jButtonEditLanguage = new JideButton();
			jButtonEditLanguage.setButtonStyle(JideButton.TOOLBAR_STYLE);
			jButtonEditLanguage.setIcon(CMIcon.EDIT_PREF.getImageIcon());
			jButtonEditLanguage.setText(CMMessages.getString("LABEL_EDIT_LANGUAGES")); //$NON-NLS-1$
			jButtonEditLanguage.setPreferredSize(new Dimension(130, 26));
		}
		return jButtonEditLanguage;
	}

	/**
	 * This method initializes jButtonDeleteLanguage
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonDeleteLanguage() {
		if (jButtonDeleteLanguage == null) {
			jButtonDeleteLanguage = new JideButton();
			jButtonDeleteLanguage.setButtonStyle(JideButton.TOOLBAR_STYLE);
			jButtonDeleteLanguage.setIcon(CMIcon.DELETE_PREF.getImageIcon());
			jButtonDeleteLanguage.setText(CMMessages.getString("LABEL_DELETE_LANGUAGES")); //$NON-NLS-1$
			jButtonDeleteLanguage.setPreferredSize(new Dimension(130, 26));
			jButtonDeleteLanguage.setEnabled(false);
		}
		return jButtonDeleteLanguage;
	}

	/**
	 * This method initializes jPanelLanguagesContainer
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLanguagesContainer() {
		if (jPanelLanguagesContainer == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanelLanguagesContainer = new JPanel();
			jPanelLanguagesContainer.setLayout(new GridBagLayout());

			jPanelLanguagesContainer.setBorder(BorderFactory.createTitledBorder(new PartialLineBorder(Color.gray, 1, true), CMMessages.getString("LABEL_AVAILABLE_LANGUAGES"), TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$ //$NON-NLS-2$
			jPanelLanguagesContainer.add(getJScrollPaneContent(), gridBagConstraints);
		}
		return jPanelLanguagesContainer;
	}

	/**
	 * This method initializes jScrollPaneContent
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneContent() {
		if (jScrollPaneContent == null) {
			jScrollPaneContent = new JScrollPane();
			jScrollPaneContent.setViewportView(getJListLanguajes());
		}
		return jScrollPaneContent;
	}

	/**
	 * This method initializes jListLanguajes
	 *
	 * @return javax.swing.JList
	 */
	public JList getJListLanguajes() {
		if (jListLanguajes == null) {
			jListLanguajes = new JList();
			jListLanguajes.setModel(getLanguageListModel());
			jListLanguajes.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount()==2)
							getJButtonEditLanguage().doClick();
					}
			});
		}
		return jListLanguajes;
	}

	public DefaultListModel getLanguageListModel() {
		if (languageListModel==null)
			languageListModel = new DefaultListModel();

		return languageListModel;
	}

	/**
	 * This method initializes jButtonSetAsDefault
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonSetAsDefault() {
		if (jButtonSetAsDefault == null) {
			jButtonSetAsDefault = new JideButton();
			jButtonSetAsDefault.setButtonStyle(JideButton.TOOLBAR_STYLE);
			jButtonSetAsDefault.setText(CMMessages.getString("LABEL_SET_AS_DEFAULT")); //$NON-NLS-1$
			jButtonSetAsDefault.setIcon(CMIcon.SETDEFAULT_PREF.getImageIcon());
			jButtonSetAsDefault.setPreferredSize(new Dimension(130, 26));
			jButtonSetAsDefault.setEnabled(false);
		}
		return jButtonSetAsDefault;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
