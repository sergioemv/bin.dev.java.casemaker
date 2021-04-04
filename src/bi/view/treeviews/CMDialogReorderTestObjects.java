package bi.view.treeviews;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Project2;
import model.TestObjectReference;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJDialog;
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
 public class CMDialogReorderTestObjects extends CMBaseJDialog{
    public CMDialogReorderTestObjects(CMFrameView p_Frame,Project2 p_Project2, Vector p_nodes)
    {
        super(p_Frame, false);
        m_Frame=p_Frame;
        testObejctReferencesClon=(Vector) p_Project2.getTestObjectReferences().clone();
        m_NodesClon=(Vector)p_nodes.clone();
		m_Project2=p_Project2;
        m_Nodes= p_nodes;
        initGUI();
    }
   public void initGUI()
    {
        jButtonDown.setText(CMMessages.getString("REORDER_TESTOBJECT_DOWN"));
        jButtonDown.setIcon(CMIcon.ARROW_DOWN.getImageIcon());
        jButtonDown.setPreferredSize(new java.awt.Dimension(122, 27));
        jButtonDown.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonDownActionPerformed(e);}});
       jButtonDown.setBorderPainted(false);
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jButtonCancel.setBounds(new java.awt.Rectangle(135, 198, 73, 27));
        jButtonCancel.setPreferredSize(new java.awt.Dimension(70,27));
        jButtonCancel.setMaximumSize(new java.awt.Dimension(100,11));
        jButtonCancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonCancelActionPerformed(e);}});
        jButtonOk.setText(CMMessages.getString("BUTTON_OK"));
        jButtonOk.setBounds(new java.awt.Rectangle(36, 197, 73, 27));
        jButtonOk.setPreferredSize(new java.awt.Dimension(70, 27));
        jButtonOk.setMaximumSize(new java.awt.Dimension(100,11));
        jButtonOk.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonOkActionPerformed(e);}});
//$NON-NLS-1$
        jButtonUp.setText(CMMessages.getString("REORDER_TESTOBJECT_UP")); //$NON-NLS-1$
        jButtonUp.setIcon(CMIcon.ARROW_UP.getImageIcon());
        jButtonUp.setPreferredSize(new java.awt.Dimension(121, 27));
        jButtonUp.setBorderPainted(false);
        
        jButtonUp.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonUpActionPerformed(e);}});
        jScrollPaneTestObjects.setPreferredSize(new java.awt.Dimension(248, 131));
        jScrollPaneTestObjects.setSize(new java.awt.Dimension(248, 143));
//$NON-NLS-1$
//$NON-NLS-1$
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        getContentPane().add(jPanelContent, java.awt.BorderLayout.CENTER);
        setBounds(new java.awt.Rectangle(0, 0, 262, 243));
        setTitle(CMMessages.getString("TITLE_REORDER_TESTOBJECTS")); //$NON-NLS-1$
        jScrollPaneTestObjects.getViewport().add(jListTestObject);
        jListTestObject.setBounds(new java.awt.Rectangle(91,59,32,32));
         jPanelContent.setLayout(new java.awt.FlowLayout());
        jPanelContent.setPreferredSize(new java.awt.Dimension(250,245));
        jPanelContent.add(jScrollPaneTestObjects);
        jPanelContent.add(jButtonUp);
        jPanelContent.add(jButtonDown);
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension dlgSize = this.getPreferredSize();
    	Point loc = getLocation();
    	this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
    	setModal(true);
        setResizable(false);
        charge();

        jPanel1.add(jButtonOk);
        jPanel1.add(jButtonCancel);
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
    }
/*	public void charge()
    {
        testObjectsReferences=m_Project2.getM_TestObjectReferences();
        for(int i=0; i< m_Project2.getM_TestObjectReferences().size();i++)
        {
             TestObjectReference testObjectReference= (TestObjectReference)m_Project2.getM_TestObjectReferences().elementAt(i);
             //grueda24112004_begin
             TestObjectReference testObjectReference2 = (TestObjectReference) m_NodesClon.elementAt(i);
             if( testObjectReference.getM_Name().equals(testObjectReference2.getM_Name()) ) {
			   testObjectNames.addElement(testObjectReference.getM_Name());
             }
             //grueda24112004_end
        }
        jListTestObject.setListData(testObjectNames);
    }
*/
    public void charge()
    {
        testObjectsReferences=m_Project2.getTestObjectReferences();
        for(int i=0; i< m_Project2.getTestObjectReferences().size();i++)
        {
             TestObjectReference testObjectReference= (TestObjectReference)m_Project2.getTestObjectReferences().elementAt(i);			 
			 testObjectNames.addElement(testObjectReference.getName());
        }
        jListTestObject.setListData(testObjectNames);
        jListTestObject.setSize(new java.awt.Dimension(245,128));
    }

    public void jButtonUpActionPerformed(ActionEvent e)
    {
		int i=jListTestObject.getSelectedIndex();
        if(i > 0)
        {
        	Object objName =jListTestObject.getSelectedValue();
        	testObjectNames.remove(i);
        	testObjectNames.insertElementAt(objName,i-1);
        	Object objReference = testObjectsReferences.elementAt(i);
        	testObjectsReferences.remove(i);
        	testObjectsReferences.insertElementAt(objReference,i-1);
            Object objNodes = m_Nodes.elementAt(i);
        	m_Nodes.remove(i);
        	m_Nodes.insertElementAt(objNodes,i-1);
            jListTestObject.setListData(testObjectNames);
            jListTestObject.setSelectedIndex(i-1);
        }
    }

    public void jButtonDownActionPerformed(ActionEvent e)
    {
         int i=jListTestObject.getSelectedIndex();
         if (i==-1)
        	 return;
        if(i+1 < testObjectNames.size())
        {
        	Object objName =jListTestObject.getSelectedValue();
        	testObjectNames.remove(i);
        	testObjectNames.insertElementAt(objName,i+1);
        	Object objReference = testObjectsReferences.elementAt(i);
        	testObjectsReferences.remove(i);
        	testObjectsReferences.insertElementAt(objReference,i+1);
             Object objNodes = m_Nodes.elementAt(i);
        	m_Nodes.remove(i);
        	m_Nodes.insertElementAt(objNodes,i+1);
            jListTestObject.setListData(testObjectNames);
            jListTestObject.setSelectedIndex(i+1);
        }
    }

    public void jButtonOkActionPerformed(ActionEvent e)
    {
        dispose();
    }

    public void jButtonCancelActionPerformed(ActionEvent e)
    {
        cancelselected=true;
        m_Project2.setTestObjectReferences(testObejctReferencesClon);
        m_Nodes=m_NodesClon;
        this.dispose();
    }
	public Vector  getTestObjectReference(){
        return testObjectsReferences;
    }
    private JScrollPane jScrollPaneTestObjects = new JScrollPane();
    private JButton jButtonUp = new JButton();
    private JList jListTestObject = new JList();
    private JButton jButtonCancel = new JButton();
    private Vector testObjectNames= new Vector(0);
    private Vector testObjectsReferences = new Vector(0);
    private JPanel jPanelContent = new JPanel();
    private JButton jButtonDown = new JButton();
    private JButton jButtonOk = new JButton();
    private Project2 m_Project2;
    private Vector m_Nodes;
    public boolean cancelselected=false;
    private Vector testObejctReferencesClon=new Vector(0);
    private Vector m_NodesClon=new Vector(0);
    private CMFrameView m_Frame;
    private JPanel jPanelButtons = new JPanel();
    private JPanel jPanel1 = new JPanel();
	protected List getOrder() {
	
		return null;
	}
	protected void fireButtonOk() {
		this.jButtonOkActionPerformed(null);
		
	}
	protected void fireButtonCancel() {
		this.jButtonCancelActionPerformed(null);
		
	}
	public JButton getDefaultButton() {
		return this.jButtonOk;
	}
}
