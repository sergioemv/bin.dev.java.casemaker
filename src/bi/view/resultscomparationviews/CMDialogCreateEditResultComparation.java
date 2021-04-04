package bi.view.resultscomparationviews;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.TDStructure;
import model.TestDataSet;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.utils.JCustomDialog;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMDialogCreateEditResultComparation extends JCustomDialog {
    public CMDialogCreateEditResultComparation(CMFrameView p_CMFrameView, TDStructure p_TDStructure) {
        super(p_CMFrameView,true);
        m_CMFrameView=p_CMFrameView;
        m_TDStructure= p_TDStructure;
        selectedsActual= m_TDStructure.getM_ResultComparation().getTestDataSetActual();
        selectedsTarget=m_TDStructure.getM_ResultComparation().getTestDataSetTarget();
        initGUI();
        charge();
    }
    public void initGUI()
    {
        jButtonOk.setText(CMMessages.getString("BUTTON_OK"));
        jButtonOk.setBounds(new java.awt.Rectangle(155, 332, 74, 25));

        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jButtonCancel.setBounds(new java.awt.Rectangle(253, 332, 73, 25));

        jListSelectedTarget.setBounds(new java.awt.Rectangle(56, 86, 32, 32));
        jListSelectedTarget.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListSelectedActual.setBounds(new java.awt.Rectangle(56, 86, 32, 32));
        jListSelectedActual.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListAvailableTarget.setBounds(new java.awt.Rectangle(84, 66, 32, 32));
        jListAvailableTarget.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListAvailableActual.setBounds(new java.awt.Rectangle(84, 66, 32, 32));
        jListAvailableActual.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListAvailableActual.setListData(availables);
        jScrollPaneSelectedTarget.setBounds(new java.awt.Rectangle(267, 21, 130, 113));
        jScrollPaneAvailableTarget.setBounds(new java.awt.Rectangle(42, 23, 130, 112));
        jButtonDeleteTarget.setText(">>");
        jButtonDeleteTarget.setBounds(new java.awt.Rectangle(184, 42, 73, 27));

        jButtonAddTarget.setText("<<");
        jButtonAddTarget.setBounds(new java.awt.Rectangle(184, 89, 73, 27));

        jScrollPaneSelectedActual.setBounds(new java.awt.Rectangle(267, 21, 130, 113));
        jScrollPaneAvailableActual.setBounds(new java.awt.Rectangle(42, 23, 130, 112));
        jButtonDeleteActual.setText("<<");
        jButtonDeleteActual.setBounds(new java.awt.Rectangle(184, 89, 73, 27));

        jButtonAddActual.setText(">>");
        jButtonAddActual.setBounds(new java.awt.Rectangle(184, 42, 73, 27));

        jPanelTestDataSetTarget.setBounds(new java.awt.Rectangle(22, 171, 439, 151));
        jPanelTestDataSetTarget.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(1,
        null, null), "Choose Test Data Set Expected", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        jPanelTestDataSetTarget.setLayout(null);
        jPanelTestDataSetTarget.add(jButtonAddTarget);
        jPanelTestDataSetTarget.add(jButtonDeleteTarget);
        jPanelTestDataSetTarget.add(jScrollPaneAvailableTarget);
        jPanelTestDataSetTarget.add(jScrollPaneSelectedTarget);
        getContentPane().setLayout(null);
        getContentPane().add(jPanelTestDataSetActual);
        getContentPane().add(jPanelTestDataSetTarget);
        getContentPane().add(jButtonCancel);
        getContentPane().add(jButtonOk);
        jPanelTestDataSetActual.setBounds(new java.awt.Rectangle(24, 14, 439, 148));
        jPanelTestDataSetActual.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(1,
        null, null), "Choose Test Data Set Actual", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        jPanelTestDataSetActual.setLayout(null);
        jPanelTestDataSetActual.add(jButtonAddActual);
        jPanelTestDataSetActual.add(jButtonDeleteActual);
        jPanelTestDataSetActual.add(jScrollPaneAvailableActual);
        jPanelTestDataSetActual.add(jScrollPaneSelectedActual);
        setBounds(new java.awt.Rectangle(0, 0, 485, 408));
        setTitle("CaseMaker - Result Comparison");
        jScrollPaneAvailableActual.getViewport().add(jListAvailableActual);
        jScrollPaneAvailableTarget.getViewport().add(jListAvailableTarget);
        jScrollPaneSelectedActual.getViewport().add(jListSelectedActual);
        jScrollPaneSelectedTarget.getViewport().add(jListSelectedTarget);
        jButtonAddActual.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonAddActualActionPerformed(e);}});
        jButtonDeleteActual.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonDeleteActualActionPerformed(e);}});
        jButtonDeleteTarget.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonDeleteTargetActionPerformed(e);}});
        jButtonAddTarget.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonAddTargetActionPerformed(e);}});
        jButtonOk.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonOkActionPerformed(e);}});
        jButtonCancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonCancelActionPerformed(e);}});

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getSize();
		Point loc = getLocation();
		setLocation((screenSize.width - dlgSize.width) / 2 , (screenSize.height - dlgSize.height) / 2 );
        setModal(true);
        this.setResizable(false);
        getRootPane().setDefaultButton(jButtonOk);
    }

    public void createAvailablesVector()
    {
        Vector tds= m_TDStructure.getM_TestDataSet();
        for(int i=0; i< tds.size(); i++)
        {
            TestDataSet testdataset=(TestDataSet)tds.elementAt(i);
			availables.addElement(testdataset.getName());
        }
        for(int j=0; j< selectedsActual.size();j++)
        {
            availables.remove(selectedsActual.elementAt(j));
        }
        for(int k=0; k< selectedsTarget.size();k++)
        {
            availables.remove(selectedsTarget.elementAt(k));
        }
    }
	private void charge() {
        createAvailablesVector();
        jListAvailableActual.setListData(availables);
        jListAvailableTarget.setListData(availables);
        jListSelectedActual.setListData(selectedsActual);
		jListSelectedTarget.setListData(selectedsTarget);
        cancelSelectedActual=(Vector) m_TDStructure.getM_ResultComparation().getTestDataSetActual().clone();
        cancelSelectedTarget=(Vector)m_TDStructure.getM_ResultComparation().getTestDataSetTarget().clone();
    }
    public void jButtonAddActualActionPerformed(ActionEvent e) {
		 int indexs[] = jListAvailableActual.getSelectedIndices();
        for (int i = 0; i < Array.getLength(indexs); i++) {
            int index = Array.getInt(indexs, i);
            selectedsActual.addElement(availables.elementAt(index));
        }
        for (int i = Array.getLength(indexs) - 1; i >= 0; i--) {
            int index = Array.getInt(indexs, i);
            availables.removeElementAt(index);
        }
        jListAvailableActual.setListData(availables);
        jListAvailableActual.revalidate();
        jListAvailableTarget.setListData(availables);
        jListAvailableTarget.revalidate();
        jListSelectedActual.setListData(selectedsActual);
        jListSelectedActual.revalidate();

    }

    public void jButtonDeleteActualActionPerformed(ActionEvent e) {
         int indexs[] = jListSelectedActual.getSelectedIndices();
        for (int i = 0; i < Array.getLength(indexs); i++) {
            int index = Array.getInt(indexs, i);
            availables.addElement(selectedsActual.elementAt(index));
        }
        for (int i = Array.getLength(indexs) - 1; i >= 0; i--) {
            int index = Array.getInt(indexs, i);
            selectedsActual.removeElementAt(index);
        }
        jListAvailableActual.setListData(availables);
        jListAvailableActual.revalidate();
        jListAvailableTarget.setListData(availables);
        jListAvailableTarget.revalidate();
        jListSelectedActual.setListData(selectedsActual);
        jListSelectedActual.revalidate();
    }

    public void jButtonDeleteTargetActionPerformed(ActionEvent e) {
	 int indexs[] = jListAvailableTarget.getSelectedIndices();
        for (int i = 0; i < Array.getLength(indexs); i++) {
            int index = Array.getInt(indexs, i);
            selectedsTarget.addElement(availables.elementAt(index));
        }
        for (int i = Array.getLength(indexs) - 1; i >= 0; i--) {
            int index = Array.getInt(indexs, i);
            availables.removeElementAt(index);
        }
        jListAvailableActual.setListData(availables);
        jListAvailableActual.revalidate();
        jListAvailableTarget.setListData(availables);
        jListAvailableTarget.revalidate();
        jListSelectedTarget.setListData(selectedsTarget);
        jListSelectedTarget.revalidate();

    }


    public void jButtonAddTargetActionPerformed(ActionEvent e) {
        int indexs[] = jListSelectedTarget.getSelectedIndices();
        for (int i = 0; i < Array.getLength(indexs); i++) {
            int index = Array.getInt(indexs, i);
            availables.addElement(selectedsTarget.elementAt(index));
        }
        for (int i = Array.getLength(indexs) - 1; i >= 0; i--) {
            int index = Array.getInt(indexs, i);
            selectedsTarget.removeElementAt(index);
        }
        jListAvailableActual.setListData(availables);
        jListAvailableActual.revalidate();
        jListAvailableTarget.setListData(availables);
        jListAvailableTarget.revalidate();
        jListSelectedTarget.setListData(selectedsTarget);
        jListSelectedTarget.revalidate();

    }

    public void jButtonOkActionPerformed(ActionEvent e) {
        CMIndexTDStructureUpdate.getInstance().initIndex();
        m_TDStructure.getM_ResultComparation().setTestDataSetActual(selectedsActual);
        m_TDStructure.getM_ResultComparation().setTestDataSetTarget(selectedsTarget);
//HCanedo_03012005_Begin
		int saveUnsave=m_CMFrameView.getCmApplication().getSessionManager().getM_Save_UnsaveVariable()+1;
		m_CMFrameView.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
//HCanedo_03012005_End
        dispose();
    }

    public void jButtonCancelActionPerformed(ActionEvent e) {
        m_TDStructure.getM_ResultComparation().setTestDataSetActual(cancelSelectedActual);
        m_TDStructure.getM_ResultComparation().setTestDataSetTarget(cancelSelectedTarget);
        dispose();
    }

    private JPanel jPanelTestDataSetActual = new JPanel();
    private JPanel jPanelTestDataSetTarget = new JPanel();
    private JButton jButtonAddActual = new JButton();
    private JButton jButtonDeleteTarget = new JButton();
    private JButton jButtonDeleteActual = new JButton();
    private JButton jButtonAddTarget = new JButton();
    private JScrollPane jScrollPaneAvailableActual = new JScrollPane();
    private JScrollPane jScrollPaneAvailableTarget = new JScrollPane();
    private JScrollPane jScrollPaneSelectedActual = new JScrollPane();
    private JScrollPane jScrollPaneSelectedTarget = new JScrollPane();
    private JList jListAvailableActual = new JList();
    private JList jListAvailableTarget = new JList();
    private JList jListSelectedActual = new JList();
    private JList jListSelectedTarget = new JList();
    private JButton jButtonCancel = new JButton();
    private JButton jButtonOk = new JButton();
    private Vector cancelSelectedActual = new Vector();
    private Vector cancelSelectedTarget = new Vector();
    private Vector selectedsActual = new Vector();
    private Vector availables = new Vector();
    private Vector selectedsTarget = new Vector();
    private CMFrameView m_CMFrameView;
    private TDStructure m_TDStructure;
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jListAvailableActual);
		focusOrder.add(jButtonAddActual);
		focusOrder.add(jListSelectedActual);
		focusOrder.add(jButtonDeleteActual);
		focusOrder.add(jListAvailableTarget);
		focusOrder.add(jButtonDeleteTarget);
		focusOrder.add(jListSelectedTarget);
		focusOrder.add(jButtonAddTarget);
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
