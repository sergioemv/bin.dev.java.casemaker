/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.stdcombinationviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;

import model.BusinessRules;
import model.CMField;
import model.Combination;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import model.util.CMStateBean;
import model.util.CMUserOrderBean;
import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellCombination;
import bi.view.cells.CMCellDependency;
import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.CMCellElement;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementDescriptionEmpty;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEmpty;
import bi.view.cells.CMCellEquivalenceClass;
import bi.view.cells.CMCellEquivalenceClassInStdCombination;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellSelectAllEquivalenceClassesOfElement;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.cells.CMCellStdCombination;
import bi.view.cells.headers.CMCellHeaderDependencyName;
import bi.view.cells.headers.CMCellHeaderEffectsInCombination;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementDescriptionEmpty;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.cells.headers.CMCellHeaderStdCombination;
import bi.view.cells.renderers.CMImageEquivalenceClassinStdCombinationRenderer;
import bi.view.cells.renderers.CMStdCombinationCellRenderer;
import bi.view.elementviews.CMElementViews;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;

import com.eliad.model.AbstractDirectSpanModel;
import com.eliad.model.ExtentCell;
import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.GridSelectionEvent;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

public class CMStdCombinationViews extends CMBaseJSmartGrid implements CMModelListener {
    CMSpanModel spanModel = null;
    CMStyleModel styleModel = null;
    CMFrameView cmFrame = null;
    JPanel contentPane = null;
    Structure m_Structure = null;
    StructureManager structureManager = null;
    CMGridModel cmGridModel = null;
    CombinationManager m_CombinationManager = null;
    TestCaseManager m_TestCaseManager = null;
    StdCombination selectedStdCombination = null;
    Object editingObject = null;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed
     * @label uses
     */
    private CMElementViews lnkCMElementViews;
    private CMElementAndStdCombinationViews m_CMElementAndStdCombinationViews;
    private boolean selectionMessageReceived = false;

    private CMStdCombinationCellRenderer cmColorRenderer = new CMStdCombinationCellRenderer();
    private CMImageEquivalenceClassinStdCombinationRenderer cmImageRenderer = new CMImageEquivalenceClassinStdCombinationRenderer();

    public CMStdCombinationViews(CMFrameView frame, CMElementViews p_CMElementViews,
        CMElementAndStdCombinationViews p_CMElementAndStdCombinationViews) {
            this.lnkCMElementViews = p_CMElementViews;
            this.m_CMElementAndStdCombinationViews = p_CMElementAndStdCombinationViews;
            cmFrame = frame;
            frame.getKeyEventIntercepter().setM_CMStdCombinationViews(this);//fcastro_13092004
            initGUI();
            this.setGridNavigationPolicy(new CMStdCombinationGridNavPolicy(this));
    }

    public void initGUI() {
        structureManager = cmFrame.getCmApplication().getSessionManager().getStructureManager();
        m_CombinationManager = cmFrame.getCmApplication().getSessionManager().getCombinationManager();
        m_TestCaseManager = cmFrame.getCmApplication().getSessionManager().getTestCaseManager();
        contentPane = (JPanel)cmFrame.getContentPane();
        cmGridModel = new CMGridModel(0, 0);
        this.setModel(cmGridModel);
        spanModel = new CMSpanModel(cmGridModel);
        this.setSpanModel(spanModel);
        styleModel = new CMStyleModel(cmGridModel);
		styleModel.setRenderer(CMCellHeaderStdCombination.class,cmColorRenderer);
		styleModel.setRenderer(CMCellEmpty.class,cmColorRenderer);
		styleModel.setEditor(CMCellEquivalenceClassInStdCombination.class, CMCellEquivalenceClassInStdCombination.defaultEditor);
		styleModel.setRenderer(CMCellEquivalenceClassInStdCombination.class, cmImageRenderer);

        this.setStyleModel(styleModel);
        ////////////////// Style ///////////////////////////////////////////////////////
        this.setOpaque(false);
        this.setColumnResizable(true);
        this.setAutoResizeMode(RulerConstants.HORIZONTAL);
        this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setSelectionBackgroundColor(SystemColor.activeCaptionText);
        this.setSelectionForegroundColor(Color.black);
        this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_CELL);
        this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
        this.setGridColor(new Color(196, 194, 196));
        ///////////////////////////////////////////////////////////////////////////////
        this.setAlignmentY((float)0.5);
        this.setAlignmentX((float)0.5);
        this.addFocusListener(new FocusAdapter()
        		{
        	public void focusGained(FocusEvent e) {
        		super.focusGained(e);
        		getLnkCMElementViews().repaint();
        	}
        		});
        this.addGridListener(
            new com.eliad.swing.GridAdapter() {
                public void gridMouseMoved(GridEvent e) {
                    eventGridMouseMoved(e);
                }
                public void gridMouseClicked(GridEvent e) {
                    //m_CMElementAndStdCombinationViews.getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().selectionChangedInGridView(e.getColumn());//fcastro_25082004
                    //updateCommandos(e); //fcastro_24082004//commented fcastro_02092004
                    eventGridMouseClicked(e);
                }
                //fcastro_02092004_begin
                public void gridMouseReleased(GridEvent e) {
                    eventGridMouseReleased(e);
                }
                //fcastro_02092004_end
            });
        
    }

public void changeSelection(int arg0, int arg1, boolean arg2, boolean arg3) {
	super.changeSelection(arg0, arg1, arg2, arg3);
	this.getM_CMElementAndStdCombinationViews().getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().repaint();
	this.getLnkCMElementViews().repaint();
	this.repaint();

}
     public boolean isStdCombinationHeaderView(int row, int column){
        Object obj = cmGridModel.getCellObjectAt(row,column);
        if(obj !=null && (obj instanceof CMCellHeaderStdCombination)){
            return true;
        }
        return false;

    }
    //fcastro_20092004_end


    public JViewport getViewport1() {
        return getViewport(); // getViewport is not public in JSmartGrid
    }
    //fcastro_13092004_begin
    public boolean isDeletePossible(){
        int row = this.getSelectionModel().getLeadRow();
        int column = this.getSelectionModel().getLeadColumn();
        Object obj = this.cmGridModel.getCellObjectAt(row,column);
        if(obj!=null){
        if(obj instanceof CMCellHeaderStdCombination){
            return true;
        }
        }
        return false;
    }
    //fcastro_13092004_end

    public void setM_Structure(Structure p_Structure) {

    	if (p_Structure != null)
    		  removeModelListener(p_Structure);

      	this.m_Structure = p_Structure;
    	addModelListener(m_Structure);
        this.lnkCMElementViews.update();

        this.deleteAllStdCombinationViews();
        this.addStdCombinationViews(p_Structure);
        updateCommandos(null); //fcastro_24082004
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
        return m_Structure;
    }

    public void setWidthOfLastStdCombinationView() {
        int numOfColumns = this.getColumnCount();
        int index = numOfColumns - 1;
        this.setWidthOfStdCombinationViewAt(index);
    }

    public void setWidthOfStdCombinationViewAt(int p_index) {
        this.setColumnWidth(p_index, 10 * 6);
    }

    void deleteStdCombinationView(int index, int count) {
        cmGridModel.removeColumns(index, count);
    }

    void deleteAllStdCombinationViews() {
        int numOfColumns = cmGridModel.getColumnCount();
        int numOfStdCombinationViews = numOfColumns;
        deleteStdCombinationView(0, numOfStdCombinationViews);
    }

  
    void insertStdCombinationView(int i, Vector stdCombinationView) {
        cmGridModel.insertColumn(i, stdCombinationView);
        if( i >= 0) {
            changeSelection(0,i, false, false);
        }
    }

    void addStdCombinationView(Vector stdCombinationView) {
        cmGridModel.addColumn(stdCombinationView);
        int lastColumn = this.getColumnCount() - 1;
        if( lastColumn >= 0) {
          changeSelection(0, lastColumn, false, false);
        }

    }

    int getNumberOfRowsInElementStdCombinationViews() {
        return this.getLnkCMElementViews().getRowCount();
    }

    void addStdCombinationViews(Structure p_Structure) {
        int numOfStdCombinationViews = p_Structure.getCombinations().size();
        cmGridModel.setRowCount(getNumberOfRowsInElementStdCombinationViews());
        for (int i = 0; i < numOfStdCombinationViews; i++) {
            StdCombination stdCombination = (StdCombination)p_Structure.getCombinations().get(i);
            Vector stdCombinationView = createStdCombinationView(stdCombination, p_Structure.getElements(CMUserOrderBean.COMPARATOR));
            addStdCombinationView(stdCombinationView);
            this.setWidthOfLastStdCombinationView();
        }
        //this.resizeAndRepaint();
        updateCommandos(null); //fcastro_24082004
    }

    public void update() {
        deleteAllStdCombinationViews();
        addStdCombinationViews(m_Structure);
        }

    Vector createStdCombinationView(StdCombination stdCombination, List<Element> stdCombinationElements) {
        Vector stdCombinationView = new Vector(0);
        int numOfStdCombinationElements = stdCombinationElements.size();
        for (int i = 0; i < numOfStdCombinationElements; i++) {
        	Element element = (Element)stdCombinationElements.get(i);
        	if (element.getEquivalenceClasses().size()==0)
        		continue;
            stdCombinationView.addElement(new CMCellHeaderStdCombination(stdCombination));
            stdCombinationView.addElement(new CMCellEmpty());
            stdCombinationView.addElement(new CMCellEmpty());

            int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
            for (int j = 0; j < numOfEquivalenceClasses; j++) {
                EquivalenceClass equivalenceClass = (EquivalenceClass)element.getEquivalenceClasses().get(j);
                stdCombinationView.addElement(new CMCellEquivalenceClassInStdCombination(equivalenceClass, stdCombination));
            }
        }
        return stdCombinationView;
    }

    public void save() {
        //structureManager.saveStructure(m_Structure);
    }

    public StdCombination getSelectedStdCombination() {
        int selectedColumn = this.getSelectionModel().getLeadColumn();
        int selectedRow = this.getSelectionModel().getLeadRow();
        if (selectedRow >= 0 && selectedColumn >= 0) {
            Object obj = cmGridModel.getCellObjectAt(selectedRow, selectedColumn);
            if (obj != null) {
                if (obj instanceof CMCellHeaderStdCombination || obj instanceof CMCellEquivalenceClassInStdCombination) {
                    StdCombination s = ((CMCellStdCombination)obj).getStdCombination();
                    return s;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    //fcastro_24082004_begin
    public void insertStdCombinationAt(int p_Index, StdCombination p_StdCombination) {
        int numOfStdCombinations = m_Structure.getCombinations().size();
        Vector stdCombinationView = createStdCombinationView(p_StdCombination, m_Structure.getElements(CMUserOrderBean.COMPARATOR));
        if (p_Index >= 0 && p_Index < numOfStdCombinations) {
            m_Structure.addCombination(p_StdCombination);
            cmGridModel.insertColumn(p_Index, stdCombinationView);
            this.setWidthOfStdCombinationViewAt(p_Index);
            m_CMElementAndStdCombinationViews.getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().fireEventStdCombinationInserted(p_StdCombination,
                p_Index);
        }
        else {
            m_Structure.addCombination(p_StdCombination);
            addStdCombinationView(stdCombinationView);
            this.setWidthOfLastStdCombinationView();
            m_CMElementAndStdCombinationViews.getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().fireEventStdCombinationAdded(p_StdCombination);
        }
        if (p_Index >= 0) {
            this.changeSelection(0, p_Index, false, false);
        }
        updateCommandos(null); //fcastro_24082004
    }
//fcastro_02092004_begin
    public void selectNextExistingStdCombination(int p_SelectedColumn) {
        int nextStdCombination = p_SelectedColumn/* + 1*/;
        if (this.getColumnCount() > nextStdCombination) {
            //selectSuccesorCombination(p_SelectedColumn);
            this.changeSelection(0,p_SelectedColumn,false,false);

        }
        else {
            selectPredecessorCombination(p_SelectedColumn);
        }
    }

    void selectSuccesorCombination(int p_SelectedColumn) {
        this.changeSelection(0, p_SelectedColumn + 1, false, false);
       }

    void selectPredecessorCombination(int p_SelectedColumn) {
        this.changeSelection(0, p_SelectedColumn - 1, false, false);
    }
    //fcastro_02092004_end

 public   EquivalenceClass getSelectedEquivalenceClass() {
        int row = this.getSelectionModel().getLeadRow();
        int column = this.getSelectionModel().getLeadColumn();
        EquivalenceClass equivalenceClass = null;
        if (row >= 0 && column >= 0) {
            Object obj = this.cmGridModel.getCellObjectAt(row, column);
            if (obj != null) {
                if (obj instanceof CMCellEquivalenceClassInStdCombination) {
                    equivalenceClass = ((CMCellEquivalenceClassInStdCombination)obj).getEquivalenceClass();
                    return equivalenceClass;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

   

    
    void eventGridMouseMoved(GridEvent e) {
        int row = e.getRow();
        int column = e.getColumn();
        Object obj = cmGridModel.getCellObjectAt(row, column);
        if (obj != null) {
            if (obj instanceof CMCellElementName /*|| obj instanceof CMCellElementGuiObject*/ || obj instanceof CMCellElementDescription ||
                obj instanceof CMCellEquivalenceClassValue) {
                    this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            }
            else {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    void eventGridMouseReleased(GridEvent e) {
        updateCommandos(e);
        changeSelection(e.getRow(), e.getColumn(), false, false); //for right clicks
    }

    void eventGridMouseClicked(GridEvent e) {
    	repaint();
        MouseEvent mouseEvent = (MouseEvent)e.getSourceEvent();
        int row = e.getRow();
        int column = e.getColumn();
        Object obj = null;
        obj = cmGridModel.getCellObjectAt(row, column);
        if (mouseEvent.getClickCount() == 2) {
            if (obj != null) {
                //fcastro_02092004_begin
                if(obj instanceof CMCellHeaderStdCombination){
                    selectedStdCombination = ((CMCellStdCombination)obj).getStdCombination();
					CMAction.STDCOMBINATION_EDIT.getAction().actionPerformed(null);
                }else
                    //fcastro_02092004_end
                if (obj instanceof CMCellEquivalenceClassInStdCombination) {
                    selectedStdCombination = ((CMCellStdCombination)obj).getStdCombination();
                    EquivalenceClass selectedEquivalenceClass = this.getSelectedEquivalenceClass();
                    if (selectedStdCombination.contains(selectedEquivalenceClass)) {
                        CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction().actionPerformed(null);
                    }
                    else {
                    	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.getAction().actionPerformed(null);
                    }
                }
            }
        }
        else if (e.getSourceEvent().getModifiers() == Event.META_MASK) {
            if (row >= 0 && column >= 0) {
                this.clearSelection();
                this.changeSelection(row, column, false, false);
                obj = cmGridModel.getCellObjectAt(row, column);
                if (obj instanceof CMCellHeaderStdCombination || obj instanceof CMCellEquivalenceClassInStdCombination) {
                    mouseEvent = (MouseEvent)e.getSourceEvent();
                    this.cmFrame.getJPopupMenuStdCombinations().show(this, mouseEvent.getX(), mouseEvent.getY());
                    //cmPopupMenuStdCombinations.show(this, mouseEvent.getX(), mouseEvent.getY());
                    selectedStdCombination = ((CMCellStdCombination)obj).getStdCombination();
                    this.revalidate();
                }
            }
        }
    }

    public CMFrameView getFrame() { return cmFrame; }

    public CMElementViews getLnkCMElementViews() {
        return lnkCMElementViews;
    }

    public void setLnkCMElementViews(CMElementViews lnkCMElementViews) {
        this.lnkCMElementViews = lnkCMElementViews;
    }

    public CMElementAndStdCombinationViews getM_CMElementAndStdCombinationViews() {
        return m_CMElementAndStdCombinationViews;
    }

    public void setM_CMElementAndStdCombinationViews(CMElementAndStdCombinationViews m_CMElementAndStdCombinationViews) {
        this.m_CMElementAndStdCombinationViews = m_CMElementAndStdCombinationViews;
    }

    //fcastro_24082004_begin
    public void updateCommandos(GridEvent e) {
        int row = -1;
        int column = -1;
        if (m_Structure.getCombinations().size() > 0) {
        	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
        	CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.setEnabled(false);
        	CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
        	 if (e != null) {
                row = e.getRow();
                column = e.getColumn();
            }
            else {
                row = this.getSelectionModel().getLeadRow();
                column = this.getSelectionModel().getLeadColumn();
            }
            if (row >= 0 && column >= 0) {
                Object obj = cmGridModel.getCellObjectAt(row, column);
                if (obj != null) {
                    if (obj instanceof CMCellHeaderStdCombination) {
                    	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
                    	CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);

                    }
                    else if (obj instanceof CMCellEmpty) {
                    	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
                    	CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.setEnabled(false);
                    	CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
                    	}
                }
            }
            m_CMElementAndStdCombinationViews.getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().setSelectionMessageReceived(true);
            m_CMElementAndStdCombinationViews.getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().selectionChangedInGridView(column);
            ////////////////////////////////////////////////////////////
        }
        else {
        	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
        	CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.setEnabled(false);
        	CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
        }
    }

    //fcastro_24082004_end
    //fcastro_25082004_begin
    public void selectionChangedInDescriptionView(int selectedRow) {
        if (m_Structure != null) {
            if (selectedRow >= 0 && selectedRow < this.m_Structure.getCombinations().size()) {
                if (selectedRow != this.getSelectionModel().getLeadColumn()) {
                    this.changeSelection(0, selectedRow, false, false);
                }
                //updateCommandos(null);
            }
        }
    }

    //fcastro_25082004_end
    //fcastro_02092004_begin
    public void rangeChanged(GridSelectionEvent e) {
        super.rangeChanged(e);
        if (!e.isAdjusting()) {
            int row = this.getFirstSelectedRow();
            int column = this.getFirstSelectedColumn();
            if (this.isSelectionMessageReceived()) {
                this.setSelectionMessageReceived(false);
            }
            else {
                m_CMElementAndStdCombinationViews.getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().setSelectionMessageReceived(true);
                m_CMElementAndStdCombinationViews.getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().selectionChangedInGridView(column);
            }
            if(row>=0 && column>=0){//fcastro_19102004
            Object obj = cmGridModel.getCellObjectAt(row, column);
            if (obj != null) {
                if (obj instanceof CMCellHeaderStdCombination) {
                	CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.setEnabled(true);
                	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
                	CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);

                }
                else if(obj instanceof CMCellEmpty){
                	CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.setEnabled(false);
                	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
                	CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);


                }
                else if(obj instanceof CMCellEquivalenceClassInStdCombination){
                	CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.setEnabled(true);
                	CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
                	CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
                }
            }
            }//fcastro_19102004
        }
    }

    public boolean isSelectionMessageReceived() {
        return selectionMessageReceived;
    }

    public void setSelectionMessageReceived(boolean selectionMessageReceived) {
        this.selectionMessageReceived = selectionMessageReceived;
    }

    //fcastro_02092004_end
    //////////////////////// GridModel ///////////////////////////////////////////
    public class CMGridModel extends GenericGridModel {
        public CMGridModel(int numRows, int numColumns) {
            super(numRows, numColumns);
        }

        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Object getCellObjectAt(int row, int column) {
            if (this.getRowCount() > row && this.getColumnCount() > column) {
                return super.getValueAt(row, column);
            }
            else {
                return null;
            }
        }



          }


    //////////////////////// SpanModel ///////////////////////////////////////////
    public class CMSpanModel extends AbstractDirectSpanModel {
        CMStdCombinationViews.CMGridModel model = null;

        public CMSpanModel(CMStdCombinationViews.CMGridModel m) {
            model = m;
        }

        public ExtentCell getSpanOver(final int row, final int column) {
            Object obj = model.getCellObjectAt(row, column);
            if (obj == null) {
                return null;
            }
            if (obj instanceof CMCellHeaderElementDescription || obj instanceof CMCellElementDescription ||
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
                            return 3;
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
    class CMStyleModel extends DefaultStyleModel {
        public CMStyleModel(CMStdCombinationViews.CMGridModel model) {
            this.setRenderer(String.class, new CMCellRenderer(model));
        }

        /////////////////////////////////////////////////////////////////////////////
        public class CMCellRenderer extends DefaultGridCellRenderer {
            CMStdCombinationViews.CMGridModel model = null;

            public CMCellRenderer(CMStdCombinationViews.CMGridModel model) {
                this.model = model;
            }

            public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row,
                int column, GridContext context) {
                    Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
                    Object obj = model.getCellObjectAt(row, column);
                    if (obj instanceof CMCellHeaderStdCombination) {
                        c.setBackground(new Color(36, 38, 116));
                        c.setForeground(new Color(252, 254, 252));
                        this.setHorizontalAlignment(JLabel.LEFT);
                        c.setFont(new Font("Dialog", Font.PLAIN, 12)); //$NON-NLS-1$
                        if (!isSelected) {
                            this.setBorder(BorderFactory.createRaisedBevelBorder());
                        }
                    }
                    else if (obj instanceof CMCellEquivalenceClassInStdCombination) {
                        c.setBackground(new Color(235, 235, 228)); //ReadOnly
                    }
                    return c;
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////


	public CMGridModel getCmGridModel() {
		return cmGridModel;
	}

	public void setCmGridModel(CMGridModel cmGridModel) {
		this.cmGridModel = cmGridModel;
	}

	public void handleCMModelChange(CMModelEvent evt) {
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
		
		if (evt.getSource() instanceof Structure)
		{
			if (evt.getChangedField() == CMField.STDCOMBINATIONS)
			{
				update();
			}
		}

	}

	@Override
	protected HashMap<Class, Component> getCellClasses() {
		// TODO Auto-generated method stub
		return null;
	}
}
