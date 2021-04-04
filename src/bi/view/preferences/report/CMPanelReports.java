package bi.view.preferences.report;

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
import com.borland.jbcl.control.SplitPanel;
import javax.swing.JSplitPane;

public class CMPanelReports extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelButtons = null;
	private JideButton addButton;
	private JideButton editButton;
	private JideButton deleteButton;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPaneDS = null;
	private JScrollPane jScrollPaneFormats = null;
	private CMReportFormatsGridView reportFormatsGridView = null;
	private CMReportDataSourceGridView reportDataSourceGridView = null;
	/**
	 * This is the default constructor
	 */
	public CMPanelReports() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(457, 252);
		this.setLayout(new BorderLayout());
		this.add(getJPanelButtons(), BorderLayout.SOUTH);
		this.add(getJSplitPane(), BorderLayout.CENTER);
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

	private JideButton getAddButton() {
		if (addButton == null){
			addButton = new JideButton();
			addButton.setText(CMMessages.getString("LABEL_ADD_XSLT"));
			addButton.setButtonStyle(JideButton.TOOLBAR_STYLE);
			addButton.setIcon(CMIcon.INSERT_PREF.getImageIcon());
			addButton.setPreferredSize(new Dimension(150,26));
			addButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					getReportFormatsGridView().create();
				}});
		}
		return addButton;
	}

	private JideButton getDeleteButton() {
		if (deleteButton == null){
			deleteButton = new JideButton();
			deleteButton.setText(CMMessages.getString("LABEL_REMOVE_XSLT"));
			deleteButton.setButtonStyle(JideButton.TOOLBAR_STYLE);
			deleteButton.setPreferredSize(new Dimension(150,26));
			deleteButton.setIcon(CMIcon.DELETE_PREF.getImageIcon());
			deleteButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					getReportFormatsGridView().delete();
				}});
		}
		return deleteButton;
	}

	private JideButton getEditButton() {
		if (editButton == null){
			editButton = new JideButton();
			editButton.setText(CMMessages.getString("LABEL_EDIT_XSLT"));
			editButton.setButtonStyle(JideButton.TOOLBAR_STYLE);
			editButton.setPreferredSize(new Dimension(150,26));
			editButton.setIcon(CMIcon.EDIT_PREF.getImageIcon());
			editButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					getReportFormatsGridView().edit();
				}});
		}
		return editButton;
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
			jSplitPane.setDividerSize(3);
			jSplitPane.setDividerLocation(130);
			jSplitPane.setBottomComponent(getJScrollPaneFormats());
			jSplitPane.setTopComponent(getJScrollPaneDS());
			jSplitPane.setBorder(BorderFactory.createTitledBorder(new PartialLineBorder(Color.gray, 1, true),"", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPaneDS
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneDS() {
		if (jScrollPaneDS == null) {
			jScrollPaneDS = new JScrollPane();
			jScrollPaneDS.setViewportView(getReportDataSourceGridView());
		}
		return jScrollPaneDS;
	}

	/**
	 * This method initializes jScrollPaneFormats
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneFormats() {
		if (jScrollPaneFormats == null) {
			jScrollPaneFormats = new JScrollPane();
			jScrollPaneFormats.setViewportView(getReportFormatsGridView());
		}
		return jScrollPaneFormats;
	}

	/**
	 * This method initializes reportFormatsGridView
	 *
	 * @return bi.view.preferences.reports.CMReportFormatsGridView
	 */
	public CMReportFormatsGridView getReportFormatsGridView() {
		if (reportFormatsGridView == null) {
			reportFormatsGridView = new CMReportFormatsGridView();
		}
		return reportFormatsGridView;
	}

	/**
	 * This method initializes reportDataSourceGridView
	 *
	 * @return bi.view.preferences.reports.CMReportDataSourceGridView
	 */
	public CMReportDataSourceGridView getReportDataSourceGridView() {
		if (reportDataSourceGridView == null) {
			reportDataSourceGridView = new CMReportDataSourceGridView(getReportFormatsGridView());
		}
		return reportDataSourceGridView;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
