/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.preferences.lang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.testdataviews.CMStyleModelHeader;
import bi.view.utils.StripeSortableTableModel;

import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;
import com.jidesoft.grid.AbstractStyleTableModel;
import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.SortableTableModel;
import com.jidesoft.grid.StyleModel;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.jidesoft.grid.QuickFilterPane;
import java.awt.GridBagLayout;
import com.jidesoft.grid.QuickTableFilterField;
import java.awt.GridBagConstraints;
import java.util.Vector;
import java.util.logging.Logger;

import com.jidesoft.swing.AutoCompletionComboBox;
import com.jidesoft.grid.QuickFilterField;
import java.lang.String;

/**
 * @author smoreno
 *
 */
public class CMLanguageDialogPanel extends JPanel {

	/**
	 * @author smoreno
	 *
	 */
	

	private JPanel jPanelSpacer1 = null;
	private JPanel jPanelSpacer2 = null;
	private JPanel jPanelSpacer3 = null;
	private JScrollPane jScrollpaneLanguage = null;
	private SortableTable languageTable;
	 public DefaultTableModel languageTableModel;

	private JPanel jPanelSpacer7 = null;

	private String langName;
	protected int lastColumn;
	protected int lastRow;
	private QuickTableFilterField quickTableFilterField = null;
	/**
	 * @param p_layout
	 * @param p_isDoubleBuffered
	 */
	public CMLanguageDialogPanel(LayoutManager p_layout,
			boolean p_isDoubleBuffered) {
		super(p_layout, p_isDoubleBuffered);
		initialize();
	}

	public String getLangName() {
		return langName;
	}
	public void setLangName(String langName){
		
		this.langName = langName;
	}

	/**
	 * @param p_layout
	 */
	public CMLanguageDialogPanel(LayoutManager p_layout) {
		super(p_layout);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_isDoubleBuffered
	 */
	public CMLanguageDialogPanel(boolean p_isDoubleBuffered) {
		super(p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 *
	 */
	public CMLanguageDialogPanel() {
		super();
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(370, 200);
		this.add(getJPanelSpacer3(), java.awt.BorderLayout.NORTH);
		this.add(getJPanelSpacer1(), java.awt.BorderLayout.WEST);
		this.add(getJPanelSpacer2(), java.awt.BorderLayout.EAST);
		this.add(getJScrollpaneLanguage(), java.awt.BorderLayout.CENTER);
		

	//	this.add(getQuickFilterPane(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jPanelSpacer1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSpacer1() {
		if (jPanelSpacer1 == null) {
			jPanelSpacer1 = new JPanel();
			jPanelSpacer1.setPreferredSize(new java.awt.Dimension(30,10));
		}
		return jPanelSpacer1;
	}

	/**
	 * This method initializes jPanelSpacer2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSpacer2() {
		if (jPanelSpacer2 == null) {
			jPanelSpacer2 = new JPanel();
			jPanelSpacer2.setPreferredSize(new java.awt.Dimension(30,10));
		}
		return jPanelSpacer2;
	}

	/**
	 * This method initializes jPanelSpacer3
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSpacer3() {
		if (jPanelSpacer3 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			flowLayout.setHgap(0);
			jPanelSpacer3 = new JPanel();
			jPanelSpacer3.setLayout(flowLayout);
			jPanelSpacer3.setPreferredSize(new Dimension(10, 30));
			jPanelSpacer3.add(getQuickTableFilterField(), null);
			jPanelSpacer3.add(getJPanelSpacer7(), null);
		}
		return jPanelSpacer3;
	}

	/**
	 * This method initializes jScrollpaneExpResults
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollpaneLanguage() {
		if (jScrollpaneLanguage == null) {
			jScrollpaneLanguage = new JScrollPane();
			jScrollpaneLanguage.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(null, CMMessages.getString("LABEL_LANGUAGE_TRANSLATIONS"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED))); //$NON-NLS-1$ //$NON-NLS-2$
			jScrollpaneLanguage.setViewportView(getLanguageTable());

		}
		return jScrollpaneLanguage;
	}
	/**
	 * This method initializes jPanelSpacer7
	 *
	 * @return javax.swing.JPanel
	 */

	  private JPanel getJPanelSpacer7() {
		if (jPanelSpacer7 == null) {
			jPanelSpacer7 = new JPanel();
			jPanelSpacer7.setPreferredSize(new java.awt.Dimension(30,10));
		}
		return jPanelSpacer7;
	}

	public SortableTable getLanguageTable() {
		if (languageTable == null){
			languageTable = new SortableTable(new StripeSortableTableModel(getQuickTableFilterField().getDisplayTableModel(),2));
			languageTable.setDefaultCellRenderer(new DefaultTableCellRenderer(){

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					// TODO Auto-generated method stub
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (column<2){
				    	setIcon(null);
					}
					else{
						if (value.toString().equalsIgnoreCase("")){ //$NON-NLS-1$
							setIcon(CMIcon.WARNING2.getImageIcon());
						}
						setForeground(Color.BLACK);
					}
				return c;
				}});
			languageTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer(){
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					// TODO Auto-generated method stub
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				        setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
						setBackground(new Color(36,38,116));
						setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
						setForeground(new Color(252,254,252));
						setBorder(BorderFactory.createRaisedBevelBorder());
						if (column==2)
							setIcon(CMIcon.EDIT_LANG.getImageIcon());
						else
							setIcon(null);
				return c;
				}
			});
			languageTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			languageTable.getColumnModel().getColumn(1).setPreferredWidth(200);
			languageTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		}
		return languageTable;
	}

	public DefaultTableModel getLanguageTableModel() {
		if (languageTableModel == null){
			languageTableModel = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column<2)
						return false;
						else
					return super.isCellEditable(row, column);
				}
			};
			languageTableModel.addColumn(CMMessages.getString("TDSTRUCTURE_FIELD_KEY")); //$NON-NLS-1$
			languageTableModel.addColumn(CMMessages.getString("LANG_ENGLISH")); //$NON-NLS-1$
			languageTableModel.addColumn(CMMessages.getString("TDSTRUCTURE_FIELD_VALUE")); //$NON-NLS-1
			
		}
		return languageTableModel;
	}

	/**
	 * This method initializes quickTableFilterField	
	 * 	
	 * @return com.jidesoft.grid.QuickTableFilterField	
	 */
	private QuickTableFilterField getQuickTableFilterField() {
		if (quickTableFilterField == null) {
			quickTableFilterField = new QuickTableFilterField(getLanguageTableModel(), new int[]{0,1,2});
		}
		return quickTableFilterField;
	}

	
	


	




}
