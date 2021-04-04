package bi.view.preferences.externalapps;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JViewport;

import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.PartialLineBorder;
import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

public class CMPanelExternalApplications extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelButtons = null;
	private JScrollPane jScrollPaneGrid = null;
	private CMExternalApplicationsGridView externalApplicationsGridView = null;

	private JideButton addButton;
	private JideButton editButton;
	private JideButton deleteButton;
	/**
	 * This is the default constructor
	 */
	public CMPanelExternalApplications() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getJPanelButtons(), BorderLayout.SOUTH);
		this.add(getJScrollPaneGrid(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanelButtons
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			FlowLayout layout = new FlowLayout();
			layout.setHgap(0);
			jPanelButtons.setLayout(layout);
			jPanelButtons.setPreferredSize(new Dimension(2, 35));
			jPanelButtons.add(getAddButton());
			jPanelButtons.add(getEditButton());
			jPanelButtons.add(getDeleteButton());

		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jScrollPaneGrid
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneGrid() {
		if (jScrollPaneGrid == null) {
			jScrollPaneGrid = new JScrollPane(getExternalApplicationsGridView());
			jScrollPaneGrid.setBorder(BorderFactory.createTitledBorder(new PartialLineBorder(Color.gray, 1, true),"" , TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			jScrollPaneGrid.setViewportView(getExternalApplicationsGridView());
		}
		return jScrollPaneGrid;
	}

	/**
	 * This method initializes externalApplicationsGridView
	 *
	 * @return bi.view.externalapplicationviews.CMExternalApplicationsGridView
	 */
	CMExternalApplicationsGridView getExternalApplicationsGridView() {
		if (externalApplicationsGridView == null) {
			externalApplicationsGridView = new CMExternalApplicationsGridView();

			externalApplicationsGridView.setColumnAutoResizable(true);
			externalApplicationsGridView.setMultiColumnSortable(false);
		}
		return externalApplicationsGridView;
	}



	
	private JideButton getAddButton() {
		if (addButton == null){
			addButton = new JideButton();
			addButton.setText(CMMessages.getString("LABEL_ADD_APPLICATION"));
			addButton.setButtonStyle(JideButton.TOOLBAR_STYLE);
			addButton.setIcon(CMIcon.INSERT_PREF.getImageIcon());
			addButton.setPreferredSize(new Dimension(150,26));
			addButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					getExternalApplicationsGridView().create();
				}});
		}
		return addButton;
	}

	private JideButton getDeleteButton() {
		if (deleteButton == null){
			deleteButton = new JideButton();
			deleteButton.setText(CMMessages.getString("LABEL_REMOVE_APPLICATION"));
			deleteButton.setButtonStyle(JideButton.TOOLBAR_STYLE);
			deleteButton.setPreferredSize(new Dimension(150,26));
			deleteButton.setIcon(CMIcon.DELETE_PREF.getImageIcon());
			deleteButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					getExternalApplicationsGridView().delete();
				}});
		}
		return deleteButton;
	}

	private JideButton getEditButton() {
		if (editButton == null){
			editButton = new JideButton();
			editButton.setText(CMMessages.getString("LABEL_EDIT_APPLICATION"));
			editButton.setButtonStyle(JideButton.TOOLBAR_STYLE);
			editButton.setPreferredSize(new Dimension(150,26));
			editButton.setIcon(CMIcon.EDIT_PREF.getImageIcon());
			editButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					getExternalApplicationsGridView().edit();
				}});
		}
		return editButton;
	}

}
