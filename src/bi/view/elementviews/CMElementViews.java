/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.elementviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.SystemColor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JViewport;

import model.CMField;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import model.util.CMUserOrderBean;

import org.apache.log4j.Logger;

import bi.view.cells.CMCellDependency;
import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementDescriptionEmpty;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEmpty;
import bi.view.cells.CMCellEquivalenceClass;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellGroupDataElement;
import bi.view.cells.CMCellGroupDataEquivalenceClass;
import bi.view.cells.CMCellGroupHeaderElement;
import bi.view.cells.CMCellGroupHeaderEquivalenceClass;
import bi.view.cells.CMCellHeaderTestCase;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.cells.headers.CMCellHeaderCombination;
import bi.view.cells.headers.CMCellHeaderDependencyName;
import bi.view.cells.headers.CMCellHeaderEffectsInCombination;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementDescriptionEmpty;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testcaseviews.CMTestCaseViews;
import bi.view.utils.CMBaseJComboBox;

import com.eliad.model.AbstractDirectSpanModel;
import com.eliad.model.ExtentCell;
import com.eliad.model.GenericGridModel;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

public class CMElementViews extends CMBaseJSmartGrid implements CMModelListener{
  private CMGridModel cmGridModel = null;
  private CMSpanModel  spanModel = null;
  private CMStyleModel styleModel = null;
  private CMFrameView cmFrame = null;

  private Structure m_Structure = null;

    private int m_Width;
    private Component m_RightSideComponent = null;
    private Component rightSide = null;

    private CMTestCaseViews m_CMTestCaseViews = null;

    //Ccastedo begins 20-02-06
    private FocusListener focusListener = new FocusAdapter(){
		public void focusGained(FocusEvent e) {
		 //	getM_RightSideComponent().repaint();
			}
		@Override
		public void focusLost(FocusEvent e) {
			repaint();

		}

	};
	 //Ccastedo ends 20-02-06


    public CMElementViews(CMFrameView frame) {
    super();
    this.cmFrame = frame;
    initGUI();
    this.setGridNavigationPolicy(new CMElementViewsGridNavPolicy(this));
    this.addFocusListener(this.focusListener );
    }

    public void initGUI() {

    this.cmGridModel = new CMGridModel(0,5);
    this.setModel(this.cmGridModel);
    this.spanModel = new CMSpanModel(this.cmGridModel);
    this.setSpanModel(this.spanModel);
    this.styleModel = new CMStyleModel();

    this.setStyleModel(this.styleModel);
////////////////// Style ///////////////////////////////////////////////////////
    this.setOpaque(false);

	this.setColumnResizable(true);
    this.setAutoResizeMode(RulerConstants.HORIZONTAL);
    this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
	this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
    this.setSelectionBackgroundColor(SystemColor.activeCaptionText);
    this.setSelectionForegroundColor(Color.black);
    this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_CELL);
    this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
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
    });
    this.addGridEditingListener(new com.eliad.swing.GridEditingListener() {
      public void editingStarted(GridEditingEvent e) {
      }

      public void editingCanceled(GridEditingEvent e) {
      }

	public void editingStopped(GridEditingEvent arg0) {}
    });

    addComponentListener(new ComponentAdapter(){public void componentResized(ComponentEvent e){thisComponentResized(e);}});
    addPropertyChangeListener(new PropertyChangeListener(){public void propertyChange(PropertyChangeEvent e){thisPropertyChange(e);}});
    initializeCellEditors();
    initializeCellRenderers();
    }


    public void changeSelection(int row,int column, boolean toogle,boolean extent){
    	super.changeSelection(row,column,false,false);
    	((CMBaseJSmartGrid)this.getM_RightSideComponent()).repaint();
        this.repaint();

    }


    public boolean isEquivalenceClassCell(int row,int column){
        Object obj = this.cmGridModel.getCellObjectAt(row,column);
        if(obj != null && (obj instanceof  CMCellEquivalenceClassName || obj instanceof CMCellEquivalenceClassDescription
                			|| obj instanceof CMCellEquivalenceClassState || obj instanceof CMCellEquivalenceClassValue)){
            return true;
        }
        return false;
    }

    public void setM_RightSideComponent(Component c){
        this.m_RightSideComponent = c;
    }
    public Component getM_RightSideComponent(){
    	if (CMApplication.frame.getTabbedPane().getSelectedIndex()==3)
    		this.m_RightSideComponent = CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews();
    	if (CMApplication.frame.getTabbedPane().getSelectedIndex()==4)
    		this.m_RightSideComponent = CMApplication.frame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews();
        return this.m_RightSideComponent;
    }
    //fcastro_20092004_end

    public JViewport getViewport1() {
      return getViewport();    // getViewport is not public in JSmartGrid
    }


    public void setM_Structure(Structure p_Structure) {
    	if (p_Structure != null)
  		  removeModelListener(p_Structure);

    	this.m_Structure = p_Structure;
  	addModelListener(m_Structure);

        update();
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
    public Structure getM_Structure() {
        return this.m_Structure;
    }

 public void setColumnWidths(){
   /* int columnWidth1 = 15*6;
   // int columnWidth2 = 15*6;//my add
    int columnWidth2 = 18*6;
    int columnWidth3 = 1*6;
    int columnWidth4 = 15*6;
    int columnWidth5 = 15*6;//fcastro_13092004

   */
     int columnWidth1 = 15*6;
    int columnWidth2 = 18*6;
    int columnWidth3 = 1*6;
    int columnWidth4 = 15*6;
    int columnWidth5 = 15*6;//fcastro_13092004


    this.setM_Width(columnWidth1+columnWidth2+columnWidth3+columnWidth4+columnWidth5);//fcastro_13092004
    this.setColumnWidth(0,columnWidth1);
    this.setColumnWidth(1,columnWidth2);
    this.setColumnWidth(2,columnWidth3);
    this.setColumnWidth(3,columnWidth4);
    this.setColumnWidth(4,columnWidth5);//fcastro_13092004
   // this.setColumnWidth(5,columnWidth6);//My add
 }



	 public void addElementViews(List<Element> p_Elements) {
	   int numOfElements = p_Elements.size();
	   for( int i = 0; i < numOfElements; i++) {
	     Element element = (Element) p_Elements.get(i);
	     if (element.getEquivalenceClasses().size()==0)
	    	 continue;
         createElementView(element);
		 int numEquivalenceClasses = element.getEquivalenceClasses().size();
		 for(int j = 0; j < numEquivalenceClasses; j++) {
		    EquivalenceClass equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(j);
            createEquivalenceClassView(element,equivalenceClass,this.m_Structure);
		 }
	   }
	 }

  public void update(){
      deleteElementViews();
      addElementViews(this.m_Structure.getElements(CMUserOrderBean.COMPARATOR));
   }



 private void createElementView(Element element) {
    CMCellGroupHeaderElement cmCellGroupHeaderElement = new CMCellGroupHeaderElement(5);//I change 5 to 6
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementName(this,element) );
//  My add
  //  cmCellGroupHeaderElement.addElement(new CMCellHeaderElementGuiObject(element) );

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescription(this,element) );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());
  //  cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());
    //My add
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());//fcastro_13092004

    CMCellGroupDataElement cmCellGroupDataElement = new CMCellGroupDataElement(5); //I change 5 to 6
    cmCellGroupDataElement.addElement( new CMCellElementName(this,element));
//  My Add
  //  cmCellGroupDataElement.addElement( new CMCellElementGuiObject(element));

    cmCellGroupDataElement.addElement( new CMCellElementDescription(this,element));
    cmCellGroupDataElement.addElement( new CMCellElementDescriptionEmpty());
    cmCellGroupDataElement.addElement( new CMCellElementDescriptionEmpty());
    //My add
   // cmCellGroupDataElement.addElement( new CMCellElementDescriptionEmpty());

   // cmCellGroupDataElement.addElement( new CMCellElementDescriptionEmpty());//fcastro_13092004

    this.cmGridModel.addRow(cmCellGroupHeaderElement);
    this.cmGridModel.addRow(cmCellGroupDataElement);
 }

 private void createEquivalenceClassView(Element element, EquivalenceClass equivalenceClass, Structure structure) {
	 //if( isFirstPositiveEquivalenceClass(equivalenceClass, element)) {
    if( element.getEquivalenceClasses().indexOf(equivalenceClass) == 0 ) {
			CMCellGroupHeaderEquivalenceClass cmCellGroupHeaderEquivalenceClass = new CMCellGroupHeaderEquivalenceClass(5);
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
			//cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassName(this,equivalenceClass) );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassState(this,equivalenceClass) );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassValue(this,equivalenceClass) );
            cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassDescription(this,equivalenceClass) );//fcastro_13092004
			this.cmGridModel.addRow(cmCellGroupHeaderEquivalenceClass);
    }
		CMCellGroupDataEquivalenceClass cmCellGroupDataEquivalenceClass = new CMCellGroupDataEquivalenceClass(5);
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
		//cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassName(this,equivalenceClass) );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassState(this,equivalenceClass) );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassValue(this,equivalenceClass) );
        cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassDescription(this,equivalenceClass) );//fcastro_13092004
		this.cmGridModel.addRow(cmCellGroupDataEquivalenceClass);
 }

  public void deleteElementViews() {
    int numOfRows = this.cmGridModel.getRowCount();
    this.cmGridModel.removeRows(0,numOfRows);
  }






  void eventGridMouseMoved(GridEvent e) {
    int row = e.getRow();
    int column = e.getColumn();
    Object obj = this.cmGridModel.getCellObjectAt(row,column);
    if( obj != null) {
        if( obj instanceof CMCellElementName/* || obj instanceof CMCellElementGuiObject */|| obj instanceof CMCellElementDescription ||
            obj instanceof CMCellEquivalenceClassValue) {

            this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }
				else {
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
    }
  }

  void eventGridMouseClicked(GridEvent e) {
     //fcastro_20092004_begin
     int row = e.getRow();
    int column = e.getColumn();
    if(e.getSourceEvent().getModifiers() == Event.META_MASK) {
        this.clearSelection();
        this.changeSelection(row,column,false,false);
    }
    //fcastro_20092004_end

  }

  public CMFrameView getFrame() { return this.cmFrame; }

  public void thisComponentResized(ComponentEvent e) {
    this.setColumnWidth(2,1*6);
  }

  public void thisPropertyChange(PropertyChangeEvent e) {
    this.setColumnWidth(2,1*6);
  }

  public int getM_Width(){
          return this.m_Width;
      }

  public void setM_Width(int m_Width){
          this.m_Width = m_Width;
      }
protected HashMap getCellClasses() {
	HashMap map = new HashMap();
	map.put(CMCellElementName.class,new JTextField());
	map.put(CMCellElementDescription.class,new JTextField());
	//map.put(CMCellElementGuiObject.class,new CMBaseJComboBox(this));
	map.put(CMCellEquivalenceClassState.class,new CMBaseJComboBox(this));
	map.put(CMCellEquivalenceClassValue.class,new JTextField());
	map.put(CMCellEquivalenceClassDescription.class,new JTextField());


	//map.put()
	return map;
}

 //////////////////////// GridModel ///////////////////////////////////////////
  private  class CMGridModel extends GenericGridModel {
    public CMGridModel(int numRows, int numColumns) {
      super(numRows, numColumns);
    }
    public boolean isCellEditable(int row, int column) {
      Object obj = super.getValueAt(row,column);
      if(obj == null) {
        return false;
      }
      if( obj instanceof CMCellHeaderElementName/* || obj instanceof CMCellHeaderElementGuiObject*/ || obj instanceof CMCellHeaderElementDescription ||
        obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
        obj instanceof CMCellHeaderEquivalenceClassValue || obj instanceof CMCellHeaderDependencyName ||
        obj instanceof CMCellHeaderEquivalenceClassDescription ||//fcastro_13092004
         obj instanceof CMCellEquivalenceClassName || obj instanceof CMCellDependencyName ||
        obj instanceof CMCellEmpty || obj instanceof CMCellHeaderCombination ||
        obj instanceof CMCellHeaderEffectsInCombination || obj instanceof CMCellEffectsInCombination ||
        obj instanceof CMCellEquivalenceClassInCombination || obj instanceof CMCellHeaderTestCase){
    	  //|| obj instanceof CMCellEquivalenceClassState) {
        return false;
      }
      else {
        return true;
      }
    }
    public Object getCellObjectAt(int row, int column) {
      return super.getValueAt(row,column);
    }

  }

  //////////////////////// SpanModel ///////////////////////////////////////////
  private  class CMSpanModel extends AbstractDirectSpanModel {
    CMElementViews.CMGridModel model = null;
    public CMSpanModel(CMElementViews.CMGridModel m) {
      this.model = m;
    }
    public ExtentCell getSpanOver(final int row, final int column) {
    	Object obj = null;
    	try{
    		obj = this.model.getCellObjectAt(row,column);
    	}catch (Exception e) {
    		Logger.getLogger(this.getClass()).error("Cannot Calculate span for "+ row +" and "+ column);
		}
        if( obj == null) {
          return null;
        }
        if ( obj instanceof CMCellHeaderElementDescription || obj instanceof CMCellElementDescription ||
             obj instanceof CMCellHeaderElementDescriptionEmpty || obj instanceof CMCellElementDescriptionEmpty) {
						return new ExtentCell() {
							public int getRow() {
								return row;
							}
							public int getRowCount() {
								return 1;
							}
							public int getColumn() {
								return 1;
							}
							public int getColumnCount() {
								//return 3;//OLD fcastro_13092004
                                return 4;//fcastro_13092004
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
  //////////////////////////////////////////////////////////////////////////////

  private class CMStyleModel extends DefaultStyleModel {
    public CMStyleModel() {
      this.setEditor(CMCellSelectEquivalenceClass.class,CMCellSelectEquivalenceClass.defaultEditor);
      this.setRenderer(CMCellEquivalenceClass.class,CMCellEquivalenceClass.defaultGridHeaderRenderer);
      this.setRenderer(CMCellDependency.class,CMCellDependency.defaultGridHeaderRenderer);
    }

  }

  //////////////////////////////////////////////////////////////////////////////

/**
 * @return Returns the cmGridModel.
 */
public CMGridModel getCmGridModel() {
    return this.cmGridModel;
}
/**
 * @param cmGridModel The cmGridModel to set.
 */
public void setCmGridModel(CMGridModel cmGridModel) {
    this.cmGridModel = cmGridModel;
}


public Component getRightSide() {
	return this.rightSide;
}

public CMTestCaseViews getM_CMTestCaseViews() {
	return this.m_CMTestCaseViews;
}

public void fireGridModelChanged() {
	this.cmGridModel.fireGridModelChanged();
}

public void handleCMModelChange(CMModelEvent evt) {
	// TODO Auto-generated method stub
	if (evt.getSource() instanceof Structure)
	{
		if (evt.getChangedField()== CMField.ELEMENTS) {
			this.removeModelListener(getM_Structure());
			this.addModelListener(getM_Structure());
			update();
		}
	}
	if (evt.getSource() instanceof Element)
	{
		if (evt.getChangedField() == CMField.EQUIVALENCE_CLASSES)
		{
			update();
		}
	}

}
}
