/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.effect;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bi.view.lang.CMMessages;
import bi.view.testdataviews.CMStyleModelHeader;

import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

/**
 * @author smoreno
 *
 */
public class CMExpectedResultsDialogPanel extends JPanel {

	/**
	 * @author smoreno
	 *
	 */
	public class CMExpResultsHeaderModel extends DefaultHeaderModel {

		/**
		 * @param p_gridExpectedResults
		 */
		public CMExpResultsHeaderModel(JSmartGrid p_gridExpectedResults) {
			 super(p_gridExpectedResults, JSmartGrid.HORIZONTAL);
			 
		}
		/* (non-Javadoc)
		 * @see com.eliad.swing.DefaultHeaderModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		/* (non-Javadoc)
		 * @see com.eliad.swing.DefaultHeaderModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		/* (non-Javadoc)
		 * @see com.eliad.swing.DefaultHeaderModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int p_arg0, int p_arg1) {
			// TODO Auto-generated method stub
			if (p_arg1 == 0)
			return CMMessages.getString("EXPECTED_RESULT_PANEL_NAME_HEADER"); //$NON-NLS-1$
			else
				return CMMessages.getString("EXPECTED_RESULT_PANEL_VALUE_HEADER"); //$NON-NLS-1$
		}
			}

	private JPanel jPanelSpacer1 = null;
	private JPanel jPanelSpacer2 = null;
	private JPanel jPanelSpacer3 = null;
	private JScrollPane jScrollpaneExpResults = null;
	private CMExpectedResultsGrid gridExpectedResults = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonAdd = null;
	private JButton jButtonDelete = null;
	private CMExpResultsHeaderModel hmodel;
	private JSmartGridHeader gridExpectedResultsHeader;
	private JPanel jPanelSpacer7 = null;
	
	/**
	 * @param p_layout
	 * @param p_isDoubleBuffered
	 */
	public CMExpectedResultsDialogPanel(LayoutManager p_layout,
			boolean p_isDoubleBuffered) {
		super(p_layout, p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_layout
	 */
	public CMExpectedResultsDialogPanel(LayoutManager p_layout) {
		super(p_layout);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_isDoubleBuffered
	 */
	public CMExpectedResultsDialogPanel(boolean p_isDoubleBuffered) {
		super(p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * 
	 */
	public CMExpectedResultsDialogPanel() {
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
		this.add(getJPanelSpacer1(), java.awt.BorderLayout.WEST);
		this.add(getJPanelSpacer2(), java.awt.BorderLayout.EAST);
		this.add(getJPanelSpacer3(), java.awt.BorderLayout.NORTH);
		this.add(getJScrollpaneExpResults(), java.awt.BorderLayout.CENTER);
		this.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
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
			jPanelSpacer3 = new JPanel();
			jPanelSpacer3.setLayout(new BorderLayout());
			jPanelSpacer3.setPreferredSize(new Dimension(10, 15));
			jPanelSpacer3.add(getJPanelSpacer7(), java.awt.BorderLayout.WEST);
		}
		return jPanelSpacer3;
	}

	/**
	 * This method initializes jScrollpaneExpResults	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollpaneExpResults() {
		if (jScrollpaneExpResults == null) {
			jScrollpaneExpResults = new JScrollPane();
			jScrollpaneExpResults.setViewportView(getGridExpectedResults());
			jScrollpaneExpResults.setColumnHeaderView(getGridColumnHeader(getGridExpectedResults()));
		}
		return jScrollpaneExpResults;
	}

	/**
	 * This method initializes gridExpectedResults	
	 * 	
	 * @return com.eliad.swing.JSmartGrid	
	 */
	public CMExpectedResultsGrid getGridExpectedResults() {
		if (gridExpectedResults == null) {
			
			gridExpectedResults = new CMExpectedResultsGrid();
			hmodel = new  CMExpResultsHeaderModel(gridExpectedResults);
			JSmartGridHeader ch = getGridColumnHeader(gridExpectedResults);
			gridExpectedResults.setColumnHeader(ch);
			//set the header
			
		}
		return gridExpectedResults;
	}
	private JSmartGridHeader getGridColumnHeader(JSmartGrid p_gridExpectedResults) {
		if (gridExpectedResultsHeader == null)
		{
			 
			gridExpectedResultsHeader = new JSmartGridHeader(p_gridExpectedResults,JSmartGrid.HORIZONTAL,hmodel,null,new CMStyleModelHeader()) ;
			gridExpectedResultsHeader.setColumnWidth(0,70);
			gridExpectedResultsHeader.setColumnWidth(1,70);
			gridExpectedResultsHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
			gridExpectedResultsHeader.setSelectionMode(JSmartGridHeader.SINGLE_COLUMN_SELECTION);
			gridExpectedResultsHeader.setBackground(new Color(36,38,116));
			gridExpectedResultsHeader.setSelectionBackgroundColor(new Color(36,38,116));
			gridExpectedResultsHeader.setSelectionForegroundColor(new Color(252,254,252));
			gridExpectedResultsHeader.setGridColor(new Color(196,194,196));
			gridExpectedResultsHeader.setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
			gridExpectedResultsHeader.setForeground(new Color(252,254,252));
			gridExpectedResultsHeader.setBorder(BorderFactory.createRaisedBevelBorder());
			
		}
		return gridExpectedResultsHeader;
	}
	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(10);
			flowLayout.setVgap(5);
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(flowLayout);
			jPanelButtons.setPreferredSize(new java.awt.Dimension(10,35));
			jPanelButtons.add(getJButtonAdd(), null);
			jPanelButtons.add(getJButtonDelete(), null);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setPreferredSize(new java.awt.Dimension(170,23));
//			jButtonAdd.setFocusPainted(false);
			jButtonAdd.setText(CMMessages.getString("EXPECTED_RESULT_PANEL_ADD_EXPECTED_RESULT_BUTTON")); //$NON-NLS-1$
//			jButtonAdd.setIcon(new ImageIcon(getClass().getResource("/bi/view/mainframeviews/icons/plus.gif")));
//			jButtonAdd.setBorderPainted(false);
			jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					((CMExpectedResultsGrid) getGridExpectedResults()).addNewRow();
					return;
				}
			});
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButtonDelete() {
		if (jButtonDelete == null) {
			jButtonDelete = new JButton();
			jButtonDelete.setPreferredSize(new java.awt.Dimension(170,23));
//			jButtonDelete.setFocusPainted(false);
			jButtonDelete.setText(CMMessages.getString("EXPECTED_RESULT_PANEL_REMOVE_EXPECTED_RESULT_BUTTON")); //$NON-NLS-1$
//			jButtonDelete.setBorderPainted(false);
			jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					((CMExpectedResultsGrid) getGridExpectedResults()).deleteSelectedRow();
					return;
				}
			});
		}
		return jButtonDelete;
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

	

	

	

	
}
