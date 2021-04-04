/*
 * Revision:
 * Created: 02-03-2005
 *
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *
 */

package bi.view.testdataviews.grid;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;

import model.TestData;
import model.TestDataSet;
import model.edit.CMModelEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.JCustomDialog;

import com.eliad.swing.JSmartGrid;

/**
 * Dialog to create and edit a <codeTestDataSet></code>.
 * @author Franz Nava
 * @version Revision: Date: 02-03-2005 10:41:16 AM
 */
public class DialogCreateEditTestDataSet extends JCustomDialog {

    private List cancelAvailables = new ArrayList();
    private List cancelSelected = new ArrayList();
    private List selecteds;
    private List availables;
    private CMFrameView frameView;
    private TestDataSet testDataSet;
    private String title;

    private JPanel jPanelTestDataSet = new JPanel();
    private JScrollPane jScrollPaneDescription = new JScrollPane();
    private CMJEditorPaneFocusChangeable jTextAreaDescription = new CMJEditorPaneFocusChangeable();
    private JPanel jPanelProperties = new JPanel();
    private JTextField jTextFieldName = new JTextField();
    private JLabel jLabelName = new JLabel();

    //private JPanel jPanelAssignTestData = new JPanel();
    private PanelAssignTestData jPanelAssignTestData = null;

    private JButton jButtonOk = new JButton();
    private JButton jButtonCancel = new JButton();
    public boolean selectedOk = false;
    CMCompoundEdit m_ce;

    /**
     * Construct an <code>DialogCreateEditTestDataSet</code>
     * @param frameView
     * @param testDataSet
     * @param title
     * @param ce
     */
    public DialogCreateEditTestDataSet(CMFrameView frameView, TestDataSet testDataSet, String title, UndoableEdit ce) {
        super(frameView, true);
        this.frameView = frameView;
        this.testDataSet = testDataSet;
        selecteds = (Vector) testDataSet.getM_TestDataCombinations().getM_TestDatas().clone();
        availables = (Vector) frameView.getPanelTDStructureView().getTDStructure().getTestDataCombination().getM_TestDatas().clone(); //
        this.title = title;
        if(ce == null)
        	m_ce  = new CMCompoundEdit();
        else
        	m_ce = (CMCompoundEdit) ce;
        init();
        initGUI();
    }

    /**
     * @param availables, a list
     * @param selecteds, a list
     */
    private void init() {
        jTextFieldName.setText(testDataSet.getName());
        jTextAreaDescription.setText(testDataSet.getDescription());
        jTextAreaDescription.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        for (Iterator iterator = selecteds.iterator(); iterator.hasNext();) {
            availables.remove(iterator.next());
        }
        cancelSelected.addAll(selecteds);
        cancelAvailables.addAll(availables);
    }

    public void initGUI() {
        jPanelAssignTestData = new PanelAssignTestData(availables, selecteds);
        jScrollPaneDescription.setBounds(new java.awt.Rectangle(41, 70, 520, 76));//svonborries_29122005
        jScrollPaneDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), CMMessages.getString("TESTDATA_DESCRIPTION_TESTDATASET"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        jLabelName.setText(CMMessages.getString("TESTDATA_NAME_TESTDATASET")); //$NON-NLS-1$
        jLabelName.setBounds(new java.awt.Rectangle(43, 44, 47, 16));
        jLabelName.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 11)); //$NON-NLS-1$
        jTextFieldName.setBounds(new java.awt.Rectangle(109, 42, 108, 20));
        jTextFieldName.setEditable(false);
        getContentPane().add(jPanelTestDataSet, java.awt.BorderLayout.CENTER);
        setBounds(new java.awt.Rectangle(0, 0, 624, 480));//svonborries_29122005
        setTitle(title);
        jPanelTestDataSet.setLayout(null);
        jPanelTestDataSet.setPreferredSize(new java.awt.Dimension(457, 350));
        jPanelTestDataSet.add(jPanelProperties);
        jPanelTestDataSet.add(jPanelAssignTestData);
        jPanelTestDataSet.add(jButtonOk);
        jPanelTestDataSet.add(jButtonCancel);
        jTextAreaDescription.setBounds(new java.awt.Rectangle(279, 14, 53, 16));
      //  jTextAreaDescription.setWrapStyleWord(true);
      //  jTextAreaDescription.setLineWrap(true);
        jScrollPaneDescription.getViewport().add(jTextAreaDescription);
        jPanelProperties.setBounds(new java.awt.Rectangle(9, 12, 600, 161));//svonborries_29122005
        jPanelProperties.setLayout(null);
        jPanelProperties.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), CMMessages.getString("TESTDATA_PROPERTIES_TESTDATASET"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
                new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        jPanelProperties.add(jTextFieldName);
        jPanelProperties.add(jLabelName);
        jPanelProperties.add(jScrollPaneDescription);
        jPanelAssignTestData.setBounds(new java.awt.Rectangle(10, 178, 600, 211));//svonborries_29122005
        jPanelAssignTestData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), CMMessages.getString("TESTDATA_ASSIGN_TESTDATA_TO_TESTDATASET"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
                new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        jPanelAssignTestData.setLayout(null);
        jButtonOk.setText(CMMessages.getString("BUTTON_OK"));
        jButtonOk.setBounds(new java.awt.Rectangle(447, 405, 73, 27));//svonborries_29122005
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jButtonCancel.setBounds(new java.awt.Rectangle(535, 405, 73, 27));//svonborries_29122005
        jButtonOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonOkActionPerformed(e);
            }
        });
        jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonCancelActionPerformed(e);
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getPreferredSize();
        Point loc = getLocation();
        this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
        setModal(true);
        setResizable(false);
        JSmartGrid availables=this.jPanelAssignTestData.getGridAvailable();
        JSmartGrid selected=this.jPanelAssignTestData.getGridSelected();
        availables.addKeyListener(defaultKeyListener);
      getRootPane().setDefaultButton(jButtonOk);
    }

    private KeyListener defaultKeyListener = new KeyAdapter()
	{

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER)
				jButtonOkActionPerformed(null);

			if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
				jButtonCancelActionPerformed(null);

		}

  };
    @SuppressWarnings("unchecked")
	public void jButtonOkActionPerformed(ActionEvent e) {
        for (int i = 0; i < selecteds.size(); i++) {
            TestData td = (TestData) selecteds.get(i);
            if (!cancelSelected.contains(td)) {
            	int id = td.getContTestDataSet()+1;
            	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeContTestDataSetInTestData(td,id));
                td.setContTestDataSet(id);
                m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIsSetTestDataModelEdit(td,true));
                td.setSet(true);
            }
        }
        for (int i = 0; i < availables.size(); i++) {
            TestData td = (TestData) availables.get(i);
            if (!cancelAvailables.contains(td)) {
            	int id = td.getContTestDataSet()-1;
            	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeContTestDataSetInTestData(td,id));
                td.setContTestDataSet(id);
                if (td.getContTestDataSet() == 0)
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIsSetTestDataModelEdit(td,false));
                    td.setSet(false);
            }
        }
        Vector testDatas = new Vector(selecteds);
        m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestDatasToTestDataCombination(testDataSet.getM_TestDataCombinations(),testDatas));
        testDataSet.getM_TestDataCombinations().setM_TestDatas(testDatas);
        //String oldDescription = testDataSet.getDescription();
        m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionInTestDataSet(testDataSet,jTextAreaDescription.getText()));
        testDataSet.setDescription(jTextAreaDescription.getText());
        //if (selecteds.size() != 0 && title.equals(CMMessages.getString("TESTDATA_EDIT_TESTDATASET")))
            //frameView.getM_CMUndoMediator().editTestDataSetAt(frameView.getGridTDStructure(), (Vector) new Vector(selecteds).clone(), jTextAreaDescription.getText(), (Vector) new Vector(cancelSelected).clone(), oldDescription, CMIndexTDStructureUpdate.getInstance().getindexTestDataSet(), frameView.getm_TabbedPaneView(), CMMessages.getString("TESTDATA_TESTDATASET"));
        selectedOk = true;
        dispose();
    }

    public void jButtonCancelActionPerformed(ActionEvent e) {
        availables = cancelAvailables;
        selecteds = cancelSelected;
        testDataSet.getM_TestDataCombinations().setM_TestDatas(new Vector(selecteds));
        dispose();
    }

	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jTextAreaDescription);
		focusOrder.add(jPanelAssignTestData.getGridAvailable());
		focusOrder.add(jPanelAssignTestData.getButtonAdd());
		focusOrder.add(jPanelAssignTestData.getGridSelected());
		focusOrder.add(jPanelAssignTestData.getButtonDelete());
		focusOrder.add(jButtonOk);
		focusOrder.add(jButtonCancel);
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);
	}

	protected void PressJButtonOk(Object object) {
		jButtonOkActionPerformed(null);
	}

}