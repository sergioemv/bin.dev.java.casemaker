/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package  bi.view.utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bi.view.lang.CMMessages;

import com.borland.jbcl.layout.BoxLayout2;
import com.borland.jbcl.layout.XYLayout;

/**
 * Title:        CaseMaker
 * Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */

public class CMShuttle extends JPanel {
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout1 = new XYLayout();
  JList jListLeft = new JList() ;
  JList jListRight = new JList();
  JButton jButtonAdd = new JButton();
  JButton jButtonDelete = new JButton();
  JPanel jPanelLeftBox = new JPanel();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  JPanel jPanelRightBox = new JPanel();
  BoxLayout2 boxLayout22 = new BoxLayout2();
  JScrollPane rightScrollPane  = new JScrollPane(jListRight);
  JScrollPane leftScrollPane = new JScrollPane(jListLeft);
  /////////////////////////////////////////////
  String leftName  = CMMessages.getString("LABEL_AVAILABEL_ELEMENTS"); //$NON-NLS-1$
  String rightName = CMMessages.getString("LABEL_DEPENDENT_ELEMENTS"); //$NON-NLS-1$
  List leftElements = null;
  Vector rightElements = null;
  Vector leftViews = null;
  Vector rightViews = null;
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  TitledBorder titledBorder2;
  //OLD: JScrollPane jScrollPane1 = new JScrollPane();
  //OLD: JScrollPane jScrollPane2 = new JScrollPane();
  JPanel jPanel1 = new JPanel();
  Comparator m_CompareData= null;
  //ccastedo
  private Vector listOfElementsToDelete= new Vector(0);
  private int[] indexToDelete;
  private boolean listchanged = true;

  //ccastedo
/**
 * @return Returns the m_CompareData.
 */
public Comparator getM_CompareData() {
	return m_CompareData;
}
/**
 * @param compareData The m_CompareData to set.
 */
public void setM_CompareData(Comparator compareData) {
	m_CompareData = compareData;
}
  ////////////////////////////////////////////

  public CMShuttle(String left, String right) {
    leftName = left;
    rightName = right;
    leftElements = new Vector(0);
    rightElements = new Vector(0);
    leftViews = new Vector(0);
    rightViews = new Vector(0);
    try {
      initGUI();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void initGUI() throws Exception {
	  listchanged = true;
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder1 = new TitledBorder(border1,leftName);
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder2 = new TitledBorder(border2,rightName);
    setLayout(null);
    jButtonAdd.setText(">"); //$NON-NLS-1$
    jButtonAdd.setBounds(new java.awt.Rectangle(272, 46, 50, 27));
    jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAdd_actionPerformed(e);
      }
    });
    jButtonDelete.setToolTipText(""); //$NON-NLS-1$
    jButtonDelete.setText("<"); //$NON-NLS-1$
    jButtonDelete.setBounds(new java.awt.Rectangle(272, 79, 50, 27));
    jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDelete_actionPerformed(e);
      }
    });
    jPanelLeftBox.setLayout(boxLayout21);
    jPanelLeftBox.setBorder(titledBorder1);
    jPanelLeftBox.setBounds(new java.awt.Rectangle(0, -3, 264, 145));
    jPanelRightBox.setBorder(titledBorder2);
    jPanelRightBox.setLayout(boxLayout22);

    //OLD: jPanelRightBox.setBounds(new java.awt.Rectangle(198,0,144,114));
	jPanelRightBox.setBounds(new java.awt.Rectangle(329, -3, 264, 145));
    xYLayout1.setWidth(400);
    xYLayout1.setHeight(132);
    setBounds(new java.awt.Rectangle(0, 0, 593, 142));
    add(leftScrollPane);
    add(jPanelLeftBox);
    add(jButtonAdd);
    add(jButtonDelete);
    add(rightScrollPane);
    add(jPanelRightBox);
    //OLD: add(jScrollPane1);
    //OLD: add(jScrollPane2);
    add(jPanel1);
    leftScrollPane.setBounds(new java.awt.Rectangle(8, 13, 248, 119));
    //OLD: rightScrollPane.setBounds(new java.awt.Rectangle(205,18,129,88));
	rightScrollPane.setBounds(new java.awt.Rectangle(337, 13,248, 119));
    //OLD: jScrollPane1.setBounds(new java.awt.Rectangle(202,18,129,88));
    //OLD: jScrollPane2.setBounds(new java.awt.Rectangle(5,16,127,92));
    jPanel1.setBounds(new java.awt.Rectangle(195,0,144,114));
    jListRight.addListSelectionListener(new ListSelectionListener(){public void valueChanged(ListSelectionEvent e){jListRightValueChanged(e);}});
    jListLeft.addListSelectionListener(new ListSelectionListener(){public void valueChanged(ListSelectionEvent e){jListLeftValueChanged(e);}});
  }
  public void changeRigthScrollPaneWitdh(int p_dialogWitdh)
  {
	  setSize(p_dialogWitdh-26,getHeight());
	  rightScrollPane.setSize(getWidth()-346,rightScrollPane.getHeight());
	  jPanelRightBox.setSize(getWidth()-330,jPanelRightBox.getHeight());
	  jListRight.setSize(getWidth()-76,jListRight.getWidth());
  }

  public void jButtonAdd_actionPerformed(ActionEvent e) {
    int[] indices = jListLeft.getSelectedIndices();
     indexToDelete = jListLeft.getSelectedIndices();
    int selectedIndex = 0;
    int newSelectedIndex = 0;

	Vector elementsToBeDeleted = new Vector(0);
    Vector viewsToBeDeleted = new Vector(0);
    Object selectedElement = null;
    Object selectedElementView = null;
	Object elementToBeDeleted = null;
	Object viewToBeDeleted = null;

    for( int i = 0; i < indices.length; i++) {
      selectedIndex = indices[i];
      if( selectedIndex >= 0) {
    	  listchanged = true;
		  selectedElement = leftElements.get(selectedIndex);
		  selectedElementView = leftViews.elementAt(selectedIndex);
		  rightElements.addElement(selectedElement);

		  listOfElementsToDelete.addElement(selectedElement);
		  rightViews.addElement(selectedElementView);
          elementsToBeDeleted.addElement(selectedElement);
          viewsToBeDeleted.addElement(selectedElementView);

      }
    }

    int numOfDeletingObjects = elementsToBeDeleted.size();

    for( int i = 0; i < numOfDeletingObjects; i++) {
      elementToBeDeleted = elementsToBeDeleted.elementAt(i);
      viewToBeDeleted = viewsToBeDeleted.elementAt(i);
      leftElements.remove(elementToBeDeleted);
	  leftViews.removeElement(viewToBeDeleted);
    }

    if( indices.length > 0) {
      if(m_CompareData!=null)
      {
      	Collections.sort(rightViews,m_CompareData);
      	Collections.sort(rightElements,m_CompareData);
      	Collections.sort(leftViews,m_CompareData);
      	Collections.sort(leftElements,m_CompareData);
      }
      jListRight.setListData(rightViews);
	  jListLeft.setListData(leftViews);
    }
  }

  public void jButtonDelete_actionPerformed(ActionEvent e) {
    int[] indices = jListRight.getSelectedIndices();
    int selectedIndex = 0;
    int newSelectedIndex = 0;
	Vector elementsToBeDeleted = new Vector(0);
    Vector viewsToBeDeleted = new Vector(0);
    Object selectedElement = null;
    Object selectedElementView = null;
	Object elementToBeDeleted = null;
	Object viewToBeDeleted = null;

    for( int i = 0; i < indices.length; i++) {
      selectedIndex = indices[i];
      if( selectedIndex >= 0) {
    	  listchanged = true;
		  selectedElement = rightElements.elementAt(selectedIndex);
		  selectedElementView = rightViews.elementAt(selectedIndex);
		  leftElements.add(selectedElement);
		  leftViews.addElement(selectedElementView);

          elementsToBeDeleted.addElement(selectedElement);
          viewsToBeDeleted.addElement(selectedElementView);
      }
    }

    int numOfDeletingObjects = elementsToBeDeleted.size();

    for( int i = 0; i < numOfDeletingObjects; i++) {
      elementToBeDeleted = elementsToBeDeleted.elementAt(i);
      viewToBeDeleted = viewsToBeDeleted.elementAt(i);
      rightElements.removeElement(elementToBeDeleted);
	  rightViews.removeElement(viewToBeDeleted);
    }

    if( indices.length > 0) {
        if(m_CompareData!=null)
        {
        	Collections.sort(rightViews,m_CompareData);
        	Collections.sort(rightElements,m_CompareData);
        	Collections.sort(leftViews,m_CompareData);
        	Collections.sort(leftElements,m_CompareData);
        }
      jListRight.setListData(rightViews);
	  jListLeft.setListData(leftViews);
    }

  }

  public List getLeftList() {
    return leftElements;
  }

  public Vector getRightList() {
    return rightElements;
  }

  public void setLeftList(List v, Vector views) {
    leftElements = v;
    leftViews = views;
    jListLeft.setListData(leftViews);
  }
  public void setRightList(Vector v, Vector views) {
    rightElements = v;
    rightViews = views;
    jListRight.setListData(rightViews);
  }

  public void jListRightValueChanged(ListSelectionEvent e) {
    int[] indices = jListRight.getSelectedIndices();
    if( indices.length > 1) {
	  jButtonDelete.setText("<<"); //$NON-NLS-1$
    }
    else {
	  jButtonDelete.setText("<"); //$NON-NLS-1$
    }
  }

  public void jListLeftValueChanged(ListSelectionEvent e) {
    int[] indices = jListLeft.getSelectedIndices();
    if( indices.length > 1) {
	  jButtonAdd.setText(">>"); //$NON-NLS-1$
    }
    else {
	  jButtonAdd.setText(">"); //$NON-NLS-1$
    }
  }

  public JButton getAddButton(){
    return this.jButtonAdd;
  }

  public JButton getDeleteButton(){
    return this.jButtonDelete;
  }

  public JList getLeftJList(){
	  if (jListLeft == null)
		  jListLeft = new JList(new DefaultListModel());
    return this.jListLeft;
  }

  public JList getRightJList(){
	  if (jListRight == null)
		  jListRight = new JList(new DefaultListModel());
    return this.jListRight;
  }

  public Vector getLeftViews(){
    return this.leftViews;
  }

  public Vector getRightViews(){
    return this.rightViews;
  }


public void deleteElementsAdded(){
	int[] indices=indexToDelete;
	for (int i=0;i<getListOfElementsToDelete().size();i++){
		indices[i]=rightElements.indexOf(getListOfElementsToDelete().elementAt(i));
	}
	if (indices.length>0){
		Vector elementsToBeDeleted = new Vector(0);
		//elementsToBeDeleted.addAll((Collection) getListOfElementsToDelete().clone());
	    Vector viewsToBeDeleted = new Vector(0);
	    Object selectedElement = null;
	    Object selectedElementView = null;
		Object elementToBeDeleted = null;
		Object viewToBeDeleted = null;
		int selectedIndex;


	   for( int i = 0; i < indices.length; i++) {
	      selectedIndex = indices[i];
	      if( selectedIndex >= 0) {
	    	  listchanged = false;
			  selectedElement = rightElements.elementAt(selectedIndex);
			  selectedElementView = rightViews.elementAt(selectedIndex);
			  leftElements.add(selectedElement);
			  leftViews.addElement(selectedElementView);

	          elementsToBeDeleted.addElement(selectedElement);
	          viewsToBeDeleted.addElement(selectedElementView);
	      }
	    }

	   int numOfDeletingObjects = elementsToBeDeleted.size();

	    for( int i = 0; i < numOfDeletingObjects; i++) {
	      elementToBeDeleted = elementsToBeDeleted.elementAt(i);
	      viewToBeDeleted = viewsToBeDeleted.elementAt(i);
	      rightElements.removeElement(elementToBeDeleted);
		  rightViews.removeElement(viewToBeDeleted);
	    }

	    if( indices.length > 0) {
	        if(m_CompareData!=null)
	        {
	        	Collections.sort(rightViews,m_CompareData);
	        	Collections.sort(rightElements,m_CompareData);
	        	Collections.sort(leftViews,m_CompareData);
	        	Collections.sort(leftElements,m_CompareData);
	        }
	      jListRight.setListData(rightViews);
		  jListLeft.setListData(leftViews);
	    }

	}
}
public Vector getListOfElementsToDelete() {
	return listOfElementsToDelete;
}
public void setListOfElementsToDelete(Vector listOfElementsToDelete) {
	this.listOfElementsToDelete = listOfElementsToDelete;
}
public int[] getIndexToDelete() {
	return indexToDelete;
}
public void setIndexToDelete(int[] indexToDelete) {
	this.indexToDelete = indexToDelete;
}
public boolean isListchanged() {
	return listchanged;
}
public TitledBorder getTitledBorder1() {
	return this.titledBorder1;
}
public void setTitledBorder1(TitledBorder p_titledBorder1) {
	this.titledBorder1 = p_titledBorder1;
}
public TitledBorder getTitledBorder2() {
	return this.titledBorder2;
}
public void setTitledBorder2(TitledBorder p_titledBorder2) {
	this.titledBorder2 = p_titledBorder2;
}

/**
 * @return Returns the jListLeft.
 */
public JList getJListLeft() {
	return this.jListLeft;
}
/**
 * @return Returns the jListRight.
 */
public JList getJListRight() {
	if (jListRight == null)
		jListRight = new JList();
	return this.jListRight;
}
/**
 * @return
 */
public java.util.List getTabOrder() {

	return (java.util.List) Arrays.asList(jListLeft,getAddButton(),getDeleteButton(),jListRight);
}
}
