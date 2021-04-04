/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.causeeffectviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JViewport;

import model.CMField;
import model.Effect;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.view.actions.CMAction;
import bi.view.cells.CMCauseEffectDescriptionView;
import bi.view.cells.CMCauseEffectNameView;
import bi.view.cells.CMCauseEffectRiskLevelView;
import bi.view.cells.CMCauseEffectStateView;
import bi.view.cells.CMCauseEffectUsedView;
import bi.view.cells.CMCauseEffectView;
import bi.view.cells.CMCellCauseEffect;
import bi.view.grids.CMBaseJSmartGrid;

import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

//HCanedo_30112004_End
public class CMCauseEffectStructureGridView extends CMBaseJSmartGrid implements CMModelListener {
    public CMCauseEffectStructureGridView(CMCauseEffectStructureView p_CMCauseEffectStructureView) {
        super();
        m_CMCauseEffectStructureView = p_CMCauseEffectStructureView;
        m_CMCauseEffectStructureView.getM_CMFrame().getKeyEventIntercepter().setM_CMCauseEffectStructureGridView(this); //fcastro_13092004

        initGUI();
        //connectToController();
    }

    public JViewport getViewport1() {
        return getViewport(); // getViewport is not public in JSmartGrid
    }

    public void setM_Structure(Structure p_Structure) {
        //remove itself as a listener from the old structure
        if (m_Structure != null)
            m_Structure.removeModelListener(this);

        m_Structure = p_Structure;
        m_Structure.addModelListener(this);
        m_CMGridModel.removeRows(0, this.getRowCount());
        addCMCauseEffectViews(m_Structure.getEffects());
        selectCMCauseEffectView(0);
        updateCommandos(); //fcastro_23082004
    }

    public Structure getM_Structure() {
        return m_Structure;
    }

    //fcastro_13092004_begin
    public boolean isDeletePossible() {
        int row = this.getSelectionModel().getLeadRow();
        int column = this.getSelectionModel().getLeadColumn();
        Object obj = this.m_CMGridModel.getCellObjectAt(row, column);
        if (obj instanceof CMCauseEffectNameView || obj instanceof CMCauseEffectDescriptionView) {
            return true;
        }
        return false;
    }

    //fcastro_13092004_end
    public void initGUI() {
        // m_CMGridModel = new CMGridModel(0,2); //fcastro_19102004
        m_CMGridModel = new CMGridModel(0, 5); //fcastro_19102004
        m_CMStyleModel = new CMStyleModel();
        setModels();
        setUIProperties();
        addEventListeners();
    }

    public void setModels() {
        this.setStyleModel(m_CMStyleModel);
        this.setModel(m_CMGridModel);
    }

    public void setUIProperties() {
        this.setOpaque(false);
        this.setColumnResizable(true);
        this.setAutoResizeMode(RulerConstants.HORIZONTAL);
        this.setGridColor(new Color(127, 157, 185)); // Read Only Grid Color
        this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setSelectionBackgroundColor(Color.orange);
        this.setSelectionForegroundColor(Color.black);
        this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_ROW);
        this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
        ////////////////////////////////////////////////////////
    }

    public void addEventListeners() {
        this.addGridListener(
            new com.eliad.swing.GridAdapter() {
                public void gridMouseClicked(GridEvent e) {
                    eventGridMouseClicked(e);
                }
            });
    }

    void eventGridMouseClicked(GridEvent e) {
        MouseEvent mouseEvent = (MouseEvent)e.getSourceEvent();
        int row = e.getRow();
        int column = e.getColumn();
        if (mouseEvent.getClickCount() == 2) {
            CMAction.EFFECT_EDIT.getAction().actionPerformed(null);
        }
        else {
            if (e.getSourceEvent().getModifiers() == Event.META_MASK) {
                if (row >= 0 && column >= 0) {
                    this.changeSelection(row, column, false, false);
                }
                this.m_CMCauseEffectStructureView.getM_CMFrame().getJPopupMenuCauseEffects().show(this, mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }

    public void selectCMCauseEffectView(int p_index) {
        if (p_index >= 0 && this.getRowCount() > 0) {
            this.changeSelection(p_index, 0, false, false);
        }
    }

    public CMCauseEffectView createCMCauseEffectView(Effect p_causeEffect) {

        CMCauseEffectNameView nameView = new CMCauseEffectNameView(this,p_causeEffect);
        CMCauseEffectDescriptionView descriptionView = new CMCauseEffectDescriptionView(this, p_causeEffect);
        CMCauseEffectUsedView usedView = new CMCauseEffectUsedView(this,p_causeEffect);
        CMCauseEffectRiskLevelView riskLevelView=new CMCauseEffectRiskLevelView(this,p_causeEffect);
        CMCauseEffectStateView stateView=new CMCauseEffectStateView(this,p_causeEffect);

        CMCauseEffectView causeEffectView = new CMCauseEffectView(p_causeEffect);
        causeEffectView.addElement(nameView);
        causeEffectView.addElement(descriptionView);
        causeEffectView.addElement(stateView);
        causeEffectView.addElement(riskLevelView);
        causeEffectView.addElement(usedView);

        return causeEffectView;
    }

    public Vector createCMCauseEffectViews(Vector p_effects) {
        int numOfCauseEffects = p_effects.size();
        Vector v = new Vector(numOfCauseEffects);
        Effect effect = null;
        for (int i = 0; i < numOfCauseEffects; i++) {
            effect = (Effect)p_effects.elementAt(i);
            v.addElement(createCMCauseEffectView(effect));
        }
        return v;
    }

    public void addCMCauseEffectView(CMCauseEffectView p_CMCauseEffectView) {
        this.m_CMGridModel.addRow(p_CMCauseEffectView);
        int newSelectionIndex = this.getRowCount() - 1;
        selectCMCauseEffectView(newSelectionIndex);
    }

    public void addCMCauseEffectViewAt(int p_Index, CMCauseEffectView p_CMCauseEffectView) {
        int newSelectionIndex = 0;
        if (p_Index >= getRowCount()) {
            this.m_CMGridModel.addRow(p_CMCauseEffectView);
            newSelectionIndex = this.getRowCount() - 1;
        }
        else {
            this.m_CMGridModel.insertRow(p_Index, p_CMCauseEffectView);
            newSelectionIndex = p_Index;
        }
        selectCMCauseEffectView(newSelectionIndex);
    }

    public void addCMCauseEffectViews(List p_effects) {
        int numOfEffects = p_effects.size();
        for (int i = 0; i < numOfEffects; i++) {
            Effect effect = (Effect)p_effects.get(i);
            addCMCauseEffectView(createCMCauseEffectView(effect));
        }
    }
    void deleteAllViews() {
        int numOfRows = m_CMGridModel.getRowCount();
        int numOfEffectViews = numOfRows;
        deleteCMCauseEffectView(0, numOfEffectViews);
    }

    void deleteCMCauseEffectView(int index, int count) {
        m_CMGridModel.removeRows(index, count);
    }

    //HCanedo_30112004_End
    public int getTheNextCMCauseEffectView(int p_index) {
        int next = 0;
        if (p_index > 0) {
            next = p_index - 1;
            return next;
        }
        else if (p_index == 0 && m_CMGridModel.getRowCount() > 0) {
            next = 0;
            return next;
        }
        else {
            return -1;
        }
    }

    public void fireEventCauseEffectAdded(Effect p_Effect) {
        CMCauseEffectView v = createCMCauseEffectView(p_Effect);
        this.addCMCauseEffectView(v);
    }

    public int getSelectedCMCauseEffectView() {
        int selectedIndex = 0;
        int[] selectedRows = this.getSelectedRows();
        int numSelectedRows = this.getSelectedRowCount();
        if (numSelectedRows > 0) {
            return selectedRows[0];
        }
        else {
            return -1;
        }
    }

    //fcastro_23082004_begin
    public void updateCommandos() {
        if (m_Structure.getEffects().size() > 0) {
            m_CMCauseEffectStructureView.getM_CMFrame().statesMenusCutCopy(true);
        }
        else {
            m_CMCauseEffectStructureView.getM_CMFrame().statesMenusCutCopy(false);
        }
    }



    //fcastro_19102004_end
    //////////////////////// Grid Model /////////////////////////////////////////
    public class CMGridModel extends GenericGridModel {
        public CMGridModel(int numRows, int numColumns) {
            super(numRows, numColumns);
        }

        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Object getCellObjectAt(int row, int column) {
            if (this.getRowCount() > row && this.getColumnCount() > column) {
                if (row >= 0 && column >= 0) {
                    return super.getValueAt(row, column);
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }


    }

    public class CMStyleModel extends DefaultStyleModel {
        public CMStyleModel() {
            this.setRenderer(CMCellCauseEffect.class, new CMCellRendererDependencies());
        }

        public class CMCellRendererDependencies extends DefaultGridCellRenderer {
            public CMCellRendererDependencies() {
            }

            public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row,
                int column, GridContext context) {
                    Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
                    //fcastro_19102004_begin
                    Object obj = m_CMGridModel.getCellObjectAt(row, column);
                    //HCAnedo_14032006_begin
                    if (obj instanceof CMCauseEffectUsedView || obj instanceof CMCauseEffectRiskLevelView || obj instanceof CMCauseEffectStateView) {
                    //Hcanedo_14032006_end
                        this.setHorizontalAlignment(JLabel.CENTER);
                    }
                    else {
                        this.setHorizontalAlignment(JLabel.LEFT);
                    }
                    if (((CMCellCauseEffect)obj).getEffect().isUsed())
                    	this.setFont(new Font("Dialog",Font.BOLD,12));
                    //fcastro_19102004_end
                    if (!isSelected) {
                        this.setBackground(new Color(235, 235, 228));
                    }
                    return c;
            }
        }
    }


    public CMGridModel getM_CMGridModel() { return m_CMGridModel; }

    public void setM_CMGridModel(CMGridModel m_CMGridModel) { this.m_CMGridModel = m_CMGridModel; }

    public CMStyleModel getM_CMStyleModel() { return m_CMStyleModel; }

    public void setM_CMStyleModel(CMStyleModel m_CMStyleModel) { this.m_CMStyleModel = m_CMStyleModel; }

    public CMCauseEffectStructureView getM_CMCauseEffectStructureView() {
        return m_CMCauseEffectStructureView;
    }

    public void setM_CMCauseEffectStructureView(CMCauseEffectStructureView m_CMCauseEffectStructureView) {
        this.m_CMCauseEffectStructureView = m_CMCauseEffectStructureView;
    }

    private CMGridModel m_CMGridModel;
    private CMStyleModel m_CMStyleModel;

    /** @directed */
    private CMCauseEffectStructureView m_CMCauseEffectStructureView;
    private Structure m_Structure;
    private JCheckBox checkBox; //fcastro_19102004

    /**
     * @param p_effect
     */
    public void addCauseEffectView(Effect p_effect) {
        // TODO Auto-generated method stub
         CMCauseEffectView causeEffectView = createCMCauseEffectView(p_effect);
         addCMCauseEffectView(causeEffectView);
    }

    /* (non-Javadoc)
     * @see model.util.CMModelListener#handleCMModelChange(model.util.CMModelEvent)
     */
    public void handleCMModelChange(CMModelEvent p_evt) {
        if (p_evt.getSource() instanceof Structure)
            if (p_evt.getChangedField() == CMField.EFFECTS)
            {
                    m_CMGridModel.removeRows(0, this.getRowCount());
                    addCMCauseEffectViews(m_Structure.getEffects());
                    selectCMCauseEffectView(0);
                    updateCommandos(); //fcastro_23082004
            }

    }
    public Effect getSelectedEffect()
    {

        Object obj = this.getCellObjectAt(this.getSelectionModel().getLastSelectedRow(),0);
        if (obj instanceof CMCellCauseEffect)
            return ((CMCellCauseEffect)obj).getEffect();
        return null;
    }

    /**
     * @param p_oldSelectedEffect
     */
    public void selectCMCauseEffectView(Effect p_SelectedEffect)
    {
        this.selectCMCauseEffectView(this.getM_Structure().getEffects().indexOf(p_SelectedEffect));
    }

    @Override
    protected HashMap<Class, Component> getCellClasses() {
        return null;
    }
}
