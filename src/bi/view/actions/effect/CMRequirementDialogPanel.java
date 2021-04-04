/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.effect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import bi.view.lang.CMMessages;
import bi.view.utils.CMDnDJList;

/**
 * @author smoreno
 *
 */
public class CMRequirementDialogPanel extends JPanel {

	/**
	 * @author smoreno
	 *
	 */
	class CMAutoCompletionCombo extends PlainDocument implements Document {
		private JComboBox comboBox;
		private ComboBoxModel model;

		/**
		 * @param p_comboSelectedRequirement
		 */
		public CMAutoCompletionCombo(JComboBox p_comboSelectedRequirement) {
			  this.comboBox = p_comboSelectedRequirement;
		        model = comboBox.getModel();
		        editor.addKeyListener(new KeyAdapter() {
		        	  public void keyPressed(KeyEvent e) {
		        	    if (comboBox.isDisplayable()) comboBox.setPopupVisible(true);
		        	  }
		        	});
		}
	
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
	        // return immediately when selecting an item
	       if (selecting) return;
	       // insert the string into the document
	        super.insertString(offs, str, a);
	        // lookup and select a matching item
	        Object item = lookupItem(getText(0, getLength()));
	        setSelectedItem(item);
	        // remove all text and insert the completed string
	        super.remove(0, getLength());
	        super.insertString(0, item.toString(), a);
	        // select the completed part
	        //JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
	        editor.setSelectionStart(offs+str.length());
	        editor.setSelectionEnd(getLength());
	    }
		 private Object lookupItem(String pattern) {
		        // iterate over all items
		        for (int i=0, n=model.getSize(); i < n; i++) {
		            Object currentItem = model.getElementAt(i);
		            // current item starts with the pattern?
		            if (currentItem.toString().startsWith(pattern)) {
		                return currentItem;
		            }
		        }
	        // no item starts with the pattern => return null
	        return null;
	    }
		    private void setSelectedItem(Object item) {
		        selecting = true;
		        model.setSelectedItem(item);
		        selecting = false;
		    }
	}

	private JPanel jPanel = null;
	private JPanel jPanelButtons = null;
	private JPanel jPanelRequirements = null;
	private  boolean selecting = false;
	private JComboBox jComboSelectedRequirement = null;
	private JButton jButtonAdd = null;
	private JButton jButtonRemove = null;
	private JScrollPane jScrollReqList = null;
	private CMDnDJList requirementsJList = null;
	private JPanel jPanelSpacer1 = null;
	private JPanel jPanelSpacer2 = null;
	private DefaultListModel reqListModel = null;
	private  DefaultComboBoxModel availabelReqsModel = null;
	private JPanel panelSpacer5 = null;
	private JPanel jPanelSpacer3 = null;
	private JTextComponent editor = null;
	private JPanel jPanelSpacer7 = null;
	private JPanel jPanel1 = null;
	private JPanel jPanelCombo = null;
	/**
	 * @param p_layout
	 * @param p_isDoubleBuffered
	 */
	public CMRequirementDialogPanel(LayoutManager p_layout,
			boolean p_isDoubleBuffered) {
		super(p_layout, p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_layout
	 */
	public CMRequirementDialogPanel(LayoutManager p_layout) {
		super(p_layout);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_isDoubleBuffered
	 */
	public CMRequirementDialogPanel(boolean p_isDoubleBuffered) {
		super(p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * 
	 */
	public CMRequirementDialogPanel() {
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
		this.setSize(300, 200);
		this.add(getJPanel(), java.awt.BorderLayout.NORTH);
		this.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
		this.add(getJPanelRequirements(), java.awt.BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			jPanel = new JPanel();
			jPanel.setLayout(borderLayout);
			jPanel.setPreferredSize(new java.awt.Dimension(10,55));
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel.add(getJPanelSpacer3(), java.awt.BorderLayout.WEST);
			jPanel.add(getPanelSpacer5(), java.awt.BorderLayout.EAST);
			jPanel.add(getJPanelCombo(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {	if (jPanelButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(10);
			flowLayout.setVgap(5);
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(flowLayout);
			jPanelButtons.setPreferredSize(new java.awt.Dimension(10,35));
			jPanelButtons.add(getJButtonAdd(), null);
			jPanelButtons.add(getJButtonRemove(), null);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jPanelRequirements	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelRequirements() {
		if (jPanelRequirements == null) {
			jPanelRequirements = new JPanel();
			jPanelRequirements.setLayout(new BorderLayout());
			jPanelRequirements.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			jPanelRequirements.setMinimumSize(new Dimension(300, 135));
			jPanelRequirements.add(getJPanelSpacer1(), java.awt.BorderLayout.WEST);
			jPanelRequirements.add(getJScrollReqList(), java.awt.BorderLayout.CENTER);
			jPanelRequirements.add(getJPanelSpacer2(), java.awt.BorderLayout.EAST);
			jPanelRequirements.add(getJPanelSpacer7(), java.awt.BorderLayout.NORTH);
		}
		return jPanelRequirements;
	}

	/**
	 * This method initializes jComboSelectedRequirement	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getJComboSelectedRequirement() {
		if (jComboSelectedRequirement == null) {
			jComboSelectedRequirement = new JComboBox();
			jComboSelectedRequirement.setPreferredSize(new java.awt.Dimension(150,22));
			jComboSelectedRequirement.setEditable(true);
			jComboSelectedRequirement.setModel(getAvailabelReqsModel());
			editor = (JTextComponent) jComboSelectedRequirement.getEditor().getEditorComponent();
		//	editor.setDocument(new CMAutoCompletionCombo(jComboSelectedRequirement));
			jComboSelectedRequirement
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							addFromComboToList();
						}
					});
		}
		return jComboSelectedRequirement;
	}

	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setBorderPainted(true);
			jButtonAdd.setFocusPainted(true);
			jButtonAdd.setText(CMMessages.getString("REQUIREMENT_PANEL_ADD_REQUIREMENT")); //$NON-NLS-1$
			jButtonAdd.setPreferredSize(new java.awt.Dimension(140,23));
			jButtonAdd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
//			jButtonAdd.setIcon(new ImageIcon(getClass().getResource("/bi/view/mainframeviews/icons/plus.gif"))); //$NON-NLS-1$
			jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					addFromComboToList();
				}

	
			});
		}
		return jButtonAdd;
	}
	public void addFromComboToList() {
		if (getJComboSelectedRequirement().getModel().getSelectedItem()!=null)
	   if (!getJComboSelectedRequirement().getModel().getSelectedItem().toString().trim().equalsIgnoreCase("")) //$NON-NLS-1$
	   {
		   Object selectedItem = getJComboSelectedRequirement().getModel().getSelectedItem();
		   List assignedList = Arrays.asList(((DefaultListModel)this.getCMJListReqs().getModel()).toArray()); 
		   for (Object item : assignedList)
			   if (item.toString().trim().equalsIgnoreCase(selectedItem.toString().trim()))
			   {
				   getCMJListReqs().getSelectionModel().setSelectionInterval(getReqListModel().indexOf(item),getReqListModel().indexOf(item));
				   return;
			   }
		   ((DefaultListModel)this.getCMJListReqs().getModel()).addElement(selectedItem.toString());
		   getCMJListReqs().getSelectionModel().setSelectionInterval(getReqListModel().indexOf(selectedItem.toString()),getReqListModel().indexOf(selectedItem.toString()));
		   if (getAvailabelReqsModel().getSize()>0)
		   for (int i = 0;i<=getAvailabelReqsModel().getSize()-1;i++)
			   if (getAvailabelReqsModel().getElementAt(i).toString().trim().equalsIgnoreCase(selectedItem.toString().trim()))
				   return;
		   getAvailabelReqsModel().addElement(selectedItem);
	   }
		
	}
	/**
	 * This method initializes jButtonRemove	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButtonRemove() {
		if (jButtonRemove == null) {
			jButtonRemove = new JButton();
			jButtonRemove.setPreferredSize(new java.awt.Dimension(140,23));
			jButtonRemove.setBorderPainted(true);
			jButtonRemove.setFocusPainted(true);
			jButtonRemove.setText(CMMessages.getString("REQUIREMENT_PANEL_REMOVE_REQUIREMENT")); //$NON-NLS-1$
			jButtonRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					removeItemFromList(); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jButtonRemove;
	}

	/**
	 * 
	 */
	protected void removeItemFromList() {
		if (this.getCMJListReqs().getSelectedIndex()!=-1)
		{	
					int selectedIndex = this.getCMJListReqs().getSelectedIndex();
					this.getReqListModel().remove(this.getCMJListReqs().getSelectedIndex());
					selectedIndex--;
					if (selectedIndex>=0)
						this.getCMJListReqs().getSelectionModel().setSelectionInterval(selectedIndex,selectedIndex);
		}
		
	}

	/**
	 * This method initializes jScrollReqList	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollReqList() {
		if (jScrollReqList == null) {
			jScrollReqList = new JScrollPane();
			jScrollReqList.setPreferredSize(new java.awt.Dimension(200,130));
			jScrollReqList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, CMMessages.getString("REQUIREMENT_PANEL_ASSIGNED_REQUIREMENTS"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)); //$NON-NLS-1$
			jScrollReqList.setViewportBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jScrollReqList.setViewportView(getCMJListReqs());
		}
		return jScrollReqList;
	}

	/**
	 * This method initializes CMJListReqs	
	 * 	
	 * @return bi.view.utils.CMDnDJList	
	 */
	public CMDnDJList getCMJListReqs() {
		if (requirementsJList == null) {
			requirementsJList = new CMDnDJList();
			requirementsJList.setSize(new java.awt.Dimension(150,126));
			requirementsJList.setModel(getReqListModel());
		}
		return requirementsJList;
	}

	/**
	 * This method initializes jPanelSpacer1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSpacer1() {
		if (jPanelSpacer1 == null) {
			jPanelSpacer1 = new JPanel();
			jPanelSpacer1.setPreferredSize(new java.awt.Dimension(50,10));
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
			jPanelSpacer2.setPreferredSize(new java.awt.Dimension(50,10));
		}
		return jPanelSpacer2;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelSpacer5() {
		if (panelSpacer5 == null) {
			panelSpacer5 = new JPanel();
			panelSpacer5.setPreferredSize(new java.awt.Dimension(50,10));
		}
		return panelSpacer5;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSpacer3() {
		if (jPanelSpacer3 == null) {
			jPanelSpacer3 = new JPanel();
			jPanelSpacer3.setPreferredSize(new java.awt.Dimension(50,10));
		}
		return jPanelSpacer3;
	}

	public DefaultListModel getReqListModel() {
		if (this.reqListModel==null)
			this.reqListModel = new DefaultListModel();
		return this.reqListModel;
	}

	public DefaultComboBoxModel getAvailabelReqsModel() {
		if (availabelReqsModel== null)
			availabelReqsModel = new DefaultComboBoxModel();
		return this.availabelReqsModel;
	}

	/**
	 * This method initializes jPanelSpacer7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSpacer7() {
		if (jPanelSpacer7 == null) {
			jPanelSpacer7 = new JPanel();
			jPanelSpacer7.setLayout(new BorderLayout());
			jPanelSpacer7.setPreferredSize(new java.awt.Dimension(20,5));
			jPanelSpacer7.add(getJPanel1(), java.awt.BorderLayout.WEST);
		}
		return jPanelSpacer7;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setPreferredSize(new java.awt.Dimension(50,10));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelCombo() {
		if (jPanelCombo == null) {
			jPanelCombo = new JPanel();
			jPanelCombo.setLayout(new BorderLayout());
			jPanelCombo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, CMMessages.getString("REQUIREMENT_PANEL_NEW_REQUIREMENT"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)); //$NON-NLS-1$
			jPanelCombo.setPreferredSize(new java.awt.Dimension(162,60));
			jPanelCombo.add(getJComboSelectedRequirement(), java.awt.BorderLayout.NORTH);
		}
		return jPanelCombo;
	}

}