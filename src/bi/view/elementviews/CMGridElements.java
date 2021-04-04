/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.elementviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import model.CMField;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import model.util.CMUserOrderBean;
import bi.controller.ElementManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellElement;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementDescriptionEmpty;
import bi.view.cells.CMCellElementGuiObject;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEmpty;
import bi.view.cells.CMCellEquivalenceClass;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassEffects;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellGroupDataElement;
import bi.view.cells.CMCellGroupDataEquivalenceClass;
import bi.view.cells.CMCellGroupHeaderElement;
import bi.view.cells.CMCellGroupHeaderEquivalenceClass;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementDescriptionEmpty;
import bi.view.cells.headers.CMCellHeaderElementGuiObject;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassEffects;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.mainframeviews.CMFrameView;
import bi.view.treeviews.CMTreeWorkspaceView;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMDnDJSmartGrid;

import com.eliad.model.AbstractDirectSpanModel;
import com.eliad.model.ExtentCell;
import com.eliad.model.GenericGridModel;
import com.eliad.model.GridSelectionEvent;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEvent;

/**
 * Title:        Grid of Elements
 * Description:  Grid that represents the elements and the equivalence classes
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */

public class CMGridElements extends CMDnDJSmartGrid implements CMModelListener{
  private CMFrameView cmFrame;
  private CMGridModel  cmGridModel = null;
  private CMSpanModel  cmSpanModel = null;
  private Structure      structure = null;
  private Object  editingObject = null;

  public CMGridElements(CMFrameView frame) {
	super();
    cmFrame = frame;
    initGUI();
    this.setGridNavigationPolicy(new CMGridElementsNavPolicy(this));
  }


  public void deleteAllElementViews() {
    int numOfRows = cmGridModel.getRowCount();
    cmGridModel.removeRows(0,numOfRows);
  }



  public void setStructure(Structure p_Structure){
	if (structure != null)
		  removeModelListener(structure);
	structure = p_Structure;
	addModelListener(structure);
    //stopCellEditing();
    this.deleteAllElementViews();
	this.addAllElementViews();


  }

  private void addModelListener(Structure structure2) {
	  structure2.addModelListener(this);
		for (Element element : structure2.getElements())
			element.addModelListener(this);

}


private void removeModelListener(Structure structure2) {
	// TODO Auto-generated method stub
	structure2.removeModelListener(this);
	for (Element element : structure2.getElements())
		element.removeModelListener(this);
}


public Structure getStructure() {
    return structure;
  }


 protected HashMap<Class,Component> getCellClasses() {
	HashMap<Class,Component>  map = new HashMap<Class,Component> ();
	map.put(CMCellElementName.class,new JTextField());
	map.put(CMCellElementDescription.class,new JTextField());
	map.put(CMCellElementGuiObject.class,new CMBaseJComboBox(this));
	map.put(CMCellEquivalenceClassValue.class,new JTextField());
	map.put(CMCellEquivalenceClassState.class,new CMBaseJComboBox(this));
	map.put(CMCellEquivalenceClassDescription.class,new JTextField());
	//map.put()
	return map;
}

  public void initGUI() {


    cmGridModel = new CMGridModel(0,6);

    this.setModel(cmGridModel);
    cmSpanModel = new CMSpanModel(cmGridModel);
    this.setSpanModel(cmSpanModel);
 ////////////////////////////////////////////////////////////////////////////////
    this.setOpaque(false);
	this.setColumnResizable(true);
////////////////////////////////////////////////////////////////////////////////
    this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
	this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
    this.setSelectionBackgroundColor(SystemColor.activeCaptionText);
    this.setSelectionForegroundColor(Color.black);
    this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_CELL);
    this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
////////////////////////////////////////////////////////////////////////////////
    this.setGridColor(new Color(196,194,196));
    this.setColumnWidths();

    this.setAlignmentY((float) 0.5);
    this.setAlignmentX((float) 0.5);
    this.addGridListener(new com.eliad.swing.GridAdapter() {
      public void gridMouseMoved(GridEvent e) {
        eventGridMouseMoved(e);
      }
      public void gridMouseClicked(GridEvent e) {
        eventGridMouseClicked(e);
      }
      public void gridMouseExited(GridEvent e) {
			}
	  public void gridMouseEntered(GridEvent e) {

      }
    });

    this.addGridEditingListener(new com.eliad.swing.GridEditingListener() {
    	public void editingStarted(GridEditingEvent e) {
                 eventEditingStarted(e);
      }
      public void editingStopped(GridEditingEvent e) {

      }
      public void editingCanceled(GridEditingEvent e) {

        eventEditingCanceled(e);

      }

    });
    addPropertyChangeListener(new PropertyChangeListener(){public void propertyChange(PropertyChangeEvent e){thisPropertyChange(e);}});
    addComponentListener(new ComponentAdapter(){public void componentResized(ComponentEvent e){thisComponentResized(e);}});

    //smoreno
    initializeCellRenderers();
    initializeCellEditors();

 }



 public void setColumnWidths(){

    this.setColumnWidth(0,35*6);
    this.setColumnWidth(1,20*6);
    this.setColumnWidth(2,1*6);
    this.setColumnWidth(3,30*6);
    this.setColumnWidth(4,50*6);
    this.setColumnWidth(5,29*6+4);
 }


  public void eventGridMouseMoved(GridEvent e) {


	if( !isEnabled() )  return;
    int row = e.getRow();
    int column = e.getColumn();
  //  GridSelectionEvent.ANCHOR_CHANGED = 3;
    Object obj = getCellObjectAt(row,column);
    if( obj != null) {
        if( obj instanceof CMCellElementName || obj instanceof CMCellElementGuiObject
        	|| obj instanceof CMCellElementDescription ||
            obj instanceof CMCellEquivalenceClassValue || obj instanceof CMCellEquivalenceClassDescription ) {
            this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }
        else {
          this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}



public void eventGridMouseClicked(GridEvent e) {

    if( !isEnabled() )  return;
    MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
	int row = e.getRow();
	int column = e.getColumn();
    Object obj = null;
	obj = getCellObjectAt(row,column);

    if( mouseEvent.getClickCount() == 2) {
			if( obj != null) {
        if( obj instanceof CMCellEquivalenceClassName) {
            cmFrame.setStateEquivalenceClassContentSelected();
            CMAction.EQUIVALENCECLASS_EDIT.getAction().actionPerformed(null);
        }
        else if( obj instanceof CMCellEquivalenceClassEffects) {
        	cmFrame.setStateEffectsContentSelected();
			if((structure.getEffects()!=null) && (structure.getEffects().size()!=0))
            	CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.getAction().actionPerformed(null);
        }
      }
    }
    else {
					if( obj != null) {
							if( obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementGuiObject || obj instanceof CMCellHeaderElementDescription) {
                  				cmFrame.setStateElementHeaderSelected();

							}
							else if( obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
											 obj instanceof CMCellHeaderEquivalenceClassValue || obj instanceof CMCellHeaderEquivalenceClassDescription ||
											 obj instanceof CMCellHeaderEquivalenceClassEffects) {

                  				cmFrame.setStateEquivalenceClassHeaderSelected();
							}
							else if( obj instanceof CMCellEquivalenceClassName  ||
                                       obj instanceof CMCellEquivalenceClassValue || obj instanceof CMCellEquivalenceClassDescription
                             ) {

                  				cmFrame.setStateEquivalenceClassContentSelected();
							}
							else if( obj instanceof CMCellEquivalenceClassState){
								cmFrame.setStateEquivalenceClassContentSelected();
                            }
							else if( obj instanceof CMCellEquivalenceClassEffects) {
						        	cmFrame.setStateEffectsContentSelected();
						        }
							else if (obj instanceof CMCellEmpty){
								cmFrame.setStateCellEmptyContentSelected();
							}
							else if( obj instanceof CMCellElementName || /*obj instanceof CMCellElementGuiObject ||*/ obj instanceof CMCellElementDescription) {
                       				   cmFrame.setStateElementContentSelected();
							}
              else {
                                cmFrame.setStateOneElements();
							}
					}
					else {
                         cmFrame.setStateOneElements();
					}
    }

		if((e.getSourceEvent().getModifiers() == Event.META_MASK)&& !(obj instanceof CMCellEmpty)) {
		  if(row >= 0 && column >= 0) {
		    this.changeSelection(row,column,false,false);
			}
          	stopCellEditing();
			this.add(cmFrame.getPopupMenuElements());
			mouseEvent = (MouseEvent) e.getSourceEvent();
			cmFrame.getPopupMenuElements().show(this, mouseEvent.getX(), mouseEvent.getY());
	  }
  }

   public void eventEditingStarted(GridEditingEvent e){
      if( !isEnabled() )  return;
      int row = e.getRow();
      int column = e.getColumn();
      editingObject = getCellObjectAt(row,column);
      if( editingObject instanceof CMCellEquivalenceClassState) {
            cmFrame.setStateEquivalenceClassContentSelected();
       }

   }
   public void eventEditingCanceled(GridEditingEvent e) {
     int x = 0;

   }



 /**
 * @param panelTestObjectView
 *  creates all the element views and the equivalence classes asociated
 */
public void addAllElementViews() {
	List<Element> elems=structure.getElements(CMUserOrderBean.COMPARATOR);
   for(Element element : elems) {
	   //Logger.getLogger(this.getClass()).debug(element+" - User Order - " +element.getUserOrder()); //$NON-NLS-1$

     createElementView(element);
     for(EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
	   createEquivalenceClassView(equivalenceClass);

   }

 }


 public void updateElement(Element element){
       CMTreeWorkspaceView m_to = cmFrame.getTreeWorkspaceView();
	   if (m_to == null){	   }
	   else{
	     String tname = cmFrame.getTreeWorkspaceView().getCurrentTestObject().getToolVendorTechnology();//m_Technology.getM_Name();
	   if (tname == ""){

	   }
	   else{
	   element.setGUIObject(0);

	   }
	   }
 }

 public void createElementView(Element element) {
	 //listen to the element
    CMCellGroupHeaderElement cmCellGroupHeaderElement = new CMCellGroupHeaderElement(6); //I change 6 to 7

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementName(this,element) );
//  My Add
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementGuiObject(this,element));

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescription(this,element) );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );


    CMCellGroupDataElement cmCellGroupDataElement = new CMCellGroupDataElement(6); // I change 6 to 7


    cmCellGroupDataElement.addElement(new CMCellElementName(this,element));

    cmCellGroupDataElement.addElement(new CMCellElementGuiObject(this,element));
    cmCellGroupDataElement.addElement(new CMCellElementDescription(this,element));

    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );

    cmGridModel.addRow(cmCellGroupHeaderElement);

    cmGridModel.addRow(cmCellGroupDataElement);
 }


 public void insertComplexElementView(int index, Element element) {
     insertElementView(index, element);
     index = index + 2;
     int x;
     int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
     for(int j = numOfEquivalenceClasses-1; j >= 0; j--) {
       EquivalenceClass equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(j);
	   insertEquivalenceClassView(index, equivalenceClass, element);
     }
 }



 public void insertElementView(int index, Element element) {
    CMCellGroupHeaderElement cmCellGroupHeaderElement = new CMCellGroupHeaderElement(6); // I change 6 to 7
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementName(this,element) );
//  My Add
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementGuiObject(this,element) );

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescription(this,element) );

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty() );

    CMCellGroupDataElement cmCellGroupDataElement = new CMCellGroupDataElement(6);// I change 6 to 7
    cmCellGroupDataElement.addElement(new CMCellElementName(this,element));

    //  My Add

//jCBTVT.initialize(jCBObjectType,ToolVendorTech,cmFrame);

//    int indexCombo = indexFromjComboBoxOT(element.getStateNameOT());
//    if (indexCombo>0){
//    	cmCellGroupDataElement.addElement((jCBObjectType.getItemAt(indexCombo)));//jCBObjectType.getItemAt(element.getStateOT()));
//    }
//    else{
//    	updateElement(element);
//    	cmCellGroupDataElement.addElement((jCBObjectType.getItemAt(indexCombo)));//jCBObjectType.getItemAt(element.getStateOT()));
//    }


    cmCellGroupDataElement.addElement(new CMCellElementGuiObject(this,element));
    cmCellGroupDataElement.addElement(new CMCellElementDescription(this,element));

    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty() );

    cmGridModel.insertRow(index, cmCellGroupDataElement);
    cmGridModel.insertRow(index, cmCellGroupHeaderElement);
 }

 public void insertEquivalenceClassView(int index, EquivalenceClass equivalenceClass, Element element) {
	CMCellGroupDataEquivalenceClass cmCellGroupDataEquivalenceClass = new CMCellGroupDataEquivalenceClass(6);
    cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
 	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassName(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassState(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassValue(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassDescription(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassEffects(this,equivalenceClass) );
    cmGridModel.insertRow(index, cmCellGroupDataEquivalenceClass);

    if( element.getEquivalenceClasses().indexOf(equivalenceClass) == 0 ) {
		CMCellGroupHeaderEquivalenceClass cmCellGroupHeaderEquivalenceClass = new CMCellGroupHeaderEquivalenceClass(6);
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassName(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassState(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassValue(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassDescription(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassEffects(this,equivalenceClass) );
        cmGridModel.insertRow(index, cmCellGroupHeaderEquivalenceClass);
    }
 }

 public void createEquivalenceClassView(EquivalenceClass equivalenceClass) {
    if( equivalenceClass.getLnkElement().getEquivalenceClasses().indexOf(equivalenceClass) == 0 ) {
		CMCellGroupHeaderEquivalenceClass cmCellGroupHeaderEquivalenceClass = new CMCellGroupHeaderEquivalenceClass(7);//I change 6 to 7
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassName(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassState(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassValue(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassDescription(this,equivalenceClass) );
		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassEffects(this,equivalenceClass) );
   		cmGridModel.addRow(cmCellGroupHeaderEquivalenceClass);
    }
	CMCellGroupDataEquivalenceClass cmCellGroupDataEquivalenceClass = new CMCellGroupDataEquivalenceClass(7);
    cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
 	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassName(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassState(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassValue(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassDescription(this,equivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassEffects(this,equivalenceClass) );
	cmGridModel.addRow(cmCellGroupDataEquivalenceClass);
 }




  public int getEquivalenceClassViewIndex(int p_SelectedRow, int p_SelectedColumn, Element currentElement) {
        int columnCount = 6;
        int equivalenceClassIndex = 0;
        int numEquivalenceClasses = currentElement.getEquivalenceClasses().size();
        Object obj = getCellObjectAt(p_SelectedRow, p_SelectedColumn);
        if( obj != null) {
          if( obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementGuiObject || obj instanceof CMCellHeaderElementDescription
           ) {
              if( numEquivalenceClasses > 0) {
                equivalenceClassIndex = p_SelectedRow + 1 + numEquivalenceClasses + 2;
              }
              else {
                equivalenceClassIndex = p_SelectedRow + numEquivalenceClasses + 2;
              }
            }
//        Ccastedo begins 12-10-05
            else if(obj instanceof CMCellElementName || obj instanceof CMCellElementDescription){
        	  if( numEquivalenceClasses > 0) {
                  equivalenceClassIndex = p_SelectedRow + 1 + numEquivalenceClasses + 1;
                }
                else {
                  equivalenceClassIndex = p_SelectedRow + numEquivalenceClasses + 1;
                }
            }
//        Ccastedo ends 12-10-05
            if( obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
              obj instanceof CMCellHeaderEquivalenceClassDescription || obj instanceof CMCellHeaderEquivalenceClassValue ||
              obj instanceof CMCellHeaderEquivalenceClassEffects
            ) {
              equivalenceClassIndex = p_SelectedRow + numEquivalenceClasses + 1;
            }
//          Ccastedo begins 12-10-05
            else  if( obj instanceof CMCellEquivalenceClassName || obj instanceof CMCellEquivalenceClassDescription || obj instanceof CMCellEquivalenceClassValue
            		|| obj instanceof CMCellEquivalenceClassState
              ) {
                  equivalenceClassIndex = getHeaderRowPosition(p_SelectedRow,p_SelectedColumn) + numEquivalenceClasses +1;
              }
//          Ccastedo ends 12-10-05
         }
        return equivalenceClassIndex;
  }
//Ccastedo begins 12-10-05
  public int getHeaderRowPosition(int p_SelectedRow,int p_SelectedColumn){
	  int l_rowHeader = 0;
	  for (int i=p_SelectedRow;i>0;i--){
		  Object obj = getCellObjectAt(i, p_SelectedColumn);
		  if (obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassDescription || obj instanceof CMCellHeaderEquivalenceClassValue
				  || obj instanceof CMCellHeaderEquivalenceClassState
		  ){
			  l_rowHeader=i;
			  break;
		  }
	  }
	  return l_rowHeader;
  }



  public Element getCurrentElement() {
    int row = this.getSelectionModel().getLeadRow();
    int column = this.getSelectionModel().getLeadColumn();
    Element currentElement;
    EquivalenceClass currentEquivalenceClass;
    Object obj = getCellObjectAt(row, column);
    if( obj == null) {
      return null;
    }
    if( obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementGuiObject || obj instanceof CMCellHeaderElementDescription
    		|| obj instanceof CMCellElementName || obj instanceof CMCellElementGuiObject || obj instanceof CMCellElementDescription
    ) {
              currentElement = ((CMCellElement)obj).getElement();
              return currentElement;
    }
    else if( obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
              obj instanceof CMCellHeaderEquivalenceClassDescription || obj instanceof CMCellHeaderEquivalenceClassValue ||
              obj instanceof CMCellHeaderEquivalenceClassEffects || obj instanceof CMCellEquivalenceClassDescription
      		|| obj instanceof CMCellEquivalenceClassName || obj instanceof CMCellEquivalenceClassState
    		|| obj instanceof CMCellEquivalenceClassValue

    ) {

              currentEquivalenceClass = ((CMCellEquivalenceClass)obj).getEquivalenceClass();
              currentElement = currentEquivalenceClass.getLnkElement();
              return currentElement;
    }
    else {
      return null;
    }
  }


  public CMCellGroupHeaderEquivalenceClass createEquivalenceClassHeaderView(EquivalenceClass p_EquivalenceClass) {
    CMCellGroupHeaderEquivalenceClass cmCellGroupHeaderEquivalenceClass = new CMCellGroupHeaderEquivalenceClass(7);

	//cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
	cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
    cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassName(this,p_EquivalenceClass) );
	cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassState(this,p_EquivalenceClass) );
    cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassValue(this,p_EquivalenceClass) );
	cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassDescription(this,p_EquivalenceClass) );
	cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassEffects(this,p_EquivalenceClass) );

    return cmCellGroupHeaderEquivalenceClass;
  }

  public CMCellGroupDataEquivalenceClass createEquivalenceClassDataView(EquivalenceClass p_EquivalenceClass) {
    CMCellGroupDataEquivalenceClass cmCellGroupDataEquivalenceClass = new CMCellGroupDataEquivalenceClass(7);
    cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
   // cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassName(this,p_EquivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassState(this,p_EquivalenceClass));
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassValue(this,p_EquivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassDescription(this,p_EquivalenceClass) );
	cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassEffects(this,p_EquivalenceClass) );
    return cmCellGroupDataEquivalenceClass;
  }





  public Element getSelectedElement(int row, int column) {
    if( row >= 0 && column >= 0) {
			Object obj = getCellObjectAt(row,column);
			if( obj != null) {
				if( obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementGuiObject ||obj instanceof CMCellHeaderElementDescription ||
						obj instanceof CMCellElementName || obj instanceof CMCellElementGuiObject || obj instanceof CMCellElementDescription) {
					Element element = ((CMCellElement)obj).getElement();
					return element;
				}
				else {
					return null;
				}
			}
			else{
				return null;
			}
		}
    else {
      return null;
    }
  }



  public EquivalenceClass getSelectedEquivalenceClass(int row, int column) {
    if( row >= 0 && column >= 0) {
			Object obj = getCellObjectAt(row,column);
			if( obj != null) {
				if( obj instanceof CMCellEquivalenceClassName  || obj instanceof CMCellEquivalenceClassValue ||
            obj instanceof CMCellEquivalenceClassDescription || obj instanceof CMCellEquivalenceClassEffects) {
					EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)obj).getEquivalenceClass();
					return equivalenceClass;
				}
        else if( obj instanceof CMCellEquivalenceClassState) {
          obj = getCellObjectAt(row,column-1);
          if( obj == null) {
            return null;
          }
					EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)obj).getEquivalenceClass();
					return equivalenceClass;

        }
				else {
					return null;
				}
			}
			else{
				return null;
			}
		}
    else {
      return null;
    }
  }


  public void updateViews() {
    cmFrame.getCMGridDependencies().updateDependencies();
    cmFrame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().update();
    cmFrame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().update();
  }


  public EquivalenceClass getSelectedEquivalenceClass() {
    int selectedRow = this.getSelectionModel().getLeadRow();
    int selectedColumn = this.getSelectionModel().getLeadColumn();
    EquivalenceClass selectedEquivalenceClass = this.getSelectedEquivalenceClass(selectedRow, selectedColumn);
    return selectedEquivalenceClass;
  }





  public Element getSelectedElement(){
    int selectedRow = this.getSelectionModel().getLeadRow();
    int selectedColumn = this.getSelectionModel().getLeadColumn();
    Element selectedElement = this.getSelectedElement(selectedRow, selectedColumn);

    return selectedElement;
  }
  public void rangeChanged(GridSelectionEvent e) {
    super.rangeChanged(e); //fcastro_02092004
        int selectedRow = this.getFirstSelectedRow();
        int selectedColumn = this.getFirstSelectedColumn();
    if( !e.isAdjusting() ) {
       Object obj = getCellObjectAt(selectedRow,selectedColumn);

			  if( obj != null) {
					if( obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementGuiObject || obj instanceof CMCellHeaderElementDescription) {
	                    cmFrame.setStateElementHeaderSelected();
					}
					else if( obj instanceof CMCellElementName || obj instanceof CMCellElementGuiObject || obj instanceof CMCellElementDescription) {

					   cmFrame.setStateElementContentSelected();
					}
					else if( obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
									 obj instanceof CMCellHeaderEquivalenceClassValue || obj instanceof CMCellHeaderEquivalenceClassDescription ||
									 obj instanceof CMCellHeaderEquivalenceClassEffects) {
	                   cmFrame.setStateEquivalenceClassHeaderSelected();
					}
					else if( obj instanceof CMCellEquivalenceClassName  ||
                            obj instanceof CMCellEquivalenceClassValue || obj instanceof CMCellEquivalenceClassDescription
                    ) {

				cmFrame.setStateEquivalenceClassContentSelected();
					}
					else if( obj instanceof CMCellEquivalenceClassEffects){
					cmFrame.setStateEffectsContentSelected();
					}
					else if (obj instanceof CMCellEmpty){

						cmFrame.setStateCellEmptyContentSelected();
					}
					else if( obj instanceof CMCellEquivalenceClassName || obj instanceof CMCellEquivalenceClassState ||
									 obj instanceof CMCellEquivalenceClassValue || obj instanceof CMCellEquivalenceClassDescription
									) {
									 cmFrame.setStateEquivalenceClassContentSelected();
								}
							 else {
                                        cmFrame.setStateOneElements();
								}
					}
					else {
                        		cmFrame.setStateOneElements();
					}
       //fcastro_02092004_begin
		if(selectedRow == this.getRowCount()-1){

        }
        //fcastro_02092004_end
    }
    this.repaint();
  }

  public void thisPropertyChange(PropertyChangeEvent e) {
	this.setColumnWidth(2,1*6);
  }

  public void thisComponentResized(ComponentEvent e) {
	this.setColumnWidth(2,1*6);

  }



  //////////////////////// GridModel ///////////////////////////////////////////
  public class CMGridModel extends GenericGridModel {
    public CMGridModel(int numRows, int numColumns) {
      super(numRows, numColumns);

    }
    public boolean isCellEditable(int row, int column) {
      if(this.getRowCount() > row && this.getColumnCount() > column
         && row >= 0 && column >= 0) {
		  Object obj = super.getValueAt(row,column);
		  if(obj == null) {
			return false;
		  }
		  if( obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementGuiObject || obj instanceof CMCellHeaderElementDescription ||
			obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
			obj instanceof CMCellHeaderEquivalenceClassValue || obj instanceof CMCellHeaderEquivalenceClassDescription ||
			obj instanceof CMCellHeaderEquivalenceClassEffects || obj instanceof CMCellEquivalenceClassName ||
			obj instanceof CMCellEquivalenceClassEffects || obj instanceof CMCellEmpty) {
			return false;
		  }
		  else {
			return true;
		  }
      }
      else{
        return false;
      }
    }


  }
  //////////////////////// SpanModel ///////////////////////////////////////////
  public class CMSpanModel extends AbstractDirectSpanModel {
    CMGridElements.CMGridModel m_gridModel = null;
    public CMSpanModel(CMGridElements.CMGridModel m) {
      m_gridModel = m;
    }
    public ExtentCell getSpanOver(final int row, final int column) {
        Object obj = getCellObjectAt(row,column);
        if( obj == null) {
          return null;
        }
        if ( 	obj instanceof CMCellHeaderElementDescription || obj instanceof CMCellElementDescription ||
        		obj instanceof CMCellHeaderElementDescriptionEmpty || obj instanceof CMCellHeaderElementDescriptionEmpty || obj instanceof CMCellElementDescriptionEmpty) {
							return new ExtentCell() {
								public int getRow() {
									return row;
								}

								public int getRowCount() {
									return 1;
								}
								public int getColumn() {
									return 2;//I change 1 to 2
								}
								public int getColumnCount() {
									return 5;
								}
								public Object getIdentifier() {
									return null;
								}
							};
        }
        else {
          return null;
        }
    }
    public boolean isEmpty() {
        return false;
    }
  }






public CMGridModel getCmGridModel() {
	return cmGridModel;
}

public void setCmGridModel(CMGridModel cmGridModel) {
	this.cmGridModel = cmGridModel;
}

/* (non-Javadoc)
 * @see model.util.CMModelListener#handleCMModelChange(model.util.CMModelEvent)
 */
public void handleCMModelChange(CMModelEvent p_evt) {
	if (p_evt.getSource() instanceof Structure)
	{
		if (p_evt.getChangedField()== CMField.ELEMENTS) {
			this.removeModelListener(getStructure());
			this.addModelListener(getStructure());
			this.deleteAllElementViews();
			this.addAllElementViews();
		}
	}
	if (p_evt.getSource() instanceof Element)
	{
		if (p_evt.getChangedField() == CMField.EQUIVALENCE_CLASSES)
		{
			this.deleteAllElementViews();
			 this.addAllElementViews();
		}
	}


}

/**
 * @param p_equivalenceClass
 */
public void selectEquivalenceClassEffects(EquivalenceClass p_equivalenceClass) {
	int gridRows = this.getRowCount();
	for (int i=0;i<gridRows;i++)
	{
		if (this.getCellObjectAt(i,5) instanceof CMCellEquivalenceClassEffects)
			if (p_equivalenceClass.equals(((CMCellEquivalenceClassEffects)this.getCellObjectAt(i,5)).getEquivalenceClass() ))
				this.changeSelection(i,5,false,false);
	}
}

/* (non-Javadoc)
 * @see bi.view.utils.CMDnDJSmartGrid#drop(java.awt.dnd.DropTargetDropEvent)
 */
@Override
public void drop(DropTargetDropEvent p_dtde) {
	Point dropPoint = p_dtde.getLocation();
	 int column = columnAtPoint(dropPoint);
     int row = rowAtPoint(dropPoint);
     if ((row == -1 && column == -1)||(row == draggedRow))
     {
    	 p_dtde.rejectDrop();
    	 return;
     }
     Object dropObject = getValueAt(row,column);

     try {
    	 Object draggedObject = p_dtde.getTransferable().getTransferData(localObjectFlavor);
    	 if ((draggedObject instanceof CMCellElement)
    		 &&(dropObject instanceof CMCellElement))
    	 {
    		 CMCellElement cellOrigin = (CMCellElement) draggedObject;
    		 CMCellElement cellDestination = (CMCellElement) dropObject;
    		 if (cellDestination.getElement().equals(cellOrigin.getElement()))
    		 {
    			 p_dtde.rejectDrop();
    			 return;
    		 }
    		 p_dtde.acceptDrop(DnDConstants.ACTION_MOVE);
    		 ElementManager.INSTANCE.moveElement(cellOrigin.getElement(), cellDestination.getElement());

    	 }

	} catch (UnsupportedFlavorException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
